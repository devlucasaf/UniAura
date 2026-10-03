package erp.uniaura.infra.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.email", name = "provider", havingValue = "smtp")
public class SmtpEmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    // --- AVISA O ALUNO QUE UMA MENSALIDADE VENCEU E ESTÁ EM ATRASO ---
    @Override
    public void notificarMensalidadeAtrasada(String destinatario, String nome, String competencia, BigDecimal valor) {
        enviar(destinatario, "Mensalidade em atraso",
                "Olá, %s! A mensalidade de %s (R$ %s) está em atraso. Regularize para evitar a incidência de multa e juros."
                        .formatted(nome, competencia, valor));
    }

    // --- AVISA O USUÁRIO QUE UM EMPRÉSTIMO DE LIVRO ESTÁ ATRASADO ---
    @Override
    public void notificarEmprestimoAtrasado(String destinatario, String nome, String livroTitulo, int diasAtraso) {
        enviar(destinatario, "Empréstimo de livro atrasado",
                "Olá, %s! O livro \"%s\" está atrasado há %d dia(s). Devolva-o para evitar o acúmulo de multa."
                        .formatted(nome, livroTitulo, diasAtraso));
    }

    // --- AVISA O ALUNO QUE UMA NOTA FOI LANÇADA PARA ELE ---
    @Override
    public void notificarNotaLancada(String destinatario, String nome, String disciplina, BigDecimal valor) {
        enviar(destinatario, "Nova nota lançada",
                "Olá, %s! Uma nova nota foi lançada em %s: %s.".formatted(nome, disciplina, valor));
    }

    // --- MONTA E ENVIA O E-MAIL, REGISTRANDO FALHA SEM INTERROMPER O FLUXO CHAMADOR ---
    private void enviar(String destinatario, String assunto, String corpo) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(destinatario);
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);
            javaMailSender.send(mensagem);
        } catch (Exception ex) {
            log.error("Falha ao enviar e-mail para {}: {}", destinatario, ex.getMessage());
        }
    }
}
