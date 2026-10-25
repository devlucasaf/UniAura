package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.StatusPlanoEnsino;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class StatusPlanoEnsinoConverter implements AttributeConverter<StatusPlanoEnsino, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(StatusPlanoEnsino valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public StatusPlanoEnsino convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<StatusPlanoEnsino> encontrado = StatusPlanoEnsino.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
