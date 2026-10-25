package erp.uniaura.services.web.curso.alterarCurso.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AlterarCursoWsV1 extends BaseWs {

    private final AlterarCursoServiceV1 alterarCursoServiceV1;

    // --- ENDPOINT QUE ALTERA UM CURSO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Altera um curso",
            description = "Atualiza os dados do curso identificado pelo uid. Sem 'ativo', a situação atual é mantida. Erro 422 se o curso não existir.")
    public void alterar(@Valid @RequestBody AlterarCursoParamsV1 params) {
        alterarCursoServiceV1.alterar(params);
    }
}
