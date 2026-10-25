package erp.uniaura.services.web.biblioteca.registrarEmprestimo.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.ConfiguracaoBiblioteca;
import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.persistence.entities.Reserva;
import erp.uniaura.persistence.entities.StatusEmprestimo;
import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.persistence.entities.StatusReserva;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.biblioteca.BuscarExemplarComponent;
import erp.uniaura.services.common.biblioteca.CalcularPrazoDoEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.ObterConfiguracaoDaBibliotecaComponent;
import erp.uniaura.services.common.biblioteca.ReordenarFilaDeReservasComponent;
import erp.uniaura.services.common.biblioteca.VerificarMultaPendenteDoUsuarioComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrarEmprestimoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarExemplarComponent buscarExemplarComponent;
    private final ObterConfiguracaoDaBibliotecaComponent obterConfiguracaoDaBibliotecaComponent;
    private final VerificarMultaPendenteDoUsuarioComponent verificarMultaPendenteDoUsuarioComponent;
    private final CalcularPrazoDoEmprestimoComponent calcularPrazoDoEmprestimoComponent;
    private final ReordenarFilaDeReservasComponent reordenarFilaDeReservasComponent;

    // --- REGISTRA O EMPRÉSTIMO APÓS VALIDAR DISPONIBILIDADE, MULTA E LIMITE ---
    @Transactional
    public RegistrarEmprestimoRetornoV1 registrar(Long atendenteId, RegistrarEmprestimoParamsV1 params) {
        ConfiguracaoBiblioteca configuracao = obterConfiguracaoDaBibliotecaComponent.obter();

        Exemplar exemplar = buscarExemplar(params);
        Usuario usuario = buscarUsuarioComponent.buscarPorUid(params.getUsuarioUid());

        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new BusinessException("Exemplar não está disponível para empréstimo. Status atual: " + exemplar.getStatus());
        }

        if (verificarMultaPendenteDoUsuarioComponent.existe(usuario)) {
            throw new BusinessException("Usuário possui multa pendente e não pode realizar novos empréstimos.");
        }

        Long ativos = em()
                .createQuery("select count(e) from Emprestimo e where e.usuario = :usuario and e.status in :status", Long.class)
                .setParameter("usuario", usuario)
                .setParameter("status", List.of(StatusEmprestimo.ATIVO, StatusEmprestimo.ATRASADO))
                .getSingleResult();

        if (ativos >= configuracao.getMaximosEmprestimosSimultaneos()) {
            throw new BusinessException("Limite de empréstimos simultâneos atingido (" + configuracao.getMaximosEmprestimosSimultaneos() + ").");
        }

        LocalDateTime agora = LocalDateTime.now();

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setExemplar(exemplar);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(agora);
        emprestimo.setDataDevolucaoPrevista(agora.plusDays(calcularPrazoDoEmprestimoComponent.calcular(usuario)));
        emprestimo.setRenovacoes(0);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);
        emprestimo.setCriadoPor(buscarUsuarioComponent.buscarPorId(atendenteId));
        emprestimo.persist();

        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        exemplar.merge();

        atenderReservaDoUsuario(exemplar, usuario);

        RegistrarEmprestimoRetornoV1 retorno = new RegistrarEmprestimoRetornoV1();

        retorno.setUid(emprestimo.getUid());
        retorno.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());

        return retorno;
    }

    // --- O EXEMPLAR VEM PELO UID OU, NO BALCÃO, PELO CÓDIGO DE BARRAS LIDO ---
    private Exemplar buscarExemplar(RegistrarEmprestimoParamsV1 params) {
        if (params.getExemplarUid() != null) {
            return buscarExemplarComponent.buscarPorUid(params.getExemplarUid());
        }

        if (params.getCodigoBarras() != null && !params.getCodigoBarras().isBlank()) {
            return buscarExemplarComponent.buscarPorCodigoDeBarras(params.getCodigoBarras());
        }

        throw new BusinessException("Informe o uid do exemplar ou o código de barras.");
    }

    // --- SE O USUÁRIO ERA O PRIMEIRO DA FILA DE RESERVAS DO LIVRO, A RESERVA DELE É MARCADA COMO ATENDIDA ---
    private void atenderReservaDoUsuario(Exemplar exemplar, Usuario usuario) {
        List<Reserva> primeiraDaFila = em()
                .createQuery("select r from Reserva r where r.livro = :livro and r.status = :status order by r.posicaoFila", Reserva.class)
                .setParameter("livro", exemplar.getLivro())
                .setParameter("status", StatusReserva.AGUARDANDO)
                .setMaxResults(1)
                .getResultList();

        if (primeiraDaFila.isEmpty()) {
            return;
        }

        Reserva reserva = primeiraDaFila.get(0);

        if (reserva.getUsuario().getId().equals(usuario.getId())) {
            reserva.setStatus(StatusReserva.ATENDIDA);
            reserva.merge();

            reordenarFilaDeReservasComponent.reordenar(exemplar.getLivro());
        }
    }
}
