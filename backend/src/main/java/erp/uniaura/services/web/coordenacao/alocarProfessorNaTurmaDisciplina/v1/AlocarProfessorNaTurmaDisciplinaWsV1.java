package erp.uniaura.services.web.coordenacao.alocarProfessorNaTurmaDisciplina.v1;

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
public class AlocarProfessorNaTurmaDisciplinaWsV1 extends BaseWs {

    private final AlocarProfessorNaTurmaDisciplinaServiceV1 alocarProfessorNaTurmaDisciplinaServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN TROCAM O PROFESSOR RESPONSÁVEL ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Troca o professor responsável por uma turma e disciplina",
            description = "Valida habilitação na disciplina, conflito de horário e carga horária semanal antes de trocar. "
                    + "Erro 422 se o vínculo ou o professor não existirem, se o professor já for o responsável ou se alguma validação falhar.")
    public void alocar(@Valid @RequestBody AlocarProfessorNaTurmaDisciplinaParamsV1 params) {
        alocarProfessorNaTurmaDisciplinaServiceV1.alocar(params);
    }
}
