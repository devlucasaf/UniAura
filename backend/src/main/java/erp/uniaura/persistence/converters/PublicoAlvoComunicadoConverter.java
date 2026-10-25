package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.PublicoAlvoComunicado;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class PublicoAlvoComunicadoConverter implements AttributeConverter<PublicoAlvoComunicado, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(PublicoAlvoComunicado valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public PublicoAlvoComunicado convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<PublicoAlvoComunicado> encontrado = PublicoAlvoComunicado.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
