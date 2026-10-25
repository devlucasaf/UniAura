package erp.uniaura.services.web.aluno.detalharAluno.v1;

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
public class DetalharAlunoWsV1 extends BaseWs {

    private final DetalharAlunoServiceV1 detalharAlunoServiceV1;

    // --- A EQUIPE ACADÊMICA E OS PROFESSORES CONSULTAM UM ALUNO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha um aluno",
            description = "Devolve os dados acadêmicos e de contato, achando o aluno pelo 'uid' ou pela 'matriculaRA'. "
                    + "Erro 422 se nenhum dos dois for informado ou se o aluno não existir.")
    public DetalharAlunoRetornoV1 detalhar(@Valid @ParameterObject DetalharAlunoParamsV1 params) {
        return detalharAlunoServiceV1.detalhar(params);
    }
}
