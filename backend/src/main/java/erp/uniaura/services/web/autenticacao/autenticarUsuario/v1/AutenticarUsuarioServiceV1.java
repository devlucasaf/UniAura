package erp.uniaura.services.web.autenticacao.autenticarUsuario.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.security.LoginRateLimiter;
import erp.uniaura.infra.security.TokenService;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutenticarUsuarioServiceV1 extends BaseService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final LoginRateLimiter loginRateLimiter;

    // --- AUTENTICA O USUÁRIO E RETORNA TOKEN E REFRESH TOKEN ---
    @Transactional(readOnly = true)
    public AutenticarUsuarioRetornoV1 autenticar(AutenticarUsuarioParamsV1 params) {
        loginRateLimiter.verificarBloqueio(params.getEmail());

        try {
            Authentication autenticacao = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(params.getEmail(), params.getSenha()));

            Usuario usuario = ((UsuarioDetails) autenticacao.getPrincipal()).getUsuario();

            loginRateLimiter.registrarSucesso(params.getEmail());

            return montarRetorno(usuario);
        } catch (BadCredentialsException excecao) {
            loginRateLimiter.registrarFalha(params.getEmail());

            throw new BusinessException("E-mail ou senha inválidos.");
        }
    }

    // --- MONTA O RETORNO COM TOKEN, REFRESH TOKEN E DADOS DO USUÁRIO ---
    private AutenticarUsuarioRetornoV1 montarRetorno(Usuario usuario) {
        AutenticarUsuarioUsuarioRetornoV1 dadosDoUsuario = new AutenticarUsuarioUsuarioRetornoV1();

        dadosDoUsuario.setUid(usuario.getUid());
        dadosDoUsuario.setNome(usuario.getNome());
        dadosDoUsuario.setEmail(usuario.getEmail());
        dadosDoUsuario.setRole(usuario.getRole());

        AutenticarUsuarioRetornoV1 retorno = new AutenticarUsuarioRetornoV1();

        retorno.setToken(tokenService.gerarToken(usuario));
        retorno.setRefreshToken(tokenService.gerarRefreshToken(usuario));
        retorno.setUsuario(dadosDoUsuario);

        return retorno;
    }
}
