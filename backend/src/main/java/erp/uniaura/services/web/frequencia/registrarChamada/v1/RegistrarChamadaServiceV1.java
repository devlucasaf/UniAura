package erp.uniaura.services.web.frequencia.registrarChamada.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aula;
import erp.uniaura.persistence.entities.Frequencia;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.frequencia.BuscarAulaComponent;
import erp.uniaura.services.common.professor.VerificarProfessorDaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrarChamadaServiceV1 extends BaseService {

    private final BuscarAulaComponent buscarAulaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarProfessorDaDisciplinaComponent verificarProfessorDaDisciplinaComponent;

    // --- REGISTRA A CHAMADA DA AULA, MARCANDO PRESENÇAS E FALTAS ---
    @Transactional
    public void registrar(Long usuarioId, RegistrarChamadaParamsV1 params) {
        Aula aula = buscarAulaComponent.buscarPorUid(params.getAulaUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarProfessorDaDisciplinaComponent.validar(aula.getTurmaDisciplina(), autenticado);

        for (RegistrarChamadaRegistroParamsV1 registro : params.getRegistros()) {
            List<Frequencia> encontradas = em()
                    .createQuery("select f from Frequencia f where f.aula = :aula and f.aluno.uid = :alunoUid", Frequencia.class)
                    .setParameter("aula", aula)
                    .setParameter("alunoUid", registro.getAlunoUid())
                    .getResultList();

            if (encontradas.isEmpty()) {
                throw new BusinessException("O aluno " + registro.getAlunoUid() + " não possui registro de frequência nesta aula.");
            }

            Frequencia frequencia = encontradas.get(0);

            frequencia.setPresente(registro.getPresente());

            if (Boolean.TRUE.equals(registro.getPresente())) {
                frequencia.setJustificativa(null);
            } else {
                frequencia.setJustificativa(registro.getJustificativa());
            }

            frequencia.merge();
        }
    }
}
