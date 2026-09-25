package erp.uniaura.modules.biblioteca.exemplar.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.biblioteca.exemplar.dto.ExemplarRequestDTO;
import erp.uniaura.modules.biblioteca.exemplar.dto.ExemplarResponseDTO;
import erp.uniaura.modules.biblioteca.exemplar.model.Exemplar;
import erp.uniaura.modules.biblioteca.exemplar.model.StatusExemplar;
import erp.uniaura.modules.biblioteca.exemplar.repository.ExemplarRepository;
import erp.uniaura.modules.biblioteca.livro.model.Livro;
import erp.uniaura.modules.biblioteca.livro.service.LivroService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExemplarService {

    private final ExemplarRepository exemplarRepository;
    private final LivroService livroService;

    // --- LISTA OS EXEMPLARES DE UM LIVRO UTILIZANDO PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public Page<ExemplarResponseDTO> listarPorLivro(UUID livroId, Pageable pageable) {
        return exemplarRepository.findByLivroId(livroId, pageable).map(this::toResponse);
    }

    // --- BUSCA UM EXEMPLAR PELO SEU IDENTIFICADOR ---
    @Transactional(readOnly = true)
    public ExemplarResponseDTO buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    // --- BUSCA UM EXEMPLAR PELO SEU CÓDIGO DE BARRAS ---
    @Transactional(readOnly = true)
    public ExemplarResponseDTO buscarPorCodigoBarras(String codigo) {
        return toResponse(exemplarRepository.findByCodigoBarras(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Exemplar", codigo)));
    }

    // --- CRIA UM NOVO EXEMPLAR E GERA UM CÓDIGO DE BARRAS QUANDO ELE NÃO É INFORMADO ---
    @Transactional
    public ExemplarResponseDTO criar(ExemplarRequestDTO dto) {
        Livro livro = livroService.buscarEntidade(dto.getLivroId());

        String codigo = (dto.getCodigoBarras() == null || dto.getCodigoBarras().isBlank())
                ? gerarCodigoBarras()
                : dto.getCodigoBarras().trim();

        if (exemplarRepository.existsByCodigoBarras(codigo)) {
            throw new BusinessException("Código de barras já cadastrado.");
        }

        Exemplar exemplar = Exemplar.builder()
                .livro(livro)
                .codigoBarras(codigo)
                .localizacao(dto.getLocalizacao())
                .status(StatusExemplar.DISPONIVEL)
                .build();

        return toResponse(exemplarRepository.save(exemplar));
    }

    // --- ATUALIZA A LOCALIZAÇÃO E O STATUS ADMINISTRATIVO DE UM EXEMPLAR ---
    @Transactional
    public ExemplarResponseDTO atualizar(UUID id, ExemplarRequestDTO dto, StatusExemplar status) {
        Exemplar ex = buscarEntidade(id);
        ex.setLocalizacao(dto.getLocalizacao());
        if (status != null) {
            if (status == StatusExemplar.EMPRESTADO || status == StatusExemplar.RESERVADO) {
                throw new BusinessException("Status " + status + " só pode ser definido via empréstimo/reserva.");
            }
            ex.setStatus(status);
        }
        return toResponse(exemplarRepository.save(ex));
    }

    // --- REMOVE UM EXEMPLAR CASO ELE NÃO ESTEJA EMPRESTADO OU RESERVADO ---
    @Transactional
    public void deletar(UUID id) {
        Exemplar ex = buscarEntidade(id);
        if (ex.getStatus() == StatusExemplar.EMPRESTADO || ex.getStatus() == StatusExemplar.RESERVADO) {
            throw new BusinessException("Não é possível excluir exemplar emprestado ou reservado.");
        }
        exemplarRepository.delete(ex);
    }

    // --- GERA UM CÓDIGO DE BARRAS ÚNICO PARA UM EXEMPLAR ---
    public String gerarCodigoBarras() {
        String codigo;
        do {
            codigo = "EX" + System.currentTimeMillis() + String.format("%04d", (int) (Math.random() * 10000));
        } while (exemplarRepository.existsByCodigoBarras(codigo));
        return codigo;
    }

    // --- BUSCA UM EXEMPLAR PELO IDENTIFICADOR OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA ENCONTRADO ---
    public Exemplar buscarEntidade(UUID id) {
        return exemplarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exemplar", id));
    }

    // --- CONVERTE A ENTIDADE EXEMPLAR EM UM DTO DE RESPOSTA ---
    public ExemplarResponseDTO toResponse(Exemplar e) {
        return ExemplarResponseDTO.builder()
                .id(e.getId())
                .livroId(e.getLivro().getId())
                .livroTitulo(e.getLivro().getTitulo())
                .codigoBarras(e.getCodigoBarras())
                .localizacao(e.getLocalizacao())
                .status(e.getStatus())
                .criadoEm(e.getCriadoEm())
                .build();
    }
}

