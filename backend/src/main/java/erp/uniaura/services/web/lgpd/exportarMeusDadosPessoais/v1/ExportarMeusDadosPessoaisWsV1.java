package erp.uniaura.services.web.lgpd.exportarMeusDadosPessoais.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ExportarMeusDadosPessoaisWsV1 extends BaseWs {

    private final ExportarMeusDadosPessoaisServiceV1 exportarMeusDadosPessoaisServiceV1;

    // --- QUALQUER USUÁRIO LOGADO EXPORTA OS PRÓPRIOS DADOS ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Exporta os dados pessoais do usuário autenticado",
            description = "Direito de acesso da LGPD: devolve usuário e, para aluno, perfil, mensalidades, notas e documentos. A exportação fica registrada na auditoria.")
    public ExportarMeusDadosPessoaisRetornoV1 exportar(@LoggedInUserId String usuarioId) {
        return exportarMeusDadosPessoaisServiceV1.exportar(Long.valueOf(usuarioId));
    }
}
