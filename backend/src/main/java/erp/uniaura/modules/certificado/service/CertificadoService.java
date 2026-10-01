package erp.uniaura.modules.certificado.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.model.StatusAluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.certificado.dto.CertificadoEmitidoResponseDTO;
import erp.uniaura.modules.certificado.model.CertificadoEmitido;
import erp.uniaura.modules.certificado.model.TipoCertificado;
import erp.uniaura.modules.certificado.repository.CertificadoEmitidoRepository;
import erp.uniaura.modules.nota.dto.NotaResponseDTO;
import erp.uniaura.modules.nota.service.NotaService;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificadoService {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float MARGEM_ESQUERDA = 60f;
    private static final float TOPO = 760f;
    private static final float ALTURA_LINHA = 18f;

    private final AlunoRepository alunoRepository;
    private final NotaService notaService;
    private final CertificadoEmitidoRepository certificadoEmitidoRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- GERA A DECLARAÇÃO DE MATRÍCULA DO ALUNO EM PDF ---
    @Transactional
    public byte[] gerarDeclaracaoMatricula(Long alunoId) {
        Aluno aluno = buscarAluno(alunoId);

        List<String> linhas = List.of(
                "Declaramos, para os devidos fins, que " + nomeDoAluno(aluno) + ", portador(a) da matrícula " + aluno.getMatriculaRA() + ",",
                "encontra-se regularmente matriculado(a) nesta instituição de ensino, com ingresso em " + formatar(aluno.getDataIngresso()) + ".",
                " ",
                "Esta declaração é válida para todos os fins de comprovação de vínculo estudantil."
        );

        String codigo = registrar(aluno, TipoCertificado.DECLARACAO_MATRICULA);
        return montarPdf("DECLARAÇÃO DE MATRÍCULA", linhas, codigo);
    }

    // --- GERA O HISTÓRICO ESCOLAR DO ALUNO EM PDF, COM TODAS AS NOTAS LANÇADAS ---
    @Transactional
    public byte[] gerarHistoricoEscolar(Long alunoId) {
        Aluno aluno = buscarAluno(alunoId);
        List<NotaResponseDTO> notas = notaService.listarPorAluno(alunoId);

        List<String> cabecalho = List.of(
                "Aluno(a): " + nomeDoAluno(aluno) + "  —  Matrícula: " + aluno.getMatriculaRA(),
                " "
        );

        List<String> linhasNotas = notas.stream()
                .map(n -> "%-28s %-8s %-14s %-14s Nota: %s".formatted(
                        truncar(n.getDisciplinaNome(), 28), n.getTurmaCodigo(),
                        n.getPeriodoAvaliacao(), n.getTipoAvaliacao(), n.getValor()))
                .toList();

        List<String> linhas = new java.util.ArrayList<>(cabecalho);
        if (linhasNotas.isEmpty()) {
            linhas.add("Nenhuma nota lançada até o momento.");
        } else {
            linhas.addAll(linhasNotas);
        }

        String codigo = registrar(aluno, TipoCertificado.HISTORICO_ESCOLAR);
        return montarPdf("HISTÓRICO ESCOLAR", linhas, codigo);
    }

    // --- GERA O CERTIFICADO DE CONCLUSÃO, SOMENTE PARA ALUNOS COM STATUS FORMADO ---
    @Transactional
    public byte[] gerarCertificadoConclusao(Long alunoId) {
        Aluno aluno = buscarAluno(alunoId);

        if (aluno.getStatus() != StatusAluno.FORMADO) {
            throw new BusinessException("Só é possível emitir certificado de conclusão para alunos formados.");
        }

        List<String> linhas = List.of(
                "Certificamos que " + nomeDoAluno(aluno) + ", portador(a) da matrícula " + aluno.getMatriculaRA() + ",",
                "concluiu com aproveitamento o curso oferecido por esta instituição de ensino.",
                " ",
                "Este certificado é válido para todos os fins legais de comprovação de conclusão de curso."
        );

        String codigo = registrar(aluno, TipoCertificado.CERTIFICADO_CONCLUSAO);
        return montarPdf("CERTIFICADO DE CONCLUSÃO", linhas, codigo);
    }

    // --- LISTA O HISTÓRICO DE CERTIFICADOS JÁ EMITIDOS PARA UM ALUNO ---
    @Transactional(readOnly = true)
    public Page<CertificadoEmitidoResponseDTO> listarPorAluno(Long alunoId, Pageable pageable) {
        return certificadoEmitidoRepository.findByAlunoId(alunoId, pageable).map(this::toResponse);
    }

    // --- VALIDA UM CÓDIGO DE VERIFICAÇÃO IMPRESSO NO PDF (AUTENTICIDADE PÚBLICA) ---
    @Transactional(readOnly = true)
    public CertificadoEmitidoResponseDTO validar(String codigoVerificacao) {
        return certificadoEmitidoRepository.findByCodigoVerificacao(codigoVerificacao)
                .map(this::toResponse)
                .orElseThrow(() -> new BusinessException("Código de verificação inválido ou não encontrado."));
    }

    // --- REGISTRA A EMISSÃO DO CERTIFICADO PARA AUDITORIA E VERIFICAÇÃO FUTURA ---
    private String registrar(Aluno aluno, TipoCertificado tipo) {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        String codigo = UUID.randomUUID().toString();

        CertificadoEmitido certificado = CertificadoEmitido.builder()
                .aluno(aluno)
                .tipo(tipo)
                .codigoVerificacao(codigo)
                .emitidoPor(autenticado)
                .build();
        certificadoEmitidoRepository.save(certificado);

        return codigo;
    }

    // --- MONTA UM PDF SIMPLES DE UMA PÁGINA COM TÍTULO, CORPO E CÓDIGO DE VERIFICAÇÃO NO RODAPÉ ---
    private byte[] montarPdf(String titulo, List<String> linhasCorpo, String codigoVerificacao) {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage(PDRectangle.A4);
            documento.addPage(pagina);

            try (PDPageContentStream conteudo = new PDPageContentStream(documento, pagina)) {
                conteudo.beginText();
                conteudo.setFont(PDType1Font.HELVETICA_BOLD, 16);
                conteudo.newLineAtOffset(MARGEM_ESQUERDA, TOPO);
                conteudo.showText(titulo);
                conteudo.endText();

                float y = TOPO - 40f;
                conteudo.beginText();
                conteudo.setFont(PDType1Font.HELVETICA, 11);
                conteudo.newLineAtOffset(MARGEM_ESQUERDA, y);
                for (String linha : linhasCorpo) {
                    conteudo.showText(linha);
                    conteudo.newLineAtOffset(0, -ALTURA_LINHA);
                }
                conteudo.endText();

                conteudo.beginText();
                conteudo.setFont(PDType1Font.HELVETICA, 8);
                conteudo.newLineAtOffset(MARGEM_ESQUERDA, 40f);
                conteudo.showText("Emitido em " + formatar(LocalDate.now()) + " — Código de verificação: " + codigoVerificacao);
                conteudo.endText();
            }

            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            documento.save(saida);
            return saida.toByteArray();
        } catch (IOException ex) {
            throw new UncheckedIOException("Falha ao gerar o PDF do certificado.", ex);
        }
    }

    private Aluno buscarAluno(Long alunoId) {
        return alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", alunoId));
    }

    private String nomeDoAluno(Aluno aluno) {
        return aluno.getUsuario() == null ? "Aluno" : aluno.getUsuario().getNome();
    }

    private String formatar(LocalDate data) {
        return data == null ? "" : data.format(FORMATO_DATA);
    }

    private String truncar(String texto, int max) {
        if (texto == null) {
            return "";
        }
        return texto.length() <= max ? texto : texto.substring(0, max - 1) + "…";
    }

    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    private CertificadoEmitidoResponseDTO toResponse(CertificadoEmitido c) {
        return CertificadoEmitidoResponseDTO.builder()
                .id(c.getId())
                .alunoId(c.getAluno().getId())
                .alunoNome(nomeDoAluno(c.getAluno()))
                .tipo(c.getTipo())
                .codigoVerificacao(c.getCodigoVerificacao())
                .emitidoPorNome(c.getEmitidoPor().getNome())
                .emitidoEm(c.getEmitidoEm())
                .build();
    }
}
