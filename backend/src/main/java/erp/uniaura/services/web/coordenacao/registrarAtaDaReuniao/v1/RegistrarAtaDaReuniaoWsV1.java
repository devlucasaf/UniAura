package erp.uniaura.services.web.coordenacao.registrarAtaDaReuniao.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RegistrarAtaDaReuniaoWsV1 extends BaseWs {

    private final RegistrarAtaDaReuniaoServiceV1 registrarAtaDaReuniaoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN REGISTRAM A ATA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Registra a ata de uma reunião do colegiado",
            description = "Grava as deliberações, marca as presenças (quem não for listado fica ausente) e passa a reunião para REALIZADA. "
                    + "Erro 422 se a reunião não existir, se já foi realizada ou cancelada ou se não há participantes convocados.")
    public void registrar(@Valid @RequestBody RegistrarAtaDaReuniaoParamsV1 params) {
        registrarAtaDaReuniaoServiceV1.registrar(params);
    }
}
