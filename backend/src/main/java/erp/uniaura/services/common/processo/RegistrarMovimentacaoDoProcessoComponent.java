package erp.uniaura.services.common.processo;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.MovimentacaoProcesso;
import erp.uniaura.persistence.entities.Processo;
import erp.uniaura.persistence.entities.StatusProcesso;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class RegistrarMovimentacaoDoProcessoComponent extends BaseComponent {

    // --- GRAVA UM PASSO NA LINHA DO TEMPO DO PROCESSO ---
    public void registrar(Processo processo, Usuario autor, StatusProcesso statusAnterior,
                          StatusProcesso statusNovo, String comentario, boolean visivelParaAluno) {
        MovimentacaoProcesso movimentacao = new MovimentacaoProcesso();

        movimentacao.setProcesso(processo);
        movimentacao.setAutor(autor);
        movimentacao.setStatusAnterior(statusAnterior);
        movimentacao.setStatusNovo(statusNovo);
        movimentacao.setComentario(comentario);
        movimentacao.setVisivelParaAluno(visivelParaAluno);
        movimentacao.persist();
    }
}
