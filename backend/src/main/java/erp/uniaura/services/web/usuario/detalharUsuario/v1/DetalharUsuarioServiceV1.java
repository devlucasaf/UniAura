package erp.uniaura.services.web.usuario.detalharUsuario.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharUsuarioServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    @Transactional(readOnly = true)
    public DetalharUsuarioRetornoV1 detalhar(DetalharUsuarioParamsV1 params) {
        Usuario usuario = buscarUsuarioComponent.buscarPorUid(params.getUid());

        DetalharUsuarioRetornoV1 retorno = new DetalharUsuarioRetornoV1();

        retorno.setUid(usuario.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setAtivo(usuario.getAtivo());
        retorno.setRole(usuario.getRole());
        retorno.setCriadoEm(usuario.getCriadoEm());
        retorno.setAtualizadoEm(usuario.getAtualizadoEm());

        return retorno;
    }
}
