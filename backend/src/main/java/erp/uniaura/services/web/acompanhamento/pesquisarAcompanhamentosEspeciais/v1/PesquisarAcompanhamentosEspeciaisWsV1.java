package erp.uniaura.services.web.acompanhamento.pesquisarAcompanhamentosEspeciais.v1;

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
public class PesquisarAcompanhamentosEspeciaisWsV1 extends BaseWs {

    private final PesquisarAcompanhamentosEspeciaisServiceV1 pesquisarAcompanhamentosEspeciaisServiceV1;

    // --- SÓ O ADMIN E A COORDENAÇÃO CONSULTAM A LISTA DE ACOMPANHAMENTOS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Pesquisa acompanhamentos de alunos especiais de forma paginada",
            description = "Lista os acompanhamentos, filtrando opcionalmente por status, tipo de necessidade e aluno. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o aluno do filtro não existir.")
    public PesquisarAcompanhamentosEspeciaisRetornoV1 pesquisar(@Valid @ParameterObject PesquisarAcompanhamentosEspeciaisParamsV1 params) {
        return pesquisarAcompanhamentosEspeciaisServiceV1.pesquisar(params);
    }
}
