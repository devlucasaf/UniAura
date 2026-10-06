package erp.uniaura.infra.email;

import java.math.BigDecimal;

public interface EmailService {

    // --- ENVIA A SENHA TEMPORÁRIA GERADA PARA UM NOVO USUÁRIO ---
    void enviarSenhaTemporaria(String destinatario, String nome, String senhaTemporaria);

    // --- AVISA O ALUNO QUE UMA MENSALIDADE VENCEU E ESTÁ EM ATRASO ---
    void notificarMensalidadeAtrasada(String destinatario, String nome, String competencia, BigDecimal valor);

    // --- AVISA O USUÁRIO QUE UM EMPRÉSTIMO DE LIVRO ESTÁ ATRASADO ---
    void notificarEmprestimoAtrasado(String destinatario, String nome, String livroTitulo, int diasAtraso);

    // --- AVISA O ALUNO QUE UMA NOTA FOI LANÇADA PARA ELE ---
    void notificarNotaLancada(String destinatario, String nome, String disciplina, BigDecimal valor);
}
