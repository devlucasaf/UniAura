"use client";

import { useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { emprestimoApi } from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda } from "@/lib/formato";

export default function BibliotecaDevolucoesPage() {
    const [empId, setEmpId] = useState("");
    const [msg, setMsg] = useState({ texto: "", tipo: "" });
    const [detalhe, setDetalhe] = useState(null);

    async function processar(acao) {
        try {
            const r = acao === "devolver" ? await emprestimoApi.devolver(empId.trim()) : await emprestimoApi.renovar(empId.trim());
            setMsg({ texto: "Operação concluída.", tipo: "ok" });
            setDetalhe(r);
        } catch (erro) {
            setMsg({ texto: erro.message, tipo: "error" });
        }
    }

    return (
        <AppShell titulo="Biblioteca — Devoluções" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Registrar devolução</h2>
                <form className="toolbar" onSubmit={(e) => { e.preventDefault(); processar("devolver"); }}>
                    <label className="field">ID do empréstimo
                        <input required autoFocus value={empId} onChange={(e) => setEmpId(e.target.value)} />
                    </label>
                    <button type="submit" className="btn">Devolver</button>
                    <button type="button" className="btn secondary" onClick={() => processar("renovar")}>Renovar</button>
                    {msg.texto && <span className={msg.tipo === "error" ? "msg-error" : "msg-ok"}>{msg.texto}</span>}
                </form>

                {detalhe && (
                    <table style={{ marginTop: "1rem" }}>
                        <tbody>
                            <tr><th>Livro</th><td>{detalhe.livroTitulo}</td></tr>
                            <tr><th>Exemplar</th><td>{detalhe.exemplarCodigoBarras}</td></tr>
                            <tr><th>Usuário</th><td>{detalhe.usuarioNome}</td></tr>
                            <tr><th>Status</th><td><Badge status={detalhe.status} /></td></tr>
                            <tr><th>Prev. devolução</th><td>{formatarData(detalhe.dataDevolucaoPrevista)}</td></tr>
                            <tr><th>Devolvido em</th><td>{formatarData(detalhe.dataDevolucaoEfetiva)}</td></tr>
                            <tr><th>Dias de atraso</th><td>{detalhe.diasAtraso}</td></tr>
                            <tr><th>Multa</th><td>{formatarMoeda(detalhe.valorMulta)}</td></tr>
                            <tr><th>Renovações</th><td>{detalhe.renovacoes}</td></tr>
                        </tbody>
                    </table>
                )}
            </section>
        </AppShell>
    );
}
