package erp.uniaura.services.web.comunicado.pesquisarMural.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Comunicado;
import erp.uniaura.persistence.entities.PublicoAlvoComunicado;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.turma.ConsultarUidDasTurmasComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PesquisarMuralServiceV1 extends BaseService {

    private static final int LIMITE = 30;

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final ConsultarUidDasTurmasComponent consultarUidDasTurmasComponent;

    // --- MONTA O MURAL DE COMUNICADOS CONFORME O PERFIL DO USUÁRIO LOGADO ---
    @Transactional(readOnly = true)
    public PesquisarMuralRetornoV1 pesquisar(Long usuarioId) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        List<Comunicado> comunicados;

        if (autenticado.getRole() == TipoUsuario.ALUNO) {
            comunicados = comunicadosDoAluno(autenticado);
        } else if (autenticado.getRole() == TipoUsuario.PROFESSOR) {
            comunicados = recentes(List.of(PublicoAlvoComunicado.TODOS, PublicoAlvoComunicado.PROFESSORES));
        } else {
            comunicados = recentes(List.of(PublicoAlvoComunicado.TODOS));
        }

        Map<Long, Long> uidsDasTurmas = consultarUidDasTurmasComponent.uidsPorId(
                comunicados.stream().map(Comunicado::getTurmaId).toList());

        PesquisarMuralRetornoV1 retorno = new PesquisarMuralRetornoV1();
        retorno.setLista(comunicados.stream().map(comunicado -> paraRetorno(comunicado, uidsDasTurmas)).toList());

        return retorno;
    }

    // --- OS GERAIS E PARA ALUNOS, MAIS OS DIRECIONADOS À TURMA ATUAL DO ALUNO, DOS MAIS RECENTES AOS MAIS ANTIGOS ---
    private List<Comunicado> comunicadosDoAluno(Usuario autenticado) {
        List<Comunicado> gerais = recentes(List.of(PublicoAlvoComunicado.TODOS, PublicoAlvoComunicado.ALUNOS));

        Aluno aluno = em()
                .createQuery("select a from Aluno a where a.usuario.id = :usuarioId", Aluno.class)
                .setParameter("usuarioId", autenticado.getId())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (aluno == null || aluno.getTurmaAtualId() == null) {
            return gerais;
        }

        List<Comunicado> daTurma = em()
                .createQuery("select c from Comunicado c where c.publicoAlvo = :publico and c.turmaId = :turmaId order by c.criadoEm desc", Comunicado.class)
                .setParameter("publico", PublicoAlvoComunicado.TURMA)
                .setParameter("turmaId", aluno.getTurmaAtualId())
                .setMaxResults(LIMITE)
                .getResultList();

        List<Comunicado> todos = new ArrayList<>(gerais);
        todos.addAll(daTurma);
        todos.sort(Comparator.comparing(Comunicado::getCriadoEm).reversed());

        return todos;
    }

    // --- BUSCA OS COMUNICADOS RECENTES DOS PÚBLICOS-ALVO INFORMADOS ---
    private List<Comunicado> recentes(List<PublicoAlvoComunicado> publicos) {
        return em()
                .createQuery("select c from Comunicado c where c.publicoAlvo in :publicos order by c.criadoEm desc", Comunicado.class)
                .setParameter("publicos", publicos)
                .setMaxResults(LIMITE)
                .getResultList();
    }

    // --- CONVERTE O COMUNICADO NO ITEM DE RETORNO DO MURAL ---
    private PesquisarMuralComunicadoRetornoV1 paraRetorno(Comunicado comunicado, Map<Long, Long> uidsDasTurmas) {
        PesquisarMuralComunicadoRetornoV1 item = new PesquisarMuralComunicadoRetornoV1();

        item.setUid(comunicado.getUid());
        item.setTitulo(comunicado.getTitulo());
        item.setMensagem(comunicado.getMensagem());
        item.setPublicoAlvo(comunicado.getPublicoAlvo());
        item.setTurmaUid(uidsDasTurmas.get(comunicado.getTurmaId()));
        item.setImportante(comunicado.getImportante());
        item.setAutorNome(comunicado.getAutor().getNome());
        item.setCriadoEm(comunicado.getCriadoEm());

        return item;
    }
}
