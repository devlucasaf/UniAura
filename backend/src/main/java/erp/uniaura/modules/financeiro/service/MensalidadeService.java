package erp.uniaura.modules.financeiro.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.auditoria.service.AuditoriaService;
import erp.uniaura.modules.financeiro.dto.MensalidadeRequestDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeResponseDTO;
import erp.uniaura.modules.financeiro.dto.PagamentoRequestDTO;
import erp.uniaura.modules.financeiro.dto.PagamentoResponseDTO;
import erp.uniaura.modules.financeiro.model.FormaPagamento;
import erp.uniaura.modules.financeiro.model.Mensalidade;
import erp.uniaura.modules.financeiro.model.Pagamento;
import erp.uniaura.modules.financeiro.model.StatusMensalidade;
import erp.uniaura.modules.financeiro.repository.MensalidadeRepository;
import erp.uniaura.modules.financeiro.repository.PagamentoRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensalidadeService {

    private final MensalidadeRepository mensalidadeRepository;
    private final PagamentoRepository pagamentoRepository;
    private final AlunoRepository alunoRepository;
    private final AuditoriaService auditoriaService;

    // --- LISTA MENSALIDADES COM FILTROS ---
    @Transactional(readOnly = true)
    public Page<MensalidadeResponseDTO> listar(UUID alunoId, StatusMensalidade status, Pageable pageable) {
        Page<Mensalidade> page;
        if (alunoId != null && status != null) {
            page = mensalidadeRepository.findByAlunoIdAndStatus(alunoId, status, pageable);
        } else if (alunoId != null) {
            page = mensalidadeRepository.findByAlunoId(alunoId, pageable);
        } else if (status != null) {
            page = mensalidadeRepository.findByStatus(status, pageable);
        } else {
            page = mensalidadeRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    // --- LISTA AS MENSALIDADES DO ALUNO AUTENTICADO ---
    @Transactional(readOnly = true)
    public Page<MensalidadeResponseDTO> minhas(Pageable pageable) {
        Aluno aluno = alunoDoAutenticado();
        return mensalidadeRepository.findByAlunoId(aluno.getId(), pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public MensalidadeResponseDTO buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    // --- CRIA UMA NOVA MENSALIDADE PARA O ALUNO ---
    @Transactional
    public MensalidadeResponseDTO criar(MensalidadeRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.getAlunoId()));

        if (mensalidadeRepository.existsByAlunoIdAndCompetencia(aluno.getId(), dto.getCompetencia())) {
            throw new BusinessException("Já existe uma mensalidade para este aluno na competência " + dto.getCompetencia() + ".");
        }

        Mensalidade mensalidade = Mensalidade.builder()
                .aluno(aluno)
                .competencia(dto.getCompetencia())
                .valor(dto.getValor())
                .vencimento(dto.getVencimento())
                .status(dto.getStatus() == null ? StatusMensalidade.PENDENTE : dto.getStatus())
                .descricao(dto.getDescricao())
                .build();

        return toResponse(mensalidadeRepository.save(mensalidade));
    }

    // --- ATUALIZA OS DADOS DE UMA MENSALIDADE AINDA NÃO PAGA ---
    @Transactional
    public MensalidadeResponseDTO atualizar(UUID id, MensalidadeRequestDTO dto) {
        Mensalidade mensalidade = buscarEntidade(id);

        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Não é possível alterar uma mensalidade já paga.");
        }

        if (!mensalidade.getAluno().getId().equals(dto.getAlunoId())) {
            Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.getAlunoId()));
            mensalidade.setAluno(aluno);
        }

        mensalidade.setCompetencia(dto.getCompetencia());
        mensalidade.setValor(dto.getValor());
        mensalidade.setVencimento(dto.getVencimento());
        mensalidade.setDescricao(dto.getDescricao());
        if (dto.getStatus() != null) {
            mensalidade.setStatus(dto.getStatus());
        }

        return toResponse(mensalidadeRepository.save(mensalidade));
    }

    // --- REMOVE UMA MENSALIDADE AINDA NÃO PAGA ---
    @Transactional
    public void deletar(UUID id) {
        Mensalidade mensalidade = buscarEntidade(id);

        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Não é possível remover uma mensalidade já paga.");
        }

        mensalidadeRepository.delete(mensalidade);
    }

    // --- REGISTRA O PAGAMENTO DE UMA MENSALIDADE ---
    @Transactional
    public MensalidadeResponseDTO registrarPagamento(UUID id, PagamentoRequestDTO dto) {
        Mensalidade mensalidade = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        validarPodePagar(mensalidade, autenticado);

        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Esta mensalidade já está paga.");
        }
        if (mensalidade.getStatus() == StatusMensalidade.CANCELADA) {
            throw new BusinessException("Não é possível pagar uma mensalidade cancelada.");
        }

        if (dto.getFormaPagamento() == FormaPagamento.CARTAO &&
                (dto.getCartaoNumero() == null || dto.getCartaoValidade() == null || dto.getCartaoCvv() == null)) {
            throw new BusinessException("Para pagamento com cartão, informe número, validade e CVV (dados fictícios).");
        }

        Pagamento pagamento = Pagamento.builder()
                .mensalidade(mensalidade)
                .formaPagamento(dto.getFormaPagamento())
                .valorPago(mensalidade.getValor())
                .cartaoFinal(ultimosDigitos(dto.getCartaoNumero()))
                .build();
        pagamentoRepository.save(pagamento);

        mensalidade.setStatus(StatusMensalidade.PAGA);
        mensalidadeRepository.save(mensalidade);

        auditoriaService.registrar(autenticado, "PAGAMENTO_REGISTRADO", "Mensalidade", mensalidade.getId(),
                "Competência %s paga via %s".formatted(mensalidade.getCompetencia(), dto.getFormaPagamento()));

        return toResponse(mensalidade);
    }

    // --- HELPERS ---

    private Mensalidade buscarEntidade(UUID id) {
        return mensalidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensalidade", id));
    }

    // --- RESOLVE O ALUNO ASSOCIADO AO USUÁRIO AUTENTICADO ---
    private Aluno alunoDoAutenticado() {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        return alunoRepository.findByUsuarioId(autenticado.getId())
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui matrícula de aluno."));
    }

    // --- SÓ O PRÓPRIO ALUNO OU A EQUIPE FINANCEIRA/ADMIN PODEM QUITAR A MENSALIDADE ---
    private void validarPodePagar(Mensalidade mensalidade, Usuario autenticado) {
        boolean equipe = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.FINANCEIRO
                || autenticado.getRole() == TipoUsuario.SECRETARIA;

        if (equipe) {
            return;
        }

        boolean donoDaMensalidade = mensalidade.getAluno().getUsuario() != null
                && mensalidade.getAluno().getUsuario().getId().equals(autenticado.getId());

        if (!donoDaMensalidade) {
            throw new BusinessException("Você não pode pagar a mensalidade de outro aluno.");
        }
    }

    private String ultimosDigitos(String cartaoNumero) {
        if (cartaoNumero == null || cartaoNumero.length() < 4) {
            return cartaoNumero;
        }
        return cartaoNumero.substring(cartaoNumero.length() - 4);
    }

    private Usuario usuarioAutenticadoOuFalha() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioDetails ud)) {
            throw new BusinessException("Usuário autenticado não identificado.");
        }
        return ud.getUsuario();
    }

    private MensalidadeResponseDTO toResponse(Mensalidade m) {
        PagamentoResponseDTO pagamentoDto = pagamentoRepository.findByMensalidadeId(m.getId())
                .map(p -> PagamentoResponseDTO.builder()
                        .id(p.getId())
                        .formaPagamento(p.getFormaPagamento())
                        .valorPago(p.getValorPago())
                        .cartaoFinal(p.getCartaoFinal())
                        .dataPagamento(p.getDataPagamento())
                        .build())
                .orElse(null);

        return MensalidadeResponseDTO.builder()
                .id(m.getId())
                .alunoId(m.getAluno().getId())
                .alunoNome(m.getAluno().getUsuario() == null ? null : m.getAluno().getUsuario().getNome())
                .competencia(m.getCompetencia())
                .valor(m.getValor())
                .vencimento(m.getVencimento())
                .status(m.getStatus())
                .descricao(m.getDescricao())
                .pagamento(pagamentoDto)
                .criadoEm(m.getCriadoEm())
                .atualizadoEm(m.getAtualizadoEm())
                .build();
    }
}
