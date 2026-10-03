package erp.uniaura.infra.email;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "app.email", name = "provider", havingValue = "log", matchIfMissing = true)
public class LogEmailService implements EmailService {

    // --- AVISA O ALUNO QUE UMA MENSALIDADE VENCEU E ESTÁ EM ATRASO ---
    @Override
    public void notificarMensalidadeAtrasada(String destinatario, String nome, String competencia, BigDecimal valor) {
        log.info("""

                Para: {} <{}>
                Assunto: Mensalidade em atraso

                Olá, {}! A mensalidade de {} (R$ {}) está em atraso. Regularize para evitar a incidência de multa e juros.

                """, nome, destinatario, nome, competencia, valor);
    }

    // --- AVISA O USUÁRIO QUE UM EMPRÉSTIMO DE LIVRO ESTÁ ATRASADO ---
    @Override
    public void notificarEmprestimoAtrasado(String destinatario, String nome, String livroTitulo, int diasAtraso) {
        log.info("""

                Para: {} <{}>
                Assunto: Empréstimo de livro atrasado

                Olá, {}! O livro "{}" está atrasado há {} dia(s). Devolva-o para evitar o acúmulo de multa.

                """, nome, destinatario, nome, livroTitulo, diasAtraso);
    }

    // --- AVISA O ALUNO QUE UMA NOTA FOI LANÇADA PARA ELE ---
    @Override
    public void notificarNotaLancada(String destinatario, String nome, String disciplina, BigDecimal valor) {
        log.info("""

                Para: {} <{}>
                Assunto: Nova nota lançada

                Olá, {}! Uma nova nota foi lançada em {}: {}.

                """, nome, destinatario, nome, disciplina, valor);
    }
}

