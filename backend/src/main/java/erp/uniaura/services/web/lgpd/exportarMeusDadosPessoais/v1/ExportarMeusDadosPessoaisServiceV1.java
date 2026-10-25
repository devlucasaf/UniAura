package erp.uniaura.services.web.lgpd.exportarMeusDadosPessoais.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.auditoria.RegistrarAuditoriaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;
import erp.uniaura.services.web.aluno.consultarMeuPerfilDeAluno.v1.ConsultarMeuPerfilDeAlunoServiceV1;
import erp.uniaura.services.web.autenticacao.consultarUsuarioLogado.v1.ConsultarUsuarioLogadoServiceV1;
import erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1.PesquisarMeusDocumentosDocumentoRetornoV1;
import erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1.PesquisarMeusDocumentosParamsV1;
import erp.uniaura.services.web.documento.pesquisarMeusDocumentos.v1.PesquisarMeusDocumentosServiceV1;
import erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1.PesquisarMinhasMensalidadesMensalidadeRetornoV1;
import erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1.PesquisarMinhasMensalidadesParamsV1;
import erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1.PesquisarMinhasMensalidadesServiceV1;
import erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1.PesquisarNotasDoAlunoNotaRetornoV1;
import erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1.PesquisarNotasDoAlunoParamsV1;
import erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1.PesquisarNotasDoAlunoServiceV1;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportarMeusDadosPessoaisServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final RegistrarAuditoriaComponent registrarAuditoriaComponent;
    private final ConsultarUsuarioLogadoServiceV1 consultarUsuarioLogadoServiceV1;
    private final ConsultarMeuPerfilDeAlunoServiceV1 consultarMeuPerfilDeAlunoServiceV1;
    private final PesquisarMinhasMensalidadesServiceV1 pesquisarMinhasMensalidadesServiceV1;
    private final PesquisarNotasDoAlunoServiceV1 pesquisarNotasDoAlunoServiceV1;
    private final PesquisarMeusDocumentosServiceV1 pesquisarMeusDocumentosServiceV1;

    // --- DIREITO DE ACESSO DA LGPD ---
    @Transactional(readOnly = true)
    public ExportarMeusDadosPessoaisRetornoV1 exportar(Long usuarioId) {
        Usuario usuario = buscarUsuarioComponent.buscarPorId(usuarioId);

        ExportarMeusDadosPessoaisRetornoV1 retorno = new ExportarMeusDadosPessoaisRetornoV1();

        retorno.setUsuario(consultarUsuarioLogadoServiceV1.consultar(usuarioId));
        retorno.setExportadoEm(LocalDateTime.now());
        retorno.setMensalidades(List.of());
        retorno.setNotas(List.of());
        retorno.setDocumentos(List.of());

        if (usuario.getRole() == TipoUsuario.ALUNO) {
            Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

            retorno.setAluno(consultarMeuPerfilDeAlunoServiceV1.consultar(usuarioId));
            retorno.setMensalidades(mensalidades(usuarioId));
            retorno.setNotas(notas(aluno.getUid()));
            retorno.setDocumentos(documentos(usuarioId));
        }

        registrarAuditoriaComponent.registrar(usuario, "EXPORTACAO_DADOS_LGPD", "Usuario", usuario.getId(),
                "Usuário exportou seus próprios dados pessoais (direito de acesso da LGPD).");

        return retorno;
    }

    // --- BUSCA TODAS AS MENSALIDADES DO USUÁRIO ---
    private List<PesquisarMinhasMensalidadesMensalidadeRetornoV1> mensalidades(Long usuarioId) {
        PesquisarMinhasMensalidadesParamsV1 params = new PesquisarMinhasMensalidadesParamsV1();

        params.setQtd(Integer.MAX_VALUE);

        return pesquisarMinhasMensalidadesServiceV1.pesquisar(usuarioId, params).getLista();
    }

    // --- BUSCA TODAS AS NOTAS DO ALUNO ---
    private List<PesquisarNotasDoAlunoNotaRetornoV1> notas(Long alunoUid) {
        PesquisarNotasDoAlunoParamsV1 params = new PesquisarNotasDoAlunoParamsV1();

        params.setAlunoUid(alunoUid);
        params.setQtd(Integer.MAX_VALUE);

        return pesquisarNotasDoAlunoServiceV1.pesquisar(params).getLista();
    }

    // --- BUSCA TODOS OS DOCUMENTOS DO USUÁRIO ---
    private List<PesquisarMeusDocumentosDocumentoRetornoV1> documentos(Long usuarioId) {
        PesquisarMeusDocumentosParamsV1 params = new PesquisarMeusDocumentosParamsV1();

        params.setQtd(Integer.MAX_VALUE);

        return pesquisarMeusDocumentosServiceV1.pesquisar(usuarioId, params).getLista();
    }
}
