package erp.uniaura.services.web.lgpd.exportarMeusDadosPessoais.v1;

import erp.uniaura.services.web.aluno.consultarMeuPerfilDeAluno.v1.ConsultarMeuPerfilDeAlunoRetornoV1;
import erp.uniaura.services.web.autenticacao.consultarUsuarioLogado.v1.ConsultarUsuarioLogadoRetornoV1;
import erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1.PesquisarMeusDocumentosDocumentoRetornoV1;
import erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1.PesquisarMinhasMensalidadesMensalidadeRetornoV1;
import erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1.PesquisarNotasDoAlunoNotaRetornoV1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ExportarMeusDadosPessoaisRetornoV1 {

    private ConsultarUsuarioLogadoRetornoV1                         usuario;

    @Schema(description = "Perfil de aluno; vazio quando o usuário não é aluno")
    private ConsultarMeuPerfilDeAlunoRetornoV1                      aluno;
    private List<PesquisarMinhasMensalidadesMensalidadeRetornoV1>   mensalidades;
    private List<PesquisarNotasDoAlunoNotaRetornoV1>                notas;
    private List<PesquisarMeusDocumentosDocumentoRetornoV1>         documentos;

    @Schema(description = "Momento da exportação")
    private LocalDateTime                                           exportadoEm;
}
