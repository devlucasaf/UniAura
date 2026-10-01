package erp.uniaura.modules.tcc.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.tcc.dto.BancaTccMembroRequestDTO;
import erp.uniaura.modules.tcc.dto.BancaTccMembroResponseDTO;
import erp.uniaura.modules.tcc.dto.LancarNotaBancaRequestDTO;
import erp.uniaura.modules.tcc.model.BancaTccMembro;
import erp.uniaura.modules.tcc.model.Tcc;
import erp.uniaura.modules.tcc.repository.BancaTccMembroRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BancaTccService {

    private final BancaTccMembroRepository bancaTccMembroRepository;
    private final TccService tccService;
    private final ProfessorRepository professorRepository;

    // --- CONVOCA UM PROFESSOR PARA COMPOR A BANCA DO TCC ---
    @Transactional
    public BancaTccMembroResponseDTO convocar(Long tccId, BancaTccMembroRequestDTO dto) {
        Tcc tcc = tccService.buscarEntidade(tccId);
        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor", dto.getProfessorId()));

        if (bancaTccMembroRepository.existsByTccIdAndProfessorId(tccId, professor.getId())) {
            throw new BusinessException("Este professor já compõe a banca deste TCC.");
        }

        BancaTccMembro membro = BancaTccMembro.builder()
                .tcc(tcc)
                .professor(professor)
                .build();

        return toResponse(bancaTccMembroRepository.save(membro));
    }

    // --- LISTA OS MEMBROS DA BANCA DE UM TCC ---
    @Transactional(readOnly = true)
    public List<BancaTccMembroResponseDTO> listarPorTcc(Long tccId) {
        return bancaTccMembroRepository.findByTccId(tccId).stream().map(this::toResponse).toList();
    }

    // --- LANÇA A NOTA E O PARECER DE UM MEMBRO DA BANCA ---
    @Transactional
    public BancaTccMembroResponseDTO lancarNota(Long id, LancarNotaBancaRequestDTO dto) {
        BancaTccMembro membro = buscarEntidade(id);
        membro.setNota(dto.getNota());
        membro.setParecer(dto.getParecer());
        return toResponse(bancaTccMembroRepository.save(membro));
    }

    private BancaTccMembro buscarEntidade(Long id) {
        return bancaTccMembroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membro da banca", id));
    }

    private BancaTccMembroResponseDTO toResponse(BancaTccMembro m) {
        return BancaTccMembroResponseDTO.builder()
                .id(m.getId())
                .tccId(m.getTcc().getId())
                .professorId(m.getProfessor().getId())
                .professorNome(m.getProfessor().getUsuario().getNome())
                .nota(m.getNota())
                .parecer(m.getParecer())
                .build();
    }
}
