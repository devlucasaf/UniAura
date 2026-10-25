package erp.uniaura.services.web.biblioteca.cadastrarExemplar.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;
import erp.uniaura.services.common.biblioteca.GerarCodigoDeBarrasComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarExemplarServiceV1 extends BaseService {

    private final BuscarLivroComponent buscarLivroComponent;
    private final GerarCodigoDeBarrasComponent gerarCodigoDeBarrasComponent;

    // --- CADASTRA EXEMPLAR DISPONÍVEL, GERANDO CÓDIGO DE BARRAS SE NÃO INFORMADO ---
    @Transactional
    public Uid cadastrar(CadastrarExemplarParamsV1 params) {
        Exemplar exemplar = new Exemplar();

        exemplar.setLivro(buscarLivroComponent.buscarPorUid(params.getLivroUid()));
        exemplar.setLocalizacao(params.getLocalizacao());
        exemplar.setStatus(StatusExemplar.DISPONIVEL);

        if (params.getCodigoBarras() == null || params.getCodigoBarras().isBlank()) {
            exemplar.setCodigoBarras(gerarCodigoDeBarrasComponent.gerar());
        } else {
            String codigo = params.getCodigoBarras().trim();

            Long emUso = em()
                    .createQuery("select count(e) from Exemplar e where e.codigoBarras = :codigo", Long.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();

            if (emUso > 0) {
                throw new BusinessException("Código de barras já cadastrado.");
            }

            exemplar.setCodigoBarras(codigo);
        }

        exemplar.persist();

        return new Uid(exemplar.getUid());
    }
}
