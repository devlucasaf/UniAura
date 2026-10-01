package erp.uniaura.modules.certificado.services.web.rest.v1;

import erp.uniaura.modules.certificado.dto.CertificadoEmitidoResponseDTO;
import erp.uniaura.modules.certificado.service.CertificadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Ws("/certificados")
@RequiredArgsConstructor
@Tag(name = "Certificados e Diplomas", description = "Emissão de declaração de matrícula, histórico escolar e certificado de conclusão")
public class CertificadoWsV1 {

    private final CertificadoService certificadoService;

    // --- EMITE A DECLARAÇÃO DE MATRÍCULA DO ALUNO EM PDF ---
    @GetMapping("/alunos/{alunoId}/declaracao-matricula")
    @Operation(summary = "Emite a declaração de matrícula do aluno em PDF")
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<byte[]> declaracaoMatricula(@PathVariable Long alunoId) {
        return pdf(certificadoService.gerarDeclaracaoMatricula(alunoId), "declaracao-matricula.pdf");
    }

    // --- EMITE O HISTÓRICO ESCOLAR DO ALUNO EM PDF ---
    @GetMapping("/alunos/{alunoId}/historico-escolar")
    @Operation(summary = "Emite o histórico escolar do aluno em PDF")
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<byte[]> historicoEscolar(@PathVariable Long alunoId) {
        return pdf(certificadoService.gerarHistoricoEscolar(alunoId), "historico-escolar.pdf");
    }

    // --- EMITE O CERTIFICADO DE CONCLUSÃO DO ALUNO EM PDF (SOMENTE ALUNOS FORMADOS) ---
    @GetMapping("/alunos/{alunoId}/certificado-conclusao")
    @Operation(summary = "Emite o certificado de conclusão do aluno em PDF (somente alunos formados)")
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<byte[]> certificadoConclusao(@PathVariable Long alunoId) {
        return pdf(certificadoService.gerarCertificadoConclusao(alunoId), "certificado-conclusao.pdf");
    }

    // --- LISTA O HISTÓRICO DE CERTIFICADOS JÁ EMITIDOS PARA UM ALUNO ---
    @GetMapping("/alunos/{alunoId}")
    @Operation(summary = "Lista o histórico de certificados já emitidos para um aluno")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<Page<CertificadoEmitidoResponseDTO>> listarPorAluno(@PathVariable Long alunoId, Pageable pageable) {
        return ResponseEntity.ok(certificadoService.listarPorAluno(alunoId, pageable));
    }

    // --- VALIDA A AUTENTICIDADE DE UM CERTIFICADO PELO CÓDIGO DE VERIFICAÇÃO IMPRESSO NO PDF ---
    @GetMapping("/verificar/{codigo}")
    @Operation(summary = "Valida a autenticidade de um certificado pelo código de verificação impresso no PDF")
    public ResponseEntity<CertificadoEmitidoResponseDTO> validar(@PathVariable String codigo) {
        return ResponseEntity.ok(certificadoService.validar(codigo));
    }

    // --- MONTA A RESPOSTA HTTP COM O PDF COMO ANEXO ---
    private ResponseEntity<byte[]> pdf(byte[] conteudo, String nomeArquivo) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(conteudo);
    }
}
