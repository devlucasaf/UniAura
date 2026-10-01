package erp.uniaura.infra.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalStorage {
    private String basePath = "./storage";
    private String publicBaseUrl = "/files";
}
