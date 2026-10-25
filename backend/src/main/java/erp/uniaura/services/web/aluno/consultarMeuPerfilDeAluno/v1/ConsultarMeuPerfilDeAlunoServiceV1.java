package erp.uniaura.services.web.aluno.consultarMeuPerfilDeAluno.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.aluno.CalcularSemestreAtualComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarMeuPerfilDeAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final CalcularSemestreAtualComponent calcularSemestreAtualComponent;

    // --- RETORNA O PERFIL COMPLETO DO ALUNO LOGADO ---
    @Transactional(readOnly = true)
    public ConsultarMeuPerfilDeAlunoRetornoV1 consultar(Long usuarioId) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);
        Usuario usuario = aluno.getUsuario();

        ConsultarMeuPerfilDeAlunoRetornoV1 retorno = new ConsultarMeuPerfilDeAlunoRetornoV1();

        retorno.setAlunoUid(aluno.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setCurso(aluno.getCurso());
        retorno.setTurno(aluno.getTurno());
        retorno.setMatriculaRA(aluno.getMatriculaRA());
        retorno.setDataIngresso(aluno.getDataIngresso());
        retorno.setSemestreAtual(calcularSemestreAtualComponent.calcular(aluno.getDataIngresso()));
        retorno.setEmailPessoal(aluno.getEmailPessoal());
        retorno.setTelefoneEmergencia(aluno.getTelefoneEmergencia());
        retorno.setNomePai(aluno.getNomePai());
        retorno.setNomeMae(aluno.getNomeMae());
        retorno.setSexo(aluno.getSexo());
        retorno.setEstadoCivil(aluno.getEstadoCivil());
        retorno.setMunicipioNascimento(aluno.getMunicipioNascimento());
        retorno.setCidade(aluno.getCidade());
        retorno.setEstado(aluno.getEstado());
        retorno.setNacionalidade(aluno.getNacionalidade());
        retorno.setDocumentoNumero(aluno.getDocumentoNumero());
        retorno.setDocumentoOrgaoEmissor(aluno.getDocumentoOrgaoEmissor());
        retorno.setUfExpedicaoIdentidade(aluno.getUfExpedicaoIdentidade());
        retorno.setDataExpedicaoIdentidade(aluno.getDataExpedicaoIdentidade());
        retorno.setNumeroTituloEleitor(aluno.getNumeroTituloEleitor());
        retorno.setNumeroZonaEleitoral(aluno.getNumeroZonaEleitoral());
        retorno.setUfZonaEleitoral(aluno.getUfZonaEleitoral());
        retorno.setNumeroCertificadoReservista(aluno.getNumeroCertificadoReservista());
        retorno.setOrgaoEmissorCertificadoReservista(aluno.getOrgaoEmissorCertificadoReservista());
        retorno.setUfReservista(aluno.getUfReservista());
        retorno.setTipoEndereco(aluno.getTipoEndereco());
        retorno.setEnderecoCep(aluno.getEnderecoCep());
        retorno.setEnderecoLogradouro(aluno.getEnderecoLogradouro());
        retorno.setEnderecoNumero(aluno.getEnderecoNumero());
        retorno.setEnderecoComplemento(aluno.getEnderecoComplemento());
        retorno.setEnderecoBairro(aluno.getEnderecoBairro());
        retorno.setEnderecoUf(aluno.getEnderecoUf());
        retorno.setTipoSanguineo(aluno.getTipoSanguineo());
        retorno.setInstituicaoOrigem(aluno.getInstituicaoOrigem());
        retorno.setTipoEscolaEnsinoMedio(aluno.getTipoEscolaEnsinoMedio());
        retorno.setNomeInstituicaoConclusao(aluno.getNomeInstituicaoConclusao());
        retorno.setMesConclusaoEnsinoMedio(aluno.getMesConclusaoEnsinoMedio());
        retorno.setAnoConclusaoEnsinoMedio(aluno.getAnoConclusaoEnsinoMedio());
        retorno.setRacaEtnia(aluno.getRacaEtnia());
        retorno.setPublicoAlvoEducacaoEspecial(aluno.getPublicoAlvoEducacaoEspecial());
        retorno.setCanhoto(aluno.getCanhoto());
        retorno.setNecessitaAcompanhamentoInstitucional(aluno.getNecessitaAcompanhamentoInstitucional());

        return retorno;
    }
}
