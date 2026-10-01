package erp.uniaura.modules.coordenacao.services.web.rest.v1;

import erp.uniaura.modules.coordenacao.dto.PainelCoordenacaoResponseDTO;
import erp.uniaura.modules.coordenacao.service.PainelCoordenacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Ws("/painel-coordenacao")
@RequiredArgsConstructor
@Tag(name = "Painel da Coordenação", description = "Indicadores acadêmicos consolidados (matrícula, desempenho, frequência, ocupação)")
public class PainelCoordenacaoWsV1 {

    private final PainelCoordenacaoService painelCoordenacaoService;

    // --- CONSOLIDA OS INDICADORES ACADÊMICOS, OPCIONALMENTE FILTRADOS POR CURSO E PERÍODO LETIVO ---
    @GetMapping
    @Operation(summary = "Consolida os indicadores acadêmicos de um curso/período letivo (ou de todos, se omitidos)")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<PainelCoordenacaoResponseDTO> consolidar(
            @RequestParam(required = false) Long curso, @RequestParam(required = false) String periodoLetivo) {
        return ResponseEntity.ok(painelCoordenacaoService.consolidar(curso, periodoLetivo));
    }
}
