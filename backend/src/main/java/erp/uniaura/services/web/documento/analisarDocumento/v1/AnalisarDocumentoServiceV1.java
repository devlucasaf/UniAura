package erp.uniaura.services.web.documento.analisarDocumento.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.DocumentoAluno;
import erp.uniaura.services.common.documento.BuscarDocumentoDoAlunoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalisarDocumentoServiceV1 extends BaseService {

    private final BuscarDocumentoDoAlunoComponent buscarDocumentoDoAlunoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- REGISTRA A ANÁLISE DO DOCUMENTO COM STATUS, OBSERVAÇÕES E RESPONSÁVEL ---
    @Transactional
    public void analisar(Long usuarioId, AnalisarDocumentoParamsV1 params) {
        DocumentoAluno documento = buscarDocumentoDoAlunoComponent.buscarPorUid(params.getUid());

        documento.setStatus(params.getStatus());
        documento.setObservacoes(params.getObservacoes());
        documento.setAnalisadoPor(buscarUsuarioComponent.buscarPorId(usuarioId));
        documento.setAnalisadoEm(LocalDateTime.now());
        documento.merge();
    }
}
