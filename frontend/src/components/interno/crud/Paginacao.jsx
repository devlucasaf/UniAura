"use client";

// --- BARRA DE PAGINAÇÃO PARA PÁGINAS SPRING (Page<T>) ---
export default function Paginacao({ page, aoIr }) {
    if (!page) {
        return null;
    }

    return (
        <div className="toolbar paginacao">
            <button className="btn secondary btn-sm" disabled={page.first} onClick={() => aoIr(page.number - 1)}>← Anterior</button>
            <span>Página {page.number + 1} de {page.totalPages || 1} · {page.totalElements} registro(s)</span>
            <button className="btn secondary btn-sm" disabled={page.last} onClick={() => aoIr(page.number + 1)}>Próxima →</button>
        </div>
    );
}
