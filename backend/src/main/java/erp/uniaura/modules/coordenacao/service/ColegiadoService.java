package erp.uniaura.modules.coordenacao.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.coordenacao.dto.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ColegiadoService {

    private final ReuniaoColegiadoRepository reuniaoColegiadoRepository;
    private final ParticipanteReuniaoRepository participanteReuniaoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

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

        return toResponse(reuniaoColegiadoRepository.save(reuniao), List.of());
    }

    // --- ATUALIZA OS DADOS DE UMA REUNIÃO AINDA AGENDADA ---
    @Transactional
    public ReuniaoColegiadoResponseDTO atualizar(Long id, ReuniaoColegiadoRequestDTO dto) {
        ReuniaoColegiado reuniaoColegiado = buscarEntidade(id);
        garantirAgendada(reuniaoColegiado, "alterada");

        if (!reuniaoColegiado.getCurso().getId().equals(dto.getCursoId())) {
            reuniaoColegiado.setCurso(buscarCurso(dto.getCursoId()));
        }

        reuniaoColegiado.setTitulo(dto.getTitulo());
        reuniaoColegiado.setDataHora(dto.getDataHora());
        reuniaoColegiado.setLocal(dto.getLocal());
        reuniaoColegiado.setPauta(dto.getPauta());

        reuniaoColegiadoRepository.save(reuniaoColegiado);
        return toResponse(reuniaoColegiado, buscarParticipantes(reuniaoColegiado.getId()));
    }

    // --- CONVOCA UM PARTICIPANTE PARA A REUNIÃO ---
    @Transactional
    public ParticipanteReuniaoResponseDTO convocar(Long reuniaoId, ConvocarParticipanteRequestDTO dto) {
        ReuniaoColegiado reuniao = buscarEntidade(reuniaoId);
        garantirAgendada(reuniao, "alterada");

        if (participanteReuniaoRepository.existsByReuniaoIdAndUsuarioId(reuniaoId, dto.getUsuarioId())) {
            throw new BusinessException("Este participante já foi convocado para a reunião.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", dto.getUsuarioId()));

        ParticipanteReuniao participante = ParticipanteReuniao.builder()
                .reuniao(reuniao)
                .usuario(usuario)
                .papel(dto.getPapel())
                .build();

        return toParticipanteResponse(participanteReuniaoRepository.save(participante));
    }

    // --- REMOVE UM CONVOCADO ANTES DA REUNIÃO ACONTECER ---
    @Transactional
    public void removerParticipante(Long reuniaoId, Long usuarioId) {
        ReuniaoColegiado reuniao = buscarEntidade(reuniaoId);
        garantirAgendada(reuniao, "alterada");

        ParticipanteReuniao participante = participanteReuniaoRepository
                .findByReuniaoIdAndUsuarioId(reuniaoId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Participante da reunião", reuniaoId + "/" + usuarioId));

        participanteReuniaoRepository.delete(participante);
    }

    // --- REGISTRA A ATA, MARCA AS PRESENÇAS E ENCERRA A REUNIÃO ---
    @Transactional
    public ReuniaoColegiadoResponseDTO registrarAta(Long id, RegistrarAtaRequestDTO dto) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        garantirAgendada(reuniao, "encerrada");

        List<ParticipanteReuniao> participantes = participanteReuniaoRepository.findByReuniaoIdOrderByCriadoEmAsc(id);
        if (participantes.isEmpty()) {
            throw new BusinessException("Não é possível registrar a ata de uma reunião sem participantes convocados.");
        }

        Map<Long, Boolean> presencas = new HashMap<>();
        if (dto.getPresencas() != null) {
            for (PresencaDTO presenca : dto.getPresencas()) {
                presencas.put(presenca.getUsuarioId(), presenca.getPresente());
            }
        }

        // --- QUEM NÃO FOI INFORMADO NA LISTA DE PRESENÇAS É REGISTRADO COMO AUSENTE ---
        for (ParticipanteReuniao participante : participantes) {
            participante.setPresente(presencas.getOrDefault(participante.getUsuario().getId(), Boolean.FALSE));
        }
        participanteReuniaoRepository.saveAll(participantes);

        reuniao.setDeliberacoes(dto.getDeliberacoes());
        reuniao.setStatus(StatusReuniao.REALIZADA);
        reuniao.setEncerradaEm(LocalDateTime.now());
        reuniaoColegiadoRepository.save(reuniao);

        return toResponse(reuniao, buscarParticipantes(id));
    }

    // --- CANCELA UMA REUNIÃO AGENDADA ---
    @Transactional
    public ReuniaoColegiadoResponseDTO cancelar(Long id, String motivo) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        garantirAgendada(reuniao, "cancelada");

        if (motivo == null || motivo.isBlank()) {
            throw new BusinessException("Informe o motivo do cancelamento da reunião.");
        }

        reuniao.setStatus(StatusReuniao.CANCELADA);
        reuniao.setMotivoCancelamento(motivo);
        reuniao.setEncerradaEm(LocalDateTime.now());
        reuniaoColegiadoRepository.save(reuniao);

        return toResponse(reuniao, buscarParticipantes(id));
    }

    // --- LISTA AS REUNIÕES, COM FILTRO OPCIONAL POR CURSO E STATUS ---
    @Transactional(readOnly = true)
    public Page<ReuniaoColegiadoResponseDTO> listar(Long cursoId, StatusReuniao status, Pageable pageable) {
        return reuniaoColegiadoRepository.buscarComFiltros(cursoId, status, pageable)
                .map(r -> toResponse(r, null));
    }

    // --- BUSCA UMA REUNIÃO PELO SEU IDENTIFICADOR ---
    @Transactional(readOnly = true)
    public ReuniaoColegiadoResponseDTO buscarPorId(Long id) {
        ReuniaoColegiado reuniao = buscarEntidade(id);
        return toResponse(reuniao, buscarParticipantes(id));
    }

    // --- BUSCA A ENTIDADE ---
    private ReuniaoColegiado buscarEntidade(Long id) {
        return reuniaoColegiadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reunião do colegiado", id));
    }

    // --- BUSCA O CURSO ---
    private Curso buscarCurso(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", id));
    }

    // --- REUNIÃO JÁ REALIZADA OU CANCELADA NÃO ACEITA MAIS ALTERAÇÕES ---
    private void garantirAgendada(ReuniaoColegiado reuniao, String acao) {
        if (reuniao.getStatus().isFinal()) {
            throw new BusinessException("A reunião está %s e não pode mais ser %s.".formatted(reuniao.getStatus(), acao));
        }
    }

    // --- BUSCA OS PARTICIPANTES CONVOCADOS PARA A REUNIÃO ---
    private List<ParticipanteReuniaoResponseDTO> buscarParticipantes(Long reuniaoId) {
        return participanteReuniaoRepository.findByReuniaoIdOrderByCriadoEmAsc(reuniaoId)
                .stream()
                .map(this::toParticipanteResponse)
                .toList();
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- CONVERTE A ENTIDADE REUNIÃO EM UM DTO DE RESPOSTA ---
    private ReuniaoColegiadoResponseDTO toResponse(ReuniaoColegiado reuniaoColegiado, List<ParticipanteReuniaoResponseDTO> participantes) {
        return ReuniaoColegiadoResponseDTO.builder()
                .id(reuniaoColegiado.getId())
                .cursoId(reuniaoColegiado.getCurso().getId())
                .cursoNome(reuniaoColegiado.getCurso().getNome())
                .titulo(reuniaoColegiado.getTitulo())
                .dataHora(reuniaoColegiado.getDataHora())
                .local(reuniaoColegiado.getLocal())
                .pauta(reuniaoColegiado.getPauta())
                .status(reuniaoColegiado.getStatus())
                .deliberacoes(reuniaoColegiado.getDeliberacoes())
                .motivoCancelamento(reuniaoColegiado.getMotivoCancelamento())
                .encerradaEm(reuniaoColegiado.getEncerradaEm())
                .criadaPorNome(reuniaoColegiado.getCriadaPor() == null ? null : reuniaoColegiado.getCriadaPor().getNome())
                .criadoEm(reuniaoColegiado.getCriadoEm())
                .atualizadoEm(reuniaoColegiado.getAtualizadoEm())
                .participantes(participantes)
                .build();
    }

    // --- CONVERTE A ENTIDADE PARTICIPANTE EM UM DTO DE RESPOSTA ---
    private ParticipanteReuniaoResponseDTO toParticipanteResponse(ParticipanteReuniao participanteReuniao) {
        return ParticipanteReuniaoResponseDTO.builder()
                .id(participanteReuniao.getId())
                .usuarioId(participanteReuniao.getUsuario().getId())
                .usuarioNome(participanteReuniao.getUsuario().getNome())
                .papel(participanteReuniao.getPapel())
                .presente(participanteReuniao.getPresente())
                .build();
    }
}
