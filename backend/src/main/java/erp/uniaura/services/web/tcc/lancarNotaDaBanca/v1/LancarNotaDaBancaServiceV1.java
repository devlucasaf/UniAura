package erp.uniaura.services.web.tcc.lancarNotaDaBanca.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.BancaTccMembro;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LancarNotaDaBancaServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    @Transactional
    public void lancar(Long usuarioId, LancarNotaDaBancaParamsV1 params) {
        BancaTccMembro membro = findByUid(BancaTccMembro.class, params.getMembroUid())
                .orElseThrow(() -> new BusinessException("Membro da banca não encontrado."));

        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        // --- O PROFESSOR SÓ LANÇA A PRÓPRIA NOTA; COORDENAÇÃO E ADMIN LANÇAM A DE QUALQUER MEMBRO ---
        if (autenticado.getRole() == TipoUsuario.PROFESSOR && !membro.getProfessor().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Cada professor só pode lançar a própria nota na banca.");
        }

        membro.setNota(params.getNota());
        membro.setParecer(params.getParecer());
        membro.merge();
    }
}
