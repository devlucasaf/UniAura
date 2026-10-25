package erp.uniaura.services.web.acompanhamento.registrarAtendimentoDeAcompanhamento.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;
import erp.uniaura.persistence.entities.RegistroDeAcompanhamento;
import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.services.common.acompanhamento.BuscarAcompanhamentoEspecialComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistrarAtendimentoDeAcompanhamentoServiceV1 extends BaseService {

    private final BuscarAcompanhamentoEspecialComponent buscarAcompanhamentoEspecialComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- ACRESCENTA UM REGISTRO AO HISTÓRICO DE UM ACOMPANHAMENTO EM ANDAMENTO, TENDO COMO AUTOR O USUÁRIO LOGADO ---
    @Transactional
    public Uid registrar(Long usuarioId, RegistrarAtendimentoDeAcompanhamentoParamsV1 params) {
        AcompanhamentoEspecial acompanhamento = buscarAcompanhamentoEspecialComponent.buscarPorUid(params.getAcompanhamentoUid());

        if (acompanhamento.getStatus() == StatusAcompanhamento.ENCERRADO) {
            throw new BusinessException("Acompanhamento encerrado não aceita novos registros.");
        }

        RegistroDeAcompanhamento registro = new RegistroDeAcompanhamento();

        registro.setAcompanhamento(acompanhamento);
        registro.setTipo(params.getTipo());
        registro.setDescricao(params.getDescricao());
        registro.setAutor(buscarUsuarioComponent.buscarPorId(usuarioId));

        if (params.getDataRegistro() == null) {
            registro.setDataRegistro(LocalDate.now());
        } else {
            registro.setDataRegistro(params.getDataRegistro());
        }

        registro.persist();

        return new Uid(registro.getUid());
    }
}
