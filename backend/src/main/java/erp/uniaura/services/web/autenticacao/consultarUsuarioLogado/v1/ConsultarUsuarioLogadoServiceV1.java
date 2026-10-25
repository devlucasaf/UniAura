package erp.uniaura.services.web.autenticacao.consultarUsuarioLogado.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarUsuarioLogadoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    @Transactional(readOnly = true)
    public ConsultarUsuarioLogadoRetornoV1 consultar(Long usuarioId) {
        Usuario usuario = buscarUsuarioComponent.buscarPorId(usuarioId);

        ConsultarUsuarioLogadoRetornoV1 retorno = new ConsultarUsuarioLogadoRetornoV1();

        retorno.setUid(usuario.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setRole(usuario.getRole());

        return retorno;
    }
}
