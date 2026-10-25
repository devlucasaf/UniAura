package erp.uniaura.services.web.material.cadastrarMaterial.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class CadastrarMaterialWsV1 extends BaseWs {

    private final CadastrarMaterialServiceV1 cadastrarMaterialServiceV1;

    // --- ENDPOINT QUE CADASTRA UM MATERIAL DIDÁTICO COM ARQUIVO OU LINK ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Cadastra um material didático",
            description = "Cria o material na turma e disciplina do vínculo informado e devolve o uid gerado. Os dados vão na parte 'dados' (JSON) e o arquivo, quando houver, na parte 'arquivo'. "
                    + "Tipo LINK exige 'linkUrl'; os demais tipos exigem arquivo. Só o professor responsável pela disciplina, a coordenação ou o admin podem cadastrar.")
    public Uid cadastrar(@LoggedInUserId String usuarioId,
                         @Valid @RequestPart("dados") CadastrarMaterialParamsV1 params,
                         @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) {
        return cadastrarMaterialServiceV1.cadastrar(Long.valueOf(usuarioId), params, arquivo);
    }
}
