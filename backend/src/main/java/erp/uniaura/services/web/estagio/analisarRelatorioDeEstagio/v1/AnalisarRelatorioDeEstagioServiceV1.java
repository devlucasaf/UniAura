package erp.uniaura.services.web.estagio.analisarRelatorioDeEstagio.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.RelatorioEstagio;
import erp.uniaura.persistence.entities.StatusRelatorioEstagio;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalisarRelatorioDeEstagioServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- ANALISA O RELATÓRIO E, SE APROVADO, SOMA AS HORAS AO ESTÁGIO ---
    @Transactional
    public void analisar(Long usuarioId, AnalisarRelatorioDeEstagioParamsV1 params) {
        RelatorioEstagio relatorio = findByUid(RelatorioEstagio.class, params.getRelatorioUid())
                .orElseThrow(() -> new BusinessException("Relatório de estágio não encontrado."));

        if (params.getStatus() == StatusRelatorioEstagio.PENDENTE) {
            throw new BusinessException("O resultado da análise deve ser APROVADO ou REJEITADO.");
        }

        if (relatorio.getStatus() != StatusRelatorioEstagio.PENDENTE) {
            throw new BusinessException("Este relatório já foi analisado.");
        }

        relatorio.setStatus(params.getStatus());
        relatorio.setObservacoes(params.getObservacoes());
        relatorio.setAnalisadoPor(buscarUsuarioComponent.buscarPorId(usuarioId));
        relatorio.setAnalisadoEm(LocalDateTime.now());
        relatorio.merge();

        if (params.getStatus() == StatusRelatorioEstagio.APROVADO) {
            Estagio estagio = relatorio.getEstagio();

            estagio.setCargaHorariaCumprida(estagio.getCargaHorariaCumprida() + relatorio.getHorasRegistradas());
            estagio.merge();
        }
    }
}
