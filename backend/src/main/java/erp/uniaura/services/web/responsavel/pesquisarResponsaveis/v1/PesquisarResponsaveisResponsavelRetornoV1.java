package erp.uniaura.services.web.responsavel.pesquisarResponsaveis.v1;

import erp.uniaura.persistence.entities.Parentesco;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarResponsaveisResponsavelRetornoV1 {

    @Schema(description = "Identificador público do responsável")
    private Long        uid;
    private String      nome;
    private String      email;
    private String      telefone;
    private Parentesco  parentesco;
}
