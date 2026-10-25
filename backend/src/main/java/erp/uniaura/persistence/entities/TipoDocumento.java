package erp.uniaura.persistence.entities;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoDocumento {
    RG,
    CPF,
    HISTORICO_ESCOLAR,
    CERTIFICADO_CONCLUSAO,
    CERTIDAO_NASCIMENTO_CASAMENTO,
    COMPROVANTE_RESIDENCIA,
    TITULO_ELEITOR,
    CERTIFICADO_RESERVISTA,
    FOTO,
    OUTRO;

    // --- TEXTO GRAVADO NO BANCO (O MESMO NOME DA CONSTANTE; O CONVERSOR USA ESTE VALOR) ---
    public String getId() {
        return name();
    }

    public static Optional<TipoDocumento> getEnum(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Stream.of(values()).filter(valor -> valor.getId().equals(id)).findFirst();
    }
}
