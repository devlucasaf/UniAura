package erp.uniaura.services.common.autenticacao;

import cloudsupport.services.BaseComponent;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GuardarPedidosDeRecuperacaoComponent extends BaseComponent {

    // --- OS PEDIDOS FICAM EM MEMÓRIA; REINICIAR O SERVIDOR OS DESCARTA, E O USUÁRIO SOLICITA OUTRO CÓDIGO ---
    private final Map<String, PedidoDeRecuperacaoDeSenha> pedidos = new ConcurrentHashMap<>();

    // --- REGISTRA PEDIDO DE RECUPERAÇÃO PARA O EMAIL ---
    public void registrar(String email, String codigo, LocalDateTime expiraEm) {
        pedidos.put(email, new PedidoDeRecuperacaoDeSenha(codigo, expiraEm, new AtomicInteger()));
    }

    // --- BUSCA PEDIDO DE RECUPERAÇÃO PELO EMAIL ---
    public Optional<PedidoDeRecuperacaoDeSenha> buscar(String email) {
        return Optional.ofNullable(pedidos.get(email));
    }

    // --- REMOVE PEDIDO DE RECUPERAÇÃO DO EMAIL ---
    public void remover(String email) {
        pedidos.remove(email);
    }
}
