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

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "documentoAluno")
public class DocumentoAluno extends SqlServerBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alunoId", nullable = false, foreignKey = @ForeignKey(name = "fkDocumentoAlunoAluno"))
    private Aluno aluno;

    @Column(name = "tipo", nullable = false, length = 40)
    private TipoDocumento tipo;

    @Column(name = "nomeArquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Column(name = "arquivoUrl", nullable = false, length = 500)
    private String arquivoUrl;
    @Column(name = "status", nullable = false, length = 20)
    private StatusDocumento status;

    @Column(name = "versao")
    private Integer versao;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisadoPor", foreignKey = @ForeignKey(name = "fkDocumentoAlunoAnalisadoPor"))
    private Usuario analisadoPor;

    @Column(name = "analisadoEm")
    private LocalDateTime analisadoEm;

    @CreationTimestamp
    @Column(name = "enviadoEm", nullable = false, updatable = false)
    private LocalDateTime enviadoEm;
}
