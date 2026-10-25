package erp.uniaura.services.common.usuario;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.infra.email.EmailService;
import erp.uniaura.infra.security.GeradorSenhaTemporaria;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CriarUsuarioComponent extends BaseComponent {

    private final ConsultarUsuarioComponent consultarUsuarioComponent;
    private final PasswordEncoder passwordEncoder;
    private final GeradorSenhaTemporaria geradorSenhaTemporaria;
    private final EmailService emailService;

    // --- SEM SENHA INFORMADA, GERA UMA TEMPORÁRIA E ENVIA POR E-MAIL ---
    @Transactional
    public Usuario criar(String nome, String email, String senha, String cpf, String telefone,
                         LocalDate dataNascimento, Boolean ativo, TipoUsuario role) {
        if (consultarUsuarioComponent.porEmail(email).isPresent()) {
            throw new BusinessException("Já existe um usuário cadastrado com o e-mail: " + email);
        }

        boolean senhaGerada = senha == null || senha.isBlank();
        String senhaFinal = senha;

        if (senhaGerada) {
            senhaFinal = geradorSenhaTemporaria.gerar();
        }

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senhaFinal));
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone);
        usuario.setDataNascimento(dataNascimento);
        usuario.setRole(role);

        if (ativo == null) {
            usuario.setAtivo(Boolean.TRUE);
        } else {
            usuario.setAtivo(ativo);
        }

        usuario.persist();

        if (senhaGerada) {
            emailService.enviarSenhaTemporaria(usuario.getEmail(), usuario.getNome(), senhaFinal);
        }

        return usuario;
    }
}
