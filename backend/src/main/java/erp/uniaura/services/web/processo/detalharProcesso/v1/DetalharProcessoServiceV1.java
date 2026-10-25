package erp.uniaura.services.web.processo.detalharProcesso.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.MovimentacaoProcesso;
import erp.uniaura.persistence.entities.Processo;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.processo.BuscarProcessoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalharProcessoServiceV1 extends BaseService {

    private final BuscarProcessoComponent buscarProcessoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- DETALHA O PROCESSO RESPEITANDO A VISIBILIDADE DAS MOVIMENTAÇÕES ---
    @Transactional(readOnly = true)
    public DetalharProcessoRetornoV1 detalhar(Long usuarioId, DetalharProcessoParamsV1 params) {
        Processo processo = buscarProcesso(params);
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        boolean equipeAdministrativa = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR
                || autenticado.getRole() == TipoUsuario.SECRETARIA;

        if (!equipeAdministrativa && !processo.getAluno().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não tem permissão para consultar este processo.");
        }

        DetalharProcessoRetornoV1 retorno = new DetalharProcessoRetornoV1();

        retorno.setUid(processo.getUid());
        retorno.setProtocolo(processo.getProtocolo());
        retorno.setAlunoNome(processo.getAluno().getUsuario().getNome());
        retorno.setAlunoMatriculaRA(processo.getAluno().getMatriculaRA());
        retorno.setTipo(processo.getTipo());
        retorno.setAssunto(processo.getAssunto());
        retorno.setDescricao(processo.getDescricao());
        retorno.setStatus(processo.getStatus());

        if (processo.getResponsavel() != null) {
            retorno.setResponsavelNome(processo.getResponsavel().getNome());
        }

        retorno.setPrazoResposta(processo.getPrazoResposta());
        retorno.setParecerFinal(processo.getParecerFinal());
        retorno.setEncerradoEm(processo.getEncerradoEm());
        retorno.setCriadoEm(processo.getCriadoEm());
        retorno.setAtualizadoEm(processo.getAtualizadoEm());
        retorno.setMovimentacoes(listarMovimentacoes(processo, !equipeAdministrativa));

        return retorno;
    }

    // --- BUSCA O PROCESSO PELO UID OU PELO PROTOCOLO ---
    private Processo buscarProcesso(DetalharProcessoParamsV1 params) {
        if (params.getUid() != null) {
            return buscarProcessoComponent.buscarPorUid(params.getUid());
        }

        if (params.getProtocolo() != null && !params.getProtocolo().isBlank()) {
            return buscarProcessoComponent.buscarPorProtocolo(params.getProtocolo());
        }

        throw new BusinessException("Informe o uid ou o protocolo do processo.");
    }

    // --- O ALUNO NÃO ENXERGA OS DESPACHOS INTERNOS DA EQUIPE ---
    private List<DetalharProcessoMovimentacaoRetornoV1> listarMovimentacoes(Processo processo, boolean somenteVisiveis) {
        String consulta = "select m from MovimentacaoProcesso m where m.processo = :processo ";
        if (somenteVisiveis) {
            consulta = consulta + "and m.visivelParaAluno = true ";
        }
        consulta = consulta + "order by m.criadoEm asc";

        List<MovimentacaoProcesso> movimentacoes = em()
                .createQuery(consulta, MovimentacaoProcesso.class)
                .setParameter("processo", processo)
                .getResultList();

        List<DetalharProcessoMovimentacaoRetornoV1> lista = new ArrayList<>();

        for (MovimentacaoProcesso movimentacao : movimentacoes) {
            DetalharProcessoMovimentacaoRetornoV1 item = new DetalharProcessoMovimentacaoRetornoV1();

            item.setUid(movimentacao.getUid());
            item.setAutorNome(movimentacao.getAutor().getNome());
            item.setStatusAnterior(movimentacao.getStatusAnterior());
            item.setStatusNovo(movimentacao.getStatusNovo());
            item.setComentario(movimentacao.getComentario());
            item.setVisivelParaAluno(movimentacao.getVisivelParaAluno());
            item.setCriadoEm(movimentacao.getCriadoEm());

            lista.add(item);
        }

        return lista;
    }
}
