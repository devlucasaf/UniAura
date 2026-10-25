package erp.uniaura.services.web.aluno.consultarMeuPerfilDeAluno.v1;

import erp.uniaura.persistence.entities.EstadoCivil;
import erp.uniaura.persistence.entities.RacaEtnia;
import erp.uniaura.persistence.entities.Sexo;
import erp.uniaura.persistence.entities.TipoEndereco;
import erp.uniaura.persistence.entities.TipoEscola;
import erp.uniaura.persistence.entities.TipoSanguineo;
import erp.uniaura.persistence.entities.Turno;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConsultarMeuPerfilDeAlunoRetornoV1 {
    @Schema(description = "Identificador público do aluno")
    private Long alunoUid;
    private String email;
    private String cpf;
    private String curso;
    private Turno turno;
    private String matriculaRA;
    private LocalDate dataIngresso;
    private Integer semestreAtual;
    private String nome;
    private String emailPessoal;
    private String telefone;
    private String telefoneEmergencia;
    private LocalDate dataNascimento;
    private String nomePai;
    private String nomeMae;
    private Sexo sexo;
    private EstadoCivil estadoCivil;
    private String municipioNascimento;
    private String cidade;
    private String estado;
    private String nacionalidade;
    private String documentoNumero;
    private String documentoOrgaoEmissor;
    private String ufExpedicaoIdentidade;
    private LocalDate dataExpedicaoIdentidade;
    private String numeroTituloEleitor;
    private String numeroZonaEleitoral;
    private String ufZonaEleitoral;
    private String numeroCertificadoReservista;
    private String orgaoEmissorCertificadoReservista;
    private String ufReservista;
    private TipoEndereco tipoEndereco;
    private String enderecoCep;
    private String enderecoLogradouro;
    private String enderecoNumero;
    private String enderecoComplemento;
    private String enderecoBairro;
    private String enderecoUf;
    private TipoSanguineo tipoSanguineo;
    private Boolean publicoAlvoEducacaoEspecial;
    private Boolean canhoto;
    private Boolean necessitaAcompanhamentoInstitucional;
    private String instituicaoOrigem;
    private TipoEscola tipoEscolaEnsinoMedio;
    private String nomeInstituicaoConclusao;
    private Integer mesConclusaoEnsinoMedio;
    private Integer anoConclusaoEnsinoMedio;
    private RacaEtnia racaEtnia;
}
