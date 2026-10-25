package erp.uniaura.services.web.certificado.emitirHistoricoEscolar.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Nota;
import erp.uniaura.persistence.entities.TipoCertificado;
import erp.uniaura.services.common.certificado.BuscarAlunoParaEmissaoComponent;
import erp.uniaura.services.common.certificado.MontarPdfDeCertificadoComponent;
import erp.uniaura.services.common.certificado.RegistrarEmissaoDeCertificadoComponent;
import erp.uniaura.services.common.nota.ListarNotasDoAlunoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmitirHistoricoEscolarServiceV1 extends BaseService {

    private final BuscarAlunoParaEmissaoComponent buscarAlunoParaEmissaoComponent;
    private final RegistrarEmissaoDeCertificadoComponent registrarEmissaoDeCertificadoComponent;
    private final MontarPdfDeCertificadoComponent montarPdfDeCertificadoComponent;
    private final ListarNotasDoAlunoComponent listarNotasDoAlunoComponent;

    // --- EMITE O HISTÓRICO ESCOLAR EM PDF COM AS NOTAS DO ALUNO ---
    @Transactional
    public byte[] emitir(Long usuarioId, EmitirHistoricoEscolarParamsV1 params) {
        Aluno aluno = buscarAlunoParaEmissaoComponent.buscar(usuarioId, params.getAlunoUid());
        List<Nota> notas = listarNotasDoAlunoComponent.listar(aluno);

        List<String> linhas = new ArrayList<>();

        linhas.add("Aluno(a): " + aluno.getUsuario().getNome() + "  —  Matrícula: " + aluno.getMatriculaRA());
        linhas.add(" ");

        if (notas.isEmpty()) {
            linhas.add("Nenhuma nota lançada até o momento.");
        }

        for (Nota nota : notas) {
            linhas.add("%-28s %-8s %-14s %-14s Nota: %s".formatted(
                    truncar(nota.getTurmaDisciplina().getDisciplina().getNome(), 28), nota.getTurmaDisciplina().getTurma().getCodigo(),
                    nota.getPeriodoAvaliacao(), nota.getTipoAvaliacao(), nota.getValor()));
        }

        String codigo = registrarEmissaoDeCertificadoComponent.registrar(usuarioId, aluno, TipoCertificado.HISTORICO_ESCOLAR);

        return montarPdfDeCertificadoComponent.montar("HISTÓRICO ESCOLAR", linhas, codigo);
    }

    // --- TRUNCA O TEXTO PARA O TAMANHO MÁXIMO ---
    private String truncar(String texto, int maximo) {
        if (texto == null) {
            return "";
        }

        if (texto.length() <= maximo) {
            return texto;
        }

        return texto.substring(0, maximo - 1) + "…";
    }
}
