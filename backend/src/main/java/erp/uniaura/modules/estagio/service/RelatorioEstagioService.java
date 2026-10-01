package erp.uniaura.modules.estagio.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.modules.estagio.dto.AnalisarRelatorioEstagioRequestDTO;
import erp.uniaura.modules.estagio.dto.RelatorioEstagioResponseDTO;
import erp.uniaura.modules.estagio.model.Estagio;
import erp.uniaura.modules.estagio.model.RelatorioEstagio;
import erp.uniaura.modules.estagio.model.StatusEstagio;
import erp.uniaura.modules.estagio.model.StatusRelatorioEstagio;
import erp.uniaura.modules.estagio.repository.RelatorioEstagioRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RelatorioEstagioService {

    private static final String SUBDIR_RELATORIOS = "relatorios-estagio";

    private final RelatorioEstagioRepository relatorioEstagioRepository;
    private final EstagioService estagioService;
    private final StorageService storageService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- ENVIA UM RELATÓRIO PERIÓDICO DE HORAS CUMPRIDAS NO ESTÁGIO ---
    @Transactional
    public RelatorioEstagioResponseDTO enviar(Long estagioId, String periodoReferencia, Integer horasRegistradas,
            String descricaoAtividades, MultipartFile comprovante) {
        Estagio estagio = estagioService.buscarEntidade(estagioId);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        validarDonoOuEquipe(estagio, autenticado);

        if (estagio.getStatus() != StatusEstagio.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível enviar relatórios para um estágio em andamento.");
        }

        if (relatorioEstagioRepository.existsByEstagioIdAndPeriodoReferencia(estagioId, periodoReferencia)) {
            throw new BusinessException("Já existe um relatório enviado para o período " + periodoReferencia + ".");
        }

        String comprovanteUrl = (comprovante == null || comprovante.isEmpty())
                ? null : storageService.store(comprovante, SUBDIR_RELATORIOS);

        RelatorioEstagio relatorio = RelatorioEstagio.builder()
                .estagio(estagio)
                .periodoReferencia(periodoReferencia)
                .horasRegistradas(horasRegistradas)
                .descricaoAtividades(descricaoAtividades)
                .comprovanteUrl(comprovanteUrl)
                .status(StatusRelatorioEstagio.PENDENTE)
                .build();

        return toResponse(relatorioEstagioRepository.save(relatorio));
    }

    // --- LISTA OS RELATÓRIOS ENVIADOS PARA UM ESTÁGIO ---
    @Transactional(readOnly = true)
    public Page<RelatorioEstagioResponseDTO> listarPorEstagio(Long estagioId, Pageable pageable) {
        return relatorioEstagioRepository.findByEstagioId(estagioId, pageable).map(this::toResponse);
    }

    // --- APROVA OU REJEITA UM RELATÓRIO, SOMANDO AS HORAS À CARGA CUMPRIDA SE APROVADO ---
    @Transactional
    public RelatorioEstagioResponseDTO analisar(Long id, AnalisarRelatorioEstagioRequestDTO dto) {
        RelatorioEstagio relatorio = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        if (relatorio.getStatus() != StatusRelatorioEstagio.PENDENTE) {
            throw new BusinessException("Este relatório já foi analisado.");
        }

        relatorio.setStatus(dto.getStatus());
        relatorio.setObservacoes(dto.getObservacoes());
        relatorio.setAnalisadoPor(autenticado);
        relatorio.setAnalisadoEm(LocalDateTime.now());

        if (dto.getStatus() == StatusRelatorioEstagio.APROVADO) {
            Estagio estagio = relatorio.getEstagio();
            estagio.setCargaHorariaCumprida(estagio.getCargaHorariaCumprida() + relatorio.getHorasRegistradas());
        }

        return toResponse(relatorioEstagioRepository.save(relatorio));
    }

    private RelatorioEstagio buscarEntidade(Long id) {
        return relatorioEstagioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relatório de estágio", id));
    }

    // --- SÓ O PRÓPRIO ALUNO DO ESTÁGIO OU A EQUIPE ACADÊMICA PODEM ENVIAR O RELATÓRIO ---
    private void validarDonoOuEquipe(Estagio estagio, Usuario autenticado) {
        boolean equipe = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR
                || autenticado.getRole() == TipoUsuario.SECRETARIA
                || autenticado.getRole() == TipoUsuario.PROFESSOR;

        if (equipe) {
            return;
        }

        boolean dono = estagio.getAluno().getUsuario() != null
                && estagio.getAluno().getUsuario().getId().equals(autenticado.getId());
        if (!dono) {
            throw new BusinessException("Você não pode enviar relatórios para o estágio de outro aluno.");
        }
    }

    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    private RelatorioEstagioResponseDTO toResponse(RelatorioEstagio r) {
        return RelatorioEstagioResponseDTO.builder()
                .id(r.getId())
                .estagioId(r.getEstagio().getId())
                .periodoReferencia(r.getPeriodoReferencia())
                .horasRegistradas(r.getHorasRegistradas())
                .descricaoAtividades(r.getDescricaoAtividades())
                .comprovanteUrl(r.getComprovanteUrl())
                .status(r.getStatus())
                .analisadoPorNome(r.getAnalisadoPor() == null ? null : r.getAnalisadoPor().getNome())
                .observacoes(r.getObservacoes())
                .analisadoEm(r.getAnalisadoEm())
                .enviadoEm(r.getEnviadoEm())
                .build();
    }
}
