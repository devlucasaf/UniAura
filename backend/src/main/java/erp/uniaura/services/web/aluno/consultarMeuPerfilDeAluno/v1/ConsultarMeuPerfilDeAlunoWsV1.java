package erp.uniaura.services.web.aluno.consultarMeuPerfilDeAluno.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarMeuPerfilDeAlunoWsV1 extends BaseWs {

    private final ConsultarMeuPerfilDeAlunoServiceV1 consultarMeuPerfilDeAlunoServiceV1;

    // --- O ALUNO CONSULTA SOMENTE O PRÓPRIO PERFIL ---
    @GetMapping
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Consulta o perfil completo do aluno autenticado",
            description = "Devolve dados pessoais, acadêmicos, endereço, documentos e escolaridade do aluno logado, com o semestre em curso. "
                    + "Erro 422 se o usuário logado não tiver cadastro de aluno.")
    public ConsultarMeuPerfilDeAlunoRetornoV1 consultar(@LoggedInUserId String usuarioId) {
        return consultarMeuPerfilDeAlunoServiceV1.consultar(Long.valueOf(usuarioId));
    }
}