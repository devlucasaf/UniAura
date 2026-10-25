package erp.uniaura.services.web.autenticacao.solicitarRecuperacaoDeSenha.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitarRecuperacaoDeSenhaRetornoV1 {

    @Schema(description = "Código de 6 números; só vem preenchido enquanto não há envio de e-mail (app.recuperacao-senha.exibir-codigo)")
    private String codigoSimulado;

    @Schema(description = "Minutos de validade do código")
    private Integer validadeMinutos;
}
