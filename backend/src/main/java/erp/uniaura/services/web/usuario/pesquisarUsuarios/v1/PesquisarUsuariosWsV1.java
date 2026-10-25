package erp.uniaura.services.web.usuario.pesquisarUsuarios.v1;

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
public class PesquisarUsuariosWsV1 extends BaseWs {

    private final PesquisarUsuariosServiceV1 pesquisarUsuariosServiceV1;

    // --- SÓ O ADMIN, A COORDENAÇÃO E A SECRETARIA LISTAM USUÁRIOS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa usuários por perfil de forma paginada",
            description = "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarUsuariosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarUsuariosParamsV1 params) {
        return pesquisarUsuariosServiceV1.pesquisar(params);
    }
}
