package erp.uniaura.services.web.certificado.pesquisarCertificadosEmitidos.v1;

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
public class PesquisarCertificadosEmitidosWsV1 extends BaseWs {

    private final PesquisarCertificadosEmitidosServiceV1 pesquisarCertificadosEmitidosServiceV1;

    // --- ENDPOINT QUE LISTA OS CERTIFICADOS EMITIDOS DE UM ALUNO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa os certificados já emitidos para um aluno de forma paginada",
            description = "Lista o histórico de emissões do aluno. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o aluno não existir.")
    public PesquisarCertificadosEmitidosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarCertificadosEmitidosParamsV1 params) {
        return pesquisarCertificadosEmitidosServiceV1.pesquisar(params);
    }
}
