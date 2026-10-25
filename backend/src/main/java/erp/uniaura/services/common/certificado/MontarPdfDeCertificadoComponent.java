package erp.uniaura.services.common.certificado;

import cloudsupport.services.BaseComponent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MontarPdfDeCertificadoComponent extends BaseComponent {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float MARGEM_ESQUERDA = 60f;
    private static final float TOPO = 760f;
    private static final float ALTURA_LINHA = 18f;

    // --- MONTA UM PDF SIMPLES DE UMA PÁGINA COM TÍTULO, CORPO E CÓDIGO DE VERIFICAÇÃO NO RODAPÉ ---
    public byte[] montar(String titulo, List<String> linhasCorpo, String codigoVerificacao) {
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
                conteudo.showText("Emitido em " + formatarData(LocalDate.now()) + " — Código de verificação: " + codigoVerificacao);
                conteudo.endText();
            }

            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            documento.save(saida);
            return saida.toByteArray();
        } catch (IOException ex) {
            throw new UncheckedIOException("Falha ao gerar o PDF do certificado.", ex);
        }
    }

    // --- FORMATA DATA NO PADRÃO DD/MM/AAAA ---
    public String formatarData(LocalDate data) {
        if (data == null) {
            return "";
        }

        return data.format(FORMATO_DATA);
    }
}
