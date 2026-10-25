package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pagamento")
public class Pagamento extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mensalidadeId", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fkPagamentoMensalidade"))
    private Mensalidade mensalidade;

    @Column(name = "formaPagamento", nullable = false, length = 20)
    private FormaPagamento formaPagamento;

    @Column(name = "valorPago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "valorMulta", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMulta;

    @Column(name = "cartaoFinal", length = 4)
    private String cartaoFinal;

    @CreationTimestamp
    @Column(name = "dataPagamento", nullable = false, updatable = false)
    private LocalDateTime dataPagamento;
}
