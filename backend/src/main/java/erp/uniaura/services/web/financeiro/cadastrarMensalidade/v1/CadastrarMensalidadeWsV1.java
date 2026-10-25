package erp.uniaura.services.web.financeiro.cadastrarMensalidade.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CadastrarMensalidadeWsV1 extends BaseWs {

    private final CadastrarMensalidadeServiceV1 cadastrarMensalidadeServiceV1;

    // --- SÓ A EQUIPE FINANCEIRA E O ADMIN LANÇAM MENSALIDADES ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO"})
    @Operation(summary = "Cadastra uma mensalidade",
            description = "Lança a mensalidade de uma competência para o aluno e devolve o uid gerado. "
                    + "Erro 422 se o aluno não existir ou se já houver mensalidade dele na mesma competência.")
    public Uid cadastrar(@Valid @RequestBody CadastrarMensalidadeParamsV1 params) {
        return cadastrarMensalidadeServiceV1.cadastrar(params);
    }
}
