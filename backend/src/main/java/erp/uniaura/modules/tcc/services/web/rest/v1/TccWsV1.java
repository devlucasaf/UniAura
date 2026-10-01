package erp.uniaura.modules.tcc.services.web.rest.v1;

import erp.uniaura.modules.tcc.dto.AgendarDefesaRequestDTO;
import erp.uniaura.modules.tcc.dto.TccRequestDTO;
import erp.uniaura.modules.tcc.dto.TccResponseDTO;
import erp.uniaura.modules.tcc.model.StatusTcc;
import erp.uniaura.modules.tcc.service.TccService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/tccs")
@RequiredArgsConstructor
@Tag(name = "TCC", description = "Abertura, acompanhamento, defesa e banca dos trabalhos de conclusão de curso")
public class TccWsV1 {

    private final TccService tccService;

    // --- ABRE UM NOVO TCC PARA UM ALUNO ---
    @PostMapping
    @Operation(summary = "Abre um novo TCC para um aluno")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<TccResponseDTO> abrir(@Valid @RequestBody TccRequestDTO dto, UriComponentsBuilder uriBuilder) {
        TccResponseDTO criado = tccService.abrir(dto);
        URI uri = uriBuilder.path("/tccs/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- LISTA TCCS, COM FILTROS OPCIONAIS ---
    @GetMapping
    @Operation(summary = "Lista TCCs (opcionalmente filtrando por aluno, orientador ou status)")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<TccResponseDTO>> listar(@RequestParam(required = false) Long aluno,
            @RequestParam(required = false) Long professorOrientador,
            @RequestParam(required = false) StatusTcc status, Pageable pageable) {
        return ResponseEntity.ok(tccService.listar(aluno, professorOrientador, status, pageable));
    }

    // --- BUSCA O TCC EM ANDAMENTO DO ALUNO AUTENTICADO ---
    @GetMapping("/meu")
    @Operation(summary = "Busca o TCC em andamento do aluno autenticado")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<TccResponseDTO> meuTcc() {
        return ResponseEntity.ok(tccService.meuTcc());
    }

    // --- BUSCA TCC PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca TCC pelo ID")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    public ResponseEntity<TccResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tccService.buscarPorId(id));
    }

    // --- AGENDA A DATA DE DEFESA ---
    @PutMapping("/{id}/defesa")
    @Operation(summary = "Agenda a data de defesa e marca o TCC como aguardando banca")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<TccResponseDTO> agendarDefesa(@PathVariable Long id, @Valid @RequestBody AgendarDefesaRequestDTO dto) {
        return ResponseEntity.ok(tccService.agendarDefesa(id, dto));
    }

    // --- FINALIZA O TCC COM BASE NA MÉDIA DAS NOTAS DA BANCA ---
    @PutMapping("/{id}/finalizacao")
    @Operation(summary = "Finaliza o TCC (aprovado/reprovado) com base na média das notas lançadas pela banca")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<TccResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(tccService.finalizarComNotaDaBanca(id));
    }
}
