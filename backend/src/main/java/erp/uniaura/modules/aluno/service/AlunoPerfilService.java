package erp.uniaura.modules.aluno.service;

import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.aluno.dto.AlunoPerfilDTO;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AlunoPerfilService {

    private final AlunoRepository alunoRepository;

    // --- DADOS COMPLETOS DO ALUNO AUTENTICADO ---
    @Transactional(readOnly = true)
    public AlunoPerfilDTO obter(Long usuarioId) {
        return paraDto(buscar(usuarioId));
    }

    // --- ATUALIZA OS DADOS PESSOAIS DO ALUNO AUTENTICADO ---
    @Transactional
    public AlunoPerfilDTO atualizar(Long usuarioId, AlunoPerfilDTO dto) {
        Aluno aluno = buscar(usuarioId);
        Usuario usuario = aluno.getUsuario();

        usuario.setNome(dto.getNome().trim());
        usuario.setTelefone(dto.getTelefone());
        usuario.setDataNascimento(dto.getDataNascimento());

        aluno.setEmailPessoal(dto.getEmailPessoal());
        aluno.setTelefoneEmergencia(dto.getTelefoneEmergencia());
        aluno.setNomePai(dto.getNomePai());
        aluno.setNomeMae(dto.getNomeMae());
        aluno.setSexo(dto.getSexo());
        aluno.setEstadoCivil(dto.getEstadoCivil());
        aluno.setMunicipioNascimento(dto.getMunicipioNascimento());
        aluno.setCidade(dto.getCidade());
        aluno.setEstado(dto.getEstado());
        aluno.setNacionalidade(dto.getNacionalidade());
        aluno.setDocumentoNumero(dto.getDocumentoNumero());
        aluno.setDocumentoOrgaoEmissor(dto.getDocumentoOrgaoEmissor());
        aluno.setUfExpedicaoIdentidade(dto.getUfExpedicaoIdentidade());
        aluno.setDataExpedicaoIdentidade(dto.getDataExpedicaoIdentidade());
        aluno.setNumeroTituloEleitor(dto.getNumeroTituloEleitor());
        aluno.setNumeroZonaEleitoral(dto.getNumeroZonaEleitoral());
        aluno.setUfZonaEleitoral(dto.getUfZonaEleitoral());
        aluno.setNumeroCertificadoReservista(dto.getNumeroCertificadoReservista());
        aluno.setOrgaoEmissorCertificadoReservista(dto.getOrgaoEmissorCertificadoReservista());
        aluno.setUfReservista(dto.getUfReservista());
        aluno.setTipoEndereco(dto.getTipoEndereco());
        aluno.setEnderecoCep(dto.getEnderecoCep());
        aluno.setEnderecoLogradouro(dto.getEnderecoLogradouro());
        aluno.setEnderecoNumero(dto.getEnderecoNumero());
        aluno.setEnderecoComplemento(dto.getEnderecoComplemento());
        aluno.setEnderecoBairro(dto.getEnderecoBairro());
        aluno.setEnderecoUf(dto.getEnderecoUf());
        aluno.setTipoSanguineo(dto.getTipoSanguineo());
        aluno.setPublicoAlvoEducacaoEspecial(Boolean.TRUE.equals(dto.getPublicoAlvoEducacaoEspecial()));
        aluno.setCanhoto(Boolean.TRUE.equals(dto.getCanhoto()));
        aluno.setNecessitaAcompanhamentoInstitucional(Boolean.TRUE.equals(dto.getNecessitaAcompanhamentoInstitucional()));
        aluno.setInstituicaoOrigem(dto.getInstituicaoOrigem());
        aluno.setTipoEscolaEnsinoMedio(dto.getTipoEscolaEnsinoMedio());
        aluno.setNomeInstituicaoConclusao(dto.getNomeInstituicaoConclusao());
        aluno.setMesConclusaoEnsinoMedio(dto.getMesConclusaoEnsinoMedio());
        aluno.setAnoConclusaoEnsinoMedio(dto.getAnoConclusaoEnsinoMedio());
        aluno.setRacaEtnia(dto.getRacaEtnia());

        return paraDto(alunoRepository.save(aluno));
    }

    // --- LOCALIZA O ALUNO PELO USUÁRIO AUTENTICADO ---
    private Aluno buscar(Long usuarioId) {
        return alunoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno (usuário)", usuarioId));
    }

    // --- SEMESTRE EM CURSO CONTADO DESDE O INGRESSO ---
    private int semestreAtual(LocalDate ingresso) {
        if (ingresso == null) {
            return 1;
        }

        LocalDate hoje = LocalDate.now();
        int semestreHoje = hoje.getMonthValue() <= 6 ? 1 : 2;
        int semestreIngresso = ingresso.getMonthValue() <= 6 ? 1 : 2;
        return Math.max(1, (hoje.getYear() - ingresso.getYear()) * 2 + (semestreHoje - semestreIngresso) + 1);
    }

    private AlunoPerfilDTO paraDto(Aluno aluno) {
        Usuario usuario = aluno.getUsuario();
        return AlunoPerfilDTO.builder()
                .alunoId(aluno.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .cpf(usuario.getCpf())
                .telefone(usuario.getTelefone())
                .dataNascimento(usuario.getDataNascimento())
                .curso(aluno.getCurso())
                .turno(aluno.getTurno())
                .matriculaRA(aluno.getMatriculaRA())
                .dataIngresso(aluno.getDataIngresso())
                .semestreAtual(semestreAtual(aluno.getDataIngresso()))
                .emailPessoal(aluno.getEmailPessoal())
                .telefoneEmergencia(aluno.getTelefoneEmergencia())
                .nomePai(aluno.getNomePai())
                .nomeMae(aluno.getNomeMae())
                .sexo(aluno.getSexo())
                .estadoCivil(aluno.getEstadoCivil())
                .municipioNascimento(aluno.getMunicipioNascimento())
                .cidade(aluno.getCidade())
                .estado(aluno.getEstado())
                .nacionalidade(aluno.getNacionalidade())
                .documentoNumero(aluno.getDocumentoNumero())
                .documentoOrgaoEmissor(aluno.getDocumentoOrgaoEmissor())
                .ufExpedicaoIdentidade(aluno.getUfExpedicaoIdentidade())
                .dataExpedicaoIdentidade(aluno.getDataExpedicaoIdentidade())
                .numeroTituloEleitor(aluno.getNumeroTituloEleitor())
                .numeroZonaEleitoral(aluno.getNumeroZonaEleitoral())
                .ufZonaEleitoral(aluno.getUfZonaEleitoral())
                .numeroCertificadoReservista(aluno.getNumeroCertificadoReservista())
                .orgaoEmissorCertificadoReservista(aluno.getOrgaoEmissorCertificadoReservista())
                .ufReservista(aluno.getUfReservista())
                .tipoEndereco(aluno.getTipoEndereco())
                .enderecoCep(aluno.getEnderecoCep())
                .enderecoLogradouro(aluno.getEnderecoLogradouro())
                .enderecoNumero(aluno.getEnderecoNumero())
                .enderecoComplemento(aluno.getEnderecoComplemento())
                .enderecoBairro(aluno.getEnderecoBairro())
                .enderecoUf(aluno.getEnderecoUf())
                .tipoSanguineo(aluno.getTipoSanguineo())
                .publicoAlvoEducacaoEspecial(aluno.getPublicoAlvoEducacaoEspecial())
                .canhoto(aluno.getCanhoto())
                .necessitaAcompanhamentoInstitucional(aluno.getNecessitaAcompanhamentoInstitucional())
                .instituicaoOrigem(aluno.getInstituicaoOrigem())
                .tipoEscolaEnsinoMedio(aluno.getTipoEscolaEnsinoMedio())
                .nomeInstituicaoConclusao(aluno.getNomeInstituicaoConclusao())
                .mesConclusaoEnsinoMedio(aluno.getMesConclusaoEnsinoMedio())
                .anoConclusaoEnsinoMedio(aluno.getAnoConclusaoEnsinoMedio())
                .racaEtnia(aluno.getRacaEtnia())
                .build();
    }
}
