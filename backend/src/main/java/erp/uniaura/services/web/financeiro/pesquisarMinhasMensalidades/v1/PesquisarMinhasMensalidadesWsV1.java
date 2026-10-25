package erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarMinhasMensalidadesWsV1 extends BaseWs {

    private final PesquisarMinhasMensalidadesServiceV1 pesquisarMinhasMensalidadesServiceV1;

    // --- O ALUNO LISTA SOMENTE AS PRÓPRIAS MENSALIDADES ---
    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Pesquisa as mensalidades do aluno autenticado de forma paginada",
            description = "Lista as mensalidades do aluno logado, com os dados do pagamento quando já quitadas. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o usuário logado não tiver cadastro de aluno.")
    public PesquisarMinhasMensalidadesRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarMinhasMensalidadesParamsV1 params) {
        return pesquisarMinhasMensalidadesServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
