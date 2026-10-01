package erp.uniaura.modules.tcc.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.tcc.dto.AgendarDefesaRequestDTO;
import erp.uniaura.modules.tcc.dto.TccRequestDTO;
import erp.uniaura.modules.tcc.dto.TccResponseDTO;
import erp.uniaura.modules.tcc.model.StatusTcc;
import erp.uniaura.modules.tcc.model.Tcc;
import erp.uniaura.modules.tcc.repository.BancaTccMembroRepository;
import erp.uniaura.modules.tcc.repository.TccRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TccService {

    private static final List<StatusTcc> STATUS_FINALIZADOS = List.of(
            StatusTcc.APROVADO, StatusTcc.REPROVADO, StatusTcc.CANCELADO);
    private static final int ESCALA_NOTA = 2;

    private final TccRepository tccRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final BancaTccMembroRepository bancaTccMembroRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- ABRE UM NOVO TCC PARA UM ALUNO ---
    @Transactional
    public TccResponseDTO abrir(TccRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.getAlunoId()));

        Professor orientador = buscarProfessor(dto.getProfessorOrientadorId());
        Professor coorientador = dto.getProfessorCoorientadorId() == null ? null
                : buscarProfessor(dto.getProfessorCoorientadorId());

        Tcc tcc = Tcc.builder()
                .aluno(aluno)
                .titulo(dto.getTitulo())
                .professorOrientador(orientador)
                .professorCoorientador(coorientador)
                .periodoLetivo(dto.getPeriodoLetivo())
                .status(StatusTcc.EM_ANDAMENTO)
                .build();

        return toResponse(tccRepository.save(tcc));
    }

    // --- LISTA TCCS COM FILTROS OPCIONAIS ---
    @Transactional(readOnly = true)
    public Page<TccResponseDTO> listar(Long alunoId, Long professorOrientadorId, StatusTcc status, Pageable pageable) {
        Page<Tcc> page;

        if (alunoId != null) {
            page = tccRepository.findByAlunoId(alunoId, pageable);
        } else if (professorOrientadorId != null) {
            page = tccRepository.findByProfessorOrientadorId(professorOrientadorId, pageable);
        } else if (status != null) {
            page = tccRepository.findByStatus(status, pageable);
        } else {
            page = tccRepository.findAll(pageable);
        }

        return page.map(this::toResponse);
    }

    // --- BUSCA UM TCC PELO SEU IDENTIFICADOR ---
    @Transactional(readOnly = true)
    public TccResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    // --- BUSCA O TCC EM ANDAMENTO (NÃO FINALIZADO) DO ALUNO AUTENTICADO ---
    @Transactional(readOnly = true)
    public TccResponseDTO meuTcc() {
        Aluno aluno = alunoDoAutenticado();
        return tccRepository.findFirstByAlunoIdAndStatusNotIn(aluno.getId(), STATUS_FINALIZADOS)
                .map(this::toResponse)
                .orElseThrow(() -> new BusinessException("Você não possui TCC em andamento."));
    }

    // --- AGENDA A DATA DE DEFESA E MARCA O TCC COMO AGUARDANDO BANCA ---
    @Transactional
    public TccResponseDTO agendarDefesa(Long id, AgendarDefesaRequestDTO dto) {
        Tcc tcc = buscarEntidade(id);

        if (tcc.getStatus() != StatusTcc.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível agendar a defesa de um TCC em andamento.");
        }

        tcc.setDataDefesa(dto.getDataDefesa());
        tcc.setStatus(StatusTcc.AGUARDANDO_BANCA);

        return toResponse(tccRepository.save(tcc));
    }

    // --- ENCERRA O TCC COM BASE NA MÉDIA DAS NOTAS LANÇADAS PELA BANCA ---
    @Transactional
    public TccResponseDTO finalizarComNotaDaBanca(Long id) {
        Tcc tcc = buscarEntidade(id);

        if (tcc.getStatus() != StatusTcc.AGUARDANDO_BANCA) {
            throw new BusinessException("O TCC precisa estar aguardando banca para ser finalizado.");
        }

        List<BigDecimal> notas = bancaTccMembroRepository.findByTccId(id).stream()
                .map(membro -> membro.getNota())
                .filter(nota -> nota != null)
                .toList();

        if (notas.isEmpty()) {
            throw new BusinessException("Nenhum membro da banca lançou nota ainda.");
        }

        BigDecimal soma = notas.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal media = soma.divide(BigDecimal.valueOf(notas.size()), ESCALA_NOTA, RoundingMode.HALF_UP);

        tcc.setNotaFinal(media);
        tcc.setStatus(media.compareTo(BigDecimal.valueOf(6)) >= 0 ? StatusTcc.APROVADO : StatusTcc.REPROVADO);

        return toResponse(tccRepository.save(tcc));
    }

    // --- EXPÕE A ENTIDADE PARA OS SERVICES DE ENTREGAS E BANCA ---
    @Transactional(readOnly = true)
    public Tcc buscarEntidade(Long id) {
        return tccRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TCC", id));
    }

    private Professor buscarProfessor(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));
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
    private TccResponseDTO toResponse(Tcc t) {
        return TccResponseDTO.builder()
                .id(t.getId())
                .alunoId(t.getAluno().getId())
                .alunoNome(t.getAluno().getUsuario() == null ? null : t.getAluno().getUsuario().getNome())
                .titulo(t.getTitulo())
                .professorOrientadorId(t.getProfessorOrientador().getId())
                .professorOrientadorNome(t.getProfessorOrientador().getUsuario().getNome())
                .professorCoorientadorId(t.getProfessorCoorientador() == null ? null : t.getProfessorCoorientador().getId())
                .professorCoorientadorNome(t.getProfessorCoorientador() == null ? null
                        : t.getProfessorCoorientador().getUsuario().getNome())
                .periodoLetivo(t.getPeriodoLetivo())
                .status(t.getStatus())
                .notaFinal(t.getNotaFinal())
                .dataDefesa(t.getDataDefesa())
                .criadoEm(t.getCriadoEm())
                .atualizadoEm(t.getAtualizadoEm())
                .build();
    }
}
