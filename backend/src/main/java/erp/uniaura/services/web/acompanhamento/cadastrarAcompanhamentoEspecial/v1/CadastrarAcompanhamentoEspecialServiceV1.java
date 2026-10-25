package erp.uniaura.services.web.acompanhamento.cadastrarAcompanhamentoEspecial.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;
import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CadastrarAcompanhamentoEspecialServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- ABRE O ACOMPANHAMENTO DE UM ALUNO ESPECIAL, SOB RESPONSABILIDADE DE QUEM ABRIU ---
    @Transactional
    public Uid cadastrar(Long usuarioId, CadastrarAcompanhamentoEspecialParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        Long jaAtivos = em()
                .createQuery("select count(a) from AcompanhamentoEspecial a where a.aluno = :aluno and a.tipoNecessidade = :tipo and a.status <> :encerrado", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("tipo", params.getTipoNecessidade())
                .setParameter("encerrado", StatusAcompanhamento.ENCERRADO)
                .getSingleResult();

        if (jaAtivos > 0) {
            throw new BusinessException("Já existe um acompanhamento em andamento deste aluno para esse tipo de necessidade.");
        }

        AcompanhamentoEspecial acompanhamento = new AcompanhamentoEspecial();

        acompanhamento.setAluno(aluno);
        acompanhamento.setTipoNecessidade(params.getTipoNecessidade());
        acompanhamento.setDescricaoNecessidade(params.getDescricaoNecessidade());
        acompanhamento.setRecursosNecessarios(params.getRecursosNecessarios());
        acompanhamento.setPossuiLaudo(Boolean.TRUE.equals(params.getPossuiLaudo()));
        acompanhamento.setStatus(StatusAcompanhamento.ATIVO);
        acompanhamento.setResponsavelPeloAcompanhamento(autenticado);

        if (params.getDataInicio() == null) {
            acompanhamento.setDataInicio(LocalDate.now());
        } else {
            acompanhamento.setDataInicio(params.getDataInicio());
        }

        acompanhamento.persist();

        return new Uid(acompanhamento.getUid());
    }
}
