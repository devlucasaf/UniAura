package erp.uniaura.services.web.biblioteca.registrarEmprestimo.v1;

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
public class RegistrarEmprestimoWsV1 extends BaseWs {

    private final RegistrarEmprestimoServiceV1 registrarEmprestimoServiceV1;

    // --- ENDPOINT QUE REGISTRA UM NOVO EMPRÉSTIMO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Registra um empréstimo",
            description = "Empresta o exemplar ao usuário, define a devolução prevista pelo perfil dele, marca o exemplar como EMPRESTADO e, se o usuário era o primeiro da fila de reservas do livro, atende a reserva. "
                    + "Devolve o uid e a devolução prevista. Erro 422 se o usuário ou o exemplar não existirem, se o exemplar não estiver DISPONIVEL, se o usuário tiver multa pendente ou atingir o limite de empréstimos simultâneos, "
                    + "ou se nem o uid nem o código de barras do exemplar forem informados.")
    public RegistrarEmprestimoRetornoV1 registrar(@LoggedInUserId String atendenteId, @Valid @RequestBody RegistrarEmprestimoParamsV1 params) {
        return registrarEmprestimoServiceV1.registrar(Long.valueOf(atendenteId), params);
    }
}
