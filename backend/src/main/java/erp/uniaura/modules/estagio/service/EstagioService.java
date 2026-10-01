package erp.uniaura.modules.estagio.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.estagio.dto.EncerrarEstagioRequestDTO;
import erp.uniaura.modules.estagio.dto.EstagioRequestDTO;
import erp.uniaura.modules.estagio.dto.EstagioResponseDTO;
import erp.uniaura.modules.estagio.model.Estagio;
import erp.uniaura.modules.estagio.model.StatusEstagio;
import erp.uniaura.modules.estagio.repository.EstagioRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstagioService {

    private final EstagioRepository estagioRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- ABRE UM NOVO ESTÁGIO PARA O ALUNO ---
    @Transactional
    public EstagioResponseDTO abrir(EstagioRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.getAlunoId()));

        Professor orientador = dto.getProfessorOrientadorId() == null ? null
                : professorRepository.findById(dto.getProfessorOrientadorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Professor", dto.getProfessorOrientadorId()));

        Estagio estagio = Estagio.builder()
                .aluno(aluno)
                .empresaConcedente(dto.getEmpresaConcedente())
                .supervisorEmpresa(dto.getSupervisorEmpresa())
                .professorOrientador(orientador)
                .dataInicio(dto.getDataInicio())
                .dataFimPrevista(dto.getDataFimPrevista())
                .cargaHorariaTotal(dto.getCargaHorariaTotal())
                .cargaHorariaCumprida(0)
                .status(StatusEstagio.EM_ANDAMENTO)
                .build();

        return toResponse(estagioRepository.save(estagio));
    }

    // --- LISTA ESTÁGIOS COM FILTROS OPCIONAIS ---
    @Transactional(readOnly = true)
    public Page<EstagioResponseDTO> listar(Long alunoId, StatusEstagio status, Pageable pageable) {
        Page<Estagio> page;

        if (alunoId != null && status != null) {
            page = estagioRepository.findByAlunoIdAndStatus(alunoId, status, pageable);
        } else if (alunoId != null) {
            page = estagioRepository.findByAlunoId(alunoId, pageable);
        } else if (status != null) {
            page = estagioRepository.findByStatus(status, pageable);
        } else {
            page = estagioRepository.findAll(pageable);
        }

        return page.map(this::toResponse);
    }

    // --- BUSCA UM ESTÁGIO PELO SEU IDENTIFICADOR ---
    @Transactional(readOnly = true)
    public EstagioResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    // --- BUSCA O ESTÁGIO EM ANDAMENTO DO ALUNO AUTENTICADO ---
    @Transactional(readOnly = true)
    public EstagioResponseDTO meuEstagio() {
        Aluno aluno = alunoDoAutenticado();
        return estagioRepository.findFirstByAlunoIdAndStatus(aluno.getId(), StatusEstagio.EM_ANDAMENTO)
                .map(this::toResponse)
                .orElseThrow(() -> new BusinessException("Você não possui estágio em andamento."));
    }

    // --- ENCERRA UM ESTÁGIO ---
    @Transactional
    public EstagioResponseDTO encerrar(Long id, EncerrarEstagioRequestDTO dto) {
        Estagio estagio = buscarEntidade(id);

        if (estagio.getStatus() == StatusEstagio.CONCLUIDO || estagio.getStatus() == StatusEstagio.CANCELADO) {
            throw new BusinessException("Este estágio já está encerrado.");
        }

        estagio.setStatus(dto.getStatus());
        estagio.setDataFimEfetiva(dto.getDataFimEfetiva());

        return toResponse(estagioRepository.save(estagio));
    }

    // --- EXPÕE A ENTIDADE PARA O SERVICE DE RELATÓRIOS DE ESTÁGIO ---
    @Transactional(readOnly = true)
    public Estagio buscarEntidade(Long id) {
        return estagioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estágio", id));
    }

    // --- RESOLVE O ALUNO ASSOCIADO AO USUÁRIO AUTENTICADO ---
    private Aluno alunoDoAutenticado() {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        return alunoRepository.findByUsuarioId(autenticado.getId())
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui matrícula de aluno."));
    }

    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- CONVERTE A ENTIDADE EM UM DTO DE RESPOSTA ---
    private EstagioResponseDTO toResponse(Estagio e) {
        return EstagioResponseDTO.builder()
                .id(e.getId())
                .alunoId(e.getAluno().getId())
                .alunoNome(e.getAluno().getUsuario() == null ? null : e.getAluno().getUsuario().getNome())
                .empresaConcedente(e.getEmpresaConcedente())
                .supervisorEmpresa(e.getSupervisorEmpresa())
                .professorOrientadorId(e.getProfessorOrientador() == null ? null : e.getProfessorOrientador().getId())
                .professorOrientadorNome(e.getProfessorOrientador() == null ? null
                        : e.getProfessorOrientador().getUsuario().getNome())
                .dataInicio(e.getDataInicio())
                .dataFimPrevista(e.getDataFimPrevista())
                .dataFimEfetiva(e.getDataFimEfetiva())
                .cargaHorariaTotal(e.getCargaHorariaTotal())
                .cargaHorariaCumprida(e.getCargaHorariaCumprida())
                .status(e.getStatus())
                .termoCompromissoUrl(e.getTermoCompromissoUrl())
                .criadoEm(e.getCriadoEm())
                .atualizadoEm(e.getAtualizadoEm())
                .build();
    }
}
