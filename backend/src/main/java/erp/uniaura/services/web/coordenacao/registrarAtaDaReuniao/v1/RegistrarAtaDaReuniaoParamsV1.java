package erp.uniaura.services.web.coordenacao.registrarAtaDaReuniao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrarAtaDaReuniaoParamsV1 {

    @Schema(description = "Identificador público da reunião")
    @NotNull(message = "O uid da reunião é obrigatório.")
    private Long reuniaoUid;

    @Schema(description = "Deliberações da reunião, de 20 a 8000 caracteres")
    @NotNull(message = "As deliberações são obrigatórias.")
    @Size(min = 20, max = 8000, message = "As deliberações devem ter de 20 a 8000 caracteres.")
    private String deliberacoes;

    @Schema(description = "Presenças; quem não for listado é registrado como ausente")
    @Valid
    private List<RegistrarAtaDaReuniaoPresencaParamsV1> presencas;
}
