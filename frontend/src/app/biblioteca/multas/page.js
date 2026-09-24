"use client";

import { useEffect, useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { multaApi } from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda } from "@/lib/formato";

export default function BibliotecaMultasPage() {
    const [status, setStatus] = useState("PENDENTE");
    const [multas, setMultas] = useState([]);

    async function listar(statusAtual = status) {
        const page = await multaApi.listar({ status: statusAtual, size: 50 });
        setMultas(page.content || []);
    }

    useEffect(() => { listar(); }, []);

    async function pagar(id) {
        try {
            await multaApi.pagar(id);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    async function cancelar(id) {
        if (!confirm("Cancelar multa?")) {
            return;
        }
        try {
            await multaApi.cancelar(id);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Biblioteca — Multas" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Multas</h2>
                <form className="toolbar" onSubmit={(e) => { e.preventDefault(); listar(); }}>
                    <label className="field">Status
                        <select value={status} onChange={(e) => setStatus(e.target.value)}>
                            <option value="PENDENTE">PENDENTE</option>
                            <option value="PAGA">PAGA</option>
                            <option value="CANCELADA">CANCELADA</option>
                        </select>
                    </label>
                    <button type="submit" className="btn">Listar</button>
                </form>
                <table>
                    <thead>
                        <tr>
                            <th>Usuário</th><th>Livro</th><th>Dias atraso</th><th>Valor</th>
                            <th>Gerada</th><th>Paga</th><th>Status</th><th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {multas.length === 0 && <tr><td colSpan={8}>Nenhuma multa.</td></tr>}
                        {multas.map((m) => (
                            <tr key={m.id}>
                                <td>{m.usuarioNome}</td>
                                <td>{m.livroTitulo}</td>
                                <td>{m.diasAtraso}</td>
                                <td>{formatarMoeda(m.valor)}</td>
                                <td>{formatarData(m.geradaEm)}</td>
                                <td>{formatarData(m.pagaEm)}</td>
                                <td><Badge status={m.status} /></td>
                                <td>
                                    {m.status === "PENDENTE" && (
                                        <>
                                            <button className="btn btn-sm" onClick={() => pagar(m.id)}>Baixar pagto.</button>
                                            <button className="btn danger btn-sm" onClick={() => cancelar(m.id)}>Cancelar</button>
                                        </>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </AppShell>
    );
}
