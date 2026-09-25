package erp.uniaura.modules.calendario.service;

import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.calendario.dto.EventoAcademicoRequestDTO;
import erp.uniaura.modules.calendario.dto.EventoAcademicoResponseDTO;
import erp.uniaura.modules.calendario.model.EventoAcademico;
import erp.uniaura.modules.calendario.repository.EventoAcademicoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoAcademicoService {

    private final EventoAcademicoRepository eventoAcademicoRepository;

    // --- LISTA TODOS OS EVENTOS ---
    @Transactional(readOnly = true)
    public Page<EventoAcademicoResponseDTO> listar(Pageable pageable) {
        return eventoAcademicoRepository.findAll(pageable).map(this::toResponse);
    }

    // --- LISTA APENAS OS EVENTOS MARCADOS COMO PÚBLICOS, PARA O CALENDÁRIO DO SITE INSTITUCIONAL ---
    @Transactional(readOnly = true)
    public List<EventoAcademicoResponseDTO> listarPublicos() {
        return eventoAcademicoRepository.findByPublicoTrueOrderByDataInicioAsc()
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EventoAcademicoResponseDTO buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    // --- CRIA UM NOVO EVENTO NO CALENDÁRIO ACADÊMICO ---
    @Transactional
    public EventoAcademicoResponseDTO criar(EventoAcademicoRequestDTO dto) {
        EventoAcademico evento = EventoAcademico.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .tipo(dto.getTipo())
                .publico(Boolean.TRUE.equals(dto.getPublico()))
                .build();

        return toResponse(eventoAcademicoRepository.save(evento));
    }

    // --- ATUALIZA UM EVENTO EXISTENTE ---
    @Transactional
    public EventoAcademicoResponseDTO atualizar(UUID id, EventoAcademicoRequestDTO dto) {
        EventoAcademico evento = buscarEntidade(id);

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataInicio(dto.getDataInicio());
        evento.setDataFim(dto.getDataFim());
        evento.setTipo(dto.getTipo());
        evento.setPublico(Boolean.TRUE.equals(dto.getPublico()));

        return toResponse(eventoAcademicoRepository.save(evento));
    }

    // --- REMOVE UM EVENTO ---
    @Transactional
    public void deletar(UUID id) {
        eventoAcademicoRepository.delete(buscarEntidade(id));
    }

    private EventoAcademico buscarEntidade(UUID id) {
        return eventoAcademicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento acadêmico", id));
    }

    private EventoAcademicoResponseDTO toResponse(EventoAcademico e) {
        return EventoAcademicoResponseDTO.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descricao(e.getDescricao())
                .dataInicio(e.getDataInicio())
                .dataFim(e.getDataFim())
                .tipo(e.getTipo())
                .publico(e.getPublico())
                .criadoEm(e.getCriadoEm())
                .atualizadoEm(e.getAtualizadoEm())
                .build();
    }
}
