// --- MÁSCARA DE CPF ---
export function mascararCpf(valor) {
    return (valor || "")
        .replace(/\D/g, "")
        .slice(0, 11)
        .replace(/(\d{3})(\d)/, "$1.$2")
        .replace(/(\d{3})(\d)/, "$1.$2")
        .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
}

// --- MÁSCARA DE TELEFONE ---
export function mascararTelefone(valor) {
    return (valor || "")
        .replace(/\D/g, "")
        .slice(0, 11)
        .replace(/(\d{2})(\d)/, "($1) $2")
        .replace(/(\d{5})(\d)/, "$1-$2");
}

// --- MÁSCARA DE CEP ---
export function mascararCep(valor) {
    return (valor || "")
        .replace(/\D/g, "")
        .slice(0, 8)
        .replace(/(\d{5})(\d)/, "$1-$2");
}
