package erp.uniaura.services.web.atividade.entregarAtividade.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Ws
@RequiredArgsConstructor
public class EntregarAtividadeWsV1 extends BaseWs {

    private final EntregarAtividadeServiceV1 entregarAtividadeServiceV1;

    // --- SÓ O ALUNO ENTREGA UMA ATIVIDADE, ENVIANDO OS DADOS EM JSON E O ARQUIVO NA MESMA REQUISIÇÃO ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HasAuthority("ROLE_ALUNO")
    @Operation(summary = "Entrega uma atividade (upload de arquivo)",
            description = "Os dados vão na parte 'dados' (JSON) e o arquivo na parte 'arquivo'. Uma nova entrega substitui a anterior, desde que ainda não tenha sido avaliada; "
                    + "depois do prazo o status fica ATRASADA. Devolve o uid da entrega. "
                    + "Erro 422 se faltar o arquivo, se a atividade não existir ou estiver inativa, se o usuário não for aluno, se não houver matrícula ATIVA na turma ou se a entrega anterior já foi avaliada.")
    public Uid entregar(@LoggedInUserId String usuarioId, @Valid @RequestPart("dados") EntregarAtividadeParamsV1 params,
                  @RequestPart("arquivo") MultipartFile arquivo) {
        return entregarAtividadeServiceV1.entregar(Long.valueOf(usuarioId), params, arquivo);
    }
}
