package erp.uniaura.services.web.certificado.verificarCertificado.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.CertificadoEmitido;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VerificarCertificadoServiceV1 extends BaseService {

    // --- VERIFICA A AUTENTICIDADE DO CERTIFICADO PELO CÓDIGO ---
    @Transactional(readOnly = true)
    public VerificarCertificadoRetornoV1 verificar(VerificarCertificadoParamsV1 params) {
        List<CertificadoEmitido> encontrados = em()
                .createQuery("select c from CertificadoEmitido c where c.codigoVerificacao = :codigo", CertificadoEmitido.class)
                .setParameter("codigo", params.getCodigo())
                .getResultList();

        if (encontrados.isEmpty()) {
            throw new BusinessException("Código de verificação inválido ou não encontrado.");
        }

        CertificadoEmitido certificado = encontrados.get(0);

        VerificarCertificadoRetornoV1 retorno = new VerificarCertificadoRetornoV1();

        retorno.setAlunoNome(certificado.getAluno().getUsuario().getNome());
        retorno.setTipo(certificado.getTipo());
        retorno.setCodigoVerificacao(certificado.getCodigoVerificacao());
        retorno.setEmitidoPorNome(certificado.getEmitidoPor().getNome());
        retorno.setEmitidoEm(certificado.getEmitidoEm());

        return retorno;
    }
}
