package erp.uniaura.services.web.preMatricula.realizarPreMatricula.v1;

import erp.uniaura.persistence.entities.EstadoCivil;
import erp.uniaura.persistence.entities.RacaEtnia;
import erp.uniaura.persistence.entities.Sexo;
import erp.uniaura.persistence.entities.TipoEndereco;
import erp.uniaura.persistence.entities.TipoEscola;
import erp.uniaura.persistence.entities.TipoSanguineo;
import erp.uniaura.persistence.entities.Turno;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RealizarPreMatriculaParamsV1 {
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150)
    private String nome;

    @NotBlank(message = "O e-mail institucional é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "O e-mail pessoal é obrigatório.")
    @Email(message = "E-mail pessoal inválido.")
    @Size(max = 150)
    private String emailPessoal;

    @NotNull(message = "O turno é obrigatório.")
    private Turno turno;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @Size(max = 150)
    private String nomePai;

    @Size(max = 150)
    private String nomeMae;

    private Sexo sexo;

    private EstadoCivil estadoCivil;

    @NotNull(message = "A data de nascimento é obrigatória.")
    private LocalDate dataNascimento;

    @Size(max = 100)
    private String municipioNascimento;

    @Size(max = 80)
    private String cidade;

    @Size(max = 2)
    private String estado;

    @Size(max = 80)
    private String nacionalidade;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(max = 14)
    private String cpf;

    @Size(max = 30)
    private String documentoNumero;

    @Size(max = 20)
    private String documentoOrgaoEmissor;

    @Size(max = 2)
    private String ufExpedicaoIdentidade;

    private LocalDate dataExpedicaoIdentidade;

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

    @Size(max = 80)
    private String enderecoBairro;

    @Size(max = 150)
    private String enderecoLogradouro;

    @Size(max = 20)
    private String enderecoNumero;

    @Size(max = 80)
    private String enderecoComplemento;

    @Size(max = 9)
    private String enderecoCep;

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(max = 20)
    private String telefone;

    @Size(max = 20)
    private String telefoneEmergencia;

    @Size(max = 2)
    private String enderecoUf;

    private TipoSanguineo tipoSanguineo;

    private Boolean publicoAlvoEducacaoEspecial;

    private Boolean canhoto;

    private Boolean necessitaAcompanhamentoInstitucional;

    @Size(max = 150)
    private String instituicaoOrigem;

    private TipoEscola tipoEscolaEnsinoMedio;

    @Size(max = 150)
    private String nomeInstituicaoConclusao;

    @Min(value = 1, message = "Informe um mês entre 1 e 12.")
    @Max(value = 12, message = "Informe um mês entre 1 e 12.")
    private Integer mesConclusaoEnsinoMedio;

    @Min(value = 1950, message = "Informe um ano de conclusão válido.")
    private Integer anoConclusaoEnsinoMedio;

    private RacaEtnia racaEtnia;

    @NotBlank(message = "O curso é obrigatório.")
    @Size(max = 150)
    private String curso;

    @AssertTrue(message = "É necessário aceitar o termo de consentimento.")
    private Boolean termoConsentimento;
}
