package erp.uniaura.services.web.autenticacao.redefinirSenha.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.autenticacao.GuardarPedidosDeRecuperacaoComponent;
import erp.uniaura.services.common.autenticacao.PedidoDeRecuperacaoDeSenha;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedefinirSenhaServiceV1 extends BaseService {

    private static final int MAX_ERROS_DO_CODIGO = 5;

    private final ConsultarUsuarioComponent consultarUsuarioComponent;
    private final GuardarPedidosDeRecuperacaoComponent guardarPedidosDeRecuperacaoComponent;
    private final PasswordEncoder passwordEncoder;

    // --- O CÓDIGO CORRETO E DENTRO DO PRAZO AUTORIZA A NOVA SENHA ---
    @Transactional
    public void redefinir(RedefinirSenhaParamsV1 params) {
        String email = params.getEmail().trim().toLowerCase();

        PedidoDeRecuperacaoDeSenha pedido = guardarPedidosDeRecuperacaoComponent.buscar(email).orElse(null);

        if (pedido == null || LocalDateTime.now().isAfter(pedido.expiraEm())) {
            guardarPedidosDeRecuperacaoComponent.remover(email);

            throw new BusinessException("Código inválido ou expirado. Solicite um novo código.");
        }

        if (!iguais(pedido.codigo(), params.getCodigo())) {
            if (pedido.erros().incrementAndGet() >= MAX_ERROS_DO_CODIGO) {
                guardarPedidosDeRecuperacaoComponent.remover(email);

                throw new BusinessException("Muitas tentativas incorretas. Solicite um novo código.");
            }

            throw new BusinessException("Código incorreto.");
        }

        Usuario usuario = consultarUsuarioComponent.porEmail(email)
                .orElseThrow(() -> new BusinessException("Não foi possível validar os dados informados."));

        usuario.setSenha(passwordEncoder.encode(params.getNovaSenha()));
        usuario.merge();

        guardarPedidosDeRecuperacaoComponent.remover(email);
    }

    // --- COMPARAÇÃO EM TEMPO CONSTANTE ---
    private boolean iguais(String esperado, String informado) {
        return MessageDigest.isEqual(esperado.getBytes(StandardCharsets.UTF_8), informado.getBytes(StandardCharsets.UTF_8));
    }
}
