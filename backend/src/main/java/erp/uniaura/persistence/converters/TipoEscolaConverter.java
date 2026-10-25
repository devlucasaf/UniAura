package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.TipoEscola;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class TipoEscolaConverter implements AttributeConverter<TipoEscola, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(TipoEscola valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public TipoEscola convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<TipoEscola> encontrado = TipoEscola.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
