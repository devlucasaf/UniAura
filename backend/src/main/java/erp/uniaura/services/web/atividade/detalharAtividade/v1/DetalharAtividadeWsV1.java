package erp.uniaura.services.web.atividade.detalharAtividade.v1;

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
public class DetalharAtividadeWsV1 extends BaseWs {

    private final DetalharAtividadeServiceV1 detalharAtividadeServiceV1;

    // --- A EQUIPE, OS PROFESSORES E OS ALUNOS CONSULTAM O DETALHE DE UMA ATIVIDADE ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO"})
    @Operation(summary = "Detalha uma atividade",
            description = "Devolve os dados da atividade identificada pelo uid. Erro 422 se a atividade não existir.")
    public DetalharAtividadeRetornoV1 detalhar(@Valid @ParameterObject DetalharAtividadeParamsV1 params) {
        return detalharAtividadeServiceV1.detalhar(params);
    }
}
