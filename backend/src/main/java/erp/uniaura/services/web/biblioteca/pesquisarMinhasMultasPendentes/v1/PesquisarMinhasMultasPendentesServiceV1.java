package erp.uniaura.services.web.biblioteca.pesquisarMinhasMultasPendentes.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Multa;
import erp.uniaura.persistence.entities.StatusMulta;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarMinhasMultasPendentesServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- LISTA AS MULTAS PENDENTES DO USUÁRIO LOGADO ---
    @Transactional(readOnly = true)
    public PesquisarMinhasMultasPendentesRetornoV1 pesquisar(Long usuarioId) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        List<Multa> multas = em()
                .createQuery("select m from Multa m where m.emprestimo.usuario = :usuario and m.status = :status order by m.geradaEm desc", Multa.class)
                .setParameter("usuario", autenticado)
                .setParameter("status", StatusMulta.PENDENTE)
                .getResultList();

        List<PesquisarMinhasMultasPendentesMultaRetornoV1> lista = new ArrayList<>();

        for (Multa multa : multas) {
            PesquisarMinhasMultasPendentesMultaRetornoV1 item = new PesquisarMinhasMultasPendentesMultaRetornoV1();

            item.setUid(multa.getUid());
            item.setLivroTitulo(multa.getEmprestimo().getExemplar().getLivro().getTitulo());
            item.setValor(multa.getValor());
            item.setDiasAtraso(multa.getDiasAtraso());
            item.setGeradaEm(multa.getGeradaEm());

            lista.add(item);
        }

        PesquisarMinhasMultasPendentesRetornoV1 retorno = new PesquisarMinhasMultasPendentesRetornoV1();

        retorno.setTotal((long) lista.size());
        retorno.setLista(lista);

        return retorno;
    }
}
