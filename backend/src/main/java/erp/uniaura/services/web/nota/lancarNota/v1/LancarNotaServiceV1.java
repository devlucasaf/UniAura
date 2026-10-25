package erp.uniaura.services.web.nota.lancarNota.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.infra.email.EmailService;
import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Nota;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.nota.VerificarPermissaoDeLancamentoDeNotaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LancarNotaServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final VerificarPermissaoDeLancamentoDeNotaComponent verificarPermissaoDeLancamentoDeNotaComponent;
    private final EmailService emailService;

    // --- LANÇA A NOTA, VALIDA A PERMISSÃO E NOTIFICA O ALUNO POR E-MAIL ---
    @Transactional
    public Uid lancar(Long usuarioId, LancarNotaParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());

        verificarPermissaoDeLancamentoDeNotaComponent.validar(autenticado, turmaDisciplina);

        Nota nota = new Nota();

        nota.setAluno(aluno);
        nota.setTurmaDisciplina(turmaDisciplina);
        nota.setPeriodoAvaliacao(params.getPeriodoAvaliacao());
        nota.setTipoAvaliacao(params.getTipoAvaliacao());
        nota.setValor(params.getValor());
        nota.setLancadaPor(autenticado);

        if (params.getPeso() == null) {
            nota.setPeso(BigDecimal.ONE);
        } else {
            nota.setPeso(params.getPeso());
        }

        nota.setObservacoes(params.getObservacoes());
        nota.persist();

        // --- AVISA O ALUNO POR E-MAIL ---
        emailService.notificarNotaLancada(aluno.getUsuario().getEmail(), aluno.getUsuario().getNome(),
                turmaDisciplina.getDisciplina().getNome(), nota.getValor());

        return new Uid(nota.getUid());
    }
}
