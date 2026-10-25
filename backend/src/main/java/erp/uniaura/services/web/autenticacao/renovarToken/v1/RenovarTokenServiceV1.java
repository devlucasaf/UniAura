package erp.uniaura.services.web.autenticacao.renovarToken.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.security.TokenService;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RenovarTokenServiceV1 extends BaseService {

    private final TokenService tokenService;
    private final ConsultarUsuarioComponent consultarUsuarioComponent;

    // --- VALIDA O REFRESH TOKEN E GERA UM NOVO TOKEN E REFRESH TOKEN ---
    @Transactional(readOnly = true)
    public RenovarTokenRetornoV1 renovar(RenovarTokenParamsV1 params) {
        String email = tokenService.validarRefreshToken(params.getRefreshToken());

        Usuario usuario = consultarUsuarioComponent.porEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new BusinessException("Usuário inativo.");
        }

        RenovarTokenRetornoV1 retorno = new RenovarTokenRetornoV1();

        retorno.setToken(tokenService.gerarToken(usuario));
        retorno.setRefreshToken(tokenService.gerarRefreshToken(usuario));

        return retorno;
    }
}
