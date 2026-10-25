package erp.uniaura.services.web.biblioteca.reservarLivro.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class ReservarLivroWsV1 extends BaseWs {

    private final ReservarLivroServiceV1 reservarLivroServiceV1;

    // --- ENDPOINT QUE RESERVA UM LIVRO PARA O USUÁRIO LOGADO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_PROFESSOR"})
    @Operation(summary = "Reserva um livro para o usuário autenticado",
            description = "Coloca o usuário no fim da fila de reservas do livro e devolve o uid e a posição. "
                    + "Erro 422 se o livro não existir, se houver exemplar disponível (nesse caso basta pedir o empréstimo) ou se o usuário já tiver reserva ativa do livro.")
    public ReservarLivroRetornoV1 reservar(@LoggedInUserId String usuarioId, @Valid @RequestBody ReservarLivroParamsV1 params) {
        return reservarLivroServiceV1.reservar(Long.valueOf(usuarioId), params);
    }
}
