package erp.uniaura.services.web.processo.abrirProcesso.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.protocolo.GeradorProtocolo;
import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Processo;
import erp.uniaura.persistence.entities.StatusProcesso;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.processo.RegistrarMovimentacaoDoProcessoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AbrirProcessoServiceV1 extends BaseService {

    // --- INÍCIO DO NÚMERO DE PROTOCOLO DOS PROCESSOS ---
    private static final String PREFIXO_PROTOCOLO = "PRC";

    // --- QUANTAS VEZES TENTA SORTEAR UM PROTOCOLO ANTES DE DESISTIR ---
    private static final int MAXIMO_DE_TENTATIVAS_DO_PROTOCOLO = 5;

    private final GeradorProtocolo geradorProtocolo;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final RegistrarMovimentacaoDoProcessoComponent registrarMovimentacaoDoProcessoComponent;

    // --- O ALUNO AUTENTICADO ABRE UM NOVO REQUERIMENTO E DEVOLVE O UID E O PROTOCOLO ---
    @Transactional
    public AbrirProcessoRetornoV1 abrir(Long usuarioId, AbrirProcessoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        // --- TODO PROCESSO NASCE COM O STATUS ABERTO ---
        Processo processo = new Processo();

        processo.setProtocolo(gerarProtocoloUnico());
        processo.setAluno(aluno);
        processo.setTipo(params.getTipo());
        processo.setAssunto(params.getAssunto());
        processo.setDescricao(params.getDescricao());
        processo.setStatus(StatusProcesso.ABERTO);
        processo.persist();

        // --- PRIMEIRO PASSO DA LINHA DO TEMPO, VISÍVEL PARA O ALUNO ---
        registrarMovimentacaoDoProcessoComponent.registrar(processo, aluno.getUsuario(), null,
                StatusProcesso.ABERTO, "Processo aberto pelo aluno.", true);

        AbrirProcessoRetornoV1 retorno = new AbrirProcessoRetornoV1();

        retorno.setUid(processo.getUid());
        retorno.setProtocolo(processo.getProtocolo());

        return retorno;
    }

    // --- SORTEIA UM PROTOCOLO ATÉ ENCONTRAR UM QUE AINDA NÃO ESTEJA EM USO ---
    private String gerarProtocoloUnico() {
        for (int tentativa = 0; tentativa < MAXIMO_DE_TENTATIVAS_DO_PROTOCOLO; tentativa++) {
            String protocolo = geradorProtocolo.gerar(PREFIXO_PROTOCOLO);

            Long emUso = em()
                    .createQuery("select count(p) from Processo p where p.protocolo = :protocolo", Long.class)
                    .setParameter("protocolo", protocolo)
                    .getSingleResult();

            if (emUso == 0) {
                return protocolo;
            }
        }

        throw new BusinessException("Não foi possível gerar um número de protocolo. Tente novamente.");
    }
}
