"use client";

// --- MODAL GENÉRICO DE CADASTRO/EDIÇÃO ---
export default function Modal({ titulo, aberto, onFechar, children }) {
    if (!aberto) {
        return null;
    }

    return (
        <div className="modal-overlay">
            <div className="modal">
                <div className="modal-header">
                    <h2>{titulo}</h2>
                    <button className="icon-btn" aria-label="Fechar" onClick={onFechar}>✕</button>
                </div>
                {children}
            </div>
        </div>
    );
}
