package erp.uniaura.services.common.processo;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.StatusProcesso;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class ValidarTransicaoDoProcessoComponent extends BaseComponent {

    // --- TRANSIÇÕES PERMITIDAS PARA CADA STATUS ---
    private static final Map<StatusProcesso, Set<StatusProcesso>> TRANSICOES = new EnumMap<>(Map.of(
            StatusProcesso.ABERTO,
            Set.of(StatusProcesso.EM_ANALISE, StatusProcesso.AGUARDANDO_ALUNO,
                    StatusProcesso.DEFERIDO, StatusProcesso.INDEFERIDO, StatusProcesso.CANCELADO),
            StatusProcesso.EM_ANALISE,
            Set.of(StatusProcesso.AGUARDANDO_ALUNO,
                    StatusProcesso.DEFERIDO, StatusProcesso.INDEFERIDO, StatusProcesso.CANCELADO),
            StatusProcesso.AGUARDANDO_ALUNO,
            Set.of(StatusProcesso.EM_ANALISE,
                    StatusProcesso.DEFERIDO, StatusProcesso.INDEFERIDO, StatusProcesso.CANCELADO)
    ));

    // --- IMPEDE SALTOS DE STATUS INVÁLIDOS E MOVIMENTAÇÃO DE PROCESSO JÁ ENCERRADO ---
    public void validar(StatusProcesso atual, StatusProcesso novo) {
        if (atual.isFinal()) {
            throw new BusinessException("O processo já está %s e não pode ser movimentado.".formatted(atual));
        }

        if (atual == novo) {
            throw new BusinessException("O processo já está com o status %s.".formatted(novo));
        }

        if (!TRANSICOES.getOrDefault(atual, Set.of()).contains(novo)) {
            throw new BusinessException("Não é permitido mudar o status de %s para %s.".formatted(atual, novo));
        }
    }
}
