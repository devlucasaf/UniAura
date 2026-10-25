package erp.uniaura.services.web.preMatricula.realizarPreMatricula.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RealizarPreMatriculaWsV1 extends BaseWs {

    private final RealizarPreMatriculaServiceV1 realizarPreMatriculaServiceV1;

    // --- ENDPOINT PÚBLICO QUE REALIZA A PRÉ-MATRÍCULA ---
    @PostMapping
    @Operation(summary = "Realiza a pré-matrícula pública",
            description = "Cria o usuário de acesso (perfil ALUNO) e o cadastro acadêmico, gera o RA e devolve os dados básicos. "
                    + "Erro 422 se o e-mail já estiver em uso.")
    public RealizarPreMatriculaRetornoV1 realizar(@Valid @RequestBody RealizarPreMatriculaParamsV1 params) {
        return realizarPreMatriculaServiceV1.realizar(params);
    }
}
