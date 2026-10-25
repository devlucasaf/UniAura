package erp.uniaura.services.web.disciplina.pesquisarPrerequisitosDaDisciplina.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.DisciplinaPrerequisito;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarPrerequisitosDaDisciplinaServiceV1 extends BaseService {

    private final BuscarDisciplinaComponent buscarDisciplinaComponent;

    // --- LISTA OS PRÉ-REQUISITOS VINCULADOS À DISCIPLINA ---
    @Transactional(readOnly = true)
    public PesquisarPrerequisitosDaDisciplinaRetornoV1 pesquisar(PesquisarPrerequisitosDaDisciplinaParamsV1 params) {
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());

        List<DisciplinaPrerequisito> vinculos = em()
                .createQuery("select v from DisciplinaPrerequisito v join fetch v.prerequisito "
                        + "where v.disciplina = :disciplina order by v.id", DisciplinaPrerequisito.class)
                .setParameter("disciplina", disciplina)
                .getResultList();

        PesquisarPrerequisitosDaDisciplinaRetornoV1 retorno = new PesquisarPrerequisitosDaDisciplinaRetornoV1();
        retorno.setLista(vinculos.stream().map(this::paraRetorno).toList());
        return retorno;
    }

    // --- CONVERTE O VÍNCULO NO ITEM DE RETORNO DO PRÉ-REQUISITO ---
    private PesquisarPrerequisitosDaDisciplinaPrerequisitoRetornoV1 paraRetorno(DisciplinaPrerequisito vinculo) {
        PesquisarPrerequisitosDaDisciplinaPrerequisitoRetornoV1 item = new PesquisarPrerequisitosDaDisciplinaPrerequisitoRetornoV1();

        item.setUid(vinculo.getUid());
        item.setPrerequisitoUid(vinculo.getPrerequisito().getUid());
        item.setPrerequisitoCodigo(vinculo.getPrerequisito().getCodigo());
        item.setPrerequisitoNome(vinculo.getPrerequisito().getNome());
        item.setCriadoEm(vinculo.getCriadoEm());

        return item;
    }
}
