package erp.uniaura.modules.material.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.modules.material.dto.MaterialRequestDTO;
import erp.uniaura.modules.material.dto.MaterialResponseDTO;
import erp.uniaura.modules.material.model.Material;
import erp.uniaura.modules.material.model.TipoMaterial;
import erp.uniaura.modules.material.repository.MaterialRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private static final String SUBDIR_MATERIAIS = "materiais";

    private final MaterialRepository materialRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final StorageService storageService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- LISTA OS MATERIAIS DE UMA TURMA + DISCIPLINA ---
    @Transactional(readOnly = true)
    public Page<MaterialResponseDTO> listarPorTurmaDisciplina(Long turmaId, Long disciplinaId, Pageable pageable) {
        return materialRepository.findByTurmaIdAndDisciplinaId(turmaId, disciplinaId, pageable)
                .map(this::toResponse);
    }

    // --- BUSCA UM MATERIAL PELO SEU ID ---
    @Transactional(readOnly = true)
    public MaterialResponseDTO buscarMaterialPorId(Long id) {
        return toResponse(buscarMaterialEntidade(id));
    }

    // --- CRIA UM MATERIAL PARA UMA TURMA/DISCIPLINA ---
    @Transactional
    public MaterialResponseDTO criarMaterialTurma(MaterialRequestDTO materialDTO, MultipartFile arquivo) {
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplina(materialDTO.getTurmaDisciplinaId());
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        Professor professor = validarProfessorDaDisciplina(turmaDisciplina, usuarioAutenticado);

        Material material = Material.builder()
                .turmaDisciplina(turmaDisciplina)
                .titulo(materialDTO.getTitulo())
                .descricao(materialDTO.getDescricao())
                .tipo(materialDTO.getTipo())
                .linkUrl(materialDTO.getLinkUrl())
                .professor(professor)
                .build();

        aplicarRegraDeArquivoOuLink(material, materialDTO, arquivo);

        return toResponse(materialRepository.save(material));
    }

    // --- ATUALIZA METADADOS DO MATERIAL ---
    @Transactional
    public MaterialResponseDTO atualizarMaterial(Long id, MaterialRequestDTO dto, MultipartFile arquivo) {
        Material material = buscarMaterialEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        validarPodeEditar(material, usuarioAutenticado);

        if (!material.getTurmaDisciplina().getId().equals(dto.getTurmaDisciplinaId())) {
            material.setTurmaDisciplina(buscarTurmaDisciplina(dto.getTurmaDisciplinaId()));
        }

        material.setTitulo(dto.getTitulo());
        material.setDescricao(dto.getDescricao());
        material.setTipo(dto.getTipo());
        material.setLinkUrl(dto.getLinkUrl());

        aplicarRegraDeArquivoOuLink(material, dto, arquivo);

        return toResponse(materialRepository.save(material));
    }

    // --- REMOVE O MATERIAL ---
    @Transactional
    public void deletarMaterial(Long id) {
        Material material = buscarMaterialEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        validarPodeEditar(material, usuarioAutenticado);

        if (material.getArquivoUrl() != null) {
            storageService.delete(material.getArquivoUrl());
        }
        materialRepository.delete(material);
    }

    // --- BUSCA UM MATERIAL PELO ID OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA ENCONTRADO ---
    private Material buscarMaterialEntidade(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", id));
    }

    // --- BUSCA O VÍNCULO ENTRE TURMA E DISCIPLINA OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA ENCONTRADO ---
    private TurmaDisciplina buscarTurmaDisciplina(Long id) {
        return turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", id));
    }

    // --- GARANTE COERÊNCIA ---
    private void aplicarRegraDeArquivoOuLink(Material material, MaterialRequestDTO materialDTO, MultipartFile arquivo) {
        if (materialDTO.getTipo() == TipoMaterial.LINK) {
            if (materialDTO.getLinkUrl() == null || materialDTO.getLinkUrl().isBlank()) {
                throw new BusinessException("Para tipo LINK é obrigatório informar 'linkUrl'.");
            }

            if (material.getArquivoUrl() != null) {
                storageService.delete(material.getArquivoUrl());
                material.setArquivoUrl(null);
            }
            return;
        }

        if (arquivo != null && !arquivo.isEmpty()) {
            if (material.getArquivoUrl() != null) {
                storageService.delete(material.getArquivoUrl());
            }

            material.setArquivoUrl(storageService.store(arquivo, SUBDIR_MATERIAIS));
            material.setLinkUrl(null);
        } else if (material.getArquivoUrl() == null) {
            throw new BusinessException("Para o tipo " + materialDTO.getTipo() + " é obrigatório enviar um arquivo.");
        }
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA IDENTIFICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- VALIDA SE O PROFESSOR AUTENTICADO É O RESPONSÁVEL PELA DISCIPLINA DA TURMA ---
    private Professor validarProfessorDaDisciplina(TurmaDisciplina td, Usuario autenticado) {
        Professor professor = td.getProfessor();
        if (professor == null || professor.getUsuario() == null) {
            throw new BusinessException("Vínculo turma/disciplina não possui professor responsável.");
        }

        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return professor;
        }

        if (!professor.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pela disciplina pode realizar esta operação.");
        }
        return professor;
    }

    // --- VERIFICA SE O USUÁRIO AUTENTICADO POSSUI PERMISSÃO PARA EDITAR O MATERIAL ---
    private void validarPodeEditar(Material material, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return;
        }

        if (!material.getProfessor().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pode editar este material.");
        }
    }

    // --- CONVERTE A ENTIDADE MATERIAL EM UM DTO DE RESPOSTA ---
    private MaterialResponseDTO toResponse(Material material) {
        return MaterialResponseDTO.builder()
                .id(material.getId())
                .turmaDisciplinaId(material.getTurmaDisciplina().getId())
                .titulo(material.getTitulo())
                .descricao(material.getDescricao())
                .tipo(material.getTipo())
                .arquivoUrl(material.getArquivoUrl())
                .linkUrl(material.getLinkUrl())
                .professorId(material.getProfessor().getId())
                .professorNome(material.getProfessor().getUsuario() == null ? null : material.getProfessor().getUsuario().getNome())
                .criadoEm(material.getCriadoEm())
                .atualizadoEm(material.getAtualizadoEm())
                .build();
    }
}

