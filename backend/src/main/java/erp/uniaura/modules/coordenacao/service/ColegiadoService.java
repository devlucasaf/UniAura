package erp.uniaura.modules.coordenacao.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.modules.coordenacao.dto.ConvocarParticipanteRequestDTO;
import erp.uniaura.modules.coordenacao.dto.ParticipanteReuniaoResponseDTO;
import erp.uniaura.modules.coordenacao.dto.RegistrarAtaRequestDTO;
import erp.uniaura.modules.coordenacao.dto.ReuniaoColegiadoRequestDTO;
import erp.uniaura.modules.coordenacao.dto.ReuniaoColegiadoResponseDTO;
import erp.uniaura.modules.coordenacao.model.ParticipanteReuniao;
import erp.uniaura.modules.coordenacao.model.ReuniaoColegiado;
import erp.uniaura.modules.coordenacao.model.StatusReuniao;
import erp.uniaura.modules.coordenacao.repository.ParticipanteReuniaoRepository;
import erp.uniaura.modules.coordenacao.repository.ReuniaoColegiadoRepository;
import erp.uniaura.modules.curso.model.Curso;
import erp.uniaura.modules.curso.repository.CursoRepository;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColegiadoService {

    private final ReuniaoColegiadoRepository reuniaoRepository;
    private final ParticipanteReuniaoRepository participanteRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    // --- AGENDA UMA NOVA REUNIÃO DO COLEGIADO ---
    @Transactional
    public ReuniaoColegiadoResponseDTO agendar(ReuniaoColegiadoRequestDTO dto) {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        Curso curso = buscarCurso(dto.getCursoId());

        ReuniaoColegiado reuniao = ReuniaoColegiado.builder()
                .curso(curso)
                .titulo(dto.getTitulo())
                .dataHora(dto.getDataHora())
                .local(dto.getLocal())
                .pauta(dto.getPauta())
                .status(StatusReuniao.AGENDADA)
                .criadaPor(autenticado)
                .build();

        return toResponse(reuniaoRepository.save(reuniao), List.of());
    }

    // --- ATUALIZA OS DADOS DE UMA REUNIÃO AINDA AGENDADA ---
    @Transactional
    public ReuniaoColegiadoResponseDTO atualizar(UUID id, ReuniaoColegiadoRequestDTO dto) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        garantirAgendada(reuniao, "alterada");

        if (!reuniao.getCurso().getId().equals(dto.getCursoId())) {
            reuniao.setCurso(buscarCurso(dto.getCursoId()));
        }

        reuniao.setTitulo(dto.getTitulo());
        reuniao.setDataHora(dto.getDataHora());
        reuniao.setLocal(dto.getLocal());
        reuniao.setPauta(dto.getPauta());

        reuniaoRepository.save(reuniao);
        return toResponse(reuniao, buscarParticipantes(reuniao.getId()));
    }

    // --- CONVOCA UM PARTICIPANTE PARA A REUNIÃO ---
    @Transactional
    public ParticipanteReuniaoResponseDTO convocar(UUID reuniaoId, ConvocarParticipanteRequestDTO dto) {
        ReuniaoColegiado reuniao = buscarEntidade(reuniaoId);
        garantirAgendada(reuniao, "alterada");

        if (participanteRepository.existsByReuniaoIdAndUsuarioId(reuniaoId, dto.getUsuarioId())) {
            throw new BusinessException("Este participante já foi convocado para a reunião.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", dto.getUsuarioId()));

        ParticipanteReuniao participante = ParticipanteReuniao.builder()
                .reuniao(reuniao)
                .usuario(usuario)
                .papel(dto.getPapel())
                .build();

        return toParticipanteResponse(participanteRepository.save(participante));
    }

    // --- REMOVE UM CONVOCADO ANTES DA REUNIÃO ACONTECER ---
    @Transactional
    public void removerParticipante(UUID reuniaoId, UUID usuarioId) {
        ReuniaoColegiado reuniao = buscarEntidade(reuniaoId);
        garantirAgendada(reuniao, "alterada");

        ParticipanteReuniao participante = participanteRepository
                .findByReuniaoIdAndUsuarioId(reuniaoId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Participante da reunião", reuniaoId + "/" + usuarioId));

        participanteRepository.delete(participante);
    }

    // --- REGISTRA A ATA, MARCA AS PRESENÇAS E ENCERRA A REUNIÃO ---
    @Transactional
    public ReuniaoColegiadoResponseDTO registrarAta(UUID id, RegistrarAtaRequestDTO dto) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        garantirAgendada(reuniao, "encerrada");

        List<ParticipanteReuniao> participantes = participanteRepository.findByReuniaoIdOrderByCriadoEmAsc(id);
        if (participantes.isEmpty()) {
            throw new BusinessException("Não é possível registrar a ata de uma reunião sem participantes convocados.");
        }

        Map<UUID, Boolean> presencas = new HashMap<>();
        if (dto.getPresencas() != null) {
            for (RegistrarAtaRequestDTO.PresencaDTO presenca : dto.getPresencas()) {
                presencas.put(presenca.getUsuarioId(), presenca.getPresente());
            }
        }

        // --- QUEM NÃO FOI INFORMADO NA LISTA DE PRESENÇAS É REGISTRADO COMO AUSENTE ---
        for (ParticipanteReuniao participante : participantes) {
            participante.setPresente(presencas.getOrDefault(participante.getUsuario().getId(), Boolean.FALSE));
        }
        participanteRepository.saveAll(participantes);

        reuniao.setDeliberacoes(dto.getDeliberacoes());
        reuniao.setStatus(StatusReuniao.REALIZADA);
        reuniao.setEncerradaEm(LocalDateTime.now());
        reuniaoRepository.save(reuniao);

        return toResponse(reuniao, buscarParticipantes(id));
    }

    // --- CANCELA UMA REUNIÃO AGENDADA ---
    @Transactional
    public ReuniaoColegiadoResponseDTO cancelar(UUID id, String motivo) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        garantirAgendada(reuniao, "cancelada");

        if (motivo == null || motivo.isBlank()) {
            throw new BusinessException("Informe o motivo do cancelamento da reunião.");
        }

        reuniao.setStatus(StatusReuniao.CANCELADA);
        reuniao.setMotivoCancelamento(motivo);
        reuniao.setEncerradaEm(LocalDateTime.now());
        reuniaoRepository.save(reuniao);

        return toResponse(reuniao, buscarParticipantes(id));
    }

    @Transactional(readOnly = true)
    public Page<ReuniaoColegiadoResponseDTO> listar(UUID cursoId, StatusReuniao status, Pageable pageable) {
        return reuniaoRepository.buscarComFiltros(cursoId, status, pageable)
                .map(r -> toResponse(r, null));
    }

    @Transactional(readOnly = true)
    public ReuniaoColegiadoResponseDTO buscarPorId(UUID id) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        return toResponse(reuniao, buscarParticipantes(id));
    }

    // --- HELPERS ---

    private ReuniaoColegiado buscarEntidade(UUID id) {
        return reuniaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reunião do colegiado", id));
    }

    private Curso buscarCurso(UUID id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", id));
    }

    // --- REUNIÃO JÁ REALIZADA OU CANCELADA NÃO ACEITA MAIS ALTERAÇÕES ---
    private void garantirAgendada(ReuniaoColegiado reuniao, String acao) {
        if (reuniao.getStatus().isFinal()) {
            throw new BusinessException(
                    "A reunião está %s e não pode mais ser %s.".formatted(reuniao.getStatus(), acao));
        }
    }

    private List<ParticipanteReuniaoResponseDTO> buscarParticipantes(UUID reuniaoId) {
        return participanteRepository.findByReuniaoIdOrderByCriadoEmAsc(reuniaoId)
                .stream()
                .map(this::toParticipanteResponse)
                .toList();
    }

    private Usuario usuarioAutenticadoOuFalha() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioDetails ud)) {
            throw new BusinessException("Usuário autenticado não identificado.");
        }
        return ud.getUsuario();
    }

    // --- CONVERTE A ENTIDADE REUNIÃO EM UM DTO DE RESPOSTA ---
    private ReuniaoColegiadoResponseDTO toResponse(ReuniaoColegiado r,
                                                   List<ParticipanteReuniaoResponseDTO> participantes) {
        return ReuniaoColegiadoResponseDTO.builder()
                .id(r.getId())
                .cursoId(r.getCurso().getId())
                .cursoNome(r.getCurso().getNome())
                .titulo(r.getTitulo())
                .dataHora(r.getDataHora())
                .local(r.getLocal())
                .pauta(r.getPauta())
                .status(r.getStatus())
                .deliberacoes(r.getDeliberacoes())
                .motivoCancelamento(r.getMotivoCancelamento())
                .encerradaEm(r.getEncerradaEm())
                .criadaPorNome(r.getCriadaPor() == null ? null : r.getCriadaPor().getNome())
                .criadoEm(r.getCriadoEm())
                .atualizadoEm(r.getAtualizadoEm())
                .participantes(participantes)
                .build();
    }

    private ParticipanteReuniaoResponseDTO toParticipanteResponse(ParticipanteReuniao p) {
        return ParticipanteReuniaoResponseDTO.builder()
                .id(p.getId())
                .usuarioId(p.getUsuario().getId())
                .usuarioNome(p.getUsuario().getNome())
                .papel(p.getPapel())
                .presente(p.getPresente())
                .build();
    }
}
