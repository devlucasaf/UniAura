package erp.uniaura.services.web.biblioteca.renovarEmprestimo.v1;

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
public class RenovarEmprestimoWsV1 extends BaseWs {

    private final RenovarEmprestimoServiceV1 renovarEmprestimoServiceV1;

    // --- ENDPOINT QUE RENOVA O EMPRÉSTIMO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN", "ROLE_ALUNO", "ROLE_PROFESSOR"})
    @Operation(summary = "Renova um empréstimo",
            description = "Estende o prazo de devolução. A equipe renova qualquer empréstimo; aluno e professor só renovam os próprios. "
                    + "Erro 422 se o empréstimo não existir, se for de outro usuário, se já estiver devolvido, se o limite de renovações foi atingido, se o usuário tiver multa pendente ou se houver reservas para o livro.")
    public void renovar(@LoggedInUserId String usuarioId, @Valid @RequestBody RenovarEmprestimoParamsV1 params) {
        renovarEmprestimoServiceV1.renovar(Long.valueOf(usuarioId), params);
    }
}
