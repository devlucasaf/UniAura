package erp.uniaura.services.common.autenticacao;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public record PedidoDeRecuperacaoDeSenha(String codigo, LocalDateTime expiraEm, AtomicInteger erros) {
}
