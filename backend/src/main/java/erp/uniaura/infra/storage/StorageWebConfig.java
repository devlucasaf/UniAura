package erp.uniaura.infra.storage;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class StorageWebConfig implements WebMvcConfigurer {

    private final StorageProperties properties;

    // --- CONFIGURA O ACESSO PÚBLICO AOS ARQUIVOS ARMAZENADOS LOCALMENTE ---
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String publicBaseUrl = properties.getLocalStorage().getPublicBaseUrl();
        String location = "file:" + Paths.get(properties.getLocalStorage().getBasePath())
                .toAbsolutePath().normalize() + "/";

        registry.addResourceHandler(publicBaseUrl + "/**")
                .addResourceLocations(location);
    }
}

