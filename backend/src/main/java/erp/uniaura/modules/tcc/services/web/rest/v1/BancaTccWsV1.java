package erp.uniaura.modules.tcc.services.web.rest.v1;

import erp.uniaura.modules.tcc.dto.BancaTccMembroRequestDTO;
import erp.uniaura.modules.tcc.dto.BancaTccMembroResponseDTO;
import erp.uniaura.modules.tcc.dto.LancarNotaBancaRequestDTO;
import erp.uniaura.modules.tcc.service.BancaTccService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Ws("/tccs/{tccId}/banca")
@RequiredArgsConstructor
@Tag(name = "Banca de TCC", description = "Convocação de membros e lançamento de notas da banca examinadora")
public class BancaTccWsV1 {

    private final BancaTccService bancaTccService;

    // --- CONVOCA UM PROFESSOR PARA COMPOR A BANCA ---
    @PostMapping
    @Operation(summary = "Convoca um professor para compor a banca do TCC")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<BancaTccMembroResponseDTO> convocar(@PathVariable Long tccId, @Valid @RequestBody BancaTccMembroRequestDTO dto) {
        return ResponseEntity.ok(bancaTccService.convocar(tccId, dto));
    }

    // --- LISTA OS MEMBROS DA BANCA ---
    @GetMapping
    @Operation(summary = "Lista os membros da banca de um TCC")
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<List<BancaTccMembroResponseDTO>> listarPorTcc(@PathVariable Long tccId) {
        return ResponseEntity.ok(bancaTccService.listarPorTcc(tccId));
    }

    // --- LANÇA A NOTA E O PARECER DE UM MEMBRO DA BANCA ---
    @PutMapping("/{id}/nota")
    @Operation(summary = "Lança a nota e o parecer de um membro da banca")
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<BancaTccMembroResponseDTO> lancarNota(@PathVariable Long tccId, @PathVariable Long id,
            @Valid @RequestBody LancarNotaBancaRequestDTO dto) {
        return ResponseEntity.ok(bancaTccService.lancarNota(id, dto));
    }
}
