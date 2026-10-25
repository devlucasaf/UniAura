package erp.uniaura.services.web.estagio.abrirEstagio.v1;

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
public class AbrirEstagioWsV1 extends BaseWs {

    private final AbrirEstagioServiceV1 abrirEstagioServiceV1;

    // --- ENDPOINT QUE ABRE UM NOVO ESTÁGIO PARA UM ALUNO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Abre um novo estágio para um aluno",
            description = "Cria o estágio EM_ANDAMENTO, com carga horária cumprida zerada, e devolve o uid gerado. "
                    + "Erro 422 se o aluno ou o professor orientador informado não existirem ou se a data de fim prevista for anterior à de início.")
    public Uid abrir(@Valid @RequestBody AbrirEstagioParamsV1 params) {
        return abrirEstagioServiceV1.abrir(params);
    }
}
