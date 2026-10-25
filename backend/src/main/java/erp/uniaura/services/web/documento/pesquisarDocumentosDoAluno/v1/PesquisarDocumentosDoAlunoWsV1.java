package erp.uniaura.services.web.documento.pesquisarDocumentosDoAluno.v1;

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
public class PesquisarDocumentosDoAlunoWsV1 extends BaseWs {

    private final PesquisarDocumentosDoAlunoServiceV1 pesquisarDocumentosDoAlunoServiceV1;

    // --- ENDPOINT QUE LISTA OS DOCUMENTOS DE UM ALUNO DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa os documentos de um aluno de forma paginada",
            description = "Lista os documentos do aluno. Com 'tipo' informado e ordenação '-versao', devolve o histórico de versões enviadas desse tipo. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o aluno não existir.")
    public PesquisarDocumentosDoAlunoRetornoV1 pesquisar(@Valid @ParameterObject PesquisarDocumentosDoAlunoParamsV1 params) {
        return pesquisarDocumentosDoAlunoServiceV1.pesquisar(params);
    }
}
