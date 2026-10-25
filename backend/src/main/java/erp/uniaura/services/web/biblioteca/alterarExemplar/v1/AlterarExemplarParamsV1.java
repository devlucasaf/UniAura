package erp.uniaura.services.web.biblioteca.alterarExemplar.v1;

import erp.uniaura.persistence.entities.StatusExemplar;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarExemplarParamsV1 {

    @Schema(description = "Identificador público do exemplar a alterar")
    @NotNull(message = "O uid do exemplar é obrigatório.")
    private Long uid;

    @Schema(description = "Localização física, como a estante")
    @Size(max = 50, message = "A localização deve ter no máximo 50 caracteres.")
    private String localizacao;

    @Schema(description = "Novo status administrativo: DISPONIVEL, MANUTENCAO ou PERDIDO. EMPRESTADO e RESERVADO só mudam por empréstimo e reserva. Sem valor, mantém o atual")
    private StatusExemplar status;
}
