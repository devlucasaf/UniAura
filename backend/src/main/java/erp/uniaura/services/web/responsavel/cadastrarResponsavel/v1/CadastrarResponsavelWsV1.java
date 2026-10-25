package erp.uniaura.services.web.responsavel.cadastrarResponsavel.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CadastrarResponsavelWsV1 extends BaseWs {

    private final CadastrarResponsavelServiceV1 cadastrarResponsavelServiceV1;

    // --- ENDPOINT QUE CADASTRA UM RESPONSÁVEL E SEU USUÁRIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Cadastra um responsável",
            description = "Cria o responsável e o usuário vinculado (com perfil RESPONSAVEL) e devolve o uid do responsável. "
                    + "Erro 422 se já existir usuário com o mesmo e-mail.")
    public Uid cadastrar(@Valid @RequestBody CadastrarResponsavelParamsV1 params) {
        return cadastrarResponsavelServiceV1.cadastrar(params);
    }
}
