// --- SELO DE STATUS (EX.: DISPONIVEL, EMPRESTADO, PENDENTE...) ---
export default function Badge({ status }) {
    return <span className={`badge ${status}`}>{status}</span>;
}
