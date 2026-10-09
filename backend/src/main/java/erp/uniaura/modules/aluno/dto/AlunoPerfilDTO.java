package erp.uniaura.modules.aluno.dto;

import erp.uniaura.modules.aluno.model.EstadoCivil;
import erp.uniaura.modules.aluno.model.RacaEtnia;
import erp.uniaura.modules.aluno.model.Sexo;
import erp.uniaura.modules.aluno.model.TipoEndereco;
import erp.uniaura.modules.aluno.model.TipoEscola;
import erp.uniaura.modules.aluno.model.TipoSanguineo;
import erp.uniaura.modules.aluno.model.Turno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlunoPerfilDTO {

    private Long      alunoId;
    private String    email;
    private String    cpf;
    private String    curso;
    private Turno     turno;
    private String    matriculaRA;
    private LocalDate dataIngresso;
    private Integer   semestreAtual;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 5, max = 150, message = "O nome deve ter entre 5 e 150 caracteres.")
    private String nome;

    @Email(message = "E-mail pessoal inválido.")
    @Size(max = 150)
    private String emailPessoal;

    @Size(max = 20)
    private String telefone;

    @Size(max = 20)
    private String telefoneEmergencia;

    private LocalDate dataNascimento;

    @Size(max = 150)
    private String nomePai;

    @Size(max = 150)
    private String nomeMae;

    private Sexo sexo;
    private EstadoCivil estadoCivil;

    @Size(max = 100)
    private String municipioNascimento;

    @Size(max = 80)
    private String cidade;

    @Size(max = 2)
    private String estado;

    @Size(max = 80)
    private String nacionalidade;

    @Size(max = 30)
    private String documentoNumero;

    @Size(max = 20)
    private String documentoOrgaoEmissor;

    @Size(max = 2)
    private String      ufExpedicaoIdentidade;
    private LocalDate   dataExpedicaoIdentidade;

    @Size(max = 20)
    private String numeroTituloEleitor;

    @Size(max = 10)
    private String numeroZonaEleitoral;

    @Size(max = 2)
    private String ufZonaEleitoral;

    @Size(max = 30)
    private String numeroCertificadoReservista;

    @Size(max = 20)
    private String orgaoEmissorCertificadoReservista;

    @Size(max = 2)
    private String ufReservista;

    private TipoEndereco tipoEndereco;

    @Size(max = 9)
    private String enderecoCep;

    @Size(max = 150)
    private String enderecoLogradouro;

    @Size(max = 20)
    private String enderecoNumero;

    @Size(max = 100)
    private String enderecoComplemento;

    @Size(max = 100)
    private String enderecoBairro;

    @Size(max = 2)
    private String enderecoUf;

    private TipoSanguineo   tipoSanguineo;
    private Boolean         publicoAlvoEducacaoEspecial;
    private Boolean         canhoto;
    private Boolean         necessitaAcompanhamentoInstitucional;

    @Size(max = 150)
    private String      instituicaoOrigem;

    private TipoEscola  tipoEscolaEnsinoMedio;

    @Size(max = 150)
    private String      nomeInstituicaoConclusao;
    private Integer     mesConclusaoEnsinoMedio;
    private Integer     anoConclusaoEnsinoMedio;
    private RacaEtnia   racaEtnia;
}
