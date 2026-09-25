package erp.uniaura.modules.biblioteca.config.rest;

import erp.uniaura.modules.biblioteca.config.dto.ConfiguracaoBibliotecaDTO;
import erp.uniaura.modules.biblioteca.config.model.ConfiguracaoBiblioteca;
import erp.uniaura.modules.biblioteca.config.service.ConfiguracaoBibliotecaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biblioteca/configuracoes")
@RequiredArgsConstructor
@Tag(name = "Biblioteca - Configurações", description = "Prazos, limites e valor da multa")
public class ConfiguracaoBibliotecaController {

    private final ConfiguracaoBibliotecaService service;

    // --- OBTÉM AS CONFIGURAÇÕES ATUAIS DA BIBLIOTECA ---
    @GetMapping
    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    public ResponseEntity<ConfiguracaoBiblioteca> obter() {
        return ResponseEntity.ok(service.obter());
    }

    // --- ATUALIZA OS PRAZOS, LIMITES E O VALOR DA MULTA DA BIBLIOTECA ---
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','BIBLIOTECARIO')")
    public ResponseEntity<ConfiguracaoBiblioteca> atualizar(@Valid @RequestBody ConfiguracaoBibliotecaDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }
}

