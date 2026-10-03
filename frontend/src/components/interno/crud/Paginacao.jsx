"use client";

import { Paginator } from "primereact/paginator";

const TAMANHO_PAGINA = 10;

// --- BARRA DE PAGINAÇÃO PARA PÁGINAS SPRING ---
export default function Paginacao({ page, aoIr }) {
    if (!page) {
        return null;
    }

    return (
        <Paginator
            first={page.number * TAMANHO_PAGINA}
            rows={TAMANHO_PAGINA}
            totalRecords={page.totalElements}
            onPageChange={(evento) => aoIr(evento.page)}
        />
    );
}
