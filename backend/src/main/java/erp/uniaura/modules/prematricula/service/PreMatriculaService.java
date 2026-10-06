package erp.uniaura.modules.prematricula.service;

import erp.uniaura.infra.protocolo.GeradorProtocolo;
import erp.uniaura.modules.auditoria.service.AuditoriaService;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.model.StatusAluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.prematricula.dto.PreMatriculaRequestDTO;
import erp.uniaura.modules.prematricula.dto.PreMatriculaResponseDTO;
import erp.uniaura.modules.usuario.dto.UsuarioRequestDTO;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PreMatriculaService {

    private static final String PREFIXO_RA = "RA";

    private final AlunoRepository alunoRepository;
    private final UsuarioService usuarioService;
    private final GeradorProtocolo geradorProtocolo;
    private final AuditoriaService auditoriaService;

    // --- CRIA O USUÁRIO E O REGISTRO ACADÊMICO A PARTIR DO FORMULÁRIO PÚBLICO ---
    @Transactional
    public PreMatriculaResponseDTO matricular(PreMatriculaRequestDTO dto) {
        Usuario usuario = usuarioService.criarEntidade(UsuarioRequestDTO.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .dataNascimento(dto.getDataNascimento())
                .ativo(true)
                .role(TipoUsuario.ALUNO)
                .build());

        Aluno aluno = Aluno.builder()
                .usuario(usuario)
                .matriculaRA(gerarMatriculaRAUnica())
                .dataIngresso(LocalDate.now())
                .status(StatusAluno.ATIVO)
                .curso(dto.getCurso())
                .nomePai(dto.getNomePai())
                .nomeMae(dto.getNomeMae())
                .sexo(dto.getSexo())
                .estadoCivil(dto.getEstadoCivil())
                .municipioNascimento(dto.getMunicipioNascimento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .nacionalidade(dto.getNacionalidade())
                .documentoNumero(dto.getDocumentoNumero())
                .documentoOrgaoEmissor(dto.getDocumentoOrgaoEmissor())
                .ufExpedicaoIdentidade(dto.getUfExpedicaoIdentidade())
                .dataExpedicaoIdentidade(dto.getDataExpedicaoIdentidade())
                .numeroTituloEleitor(dto.getNumeroTituloEleitor())
                .numeroZonaEleitoral(dto.getNumeroZonaEleitoral())
                .ufZonaEleitoral(dto.getUfZonaEleitoral())
                .numeroCertificadoReservista(dto.getNumeroCertificadoReservista())
                .orgaoEmissorCertificadoReservista(dto.getOrgaoEmissorCertificadoReservista())
                .ufReservista(dto.getUfReservista())
                .tipoEndereco(dto.getTipoEndereco())
                .enderecoBairro(dto.getEnderecoBairro())
                .enderecoLogradouro(dto.getEnderecoLogradouro())
                .enderecoNumero(dto.getEnderecoNumero())
                .enderecoComplemento(dto.getEnderecoComplemento())
                .enderecoCep(dto.getEnderecoCep())
                .telefoneEmergencia(dto.getTelefoneEmergencia())
                .enderecoUf(dto.getEnderecoUf())
                .tipoSanguineo(dto.getTipoSanguineo())
                .publicoAlvoEducacaoEspecial(Boolean.TRUE.equals(dto.getPublicoAlvoEducacaoEspecial()))
                .canhoto(Boolean.TRUE.equals(dto.getCanhoto()))
                .necessitaAcompanhamentoInstitucional(Boolean.TRUE.equals(dto.getNecessitaAcompanhamentoInstitucional()))
                .instituicaoOrigem(dto.getInstituicaoOrigem())
                .tipoEscolaEnsinoMedio(dto.getTipoEscolaEnsinoMedio())
                .nomeInstituicaoConclusao(dto.getNomeInstituicaoConclusao())
                .mesConclusaoEnsinoMedio(dto.getMesConclusaoEnsinoMedio())
                .anoConclusaoEnsinoMedio(dto.getAnoConclusaoEnsinoMedio())
                .racaEtnia(dto.getRacaEtnia())
                .build();

        Aluno salvo = alunoRepository.save(aluno);

        auditoriaService.registrar(usuario, "CADASTRO_PUBLICO", "Aluno", salvo.getId(),
                "Pré-matrícula pública para o curso " + salvo.getCurso());

        return PreMatriculaResponseDTO.builder()
                .alunoId(salvo.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .matriculaRA(salvo.getMatriculaRA())
                .curso(salvo.getCurso())
                .build();
    }

    // --- GERA UM RA NO FORMATO RA-ANO-XXXXXXXX E GARANTE QUE É INÉDITO ---
    private String gerarMatriculaRAUnica() {
        String ra;
        do {
            ra = geradorProtocolo.gerar(PREFIXO_RA);
        } while (alunoRepository.existsByMatriculaRA(ra));
        return ra;
    }
}
