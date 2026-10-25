package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.TipoRegistroAcompanhamento;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class TipoRegistroAcompanhamentoConverter implements AttributeConverter<TipoRegistroAcompanhamento, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(TipoRegistroAcompanhamento valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public TipoRegistroAcompanhamento convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<TipoRegistroAcompanhamento> encontrado = TipoRegistroAcompanhamento.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
