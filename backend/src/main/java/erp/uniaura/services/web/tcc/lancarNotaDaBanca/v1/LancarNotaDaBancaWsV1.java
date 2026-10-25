package erp.uniaura.services.web.tcc.lancarNotaDaBanca.v1;

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
public class LancarNotaDaBancaWsV1 extends BaseWs {

    private final LancarNotaDaBancaServiceV1 lancarNotaDaBancaServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Lança a nota e o parecer de um membro da banca",
            description = "Grava a nota e o parecer do membro. O professor só lança a própria nota; coordenação e admin lançam a de qualquer membro. "
                    + "Erro 422 se o membro não existir ou se o professor tentar lançar a nota de outro membro.")
    public void lancar(@LoggedInUserId String usuarioId, @Valid @RequestBody LancarNotaDaBancaParamsV1 params) {
        lancarNotaDaBancaServiceV1.lancar(Long.valueOf(usuarioId), params);
    }
}
