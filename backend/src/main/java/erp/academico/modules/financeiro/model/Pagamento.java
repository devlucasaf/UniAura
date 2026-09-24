package erp.academico.modules.financeiro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mensalidadeId", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fkPagamentoMensalidade"))
    private Mensalidade mensalidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "formaPagamento", nullable = false, length = 20)
    private FormaPagamento formaPagamento;

    @Column(name = "valorPago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "cartaoFinal", length = 4)
    private String cartaoFinal;

    @CreationTimestamp
    @Column(name = "dataPagamento", nullable = false, updatable = false)
    private LocalDateTime dataPagamento;
}
