package erp.uniaura.services.web.atividade.pesquisarEntregasDoAluno.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.EntregaAtividade;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.responsavel.BuscarResponsavelComponent;
import erp.uniaura.services.common.responsavel.BuscarVinculoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarEntregasDoAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarResponsavelComponent buscarResponsavelComponent;
    private final BuscarVinculoComponent buscarVinculoComponent;

    // --- PESQUISA ENTREGAS DE UM ALUNO COM PAGINAÇÃO E FILTRO POR STATUS ---
    @Transactional(readOnly = true)
    public PesquisarEntregasDoAlunoRetornoV1 pesquisar(Long usuarioId, PesquisarEntregasDoAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        validarAcesso(autenticado, aluno);

        PageableQuery.Result<PesquisarEntregasDoAlunoEntregaRetornoV1> resultado =
                PageableQuery.<EntregaAtividade, PesquisarEntregasDoAlunoEntregaRetornoV1>builder()
                        .clazz(EntregaAtividade.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            condicoes.add(cb().equal(from.get("aluno"), aluno));

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarEntregasDoAlunoRetornoV1 retorno = new PesquisarEntregasDoAlunoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- O ALUNO VÊ AS PRÓPRIAS ENTREGAS, O RESPONSÁVEL VÊ AS DOS ALUNOS VINCULADOS, E A EQUIPE E OS PROFESSORES VEEM TODAS ---
    private void validarAcesso(Usuario autenticado, Aluno aluno) {
        if (autenticado.getRole() == TipoUsuario.ALUNO && !aluno.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você só pode consultar as suas próprias entregas.");
        }

        if (autenticado.getRole() == TipoUsuario.RESPONSAVEL) {
            boolean vinculado = buscarVinculoComponent
                    .buscar(buscarResponsavelComponent.buscarPorUsuarioId(autenticado.getId()), aluno)
                    .isPresent();

            if (!vinculado) {
                throw new BusinessException("Você só pode consultar as entregas de alunos vinculados a você.");
            }
        }
    }

    // --- CONVERTE A ENTREGA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarEntregasDoAlunoEntregaRetornoV1 paraRetorno(EntregaAtividade entrega) {
        PesquisarEntregasDoAlunoEntregaRetornoV1 item = new PesquisarEntregasDoAlunoEntregaRetornoV1();

        item.setUid(entrega.getUid());
        item.setAtividadeUid(entrega.getAtividade().getUid());
        item.setAtividadeTitulo(entrega.getAtividade().getTitulo());
        item.setArquivoUrl(entrega.getArquivoUrl());
        item.setComentarioAluno(entrega.getComentarioAluno());
        item.setDataEntrega(entrega.getDataEntrega());
        item.setNota(entrega.getNota());
        item.setFeedback(entrega.getFeedback());
        item.setStatus(entrega.getStatus());

        return item;
    }
}
