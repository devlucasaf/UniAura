package erp.uniaura.services.common.certificado;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.CertificadoEmitido;
import erp.uniaura.persistence.entities.TipoCertificado;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrarEmissaoDeCertificadoComponent extends BaseComponent {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- REGISTRA A EMISSÃO PARA AUDITORIA E VERIFICAÇÃO FUTURA E DEVOLVE O CÓDIGO IMPRESSO NO PDF ---
    public String registrar(Long usuarioId, Aluno aluno, TipoCertificado tipo) {
        CertificadoEmitido certificado = new CertificadoEmitido();

        certificado.setAluno(aluno);
        certificado.setTipo(tipo);
        certificado.setCodigoVerificacao(UUID.randomUUID().toString());
        certificado.setEmitidoPor(buscarUsuarioComponent.buscarPorId(usuarioId));
        certificado.persist();

        return certificado.getCodigoVerificacao();
    }
}
