package erp.uniaura.modules.biblioteca.multa.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.biblioteca.multa.dto.MultaResponseDTO;
import erp.uniaura.modules.biblioteca.multa.model.Multa;
import erp.uniaura.modules.biblioteca.multa.model.StatusMulta;
import erp.uniaura.modules.biblioteca.multa.repository.MultaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MultaService {

    private final MultaRepository multaRepository;

    // --- LISTA AS MULTAS DE FORMA PAGINADA DE ACORDO COM O STATUS INFORMADO ---
    @Transactional(readOnly = true)
    public Page<MultaResponseDTO> listarPorStatus(StatusMulta status, Pageable pageable) {
        return multaRepository.findByStatus(status, pageable).map(this::toResponse);
    }

    // --- LISTA AS MULTAS PENDENTES DE UM USUÁRIO ---
    @Transactional(readOnly = true)
    public List<MultaResponseDTO> pendentesDoUsuario(UUID usuarioId) {
        return multaRepository.findByEmprestimoUsuarioIdAndStatus(usuarioId, StatusMulta.PENDENTE)
                .stream().map(this::toResponse).toList();
    }

    // --- BAIXA DE PAGAMENTO ---
    @Transactional
    public MultaResponseDTO pagar(UUID multaId) {
        Multa multa = multaRepository.findById(multaId)
                .orElseThrow(() -> new ResourceNotFoundException("Multa", multaId));
        if (multa.getStatus() != StatusMulta.PENDENTE) {
            throw new BusinessException("Somente multas PENDENTES podem ser pagas.");
        }

        multa.setStatus(StatusMulta.PAGA);
        multa.setPagaEm(LocalDateTime.now());
        return toResponse(multaRepository.save(multa));
    }

    // --- CANCELA UMA MULTA CASO ELA AINDA NÃO TENHA SIDO PAGA ---
    @Transactional
    public MultaResponseDTO cancelar(UUID multaId) {
        Multa multa = multaRepository.findById(multaId)
                .orElseThrow(() -> new ResourceNotFoundException("Multa", multaId));
        if (multa.getStatus() == StatusMulta.PAGA) {
            throw new BusinessException("Não é possível cancelar multa já paga.");
        }

        multa.setStatus(StatusMulta.CANCELADA);
        return toResponse(multaRepository.save(multa));
    }

    // --- CONVERTE A ENTIDADE MULTA EM UM DTO DE RESPOSTA ---
    private MultaResponseDTO toResponse(Multa m) {
        return MultaResponseDTO.builder()
                .id(m.getId())
                .emprestimoId(m.getEmprestimo().getId())
                .usuarioId(m.getEmprestimo().getUsuario().getId())
                .usuarioNome(m.getEmprestimo().getUsuario().getNome())
                .livroTitulo(m.getEmprestimo().getExemplar().getLivro().getTitulo())
                .valor(m.getValor())
                .diasAtraso(m.getDiasAtraso())
                .status(m.getStatus())
                .geradaEm(m.getGeradaEm())
                .pagaEm(m.getPagaEm())
                .build();
    }
}

