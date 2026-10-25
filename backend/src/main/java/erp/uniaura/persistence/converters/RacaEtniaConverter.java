package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.RacaEtnia;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class RacaEtniaConverter implements AttributeConverter<RacaEtnia, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(RacaEtnia valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public RacaEtnia convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<RacaEtnia> encontrado = RacaEtnia.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
