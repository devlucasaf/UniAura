let toastRef = null;

// --- REGISTRA A REFERÊNCIA DO <Toast> DO PRIMEREACT MONTADO PELO PrimeToastHost ---
export function registrarToastRef(ref) {
    toastRef = ref;
}

const SEVERIDADE_POR_TIPO = {
    info: "info",
    success: "success",
    error: "error",
    warning: "warn"
};

// --- EXIBE UMA NOTIFICAÇÃO (TOAST DO PRIMEREACT) ---
export function notificar(mensagem, tipo = "info", tempo = 3500) {
    if (!toastRef) {
        return;
    }
    toastRef.show({
        severity: SEVERIDADE_POR_TIPO[tipo] || "info",
        detail: mensagem,
        life: tempo
    });
}
