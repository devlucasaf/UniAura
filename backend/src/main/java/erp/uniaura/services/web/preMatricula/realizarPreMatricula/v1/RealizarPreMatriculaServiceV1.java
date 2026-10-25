package erp.uniaura.services.web.preMatricula.realizarPreMatricula.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.StatusAluno;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.GerarMatriculaRAComponent;
import erp.uniaura.services.common.auditoria.RegistrarAuditoriaComponent;
import erp.uniaura.services.common.usuario.CriarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RealizarPreMatriculaServiceV1 extends BaseService {

    private final CriarUsuarioComponent criarUsuarioComponent;
    private final GerarMatriculaRAComponent gerarMatriculaRAComponent;
    private final RegistrarAuditoriaComponent registrarAuditoriaComponent;

    // --- CRIA O USUÁRIO DE ACESSO E O CADASTRO ACADÊMICO A PARTIR DO FORMULÁRIO PÚBLICO ---
    @Transactional
    public RealizarPreMatriculaRetornoV1 realizar(RealizarPreMatriculaParamsV1 params) {
        Usuario usuario = criarUsuarioComponent.criar(params.getNome(), params.getEmail(), params.getSenha(), params.getCpf(),
                params.getTelefone(), params.getDataNascimento(), true, TipoUsuario.ALUNO);

        Aluno aluno = new Aluno();

        aluno.setUsuario(usuario);
        aluno.setMatriculaRA(gerarMatriculaRAComponent.gerar());
        aluno.setDataIngresso(LocalDate.now());
        aluno.setStatus(StatusAluno.ATIVO);
        aluno.setCurso(params.getCurso());
        aluno.setTurno(params.getTurno());
        aluno.setEmailPessoal(params.getEmailPessoal());
        aluno.setNomePai(params.getNomePai());
        aluno.setNomeMae(params.getNomeMae());
        aluno.setSexo(params.getSexo());
        aluno.setEstadoCivil(params.getEstadoCivil());
        aluno.setMunicipioNascimento(params.getMunicipioNascimento());
        aluno.setCidade(params.getCidade());
        aluno.setEstado(params.getEstado());
        aluno.setNacionalidade(params.getNacionalidade());
        aluno.setDocumentoNumero(params.getDocumentoNumero());
        aluno.setDocumentoOrgaoEmissor(params.getDocumentoOrgaoEmissor());
        aluno.setUfExpedicaoIdentidade(params.getUfExpedicaoIdentidade());
        aluno.setDataExpedicaoIdentidade(params.getDataExpedicaoIdentidade());
        aluno.setNumeroTituloEleitor(params.getNumeroTituloEleitor());
        aluno.setNumeroZonaEleitoral(params.getNumeroZonaEleitoral());
        aluno.setUfZonaEleitoral(params.getUfZonaEleitoral());
        aluno.setNumeroCertificadoReservista(params.getNumeroCertificadoReservista());
        aluno.setOrgaoEmissorCertificadoReservista(params.getOrgaoEmissorCertificadoReservista());
        aluno.setUfReservista(params.getUfReservista());
        aluno.setTipoEndereco(params.getTipoEndereco());
        aluno.setEnderecoBairro(params.getEnderecoBairro());
        aluno.setEnderecoLogradouro(params.getEnderecoLogradouro());
        aluno.setEnderecoNumero(params.getEnderecoNumero());
        aluno.setEnderecoComplemento(params.getEnderecoComplemento());
        aluno.setEnderecoCep(params.getEnderecoCep());
        aluno.setTelefoneEmergencia(params.getTelefoneEmergencia());
        aluno.setEnderecoUf(params.getEnderecoUf());
        aluno.setTipoSanguineo(params.getTipoSanguineo());
        aluno.setInstituicaoOrigem(params.getInstituicaoOrigem());
        aluno.setTipoEscolaEnsinoMedio(params.getTipoEscolaEnsinoMedio());
        aluno.setNomeInstituicaoConclusao(params.getNomeInstituicaoConclusao());
        aluno.setMesConclusaoEnsinoMedio(params.getMesConclusaoEnsinoMedio());
        aluno.setAnoConclusaoEnsinoMedio(params.getAnoConclusaoEnsinoMedio());
        aluno.setRacaEtnia(params.getRacaEtnia());
        aluno.setPublicoAlvoEducacaoEspecial(Boolean.TRUE.equals(params.getPublicoAlvoEducacaoEspecial()));
        aluno.setCanhoto(Boolean.TRUE.equals(params.getCanhoto()));
        aluno.setNecessitaAcompanhamentoInstitucional(Boolean.TRUE.equals(params.getNecessitaAcompanhamentoInstitucional()));
        aluno.persist();

        registrarAuditoriaComponent.registrar(usuario, "CADASTRO_PUBLICO", "Aluno", aluno.getId(),
                "Pré-matrícula pública para o curso " + aluno.getCurso());

        RealizarPreMatriculaRetornoV1 retorno = new RealizarPreMatriculaRetornoV1();

        retorno.setAlunoUid(aluno.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setMatriculaRA(aluno.getMatriculaRA());
        retorno.setCurso(aluno.getCurso());

        return retorno;
    }
}