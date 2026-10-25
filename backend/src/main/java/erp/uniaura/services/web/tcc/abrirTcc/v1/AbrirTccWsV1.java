package erp.uniaura.services.web.tcc.abrirTcc.v1;

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
public class AbrirTccWsV1 extends BaseWs {

    private final AbrirTccServiceV1 abrirTccServiceV1;

    // --- ENDPOINT QUE ABRE UM NOVO TCC PARA UM ALUNO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Abre um novo TCC para um aluno",
            description = "Cria o TCC EM_ANDAMENTO e devolve o uid gerado. Erro 422 se o aluno, o orientador ou o coorientador informados não existirem.")
    public Uid abrir(@Valid @RequestBody AbrirTccParamsV1 params) {
        return abrirTccServiceV1.abrir(params);
    }
}
