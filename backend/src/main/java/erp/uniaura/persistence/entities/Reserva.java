package erp.uniaura.persistence.entities;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reserva")
public class Reserva extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livroId", nullable = false, foreignKey = @ForeignKey(name = "fkReservaLivro"))
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarioId", nullable = false, foreignKey = @ForeignKey(name = "fkReservaUsuario"))
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "dataReserva", nullable = false, updatable = false)
    private LocalDateTime dataReserva;
    @Column(name = "status", nullable = false, length = 20)
    private StatusReserva status;

    @Column(name = "posicaoFila", nullable = false)
    private Integer posicaoFila;

    @Column(name = "prazoRetirada")
    private LocalDateTime prazoRetirada;
}

