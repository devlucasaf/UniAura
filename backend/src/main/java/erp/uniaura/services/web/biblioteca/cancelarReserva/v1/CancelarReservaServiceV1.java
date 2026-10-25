package erp.uniaura.services.web.biblioteca.cancelarReserva.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusReserva;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.biblioteca.BuscarReservaComponent;
import erp.uniaura.services.common.biblioteca.ReordenarFilaDeReservasComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelarReservaServiceV1 extends BaseService {

    private final BuscarReservaComponent buscarReservaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final ReordenarFilaDeReservasComponent reordenarFilaDeReservasComponent;

    // --- CANCELA A RESERVA E REORDENA A FILA DE RESERVAS DO LIVRO ---
    @Transactional
    public void cancelar(Long usuarioId, CancelarReservaParamsV1 params) {
        Reserva reserva = buscarReservaComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        boolean equipe = autenticado.getRole() == TipoUsuario.BIBLIOTECARIO || autenticado.getRole() == TipoUsuario.ADMIN;

        if (!equipe && !reserva.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode cancelar reservas de outro usuário.");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reserva.merge();

        reordenarFilaDeReservasComponent.reordenar(reserva.getLivro());
    }
}
