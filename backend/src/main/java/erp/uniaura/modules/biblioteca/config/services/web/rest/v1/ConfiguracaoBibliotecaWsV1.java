package erp.uniaura.modules.biblioteca.config.services.web.rest.v1;

import erp.uniaura.modules.biblioteca.config.dto.ConfiguracaoBibliotecaDTO;
import erp.uniaura.modules.biblioteca.config.model.ConfiguracaoBiblioteca;
import erp.uniaura.modules.biblioteca.config.service.ConfiguracaoBibliotecaService;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Ws("/biblioteca/configuracoes")
@RequiredArgsConstructor
@Tag(name = "Biblioteca - Configurações", description = "Prazos, limites e valor da multa")
public class ConfiguracaoBibliotecaWsV1 {

    private final ConfiguracaoBibliotecaService configuracaoBibliotecaService;

    // --- OBTÉM AS CONFIGURAÇÕES ATUAIS DA BIBLIOTECA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    public ResponseEntity<ConfiguracaoBiblioteca> obter() {
        return ResponseEntity.ok(configuracaoBibliotecaService.obter());
    }

    // --- ATUALIZA OS PRAZOS, LIMITES E O VALOR DA MULTA DA BIBLIOTECA ---
    @PutMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_BIBLIOTECARIO"})
    public ResponseEntity<ConfiguracaoBiblioteca> atualizar(@Valid @RequestBody ConfiguracaoBibliotecaDTO dto) {
        return ResponseEntity.ok(configuracaoBibliotecaService.atualizar(dto));
    }
}

