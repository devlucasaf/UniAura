package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import cloudsupport.services.BaseFragment;
import cloudsupport.services.Fragment;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Fragment
public class PesquisarDisciplinasDoAlunoCatalogoFragmentV1 extends BaseFragment {

    private static final String ARQUIVO = "catalogo/cursos.json";

    private final List<PesquisarDisciplinasDoAlunoCursoCatalogoV1> cursos;

    // --- CARREGA O CATÁLOGO E FALHA NA SUBIDA SE O ARQUIVO NÃO PUDER SER LIDO ---
    public PesquisarDisciplinasDoAlunoCatalogoFragmentV1(ObjectMapper mapper) {
        try (InputStream entrada = new ClassPathResource(ARQUIVO).getInputStream()) {
            this.cursos = List.copyOf(mapper.readValue(entrada, PesquisarDisciplinasDoAlunoCatalogoV1.class).getCursos());
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler o catálogo de cursos (" + ARQUIVO + ")", e);
        }
    }

    // --- ACHA O CURSO PELO NOME OU PELO SLUG, SEM DIFERENCIAR ACENTOS E MAIÚSCULAS ---
    public Optional<PesquisarDisciplinasDoAlunoCursoCatalogoV1> buscar(String nomeOuSlug) {
        String alvo = normalizar(nomeOuSlug);
        if (alvo.isEmpty()) {
            return Optional.empty();
        }
        return cursos.stream()
                .filter(curso -> normalizar(curso.getTitulo()).equals(alvo) || normalizar(curso.getSlug()).equals(alvo))
                .findFirst();
    }

    // --- TIRA ACENTOS, TROCA PONTUAÇÃO POR ESPAÇO E DEIXA EM MINÚSCULAS, PARA COMPARAR TEXTOS ---
    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", " ")
                .trim();
    }
}
