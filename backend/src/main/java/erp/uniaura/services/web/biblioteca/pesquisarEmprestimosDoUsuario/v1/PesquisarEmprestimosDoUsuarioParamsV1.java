package erp.uniaura.services.web.biblioteca.pesquisarEmprestimosDoUsuario.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarEmprestimosDoUsuarioParamsV1 {

    @Schema(description = "Identificador público do usuário")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long usuarioUid;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "50")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 50;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (dataEmprestimo, dataDevolucaoPrevista, status). Ex.: -dataEmprestimo", defaultValue = "-dataEmprestimo")
    @Pattern(regexp = "[+-](dataEmprestimo|dataDevolucaoPrevista|status)", message = "Ordenação inválida. Use, por exemplo, -dataEmprestimo ou +status.")
    private String ordem = "-dataEmprestimo";
}
