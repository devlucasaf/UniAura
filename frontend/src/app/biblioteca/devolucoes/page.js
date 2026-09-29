"use client";

import { useState }                     from "react";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { emprestimoApi }                from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda }  from "@/lib/formato";

// --- COMPONENTE DE DEVOLUÇÃO E RENOVAÇÃO DE EMPRÉSTIMOS DA BIBLIOTECA ---
export default function BibliotecaDevolucoesPage() {
    const [emprestimoId,    setEmprestimoId]    = useState("");
    const [mensagem,        setMensagem]        = useState({ texto: "", tipo: "" });
    const [detalhe,         setDetalhe]         = useState(null);

    // --- PROCESSA A DEVOLUÇÃO OU RENOVAÇÃO DO EMPRÉSTIMO NA API ---
    async function processar(acao) {
        try {
            const r = acao === "devolver" ? await emprestimoApi.devolver(emprestimoId.trim()) : await emprestimoApi.renovar(emprestimoId.trim());
            setMensagem({ texto: "Operação concluída.", tipo: "ok" });
            setDetalhe(r);
        } catch (erro) {
            setMensagem({ texto: erro.message, tipo: "error" });
        }
    }

    return (
        <AppShell titulo="Biblioteca — Devoluções" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Registrar devolução</h2>
                <form className="toolbar" onSubmit={(e) => { e.preventDefault(); processar("devolver"); }}>
                    <label className="field">ID do empréstimo
                        <input 
                            required 
                            autoFocus 
                            value={emprestimoId} 
                            onChange={(e) => setEmprestimoId(e.target.value)} 
                        />
                    </label>
                    <button type="submit" className="btn">Devolver</button>
                    <button type="button" className="btn secondary" onClick={() => processar("renovar")}>Renovar</button>
                    {mensagem.texto && <span className={mensagem.tipo === "error" ? "msg-error" : "msg-ok"}>{mensagem.texto}</span>}
                </form>

                {detalhe && (
                    <table style={{ marginTop: "1rem" }}>
                        <tbody>
                            <tr>
                                <th>Livro</th>
                                <td>{detalhe.livroTitulo}</td>
                            </tr>
                            <tr>
                                <th>Exemplar</th>
                                <td>{detalhe.exemplarCodigoBarras}</td>
                            </tr>
                            <tr>
                                <th>Usuário</th>
                                <td>{detalhe.usuarioNome}</td>
                            </tr>
                            <tr>
                                <th>Status</th>
                                <td><Badge status={detalhe.status} /></td>
                            </tr>
                            <tr>
                                <th>Prev. devolução</th>
                                <td>{formatarData(detalhe.dataDevolucaoPrevista)}</td>
                            </tr>
                            <tr>
                                <th>Devolvido em</th>
                                <td>{formatarData(detalhe.dataDevolucaoEfetiva)}</td>
                            </tr>
                            <tr>
                                <th>Dias de atraso</th>
                                <td>{detalhe.diasAtraso}</td>
                            </tr>
                            <tr>
                                <th>Multa</th>
                                <td>{formatarMoeda(detalhe.valorMulta)}</td>
                            </tr>
                            <tr>
                                <th>Renovações</th>
                                <td>{detalhe.renovacoes}</td>
                            </tr>
                        </tbody>
                    </table>
                )}
            </section>
        </AppShell>
    );
}
