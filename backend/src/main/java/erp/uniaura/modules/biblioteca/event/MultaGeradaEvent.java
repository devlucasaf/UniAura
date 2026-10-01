package erp.uniaura.modules.biblioteca.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MultaGeradaEvent {

    private final Long          multaId;
    private final Long          emprestimoId;
    private final Long          usuarioId;
    private final BigDecimal    valor;
    private final Integer       diasAtraso;
    private final LocalDateTime geradaEm;
}

