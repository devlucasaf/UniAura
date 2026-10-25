// --- FORMATA DATA/DATA-HORA ISO PARA O PADRÃO BR ---
export function formatarData(dataIso) {
    if (!dataIso) {
        return "-";
    }
    return new Date(dataIso).toLocaleString("pt-BR");
}

// --- FORMATA VALOR MONETÁRIO EM BRL ---
export function formatarMoeda(valor) {
    if (valor == null) {
        return "-";
    }
    return Number(valor).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}
