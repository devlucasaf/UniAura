package erp.uniaura.modules.atividade.services.web.rest.v1;

import erp.uniaura.modules.atividade.dto.AtividadeRequestDTO;
import erp.uniaura.modules.atividade.dto.AtividadeResponseDTO;
import erp.uniaura.modules.atividade.dto.AvaliarEntregaRequestDTO;
import erp.uniaura.modules.atividade.dto.EntregaAtividadeResponseDTO;
import erp.uniaura.modules.atividade.model.StatusEntrega;
import erp.uniaura.modules.atividade.service.AtividadeService;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/atividades")
@RequiredArgsConstructor
@Tag(name = "Atividades", description = "Atividades postadas pelo professor e entregas dos alunos")
public class AtividadeWsV1 {

    private final AtividadeService atividadeService;

    // --- LISTA ATIVIDADES DE UMA TURMA/DISCIPLINA ---
    @GetMapping("/turma-disciplina/{turmaDisciplinaId}")
    @Operation(summary = "Lista atividades de uma turma/disciplina")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO"})
    public ResponseEntity<Page<AtividadeResponseDTO>> listarPorTurmaDisciplina(@PathVariable Long turmaDisciplinaId, Pageable pageable) {
        return ResponseEntity.ok(atividadeService.listarPorTurmaDisciplina(turmaDisciplinaId, pageable));
    }

    // --- BUSCA UMA ATIVIDADE PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca atividade pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO"})
    public ResponseEntity<AtividadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(atividadeService.buscarPorId(id));
    }

    // --- PROFESSOR POSTA UMA NOVA ATIVIDADE ---
    @PostMapping
    @Operation(summary = "Posta uma nova atividade")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<AtividadeResponseDTO> criar(@Valid @RequestBody AtividadeRequestDTO dto, UriComponentsBuilder uriBuilder) {
        AtividadeResponseDTO criada = atividadeService.criar(dto);
        URI uri = uriBuilder.path("/atividades/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- PROFESSOR ATUALIZA ATIVIDADE EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma atividade")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<AtividadeResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AtividadeRequestDTO dto) {
        return ResponseEntity.ok(atividadeService.atualizar(id, dto));
    }

    // --- REMOVE A ATIVIDADE ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma atividade")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- ALUNO ENVIA ARQUIVO + COMENTÁRIO ---
    @PostMapping(value = "/{id}/entregar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Aluno entrega a atividade (upload de arquivo)")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<EntregaAtividadeResponseDTO> entregar(@PathVariable Long id, @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam(value = "comentarioAluno", required = false) String comentarioAluno) {
        return ResponseEntity.status(201).body(atividadeService.entregar(id, arquivo, comentarioAluno));
    }

    // --- PROFESSOR LISTA AS ENTREGAS DOS ALUNOS ---
    @GetMapping("/{id}/entregas")
    @Operation(summary = "Lista as entregas dos alunos para a atividade")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<List<EntregaAtividadeResponseDTO>> listarEntregas(@PathVariable Long id) {
        return ResponseEntity.ok(atividadeService.listarEntregasDaAtividade(id));
    }

    // --- PROFESSOR AVALIA UMA ENTREGA ---
    @PutMapping("/entregas/{entregaId}/avaliar")
    @Operation(summary = "Professor avalia uma entrega")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<EntregaAtividadeResponseDTO> avaliarEntrega(@PathVariable Long entregaId,
            @Valid @RequestBody AvaliarEntregaRequestDTO dto) {
        return ResponseEntity.ok(atividadeService.avaliar(entregaId, dto));
    }

    // --- HISTÓRICO DE ENTREGAS DE UM ALUNO ---
    @GetMapping("/alunos/{alunoId}/entregas")
    @Operation(summary = "Lista as entregas de um aluno (opcionalmente filtrando por status)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_RESPONSAVEL"})
    public ResponseEntity<Page<EntregaAtividadeResponseDTO>> listarEntregasDoAluno(@PathVariable Long alunoId,
            @RequestParam(required = false) StatusEntrega status, Pageable pageable) {
        return ResponseEntity.ok(atividadeService.listarEntregasDoAluno(alunoId, status, pageable));
    }
}

