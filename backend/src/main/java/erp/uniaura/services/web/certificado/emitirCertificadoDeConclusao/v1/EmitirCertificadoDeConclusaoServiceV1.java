package erp.uniaura.services.web.certificado.emitirCertificadoDeConclusao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.StatusAluno;
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
public class EmitirCertificadoDeConclusaoServiceV1 extends BaseService {

    private final BuscarAlunoParaEmissaoComponent buscarAlunoParaEmissaoComponent;
    private final RegistrarEmissaoDeCertificadoComponent registrarEmissaoDeCertificadoComponent;
    private final MontarPdfDeCertificadoComponent montarPdfDeCertificadoComponent;

    // --- EMITE O CERTIFICADO DE CONCLUSÃO EM PDF PARA ALUNO FORMADO ---
    @Transactional
    public byte[] emitir(Long usuarioId, EmitirCertificadoDeConclusaoParamsV1 params) {
        Aluno aluno = buscarAlunoParaEmissaoComponent.buscar(usuarioId, params.getAlunoUid());

        if (aluno.getStatus() != StatusAluno.FORMADO) {
            throw new BusinessException("Só é possível emitir certificado de conclusão para alunos formados.");
        }

        List<String> linhas = List.of(
                "Certificamos que " + aluno.getUsuario().getNome() + ", portador(a) da matrícula " + aluno.getMatriculaRA() + ",",
                "concluiu com aproveitamento o curso oferecido por esta instituição de ensino.",
                " ",
                "Este certificado é válido para todos os fins legais de comprovação de conclusão de curso."
        );

        String codigo = registrarEmissaoDeCertificadoComponent.registrar(usuarioId, aluno, TipoCertificado.CERTIFICADO_CONCLUSAO);

        return montarPdfDeCertificadoComponent.montar("CERTIFICADO DE CONCLUSÃO", linhas, codigo);
    }
}
