package erp.uniaura.services.web.nota.consultarBoletim.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Nota;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsultarBoletimServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- CONSULTA O BOLETIM DO ALUNO AGRUPADO POR DISCIPLINA ---
    @Transactional(readOnly = true)
    public ConsultarBoletimRetornoV1 consultar(ConsultarBoletimParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        List<Nota> notas = em()
                .createQuery("select n from Nota n where n.aluno = :aluno and n.turmaDisciplina.turma.periodoLetivo = :periodoLetivo", Nota.class)
                .setParameter("aluno", aluno)
                .setParameter("periodoLetivo", params.getPeriodoLetivo())
                .getResultList();

        // --- AGRUPA AS NOTAS POR DISCIPLINA, NA ORDEM EM QUE APARECEM ---
        Map<Disciplina, List<Nota>> porDisciplina = new LinkedHashMap<>();
        for (Nota nota : notas) {
            Disciplina disciplina = nota.getTurmaDisciplina().getDisciplina();
            porDisciplina.computeIfAbsent(disciplina, chave -> new ArrayList<>()).add(nota);
        }

        List<ConsultarBoletimDisciplinaRetornoV1> linhas = new ArrayList<>();

        for (Map.Entry<Disciplina, List<Nota>> grupo : porDisciplina.entrySet()) {
            ConsultarBoletimDisciplinaRetornoV1 linha = new ConsultarBoletimDisciplinaRetornoV1();

            linha.setDisciplinaUid(grupo.getKey().getUid());
            linha.setDisciplinaCodigo(grupo.getKey().getCodigo());
            linha.setDisciplinaNome(grupo.getKey().getNome());
            linha.setMediaFinal(calcularMediaPonderada(grupo.getValue()));
            linha.setNotas(listarNotas(grupo.getValue()));

            linhas.add(linha);
        }

        ConsultarBoletimRetornoV1 retorno = new ConsultarBoletimRetornoV1();

        retorno.setAlunoNome(aluno.getUsuario().getNome());
        retorno.setAlunoMatriculaRA(aluno.getMatriculaRA());
        retorno.setPeriodoLetivo(params.getPeriodoLetivo());
        retorno.setDisciplinas(linhas);

        return retorno;
    }

    // --- LISTA AS NOTAS ORDENADAS POR PERÍODO DE AVALIAÇÃO ---
    private List<ConsultarBoletimNotaRetornoV1> listarNotas(List<Nota> notas) {
        List<Nota> ordenadas = new ArrayList<>(notas);
        ordenadas.sort(Comparator.comparing(Nota::getPeriodoAvaliacao));

        List<ConsultarBoletimNotaRetornoV1> lista = new ArrayList<>();

        for (Nota nota : ordenadas) {
            ConsultarBoletimNotaRetornoV1 item = new ConsultarBoletimNotaRetornoV1();

            item.setPeriodoAvaliacao(nota.getPeriodoAvaliacao());
            item.setTipoAvaliacao(nota.getTipoAvaliacao());
            item.setValor(nota.getValor());
            item.setPeso(nota.getPeso());

            lista.add(item);
        }

        return lista;
    }

    // --- CALCULA A MÉDIA PONDERADA PELO PESO DAS NOTAS ---
    private BigDecimal calcularMediaPonderada(List<Nota> notas) {
        BigDecimal somaPonderada = BigDecimal.ZERO;
        BigDecimal somaPesos = BigDecimal.ZERO;

        for (Nota nota : notas) {
            somaPonderada = somaPonderada.add(nota.getValor().multiply(nota.getPeso()));
            somaPesos = somaPesos.add(nota.getPeso());
        }

        if (somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return somaPonderada.divide(somaPesos, 2, RoundingMode.HALF_UP);
    }
}
