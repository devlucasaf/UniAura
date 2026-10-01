package erp.uniaura.modules.financeiro.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.email.EmailService;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.auditoria.service.AuditoriaService;
import erp.uniaura.modules.financeiro.dto.BoletoResponseDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeRequestDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeResponseDTO;
import erp.uniaura.modules.financeiro.dto.PagamentoRequestDTO;
import erp.uniaura.modules.financeiro.dto.PagamentoResponseDTO;
import erp.uniaura.modules.financeiro.dto.PixResponseDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensalidadeService {

    private static final BigDecimal PERCENTUAL_MULTA_ATRASO = new BigDecimal("0.02");
    private static final BigDecimal PERCENTUAL_JUROS_AO_DIA = new BigDecimal("0.00033");
    private static final int MINUTOS_EXPIRACAO_PIX = 30;

    private final MensalidadeRepository mensalidadeRepository;
    private final PagamentoRepository pagamentoRepository;
    private final AlunoRepository alunoRepository;
    private final AuditoriaService auditoriaService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;
    private final EmailService emailService;

    // --- LISTA MENSALIDADES COM FILTROS ---
    @Transactional(readOnly = true)
    public Page<MensalidadeResponseDTO> listar(Long alunoId, StatusMensalidade status, Pageable pageable) {
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

    // --- BUSCA UMA MENSALIDADE PELO SEU IDENTIFICADOR ---
    @Transactional(readOnly = true)
    public MensalidadeResponseDTO buscarPorId(Long id) {
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
    public MensalidadeResponseDTO atualizar(Long id, MensalidadeRequestDTO dto) {
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
    public void deletar(Long id) {
        Mensalidade mensalidade = buscarEntidade(id);

        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Não é possível remover uma mensalidade já paga.");
        }

        mensalidadeRepository.delete(mensalidade);
    }

    // --- REGISTRA O PAGAMENTO DE UMA MENSALIDADE ---
    @Transactional
    public MensalidadeResponseDTO registrarPagamento(Long id, PagamentoRequestDTO dto) {
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

        BigDecimal valorMulta = calcularMulta(mensalidade);

        Pagamento pagamento = Pagamento.builder()
                .mensalidade(mensalidade)
                .formaPagamento(dto.getFormaPagamento())
                .valorPago(mensalidade.getValor().add(valorMulta))
                .valorMulta(valorMulta)
                .cartaoFinal(ultimosDigitos(dto.getCartaoNumero()))
                .build();
        pagamentoRepository.save(pagamento);

        mensalidade.setStatus(StatusMensalidade.PAGA);
        mensalidadeRepository.save(mensalidade);

        auditoriaService.registrar(autenticado, "PAGAMENTO_REGISTRADO", "Mensalidade", mensalidade.getId(),
                "Competência %s paga via %s".formatted(mensalidade.getCompetencia(), dto.getFormaPagamento()));

        return toResponse(mensalidade);
    }

    // --- MARCA COMO ATRASADA TODA MENSALIDADE PENDENTE COM VENCIMENTO EXPIRADO ---
    @Transactional
    public int marcarMensalidadesAtrasadas() {
        List<Mensalidade> vencidas = mensalidadeRepository
                .findByStatusAndVencimentoBefore(StatusMensalidade.PENDENTE, LocalDate.now());
        vencidas.forEach(m -> m.setStatus(StatusMensalidade.ATRASADA));
        mensalidadeRepository.saveAll(vencidas);
        vencidas.forEach(this::notificarAtraso);
        return vencidas.size();
    }

    // --- NOTIFICA O ALUNO QUE UMA MENSALIDADE SUA ACABOU DE FICAR ATRASADA ---
    private void notificarAtraso(Mensalidade mensalidade) {
        Usuario usuario = mensalidade.getAluno().getUsuario();
        if (usuario == null) {
            return;
        }
        emailService.notificarMensalidadeAtrasada(usuario.getEmail(), usuario.getNome(),
                mensalidade.getCompetencia(), mensalidade.getValor());
    }

    // --- GERA UM PIX FICTÍCIO (COPIA E COLA) PARA PAGAMENTO DA MENSALIDADE ---
    @Transactional(readOnly = true)
    public PixResponseDTO gerarPix(Long id) {
        Mensalidade mensalidade = buscarEntidade(id);
        validarPendenteOuAtrasada(mensalidade);

        BigDecimal valorTotal = mensalidade.getValor().add(calcularMulta(mensalidade));
        String payload = "00020126580014BR.GOV.BCB.PIX0136uniaura-ficticio-%06d520400005303986540%s5802BR5913UNIAURA ERP6009SAO PAULO62070503***6304FICT"
                .formatted(mensalidade.getId(), valorTotal.setScale(2, RoundingMode.HALF_UP));

        return PixResponseDTO.builder()
                .payload(payload)
                .valor(valorTotal)
                .expiraEm(LocalDateTime.now().plusMinutes(MINUTOS_EXPIRACAO_PIX))
                .build();
    }

    // --- GERA UM BOLETO FICTÍCIO PARA PAGAMENTO DA MENSALIDADE ---
    @Transactional(readOnly = true)
    public BoletoResponseDTO gerarBoleto(Long id) {
        Mensalidade mensalidade = buscarEntidade(id);
        validarPendenteOuAtrasada(mensalidade);

        BigDecimal valorTotal = mensalidade.getValor().add(calcularMulta(mensalidade));
        long centavos = valorTotal.multiply(BigDecimal.valueOf(100)).longValue();
        String codigoBarras = "%03d9%01d%010d%010d%010d"
                .formatted(237, 9, centavos, mensalidade.getId(), mensalidade.getId() * 7 + 13);
        String linhaDigitavel = "%s.%s %s.%s %s.%s %s %s".formatted(
                codigoBarras.substring(0, 5), codigoBarras.substring(5, 10),
                codigoBarras.substring(10, 15), codigoBarras.substring(15, 21),
                codigoBarras.substring(21, 26), codigoBarras.substring(26, 32),
                codigoBarras.substring(32, 33), codigoBarras.substring(33));

        return BoletoResponseDTO.builder()
                .linhaDigitavel(linhaDigitavel)
                .codigoBarras(codigoBarras)
                .valor(valorTotal)
                .vencimento(mensalidade.getVencimento())
                .build();
    }

    // --- CALCULA MULTA + JUROS PRO RATA PARA MENSALIDADES EM ATRASO ---
    private BigDecimal calcularMulta(Mensalidade mensalidade) {
        if (mensalidade.getStatus() != StatusMensalidade.ATRASADA) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        long diasAtraso = Math.max(0, ChronoUnit.DAYS.between(mensalidade.getVencimento(), LocalDate.now()));
        BigDecimal percentualJuros = PERCENTUAL_JUROS_AO_DIA.multiply(BigDecimal.valueOf(diasAtraso));
        BigDecimal percentualTotal = PERCENTUAL_MULTA_ATRASO.add(percentualJuros);

        return mensalidade.getValor().multiply(percentualTotal).setScale(2, RoundingMode.HALF_UP);
    }

    // --- SÓ É POSSÍVEL GERAR PIX/BOLETO PARA MENSALIDADE AINDA NÃO PAGA/CANCELADA ---
    private void validarPendenteOuAtrasada(Mensalidade mensalidade) {
        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Esta mensalidade já está paga.");
        }
        if (mensalidade.getStatus() == StatusMensalidade.CANCELADA) {
            throw new BusinessException("Não é possível gerar cobrança para uma mensalidade cancelada.");
        }
    }

    // --- BUSCA A ENTIDADE ---
    private Mensalidade buscarEntidade(Long id) {
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

    // --- MASCARA O NÚMERO DO CARTÃO, MANTENDO SOMENTE OS ÚLTIMOS 4 DÍGITOS ---
    private String ultimosDigitos(String cartaoNumero) {
        if (cartaoNumero == null || cartaoNumero.length() < 4) {
            return cartaoNumero;
        }
        return cartaoNumero.substring(cartaoNumero.length() - 4);
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- CONVERTE A ENTIDADE EM UM DTO DE RESPOSTA ---
    private MensalidadeResponseDTO toResponse(Mensalidade m) {
        PagamentoResponseDTO pagamentoDto = pagamentoRepository.findByMensalidadeId(m.getId())
                .map(p -> PagamentoResponseDTO.builder()
                        .id(p.getId())
                        .formaPagamento(p.getFormaPagamento())
                        .valorPago(p.getValorPago())
                        .valorMulta(p.getValorMulta())
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
