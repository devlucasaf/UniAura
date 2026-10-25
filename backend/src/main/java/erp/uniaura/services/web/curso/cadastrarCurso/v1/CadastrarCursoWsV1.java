package erp.uniaura.services.web.curso.cadastrarCurso.v1;

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
public class CadastrarCursoWsV1 extends BaseWs {

    private final CadastrarCursoServiceV1 cadastrarCursoServiceV1;

    // --- ENDPOINT QUE CADASTRA UM CURSO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Cadastra um curso",
            description = "Cria o curso e devolve o uid gerado. Sem 'ativo', o curso é cadastrado como ativo.")
    public Uid cadastrar(@Valid @RequestBody CadastrarCursoParamsV1 params) {
        return cadastrarCursoServiceV1.cadastrar(params);
    }
}
