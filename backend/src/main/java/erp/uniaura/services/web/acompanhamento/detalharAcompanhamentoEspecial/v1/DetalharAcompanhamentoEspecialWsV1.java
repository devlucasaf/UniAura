package erp.uniaura.services.web.acompanhamento.detalharAcompanhamentoEspecial.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class DetalharAcompanhamentoEspecialWsV1 extends BaseWs {

    private final DetalharAcompanhamentoEspecialServiceV1 detalharAcompanhamentoEspecialServiceV1;

    // --- SÓ O ADMIN E A COORDENAÇÃO CONSULTAM O DETALHE DE UM ACOMPANHAMENTO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Detalha o acompanhamento de um aluno especial",
            description = "Devolve os dados completos do acompanhamento identificado pelo uid, incluindo a descrição da necessidade (dado sensível). Erro 422 se o acompanhamento não existir.")
    public DetalharAcompanhamentoEspecialRetornoV1 detalhar(@Valid @ParameterObject DetalharAcompanhamentoEspecialParamsV1 params) {
        return detalharAcompanhamentoEspecialServiceV1.detalhar(params);
    }
}
