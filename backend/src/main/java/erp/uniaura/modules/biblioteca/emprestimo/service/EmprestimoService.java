package erp.uniaura.modules.biblioteca.emprestimo.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.email.EmailService;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.biblioteca.config.model.ConfiguracaoBiblioteca;
import erp.uniaura.modules.biblioteca.config.service.ConfiguracaoBibliotecaService;
import erp.uniaura.modules.biblioteca.emprestimo.dto.EmprestimoRequestDTO;
import erp.uniaura.modules.biblioteca.emprestimo.dto.EmprestimoResponseDTO;
import erp.uniaura.modules.biblioteca.emprestimo.model.Emprestimo;
import erp.uniaura.modules.biblioteca.emprestimo.model.StatusEmprestimo;
import erp.uniaura.modules.biblioteca.emprestimo.repository.EmprestimoRepository;
import erp.uniaura.modules.biblioteca.event.MultaGeradaEvent;
import erp.uniaura.modules.biblioteca.exemplar.model.Exemplar;
import erp.uniaura.modules.biblioteca.exemplar.model.StatusExemplar;
import erp.uniaura.modules.biblioteca.exemplar.repository.ExemplarRepository;
import erp.uniaura.modules.biblioteca.multa.model.Multa;
import erp.uniaura.modules.biblioteca.multa.model.StatusMulta;
import erp.uniaura.modules.biblioteca.multa.repository.MultaRepository;
import erp.uniaura.modules.biblioteca.reserva.model.Reserva;
import erp.uniaura.modules.biblioteca.reserva.model.StatusReserva;
import erp.uniaura.modules.biblioteca.reserva.repository.ReservaRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private static final int PRAZO_RETIRADA_RESERVA_DIAS = 2;

    private final EmprestimoRepository emprestimoRepository;
    private final ExemplarRepository exemplarRepository;
    private final UsuarioRepository usuarioRepository;
    private final MultaRepository multaRepository;
    private final ReservaRepository reservaRepository;
    private final ConfiguracaoBibliotecaService configuracaoBibliotecaService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;
    private final EmailService emailService;

    // --- REGISTRA UM NOVO EMPRÉSTIMO ---
    @Transactional
    public EmprestimoResponseDTO registrar(EmprestimoRequestDTO dto) {
        ConfiguracaoBiblioteca configuracaoBiblioteca = configuracaoBibliotecaService.obter();

        Exemplar exemplar = resolverExemplar(dto);
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", dto.getUsuarioId()));

        // --- REGRAS DE NEGÓCIO ---
        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new BusinessException("Exemplar não está disponível para empréstimo. Status atual: "
                    + exemplar.getStatus());
        }

        if (multaRepository.existePendenteDoUsuario(usuario.getId())) {
            throw new BusinessException("Usuário possui multa pendente e não pode realizar novos empréstimos.");
        }

        long ativos = emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO)
                + emprestimoRepository.countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATRASADO);

        if (ativos >= configuracaoBiblioteca.getMaxEmprestimosSimultaneos()) {
            throw new BusinessException("Limite de empréstimos simultâneos atingido ("
                    + configuracaoBiblioteca.getMaxEmprestimosSimultaneos() + ").");
        }

        LocalDateTime agora = LocalDateTime.now();
        int prazoDias = prazoDias(usuario, configuracaoBiblioteca);

        Emprestimo emprestimo = Emprestimo.builder()
                .exemplar(exemplar)
                .usuario(usuario)
                .dataEmprestimo(agora)
                .dataDevolucaoPrevista(agora.plusDays(prazoDias))
                .renovacoes(0)
                .status(StatusEmprestimo.ATIVO)
                .criadoPor(usuarioAutenticadoOuFalha())
                .build();

        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        exemplarRepository.save(exemplar);

        // --- SE ESTE USUÁRIO ERA O PRIMEIRO DA FILA DE RESERVA, MARCAR COMO ATENDIDA ---
        reservaRepository.findFirstByLivroIdAndStatusOrderByPosicaoFilaAsc(
                        exemplar.getLivro().getId(), StatusReserva.AGUARDANDO)
                .filter(r -> r.getUsuario().getId().equals(usuario.getId()))
                .ifPresent(r -> {
                    r.setStatus(StatusReserva.ATENDIDA);
                    reservaRepository.save(r);
                    reordenarFila(exemplar.getLivro().getId());
                });

        return toResponse(emprestimoRepository.save(emprestimo));
    }

    // --- REGISTRA A DEVOLUÇÃO E GERA MULTA SE ATRASADO ---
    @Transactional
    public EmprestimoResponseDTO devolver(Long emprestimoId) {
        Emprestimo emprestimo = buscarEntidade(emprestimoId);

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new BusinessException("Este empréstimo já foi devolvido.");
        }

        LocalDateTime agora = LocalDateTime.now();
        emprestimo.setDataDevolucaoEfetiva(agora);
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);

        int diasAtraso = calcularDiasAtraso(emprestimo.getDataDevolucaoPrevista(), agora);
        if (diasAtraso > 0) {
            gerarMulta(emprestimo, diasAtraso);
        }

        // --- ATUALIZA STATUS DO EXEMPLAR CONFORME FILA DE RESERVA ---
        Exemplar exemplar = emprestimo.getExemplar();
        Optional<Reserva> primeiroDaFila = reservaRepository
                .findFirstByLivroIdAndStatusOrderByPosicaoFilaAsc(exemplar.getLivro().getId(), StatusReserva.AGUARDANDO);

        if (primeiroDaFila.isPresent()) {
            exemplar.setStatus(StatusExemplar.RESERVADO);
            Reserva reserva = primeiroDaFila.get();
            reserva.setPrazoRetirada(agora.plusDays(PRAZO_RETIRADA_RESERVA_DIAS));
            reservaRepository.save(reserva);
            log.info("Notificando usuário {} que o livro '{}' está disponível para retirada.",
                    reserva.getUsuario().getEmail(),
                    exemplar.getLivro().getTitulo());
        } else {
            exemplar.setStatus(StatusExemplar.DISPONIVEL);
        }
        exemplarRepository.save(exemplar);

        return toResponse(emprestimoRepository.save(emprestimo));
    }

    // --- RENOVA UM EMPRÉSTIMO EM ANDAMENTO ---
    @Transactional
    public EmprestimoResponseDTO renovar(Long emprestimoId) {
        Emprestimo emprestimo = buscarEntidade(emprestimoId);
        ConfiguracaoBiblioteca configuracaoBiblioteca = configuracaoBibliotecaService.obter();

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new BusinessException("Empréstimo já devolvido não pode ser renovado.");
        }

        if (emprestimo.getRenovacoes() >= configuracaoBiblioteca.getMaxRenovacoes()) {
            throw new BusinessException("Limite de renovações atingido (" + configuracaoBiblioteca.getMaxRenovacoes() + ").");
        }

        if (multaRepository.existePendenteDoUsuario(emprestimo.getUsuario().getId())) {
            throw new BusinessException("Usuário possui multa pendente. Renovação bloqueada.");
        }

        long aguardando = reservaRepository.countByLivroIdAndStatus(
                emprestimo.getExemplar().getLivro().getId(), StatusReserva.AGUARDANDO
        );

        if (aguardando > 0) {
            throw new BusinessException("Existem reservas para este livro. Renovação não permitida.");
        }

        int prazoDias = prazoDias(emprestimo.getUsuario(), configuracaoBiblioteca);
        LocalDateTime base = emprestimo.getDataDevolucaoPrevista().isBefore(LocalDateTime.now())
                ? LocalDateTime.now()
                : emprestimo.getDataDevolucaoPrevista();
        emprestimo.setDataDevolucaoPrevista(base.plusDays(prazoDias));
        emprestimo.setRenovacoes(emprestimo.getRenovacoes() + 1);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);
        return toResponse(emprestimoRepository.save(emprestimo));
    }

    // --- LISTA OS EMPRÉSTIMOS DE UM USUÁRIO UTILIZANDO PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public Page<EmprestimoResponseDTO> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return emprestimoRepository.findByUsuarioId(usuarioId, pageable).map(this::toResponse);
    }

    // --- BUSCA UM EMPRÉSTIMO PELO SEU IDENTIFICADOR ---
    @Transactional(readOnly = true)
    public EmprestimoResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    // --- MARCA COMO ATRASADO EMPRÉSTIMOS COM PRAZO VENCIDO ---
    @Transactional
    public int marcarEmprestimosVencidos() {
        List<Emprestimo> vencidos = emprestimoRepository
                .findByStatusAndDataDevolucaoPrevistaBefore(StatusEmprestimo.ATIVO, LocalDateTime.now());
        vencidos.forEach(e -> e.setStatus(StatusEmprestimo.ATRASADO));
        emprestimoRepository.saveAll(vencidos);
        vencidos.forEach(this::notificarAtraso);
        return vencidos.size();
    }

    // --- NOTIFICA O USUÁRIO QUE UM EMPRÉSTIMO SEU ACABOU DE FICAR ATRASADO ---
    private void notificarAtraso(Emprestimo emprestimo) {
        int diasAtraso = calcularDiasAtraso(emprestimo.getDataDevolucaoPrevista(), LocalDateTime.now());
        Usuario usuario = emprestimo.getUsuario();
        emailService.notificarEmprestimoAtrasado(usuario.getEmail(), usuario.getNome(),
                emprestimo.getExemplar().getLivro().getTitulo(), diasAtraso);
    }

    // --- BUSCA UM EMPRÉSTIMO PELO IDENTIFICADOR OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA ENCONTRADO ---
    private Emprestimo buscarEntidade(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo", id));
    }

    // --- LOCALIZA O EXEMPLAR PELO IDENTIFICADOR OU PELO CÓDIGO DE BARRAS INFORMADO ---
    private Exemplar resolverExemplar(EmprestimoRequestDTO dto) {
        if (dto.getExemplarId() != null) {
            return exemplarRepository.findById(dto.getExemplarId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exemplar", dto.getExemplarId()));
        }

        if (dto.getCodigoBarras() != null && !dto.getCodigoBarras().isBlank()) {
            return exemplarRepository.findByCodigoBarras(dto.getCodigoBarras().trim())
                    .orElseThrow(() -> new ResourceNotFoundException("Exemplar", dto.getCodigoBarras()));
        }
        throw new BusinessException("Informe o ID do exemplar ou o código de barras.");
    }

    // --- DEFINE O PRAZO DO EMPRÉSTIMO DE ACORDO COM O PERFIL DO USUÁRIO ---
    private int prazoDias(Usuario usuario, ConfiguracaoBiblioteca cfg) {
        return usuario.getRole() == TipoUsuario.PROFESSOR
                ? cfg.getPrazoEmprestimoProfessor()
                : cfg.getPrazoEmprestimoAluno();
    }

    // --- CALCULA A QUANTIDADE DE DIAS DE ATRASO ENTRE A DATA PREVISTA E A DATA DA DEVOLUÇÃO ---
    private int calcularDiasAtraso(LocalDateTime previsto, LocalDateTime devolvido) {
        if (!devolvido.isAfter(previsto)) {
            return 0;
        }
        return (int) Math.ceil(Duration.between(previsto, devolvido).toHours() / 24.0);
    }

    // --- GERA A MULTA E DISPARA EVENTO PARA O MÓDULO FINANCEIRO ---
    private void gerarMulta(Emprestimo emp, int diasAtraso) {
        ConfiguracaoBiblioteca cfg = configuracaoBibliotecaService.obter();
        BigDecimal valor = cfg.getValorMultaDia().multiply(BigDecimal.valueOf(diasAtraso));

        Multa multa = Multa.builder()
                .emprestimo(emp)
                .valor(valor)
                .diasAtraso(diasAtraso)
                .status(StatusMulta.PENDENTE)
                .build();
        multa = multaRepository.save(multa);

        applicationEventPublisher.publishEvent(new MultaGeradaEvent(
                multa.getId(),
                emp.getId(),
                emp.getUsuario().getId(),
                multa.getValor(),
                diasAtraso,
                LocalDateTime.now()
        ));
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

    // --- CONVERTE A ENTIDADE EMPRÉSTIMO EM UM DTO DE RESPOSTA, INCLUINDO ATRASO E MULTA ---
    public EmprestimoResponseDTO toResponse(Emprestimo e) {
        int diasAtraso = 0;
        BigDecimal valorMulta = null;
        LocalDateTime baseComparacao = e.getDataDevolucaoEfetiva() != null
                ? e.getDataDevolucaoEfetiva() : LocalDateTime.now();

        if (baseComparacao.isAfter(e.getDataDevolucaoPrevista())) {
            diasAtraso = calcularDiasAtraso(e.getDataDevolucaoPrevista(), baseComparacao);
        }

        Optional<Multa> multaOptional = multaRepository.findAll().stream()
                .filter(m -> m.getEmprestimo().getId().equals(e.getId()))
                .findFirst();

        if (multaOptional.isPresent()) {
            valorMulta = multaOptional.get().getValor();
        }

        return EmprestimoResponseDTO.builder()
                .id(e.getId())
                .exemplarId(e.getExemplar().getId())
                .exemplarCodigoBarras(e.getExemplar().getCodigoBarras())
                .livroId(e.getExemplar().getLivro().getId())
                .livroTitulo(e.getExemplar().getLivro().getTitulo())
                .usuarioId(e.getUsuario().getId())
                .usuarioNome(e.getUsuario().getNome())
                .dataEmprestimo(e.getDataEmprestimo())
                .dataDevolucaoPrevista(e.getDataDevolucaoPrevista())
                .dataDevolucaoEfetiva(e.getDataDevolucaoEfetiva())
                .renovacoes(e.getRenovacoes())
                .status(e.getStatus())
                .valorMulta(valorMulta)
                .diasAtraso(diasAtraso)
                .build();
    }
}

