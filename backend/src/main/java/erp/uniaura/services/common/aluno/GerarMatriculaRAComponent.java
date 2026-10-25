package erp.uniaura.services.common.aluno;

import cloudsupport.services.BaseComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class GerarMatriculaRAComponent extends BaseComponent {

    private final BuscarAlunoPorMatriculaComponent buscarAlunoPorMatriculaComponent;

    private final SecureRandom random = new SecureRandom();

    // --- GERA UM RA DE 8 NÚMEROS E GARANTE QUE AINDA NÃO É DE NENHUM ALUNO ---
    public String gerar() {
        String ra;

        do {
            ra = String.valueOf(10_000_000 + random.nextInt(90_000_000));
        } while (buscarAlunoPorMatriculaComponent.buscar(ra).isPresent());

        return ra;
    }
}