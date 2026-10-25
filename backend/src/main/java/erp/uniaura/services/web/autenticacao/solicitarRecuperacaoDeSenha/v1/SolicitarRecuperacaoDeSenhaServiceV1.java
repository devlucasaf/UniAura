package erp.uniaura.services.web.autenticacao.solicitarRecuperacaoDeSenha.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.security.LoginRateLimiter;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.autenticacao.GuardarPedidosDeRecuperacaoComponent;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolicitarRecuperacaoDeSenhaServiceV1 extends BaseService {

    private static final int    VALIDADE_MINUTOS         = 10;
    private static final String MENSAGEM_DADOS_INVALIDOS = "Não foi possível validar os dados informados.";

    private final ConsultarUsuarioComponent consultarUsuarioComponent;
    private final GuardarPedidosDeRecuperacaoComponent guardarPedidosDeRecuperacaoComponent;
    private final LoginRateLimiter loginRateLimiter;

    @Value("${app.recuperacao-senha.exibir-codigo:true}")
    private boolean exibirCodigo;

    private final SecureRandom random = new SecureRandom();

    // --- CONFERE E-MAIL, CPF E NASCIMENTO E GERA O CÓDIGO DE RECUPERAÇÃO ---
    @Transactional(readOnly = true)
    public SolicitarRecuperacaoDeSenhaRetornoV1 solicitar(SolicitarRecuperacaoDeSenhaParamsV1 params) {
        String email = params.getEmail().trim().toLowerCase();
        String chaveLimite = "recuperar:" + email;

        loginRateLimiter.verificarBloqueio(chaveLimite);

        Optional<Usuario> usuario = consultarUsuarioComponent.porEmail(email);

        if (usuario.isEmpty() || !dadosConferem(usuario.get(), params)) {
            loginRateLimiter.registrarFalha(chaveLimite);

            throw new BusinessException(MENSAGEM_DADOS_INVALIDOS);
        }

        loginRateLimiter.registrarSucesso(chaveLimite);

        String codigo = "%06d".formatted(random.nextInt(1_000_000));

        guardarPedidosDeRecuperacaoComponent.registrar(email, codigo, LocalDateTime.now().plusMinutes(VALIDADE_MINUTOS));

        SolicitarRecuperacaoDeSenhaRetornoV1 retorno = new SolicitarRecuperacaoDeSenhaRetornoV1();

        retorno.setValidadeMinutos(VALIDADE_MINUTOS);

        if (exibirCodigo) {
            retorno.setCodigoSimulado(codigo);
        }

        return retorno;
    }

    // --- VERIFICA SE O USUÁRIO ESTÁ ATIVO E SE CPF E NASCIMENTO CONFEREM ---
    private boolean dadosConferem(Usuario usuario, SolicitarRecuperacaoDeSenhaParamsV1 params) {
        String cpfCadastrado = apenasDigitos(usuario.getCpf());

        return Boolean.TRUE.equals(usuario.getAtivo())
                && !cpfCadastrado.isEmpty()
                && cpfCadastrado.equals(apenasDigitos(params.getCpf()))
                && params.getDataNascimento().equals(usuario.getDataNascimento());
    }

    // --- REMOVE TUDO QUE NÃO FOR DÍGITO DO VALOR ---
    private String apenasDigitos(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.replaceAll("\\D", "");
    }
}
