let toastReferencia = null;

// --- REGISTRA A REFERÊNCIA DO <Toast> DO PRIMEREACT MONTADO PELO PrimeToastHost ---
export function registrarToastReferencia(referencia) {
    toastReferencia = referencia;
}

const SEVERIDADE_POR_TIPO = {
    info: "info",
    success: "success",
    error: "error",
    warning: "warn"
};

// --- EXIBE UMA NOTIFICAÇÃO ---
export function notificar(mensagem, tipo = "info", tempo = 3500) {
    if (!toastReferencia) {
        return;
    }
    toastReferencia.show({
        severity: SEVERIDADE_POR_TIPO[tipo] || "info",
        detail: mensagem,
        life: tempo
    });
}
