package erp.uniaura.modules.biblioteca.reserva.model;

import erp.uniaura.infra.persistence.SqlServerBaseEntity;
import erp.uniaura.modules.biblioteca.livro.model.Livro;
import erp.uniaura.modules.usuario.model.Usuario;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusReserva status;

    @Column(name = "posicaoFila", nullable = false)
    private Integer posicaoFila;

    @Column(name = "prazoRetirada")
    private LocalDateTime prazoRetirada;
}

