package erp.uniaura.modules.autenticacao.service;

import erp.uniaura.dto.auth.AlterarSenhaRequestDTO;
import erp.uniaura.dto.auth.LoginRequestDTO;
import erp.uniaura.dto.auth.LoginResponseDTO;
import erp.uniaura.dto.auth.RefreshTokenRequestDTO;
import erp.uniaura.dto.auth.RegisterRequestDTO;
import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.LoginRateLimiter;
import erp.uniaura.infra.security.TokenService;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.modules.usuario.dto.UsuarioRequestDTO;
import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.repository.UsuarioRepository;
import erp.uniaura.modules.usuario.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final LoginRateLimiter loginRateLimiter;
    private final PasswordEncoder passwordEncoder;

    // --- TROCA A SENHA DO USUÁRIO AUTENTICADO ---
    @Transactional
    public void alterarSenha(Long usuarioId, AlterarSenhaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", usuarioId));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new BusinessException("A senha atual está incorreta.");
        }

        if (passwordEncoder.matches(dto.getNovaSenha(), usuario.getSenha())) {
            throw new BusinessException("A nova senha deve ser diferente da senha atual.");
        }

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepository.save(usuario);
    }

    // --- AUTENTICA O USUÁRIO POR E-MAIL/SENHA E DEVOLVE OS TOKENS ---
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {
        loginRateLimiter.verificarBloqueio(dto.getEmail());

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
            );

            Usuario usuario = ((UsuarioDetails) auth.getPrincipal()).getUsuario();
            loginRateLimiter.registrarSucesso(dto.getEmail());
            return montarLoginResponse(usuario);
        } catch (BadCredentialsException ex) {
            loginRateLimiter.registrarFalha(dto.getEmail());
            throw new BusinessException("E-mail ou senha inválidos.");
        }
    }

    // --- REGISTRA UM NOVO USUÁRIO ---
    @Transactional
    public UsuarioResponseDTO register(RegisterRequestDTO dto) {
        UsuarioRequestDTO usuarioRequest = UsuarioRequestDTO.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .dataNascimento(dto.getDataNascimento())
                .ativo(true)
                .role(dto.getRole())
                .build();

        return usuarioService.criar(usuarioRequest);
    }

    // --- VALIDA UM REFRESH TOKEN ---
    @Transactional(readOnly = true)
    public LoginResponseDTO refresh(RefreshTokenRequestDTO dto) {
        String email = tokenService.validarRefreshToken(dto.getRefreshToken());

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", email));

        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new BusinessException("Usuário inativo.");
        }

        return montarLoginResponse(usuario);
    }

    // --- RETORNA OS DADOS DO USUÁRIO AUTENTICADO ---
    @Transactional(readOnly = true)
    public UsuarioResponseDTO dadosDoUsuario(Long usuarioId) {
        return usuarioService.buscarPorId(usuarioId);
    }

    // --- GERA OS DOIS TOKENS E MONTA O DTO DE RESPOSTA COM OS DADOS DO USUÁRIO ---
    private LoginResponseDTO montarLoginResponse(Usuario usuario) {
        return LoginResponseDTO.builder()
                .token(tokenService.gerarToken(usuario))
                .refreshToken(tokenService.gerarRefreshToken(usuario))
                .usuarioDTO(usuarioService.buscarPorId(usuario.getId()))
                .build();
    }
}
