package erp.uniaura.services.common.financeiro;

import cloudsupport.services.BaseComponent;

import erp.uniaura.infra.email.EmailService;
import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.StatusMensalidade;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MarcarMensalidadesAtrasadasComponent extends BaseComponent {

    private final EmailService emailService;

    // --- MENSALIDADES PENDENTES COM O VENCIMENTO PASSADO VIRAM ATRASADAS E O ALUNO É AVISADO POR E-MAIL ---
    @Transactional
    public int marcar() {
        List<Mensalidade> vencidas = em()
                .createQuery("select m from Mensalidade m where m.status = :status and m.vencimento < :hoje", Mensalidade.class)
                .setParameter("status", StatusMensalidade.PENDENTE)
                .setParameter("hoje", LocalDate.now())
                .getResultList();

        for (Mensalidade mensalidade : vencidas) {
            mensalidade.setStatus(StatusMensalidade.ATRASADA);
            mensalidade.merge();

            Usuario usuario = mensalidade.getAluno().getUsuario();

            if (usuario != null) {
                emailService.notificarMensalidadeAtrasada(usuario.getEmail(), usuario.getNome(),
                        mensalidade.getCompetencia(), mensalidade.getValor());
            }
        }

        return vencidas.size();
    }
}
