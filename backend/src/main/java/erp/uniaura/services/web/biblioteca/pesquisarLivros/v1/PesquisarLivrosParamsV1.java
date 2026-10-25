package erp.uniaura.services.web.biblioteca.pesquisarLivros.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarLivrosParamsV1 {

    @Schema(description = "Parte do título; sem valor, não filtra")
    private String titulo;

    @Schema(description = "Parte do nome do autor; sem valor, não filtra")
    private String autor;

    @Schema(description = "Categoria exata (sem diferenciar maiúsculas); sem valor, não filtra")
    private String categoria;

    @Schema(description = "ISBN exato; sem valor, não filtra")
    private String isbn;

    @Schema(description = "Posição do primeiro registro, a partir de 0", defaultValue = "0")
    @Min(value = 0, message = "A posição inicial não pode ser negativa.")
    private Integer de = 0;

    @Schema(description = "Quantidade de registros, de 1 a 100", defaultValue = "20")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1.")
    @Max(value = 100, message = "A quantidade deve ser de no máximo 100.")
    private Integer qtd = 20;

    @Schema(description = "Ordenação: sinal + (crescente) ou - (decrescente) seguido do campo (titulo, autor, categoria, criadoEm). Ex.: +titulo", defaultValue = "+titulo")
    @Pattern(regexp = "[+-](titulo|autor|categoria|criadoEm)", message = "Ordenação inválida. Use, por exemplo, +titulo ou -criadoEm.")
    private String ordem = "+titulo";
}
