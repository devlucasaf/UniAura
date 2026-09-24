package erp.academico.modules.documento.dto;

import erp.academico.modules.documento.model.StatusDocumento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisarDocumentoRequestDTO {

    @NotNull(message = "O status é obrigatório.")
    private StatusDocumento status;

    @Size(max = 500)
    private String observacoes;
}
