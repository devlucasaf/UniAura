package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.infra.email.EmailService;
import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.StatusEmprestimo;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MarcarEmprestimosVencidosComponent extends BaseComponent {

    private final CalcularDiasDeAtrasoComponent calcularDiasDeAtrasoComponent;
    private final EmailService emailService;

    // --- EMPRÉSTIMOS ATIVOS COM O PRAZO VENCIDO PASSAM A ATRASADOS E O USUÁRIO É AVISADO POR E-MAIL ---
    @Transactional
    public int marcar() {
        LocalDateTime agora = LocalDateTime.now();

        List<Emprestimo> vencidos = em()
                .createQuery("select e from Emprestimo e where e.status = :status and e.dataDevolucaoPrevista < :agora", Emprestimo.class)
                .setParameter("status", StatusEmprestimo.ATIVO)
                .setParameter("agora", agora)
                .getResultList();

        for (Emprestimo emprestimo : vencidos) {
            emprestimo.setStatus(StatusEmprestimo.ATRASADO);
            emprestimo.merge();

            Usuario usuario = emprestimo.getUsuario();

            emailService.notificarEmprestimoAtrasado(usuario.getEmail(), usuario.getNome(),
                    emprestimo.getExemplar().getLivro().getTitulo(),
                    calcularDiasDeAtrasoComponent.calcular(emprestimo.getDataDevolucaoPrevista(), agora));
        }

        return vencidos.size();
    }
}
