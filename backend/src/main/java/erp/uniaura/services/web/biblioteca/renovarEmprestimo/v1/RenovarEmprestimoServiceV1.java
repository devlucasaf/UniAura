package erp.uniaura.services.web.biblioteca.renovarEmprestimo.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.ConfiguracaoBiblioteca;
import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.StatusEmprestimo;
import erp.uniaura.persistence.entities.StatusReserva;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.biblioteca.BuscarEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.CalcularPrazoDoEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.ObterConfiguracaoDaBibliotecaComponent;
import erp.uniaura.services.common.biblioteca.VerificarAcessoAoEmprestimoComponent;
import erp.uniaura.services.common.biblioteca.VerificarMultaPendenteDoUsuarioComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RenovarEmprestimoServiceV1 extends BaseService {

    private final BuscarEmprestimoComponent buscarEmprestimoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAoEmprestimoComponent verificarAcessoAoEmprestimoComponent;
    private final ObterConfiguracaoDaBibliotecaComponent obterConfiguracaoDaBibliotecaComponent;
    private final VerificarMultaPendenteDoUsuarioComponent verificarMultaPendenteDoUsuarioComponent;
    private final CalcularPrazoDoEmprestimoComponent calcularPrazoDoEmprestimoComponent;

    // --- RENOVA O EMPRÉSTIMO APÓS VALIDAR LIMITES, MULTA E RESERVAS ---
    @Transactional
    public void renovar(Long usuarioId, RenovarEmprestimoParamsV1 params) {
        Emprestimo emprestimo = buscarEmprestimoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAoEmprestimoComponent.validar(emprestimo, autenticado);

        ConfiguracaoBiblioteca configuracao = obterConfiguracaoDaBibliotecaComponent.obter();

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new BusinessException("Empréstimo já devolvido não pode ser renovado.");
        }

        if (emprestimo.getRenovacoes() >= configuracao.getMaximasRenovacoes()) {
            throw new BusinessException("Limite de renovações atingido (" + configuracao.getMaximasRenovacoes() + ").");
        }

        if (verificarMultaPendenteDoUsuarioComponent.existe(emprestimo.getUsuario())) {
            throw new BusinessException("Usuário possui multa pendente. Renovação bloqueada.");
        }

        Long aguardando = em()
                .createQuery("select count(r) from Reserva r where r.livro = :livro and r.status = :status", Long.class)
                .setParameter("livro", emprestimo.getExemplar().getLivro())
                .setParameter("status", StatusReserva.AGUARDANDO)
                .getSingleResult();

        if (aguardando > 0) {
            throw new BusinessException("Existem reservas para este livro. Renovação não permitida.");
        }

        LocalDateTime base = emprestimo.getDataDevolucaoPrevista();
        if (base.isBefore(LocalDateTime.now())) {
            base = LocalDateTime.now();
        }

        emprestimo.setDataDevolucaoPrevista(base.plusDays(calcularPrazoDoEmprestimoComponent.calcular(emprestimo.getUsuario())));
        emprestimo.setRenovacoes(emprestimo.getRenovacoes() + 1);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);
        emprestimo.merge();
    }
}
