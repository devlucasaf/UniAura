package erp.uniaura.modules.biblioteca.config.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.modules.biblioteca.config.dto.ConfiguracaoBibliotecaDTO;
import erp.uniaura.modules.biblioteca.config.model.ConfiguracaoBiblioteca;
import erp.uniaura.modules.biblioteca.config.repository.ConfiguracaoBibliotecaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfiguracaoBibliotecaService {

    private final ConfiguracaoBibliotecaRepository configuracaoBibliotecaRepository;

    // --- RETORNA A ÚNICA LINHA DE CONFIGURAÇÃO ---
    @Transactional(readOnly = true)
    public ConfiguracaoBiblioteca obter() {
        return configuracaoBibliotecaRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new BusinessException(
                        "Configuração da biblioteca não inicializada. Verifique a migration V14."));
    }

    // --- ATUALIZA OS PARÂMETROS GLOBAIS ---
    @Transactional
    public ConfiguracaoBiblioteca atualizar(ConfiguracaoBibliotecaDTO dto) {
        ConfiguracaoBiblioteca configuracaoBiblioteca = obter();

        configuracaoBiblioteca.setPrazoEmprestimoAluno(dto.getPrazoEmprestimoAluno());
        configuracaoBiblioteca.setPrazoEmprestimoProfessor(dto.getPrazoEmprestimoProfessor());
        configuracaoBiblioteca.setMaxEmprestimosSimultaneos(dto.getMaxEmprestimosSimultaneos());
        configuracaoBiblioteca.setMaxRenovacoes(dto.getMaxRenovacoes());
        configuracaoBiblioteca.setValorMultaDia(dto.getValorMultaDia());

        return configuracaoBibliotecaRepository.save(configuracaoBiblioteca);
    }
}

