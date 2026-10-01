package erp.uniaura.modules.lgpd.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.aluno.service.AlunoService;
import erp.uniaura.modules.auditoria.service.AuditoriaService;
import erp.uniaura.modules.documento.service.DocumentoAlunoService;
import erp.uniaura.modules.financeiro.service.MensalidadeService;
import erp.uniaura.modules.lgpd.dto.DadosPessoaisResponseDTO;
import erp.uniaura.modules.nota.service.NotaService;
import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LgpdService {

    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;
    private final UsuarioService usuarioService;
    private final AlunoRepository alunoRepository;
    private final AlunoService alunoService;
    private final MensalidadeService mensalidadeService;
    private final NotaService notaService;
    private final DocumentoAlunoService documentoAlunoService;
    private final AuditoriaService auditoriaService;

    // --- MONTA E DEVOLVE TODOS OS DADOS PESSOAIS DO USUÁRIO AUTENTICADO (DIREITO DE ACESSO DA LGPD) ---
    @Transactional(readOnly = true)
    public DadosPessoaisResponseDTO meusDados() {
        Usuario usuario = usuarioAutenticadoOuFalha();

        UsuarioResponseDTO usuarioDto = usuarioService.buscarPorId(usuario.getId());
        Aluno aluno = alunoRepository.findByUsuarioId(usuario.getId()).orElse(null);

        DadosPessoaisResponseDTO.DadosPessoaisResponseDTOBuilder dados = DadosPessoaisResponseDTO.builder()
                .usuario(usuarioDto)
                .exportadoEm(LocalDateTime.now());

        if (aluno != null) {
            dados.aluno(alunoService.buscarPorId(aluno.getId()))
                    .mensalidades(mensalidadeService.minhas(Pageable.unpaged()).getContent())
                    .notas(notaService.listarPorAluno(aluno.getId()))
                    .documentos(documentoAlunoService.meus(Pageable.unpaged()).getContent());
        } else {
            dados.mensalidades(List.of()).notas(List.of()).documentos(List.of());
        }

        auditoriaService.registrar(usuario, "EXPORTACAO_DADOS_LGPD", "Usuario", usuario.getId(),
                "Usuário exportou seus próprios dados pessoais (direito de acesso da LGPD).");

        return dados.build();
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }
}
