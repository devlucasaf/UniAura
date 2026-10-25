package erp.uniaura.services.web.avaliacaoDocente.consultarMediaDaTurmaDisciplina.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarMediaDaTurmaDisciplinaWsV1 extends BaseWs {

    private final ConsultarMediaDaTurmaDisciplinaServiceV1 consultarMediaDaTurmaDisciplinaServiceV1;

    // --- ENDPOINT QUE RETORNA AS MÉDIAS DAS AVALIAÇÕES DA TURMA/DISCIPLINA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Consulta a média das avaliações de uma turma e disciplina",
            description = "Devolve a quantidade de avaliações e as médias de didática, pontualidade, disponibilidade e geral. Erro 422 se o vínculo não existir.")
    public ConsultarMediaDaTurmaDisciplinaRetornoV1 consultar(@Valid @ParameterObject ConsultarMediaDaTurmaDisciplinaParamsV1 params) {
        return consultarMediaDaTurmaDisciplinaServiceV1.consultar(params);
    }
}
