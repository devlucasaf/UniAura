package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.StatusReserva;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class StatusReservaConverter implements AttributeConverter<StatusReserva, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(StatusReserva valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public StatusReserva convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<StatusReserva> encontrado = StatusReserva.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
