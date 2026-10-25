package erp.uniaura.persistence.converters;

import cloudsupport.persistence.UnexpectedEnumValueException;

import erp.uniaura.persistence.entities.PeriodoAvaliacao;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class PeriodoAvaliacaoConverter implements AttributeConverter<PeriodoAvaliacao, String> {

    // --- ENUM PARA O TEXTO GRAVADO NO BANCO ---
    @Override
    public String convertToDatabaseColumn(PeriodoAvaliacao valor) {
        if (valor == null) {
            return null;
        }

        return valor.getId();
    }

    // --- TEXTO DO BANCO PARA O ENUM ---
    @Override
    public PeriodoAvaliacao convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        Optional<PeriodoAvaliacao> encontrado = PeriodoAvaliacao.getEnum(id);
        if (encontrado.isEmpty()) {
            throw new UnexpectedEnumValueException(id);
        }

        return encontrado.get();
    }
}
