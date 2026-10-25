package erp.uniaura.services.web.certificado.emitirDeclaracaoDeMatricula.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.TipoCertificado;
import erp.uniaura.services.common.certificado.BuscarAlunoParaEmissaoComponent;
import erp.uniaura.services.common.certificado.MontarPdfDeCertificadoComponent;
import erp.uniaura.services.common.certificado.RegistrarEmissaoDeCertificadoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmitirDeclaracaoDeMatriculaServiceV1 extends BaseService {

    private final BuscarAlunoParaEmissaoComponent buscarAlunoParaEmissaoComponent;
    private final RegistrarEmissaoDeCertificadoComponent registrarEmissaoDeCertificadoComponent;
    private final MontarPdfDeCertificadoComponent montarPdfDeCertificadoComponent;

    // --- EMITE A DECLARAÇÃO DE MATRÍCULA EM PDF E REGISTRA A EMISSÃO ---
    @Transactional
    public byte[] emitir(Long usuarioId, EmitirDeclaracaoDeMatriculaParamsV1 params) {
        Aluno aluno = buscarAlunoParaEmissaoComponent.buscar(usuarioId, params.getAlunoUid());

        List<String> linhas = List.of(
                "Declaramos, para os devidos fins, que " + aluno.getUsuario().getNome() + ", portador(a) da matrícula " + aluno.getMatriculaRA() + ",",
                "encontra-se regularmente matriculado(a) nesta instituição de ensino, com ingresso em " + montarPdfDeCertificadoComponent.formatarData(aluno.getDataIngresso()) + ".",
                " ",
                "Esta declaração é válida para todos os fins de comprovação de vínculo estudantil."
        );

        String codigo = registrarEmissaoDeCertificadoComponent.registrar(usuarioId, aluno, TipoCertificado.DECLARACAO_MATRICULA);

        return montarPdfDeCertificadoComponent.montar("DECLARAÇÃO DE MATRÍCULA", linhas, codigo);
    }
}
