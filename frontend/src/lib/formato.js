// --- FORMATA DATA/DATA-HORA ISO PARA O PADRÃO BR ---
export function formatarData(iso) {
    if (!iso) {
        return "-";
    }
    return new Date(iso).toLocaleString("pt-BR");
}

// --- FORMATA VALOR MONETÁRIO EM BRL ---
export function formatarMoeda(valor) {
    if (valor == null) {
        return "-";
    }
    return Number(valor).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}
