package erp.uniaura.services.web.comunicado.pesquisarMural.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarMuralWsV1 extends BaseWs {

    private final PesquisarMuralServiceV1 pesquisarMuralServiceV1;

    // --- ENDPOINT QUE RETORNA O MURAL DE COMUNICADOS DO USUÁRIO LOGADO ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa o mural de comunicados de quem está logado",
            description = "Devolve até 30 comunicados relevantes para o usuário do token: alunos veem os gerais, os para alunos e os da própria turma; "
                    + "professores veem os gerais e os para professores; os demais perfis veem os gerais.")
    public PesquisarMuralRetornoV1 pesquisar(@LoggedInUserId String usuarioId) {
        return pesquisarMuralServiceV1.pesquisar(Long.valueOf(usuarioId));
    }
}
