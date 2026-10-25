package erp.uniaura.services.web.professor.cadastrarProfessor.v1;

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
public class CadastrarProfessorWsV1 extends BaseWs {

    private final CadastrarProfessorServiceV1 cadastrarProfessorServiceV1;

    // --- ENDPOINT QUE CADASTRA UM PROFESSOR E SEU USUÁRIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Cadastra um professor",
            description = "Cria o professor e o usuário vinculado (com perfil PROFESSOR) e devolve o uid do professor. "
                    + "Erro 422 se já existir usuário com o mesmo e-mail. Sem 'ativo', o professor é cadastrado como ativo.")
    public Uid cadastrar(@Valid @RequestBody CadastrarProfessorParamsV1 params) {
        return cadastrarProfessorServiceV1.cadastrar(params);
    }
}
