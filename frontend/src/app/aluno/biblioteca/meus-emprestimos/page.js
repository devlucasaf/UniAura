"use client";

import { useEffect, useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { emprestimoApi, multaApi } from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda } from "@/lib/formato";
import { obterUsuario } from "@/lib/auth";

export default function AlunoMeusEmprestimosPage() {
    const [emprestimos, setEmprestimos] = useState([]);
    const [multas, setMultas] = useState([]);

    async function carregar() {
        const usuario = obterUsuario();
        if (!usuario?.id) {
            return;
        }
        const pageEmp = await emprestimoApi.listarPorUsuario(usuario.id, { size: 100, sort: "dataEmprestimo,desc" });
        setEmprestimos(pageEmp.content || []);
        const listaMultas = await multaApi.pendentesDoUsuario(usuario.id);
        setMultas(listaMultas);
    }

    useEffect(() => { carregar(); }, []);

    async function renovar(id) {
        try {
            await emprestimoApi.renovar(id);
            carregar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Portal do Aluno — Meus empréstimos" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Meus empréstimos</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Livro</th><th>Empréstimo</th><th>Devolução prevista</th>
                            <th>Devolvido em</th><th>Status</th><th>Renovações</th><th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {emprestimos.length === 0 && <tr><td colSpan={7}>Nenhum empréstimo.</td></tr>}
                        {emprestimos.map((e) => (
                            <tr key={e.id}>
                                <td>{e.livroTitulo}</td>
                                <td>{formatarData(e.dataEmprestimo)}</td>
                                <td>{formatarData(e.dataDevolucaoPrevista)}</td>
                                <td>{formatarData(e.dataDevolucaoEfetiva)}</td>
                                <td><Badge status={e.status} /></td>
                                <td>{e.renovacoes}</td>
                                <td>{e.status !== "DEVOLVIDO" && <button className="btn btn-sm" onClick={() => renovar(e.id)}>Renovar</button>}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>

            <section className="card">
                <h2>Minhas multas pendentes</h2>
                <table>
                    <thead>
                        <tr><th>Livro</th><th>Dias atraso</th><th>Valor</th><th>Gerada em</th></tr>
                    </thead>
                    <tbody>
                        {multas.length === 0 && <tr><td colSpan={4}>Sem multas pendentes.</td></tr>}
                        {multas.map((m) => (
                            <tr key={m.id}>
                                <td>{m.livroTitulo}</td>
                                <td>{m.diasAtraso}</td>
                                <td>{formatarMoeda(m.valor)}</td>
                                <td>{formatarData(m.geradaEm)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </AppShell>
    );
}
