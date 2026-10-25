package erp.uniaura.infra.seed;

import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;
import erp.uniaura.services.common.usuario.CriarUsuarioComponent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@escola.com";
    private static final String ADMIN_SENHA = "admin123";

    private final ConsultarUsuarioComponent consultarUsuarioComponent;
    private final CriarUsuarioComponent     criarUsuarioComponent;

    // --- CRIA O USUÁRIO ADMINISTRADOR INICIAL CASO ELE AINDA NÃO ESTEJA CADASTRADO ---
    @Override
    public void run(String... args) {
        if (consultarUsuarioComponent.porEmail(ADMIN_EMAIL).isPresent()) {
            return;
        }

        criarUsuarioComponent.criar("Administrador", ADMIN_EMAIL, ADMIN_SENHA, null, null, null, true, TipoUsuario.ADMIN);

        log.info("Usuario usuarioAdmin inicial criado: {} / {}", ADMIN_EMAIL, ADMIN_SENHA);
    }
}