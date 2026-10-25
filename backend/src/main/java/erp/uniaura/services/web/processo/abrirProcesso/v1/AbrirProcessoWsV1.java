package erp.uniaura.services.web.processo.abrirProcesso.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AbrirProcessoWsV1 extends BaseWs {

    private final AbrirProcessoServiceV1 abrirProcessoServiceV1;

    // --- SÓ O ALUNO ABRE UM REQUERIMENTO, SEMPRE EM NOME DO USUÁRIO AUTENTICADO ---
    @PostMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Abre um novo processo em nome do aluno autenticado",
            description = "Cria o requerimento com status ABERTO, registra o primeiro passo da linha do tempo e devolve o uid e o protocolo. "
                    + "Erro 422 se o usuário não tiver cadastro de aluno ou se não for possível gerar um protocolo único.")
    public AbrirProcessoRetornoV1 abrir(@LoggedInUserId String usuarioId, @Valid @RequestBody AbrirProcessoParamsV1 params) {
        return abrirProcessoServiceV1.abrir(Long.valueOf(usuarioId), params);
    }
}
