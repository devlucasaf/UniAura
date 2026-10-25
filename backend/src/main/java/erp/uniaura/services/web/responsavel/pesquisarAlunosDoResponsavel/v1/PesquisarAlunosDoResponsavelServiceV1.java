package erp.uniaura.services.web.responsavel.pesquisarAlunosDoResponsavel.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.persistence.entities.ResponsavelAluno;
import erp.uniaura.services.common.responsavel.BuscarResponsavelComponent;
import erp.uniaura.services.common.responsavel.BuscarVinculoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarAlunosDoResponsavelServiceV1 extends BaseService {

    private final BuscarResponsavelComponent buscarResponsavelComponent;
    private final BuscarVinculoComponent buscarVinculoComponent;

    // --- LISTA OS ALUNOS VINCULADOS AO RESPONSÁVEL ---
    @Transactional(readOnly = true)
    public PesquisarAlunosDoResponsavelRetornoV1 pesquisar(PesquisarAlunosDoResponsavelParamsV1 params) {
        Responsavel responsavel = buscarResponsavelComponent.buscarPorUid(params.getUid());

        List<PesquisarAlunosDoResponsavelAlunoRetornoV1> lista = new ArrayList<>();

        for (ResponsavelAluno vinculo : buscarVinculoComponent.listarDoResponsavel(responsavel)) {
            PesquisarAlunosDoResponsavelAlunoRetornoV1 item = new PesquisarAlunosDoResponsavelAlunoRetornoV1();

            item.setAlunoUid(vinculo.getAluno().getUid());
            item.setNome(vinculo.getAluno().getUsuario().getNome());
            item.setMatriculaRA(vinculo.getAluno().getMatriculaRA());
            item.setObservacao(vinculo.getObservacao());
            item.setVinculadoEm(vinculo.getCriadoEm());

            lista.add(item);
        }

        PesquisarAlunosDoResponsavelRetornoV1 retorno = new PesquisarAlunosDoResponsavelRetornoV1();

        retorno.setTotal((long) lista.size());
        retorno.setLista(lista);

        return retorno;
    }
}
