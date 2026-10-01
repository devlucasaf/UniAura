package erp.uniaura.modules.biblioteca.reserva.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.biblioteca.exemplar.model.Exemplar;
import erp.uniaura.modules.biblioteca.exemplar.model.StatusExemplar;
import erp.uniaura.modules.biblioteca.exemplar.repository.ExemplarRepository;
import erp.uniaura.modules.biblioteca.livro.model.Livro;
import erp.uniaura.modules.biblioteca.livro.service.LivroService;
import erp.uniaura.modules.biblioteca.reserva.dto.ReservaRequestDTO;
import erp.uniaura.modules.biblioteca.reserva.dto.ReservaResponseDTO;
import erp.uniaura.modules.biblioteca.reserva.model.Reserva;
import erp.uniaura.modules.biblioteca.reserva.model.StatusReserva;
import erp.uniaura.modules.biblioteca.reserva.repository.ReservaRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private static final int PRAZO_RETIRADA_RESERVA_DIAS = 2;

    private final ReservaRepository reservaRepository;
    private final ExemplarRepository exemplarRepository;
    private final LivroService livroService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- CRIA UMA RESERVA PARA O USUÁRIO AUTENTICADO ---
    @Transactional
    public ReservaResponseDTO reservar(ReservaRequestDTO dto) {
        Livro livro = livroService.buscarEntidade(dto.getLivroId());
        Usuario autenticado = usuarioAutenticadoOuFalha();

        // --- SE HÁ EXEMPLAR DISPONÍVEL, NÃO PRECISA RESERVAR ---
        long disponiveis = exemplarRepository.countByLivroIdAndStatus(livro.getId(), StatusExemplar.DISPONIVEL);
        if (disponiveis > 0) {
            throw new BusinessException("Existe(m) exemplar(es) disponível(is) para este livro. Faça o empréstimo diretamente.");
        }

        if (reservaRepository.existsByLivroIdAndUsuarioIdAndStatus(livro.getId(), autenticado.getId(), StatusReserva.AGUARDANDO)) {
            throw new BusinessException("Você já possui uma reserva ativa para este livro.");
        }

        int posicao = (int) reservaRepository.countByLivroIdAndStatus(livro.getId(), StatusReserva.AGUARDANDO) + 1;

        Reserva reserva = Reserva.builder()
                .livro(livro)
                .usuario(autenticado)
                .status(StatusReserva.AGUARDANDO)
                .posicaoFila(posicao)
                .build();
        return toResponse(reservaRepository.save(reserva));
    }

    // --- CANCELA UMA RESERVA E REORDENA A FILA ---
    @Transactional
    public void cancelar(Long id) {
        Reserva r = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva", id));

        Usuario autenticado = usuarioAutenticadoOuFalha();
        if (!r.getUsuario().getId().equals(autenticado.getId())
                && autenticado.getRole().name().equals("ALUNO")) {
            throw new BusinessException("Você não pode cancelar reservas de outro usuário.");
        }

        r.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(r);
        reordenarFila(r.getLivro().getId());
    }

    // --- LISTA A FILA DE RESERVAS ATIVAS DE UM LIVRO ORDENADA PELA POSIÇÃO ---
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> filaDoLivro(Long livroId) {
        return reservaRepository
                .findByLivroIdAndStatusOrderByPosicaoFilaAsc(livroId, StatusReserva.AGUARDANDO)
                .stream().map(this::toResponse).toList();
    }

    // --- LISTA AS RESERVAS DE UM USUÁRIO ORDENADAS PELA DATA MAIS RECENTE ---
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> reservasDoUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioIdOrderByDataReservaDesc(usuarioId)
                .stream().map(this::toResponse).toList();
    }

    // --- EXPIRA RESERVAS CUJO PRAZO DE RETIRADA DO EXEMPLAR JÁ PASSOU, LIBERANDO-O PARA O PRÓXIMO DA FILA ---
    @Transactional
    public int expirarReservasNaoRetiradas() {
        List<Reserva> expiradas = reservaRepository
                .findByStatusAndPrazoRetiradaBefore(StatusReserva.AGUARDANDO, LocalDateTime.now());

        for (Reserva reserva : expiradas) {
            reserva.setStatus(StatusReserva.EXPIRADA);
            reservaRepository.save(reserva);
            reordenarFila(reserva.getLivro().getId());
            transferirOuLiberarExemplar(reserva.getLivro().getId());
        }
        return expiradas.size();
    }

    // --- PASSA A RESERVA DO EXEMPLAR PARA O PRÓXIMO DA FILA OU O TORNA DISPONÍVEL SE A FILA ESTIVER VAZIA ---
    private void transferirOuLiberarExemplar(Long livroId) {
        Optional<Exemplar> exemplarReservado = exemplarRepository
                .findByLivroIdAndStatus(livroId, StatusExemplar.RESERVADO)
                .stream().findFirst();
        if (exemplarReservado.isEmpty()) {
            return;
        }

        Optional<Reserva> proximoDaFila = reservaRepository
                .findFirstByLivroIdAndStatusOrderByPosicaoFilaAsc(livroId, StatusReserva.AGUARDANDO);

        if (proximoDaFila.isPresent()) {
            Reserva proxima = proximoDaFila.get();
            proxima.setPrazoRetirada(LocalDateTime.now().plusDays(PRAZO_RETIRADA_RESERVA_DIAS));
            reservaRepository.save(proxima);
        } else {
            Exemplar exemplar = exemplarReservado.get();
            exemplar.setStatus(StatusExemplar.DISPONIVEL);
            exemplarRepository.save(exemplar);
        }
    }

    // --- REORDENA AS POSIÇÕES DOS USUÁRIOS QUE ESTÃO AGUARDANDO NA FILA DE RESERVA DO LIVRO ---
    private void reordenarFila(Long livroId) {
        List<Reserva> fila = reservaRepository
                .findByLivroIdAndStatusOrderByPosicaoFilaAsc(livroId, StatusReserva.AGUARDANDO);
        int pos = 1;
        for (Reserva r : fila) {
            r.setPosicaoFila(pos++);
        }
        reservaRepository.saveAll(fila);
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA IDENTIFICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- CONVERTE A ENTIDADE RESERVA EM UM DTO DE RESPOSTA ---
    private ReservaResponseDTO toResponse(Reserva r) {
        return ReservaResponseDTO.builder()
                .id(r.getId())
                .livroId(r.getLivro().getId())
                .livroTitulo(r.getLivro().getTitulo())
                .usuarioId(r.getUsuario().getId())
                .usuarioNome(r.getUsuario().getNome())
                .dataReserva(r.getDataReserva())
                .status(r.getStatus())
                .posicaoFila(r.getPosicaoFila())
                .build();
    }
}

