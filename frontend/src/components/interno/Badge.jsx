"use client";

import { Tag } from "primereact/tag";

const STATUS_POSITIVOS = [
    "ATIVO", "ATIVA", "APROVADO", "APROVADA", "PAGA", "PAGO", "CONCLUIDO", "CONCLUÍDO",
    "DISPONIVEL", "DISPONÍVEL", "FORMADO", "ATENDIDA", "DEVOLVIDO"
];
const STATUS_NEGATIVOS = [
    "ATRASADO", "ATRASADA", "REJEITADO", "REJEITADA", "CANCELADO", "CANCELADA",
    "REPROVADO", "REPROVADA", "EVADIDO", "VENCIDO", "EXPIRADA", "PERDIDO", "TRANCADO", "TRANCADA"
];

// --- DEDUZ A SEVERIDADE VISUAL A PARTIR DO NOME DO STATUS ---
function severidadeDoStatus(status) {
    const valor = String(status || "").toUpperCase();
    if (STATUS_POSITIVOS.includes(valor)) {
        return "success";
    }
    if (STATUS_NEGATIVOS.includes(valor)) {
        return "danger";
    }
    return "info";
}

// --- SELO DE STATUS ---
export default function Badge({ status }) {
    return <Tag value={status} severity={severidadeDoStatus(status)} />;
}
