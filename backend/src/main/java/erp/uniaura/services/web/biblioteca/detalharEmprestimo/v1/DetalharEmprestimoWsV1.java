package erp.uniaura.services.web.biblioteca.detalharEmprestimo.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class DetalharEmprestimoWsV1 extends BaseWs {

    private final DetalharEmprestimoServiceV1 detalharEmprestimoServiceV1;

    // --- ENDPOINT QUE DETALHA O EMPRÉSTIMO COM ATRASO E MULTA ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Detalha um empréstimo",
            description = "Devolve os dados do empréstimo, os dias de atraso e o valor da multa, se houver. A equipe da biblioteca vê qualquer empréstimo; os demais usuários só os próprios. "
                    + "Erro 422 se o empréstimo não existir ou for de outro usuário.")
    public DetalharEmprestimoRetornoV1 detalhar(@LoggedInUserId String usuarioId, @Valid @ParameterObject DetalharEmprestimoParamsV1 params) {
        return detalharEmprestimoServiceV1.detalhar(Long.valueOf(usuarioId), params);
    }
}
