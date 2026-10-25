package erp.uniaura.services.web.tcc.pesquisarMembrosDaBanca.v1;

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
public class PesquisarMembrosDaBancaWsV1 extends BaseWs {

    private final PesquisarMembrosDaBancaServiceV1 pesquisarMembrosDaBancaServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa os membros da banca de um TCC de forma paginada",
            description = "Lista os professores da banca com as notas e pareceres já lançados, na ordem de convocação. A paginação usa 'de' e 'qtd'. Erro 422 se o TCC não existir.")
    public PesquisarMembrosDaBancaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarMembrosDaBancaParamsV1 params) {
        return pesquisarMembrosDaBancaServiceV1.pesquisar(params);
    }
}
