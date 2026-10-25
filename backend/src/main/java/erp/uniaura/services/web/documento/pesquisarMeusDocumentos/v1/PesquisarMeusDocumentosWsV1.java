package erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1;

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
public class PesquisarMeusDocumentosWsV1 extends BaseWs {

    private final PesquisarMeusDocumentosServiceV1 pesquisarMeusDocumentosServiceV1;

    // --- ENDPOINT QUE LISTA OS DOCUMENTOS DO ALUNO LOGADO ---
    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Pesquisa os documentos do aluno autenticado de forma paginada",
            description = "Lista os documentos enviados pelo aluno autenticado. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o usuário não tiver cadastro de aluno.")
    public PesquisarMeusDocumentosRetornoV1 pesquisar(@LoggedInUserId String usuarioId, @Valid @ParameterObject PesquisarMeusDocumentosParamsV1 params) {
        return pesquisarMeusDocumentosServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
