package erp.uniaura.services.web.aluno.alterarMeuPerfilDeAluno.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarMeuPerfilDeAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- E-MAIL INSTITUCIONAL, CPF, CURSO, TURNO E RA NÃO MUDAM POR AQUI ---
    @Transactional
    public void alterar(Long usuarioId, AlterarMeuPerfilDeAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);
        Usuario usuario = aluno.getUsuario();

        usuario.setNome(params.getNome().trim());
        usuario.setTelefone(params.getTelefone());
        usuario.setDataNascimento(params.getDataNascimento());
        usuario.merge();

        aluno.setEmailPessoal(params.getEmailPessoal());
        aluno.setTelefoneEmergencia(params.getTelefoneEmergencia());
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
        aluno.setEnderecoCep(params.getEnderecoCep());
        aluno.setEnderecoLogradouro(params.getEnderecoLogradouro());
        aluno.setEnderecoNumero(params.getEnderecoNumero());
        aluno.setEnderecoComplemento(params.getEnderecoComplemento());
        aluno.setEnderecoBairro(params.getEnderecoBairro());
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
        aluno.merge();
    }
}