package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.TipoAtividade;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class TipoAtividadeConverter implements AttributeConverter<TipoAtividade, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(TipoAtividade valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public TipoAtividade convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<TipoAtividade> encontrado = TipoAtividade.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
