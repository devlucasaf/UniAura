"use client";

import { Dialog } from "primereact/dialog";

// --- MODAL GENÉRICO DE CADASTRO/EDIÇÃO ---
export default function Modal({ titulo, aberto, onFechar, children }) {
    return (
        <Dialog header={titulo} visible={aberto} onHide={onFechar} modal style={{ width: "min(640px, 92vw)" }}>
            {children}
        </Dialog>
    );
}
