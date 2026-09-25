package erp.uniaura.modules.aluno.model;

import erp.uniaura.modules.usuario.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarioId", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fkAlunoUsuario"))
    private Usuario usuario;

    @Column(name = "matriculaRA", nullable = false, unique = true, length = 30)
    private String matriculaRA;

    @Column(name = "dataIngresso", nullable = false)
    private LocalDate dataIngresso;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusAluno status;

    @Column(name = "turmaAtualId")
    private UUID turmaAtualId;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @Column(name = "curso", length = 150)
    private String curso;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", length = 25)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoCivil", length = 20)
    private EstadoCivil estadoCivil;

    @Column(name = "nacionalidade", length = 80)
    private String nacionalidade;

    // --- ORIGEM ---
    @Column(name = "municipioNascimento", length = 100)
    private String municipioNascimento;

    @Column(name = "cidade", length = 80)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    // --- IDENTIFICAÇÃO ---
    @Column(name = "documentoOrgaoEmissor", length = 20)
    private String documentoOrgaoEmissor;

    @Column(name = "documentoNumero", length = 30)
    private String documentoNumero;

    @Column(name = "ufExpedicaoIdentidade", length = 2)
    private String ufExpedicaoIdentidade;

    @Column(name = "dataExpedicaoIdentidade")
    private LocalDate dataExpedicaoIdentidade;

    @Column(name = "numeroTituloEleitor", length = 20)
    private String numeroTituloEleitor;

    @Column(name = "numeroZonaEleitoral", length = 10)
    private String numeroZonaEleitoral;

    @Column(name = "ufZonaEleitoral", length = 2)
    private String ufZonaEleitoral;

    // --- CERTIFICADO DE RESERVISTA: OPCIONAL ---
    @Column(name = "numeroCertificadoReservista", length = 30)
    private String numeroCertificadoReservista;

    @Column(name = "orgaoEmissorCertificadoReservista", length = 20)
    private String orgaoEmissorCertificadoReservista;

    @Column(name = "ufReservista", length = 2)
    private String ufReservista;

    // --- FILIAÇÃO ---
    @Column(name = "nomePai", length = 150)
    private String nomePai;

    @Column(name = "nomeMae", length = 150)
    private String nomeMae;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoEndereco", length = 20)
    private TipoEndereco tipoEndereco;

    @Column(name = "enderecoCep", length = 9)
    private String enderecoCep;

    @Column(name = "enderecoLogradouro", length = 150)
    private String enderecoLogradouro;

    @Column(name = "enderecoNumero", length = 20)
    private String enderecoNumero;

    @Column(name = "enderecoComplemento", length = 80)
    private String enderecoComplemento;

    @Column(name = "enderecoBairro", length = 80)
    private String enderecoBairro;

    @Column(name = "enderecoUf", length = 2)
    private String enderecoUf;

    @Column(name = "telefoneEmergencia", length = 20)
    private String telefoneEmergencia;

    // --- INFORMAÇÕES GERAIS ---
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoSanguineo", length = 15)
    private TipoSanguineo tipoSanguineo;

    @Column(name = "publicoAlvoEducacaoEspecial", nullable = false)
    private Boolean publicoAlvoEducacaoEspecial;

    @Column(name = "canhoto", nullable = false)
    private Boolean canhoto;

    @Column(name = "necessitaAcompanhamentoInstitucional", nullable = false)
    private Boolean necessitaAcompanhamentoInstitucional;

    // --- CENSO ESCOLAR ---
    @Column(name = "instituicaoOrigem", length = 150)
    private String instituicaoOrigem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoEscolaEnsinoMedio", length = 20)
    private TipoEscola tipoEscolaEnsinoMedio;

    @Column(name = "nomeInstituicaoConclusao", length = 150)
    private String nomeInstituicaoConclusao;

    @Column(name = "mesConclusaoEnsinoMedio")
    private Integer mesConclusaoEnsinoMedio;

    @Column(name = "anoConclusaoEnsinoMedio")
    private Integer anoConclusaoEnsinoMedio;

    @Enumerated(EnumType.STRING)
    @Column(name = "racaEtnia", length = 20)
    private RacaEtnia racaEtnia;

    @CreationTimestamp
    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm", nullable = false)
    private LocalDateTime atualizadoEm;
}

