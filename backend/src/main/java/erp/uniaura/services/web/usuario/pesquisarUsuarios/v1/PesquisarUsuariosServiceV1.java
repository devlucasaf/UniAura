package erp.uniaura.services.web.usuario.pesquisarUsuarios.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarUsuariosServiceV1 extends BaseService {

    @Transactional(readOnly = true)
    public PesquisarUsuariosRetornoV1 pesquisar(PesquisarUsuariosParamsV1 params) {
        PageableQuery.Result<PesquisarUsuariosUsuarioRetornoV1> resultado =
                PageableQuery.<Usuario, PesquisarUsuariosUsuarioRetornoV1>builder()
                        .clazz(Usuario.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getRole() != null) {
                                criteria.where(cb().equal(from.get("role"), params.getRole()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarUsuariosRetornoV1 retorno = new PesquisarUsuariosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    private PesquisarUsuariosUsuarioRetornoV1 paraRetorno(Usuario usuario) {
        PesquisarUsuariosUsuarioRetornoV1 item = new PesquisarUsuariosUsuarioRetornoV1();

        item.setUid(usuario.getUid());
        item.setNome(usuario.getNome());
        item.setEmail(usuario.getEmail());
        item.setCpf(usuario.getCpf());
        item.setTelefone(usuario.getTelefone());
        item.setDataNascimento(usuario.getDataNascimento());
        item.setAtivo(usuario.getAtivo());
        item.setRole(usuario.getRole());
        item.setCriadoEm(usuario.getCriadoEm());

        return item;
    }
}
