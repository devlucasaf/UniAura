package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import org.springframework.stereotype.Component;

@Component
public class GerarCodigoDeBarrasComponent extends BaseComponent {

    // --- SORTEIA CÓDIGOS ATÉ ACHAR UM QUE AINDA NÃO ESTEJA EM USO POR OUTRO EXEMPLAR ---
    public String gerar() {
        String codigo;
        Long emUso;

        do {
            codigo = "EX" + System.currentTimeMillis() + String.format("%04d", (int) (Math.random() * 10000));

            emUso = em()
                    .createQuery("select count(e) from Exemplar e where e.codigoBarras = :codigo", Long.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } while (emUso > 0);

        return codigo;
    }
}
