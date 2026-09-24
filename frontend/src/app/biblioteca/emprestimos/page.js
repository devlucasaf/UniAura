"use client";

import { useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { emprestimoApi, exemplarApi } from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda } from "@/lib/formato";

export default function BibliotecaEmprestimosPage() {
    const [codigoBarras, setCodigoBarras] = useState("");
    const [usuarioIdNovo, setUsuarioIdNovo] = useState("");
    const [msg, setMsg] = useState({ texto: "", tipo: "" });
    const [usuarioIdConsulta, setUsuarioIdConsulta] = useState("");
    const [emprestimos, setEmprestimos] = useState(null);

    async function registrar(evento) {
        evento.preventDefault();
        try {
            const ex = await exemplarApi.buscarPorCodigo(codigoBarras.trim());
            const emp = await emprestimoApi.registrar({ exemplarId: ex.id, usuarioId: usuarioIdNovo.trim() });
            setMsg({
                texto: `Empréstimo #${emp.id.substring(0, 8)} registrado. Devolução prevista: ${formatarData(emp.dataDevolucaoPrevista)}`,
                tipo: "ok"
            });
            setCodigoBarras("");
            setUsuarioIdNovo("");
        } catch (erro) {
            setMsg({ texto: erro.message, tipo: "error" });
        }
    }

    async function consultar(evento) {
        evento.preventDefault();
        const page = await emprestimoApi.listarPorUsuario(usuarioIdConsulta.trim(), { size: 50, sort: "dataEmprestimo,desc" });
        setEmprestimos(page.content || []);
    }

    return (
        <AppShell titulo="Biblioteca — Empréstimos" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Novo empréstimo</h2>
                <form className="toolbar" onSubmit={registrar}>
                    <label className="field">Código de barras do exemplar
                        <input required autoFocus value={codigoBarras} onChange={(e) => setCodigoBarras(e.target.value)} />
                    </label>
                    <label className="field">ID do usuário (aluno/professor)
                        <input required value={usuarioIdNovo} onChange={(e) => setUsuarioIdNovo(e.target.value)} />
                    </label>
                    <button type="submit" className="btn">Registrar</button>
                    {msg.texto && <span className={msg.tipo === "error" ? "msg-error" : "msg-ok"}>{msg.texto}</span>}
                </form>
            </section>

            <section className="card">
                <h2>Consultar empréstimos por usuário</h2>
                <form className="toolbar" onSubmit={consultar}>
                    <label className="field">ID do usuário
                        <input required value={usuarioIdConsulta} onChange={(e) => setUsuarioIdConsulta(e.target.value)} />
                    </label>
                    <button type="submit" className="btn">Listar</button>
                </form>
                <table>
                    <thead>
                        <tr>
                            <th>Livro</th><th>Código</th><th>Empréstimo</th><th>Prev. devolução</th>
                            <th>Devolvido em</th><th>Status</th><th>Renovações</th><th>Multa</th>
                        </tr>
                    </thead>
                    <tbody>
                        {emprestimos && emprestimos.length === 0 && <tr><td colSpan={8}>Nenhum empréstimo.</td></tr>}
                        {emprestimos?.map((emp) => (
                            <tr key={emp.id}>
                                <td>{emp.livroTitulo}</td>
                                <td>{emp.exemplarCodigoBarras}</td>
                                <td>{formatarData(emp.dataEmprestimo)}</td>
                                <td>{formatarData(emp.dataDevolucaoPrevista)}</td>
                                <td>{formatarData(emp.dataDevolucaoEfetiva)}</td>
                                <td><Badge status={emp.status} /></td>
                                <td>{emp.renovacoes}</td>
                                <td>{formatarMoeda(emp.valorMulta)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </AppShell>
    );
}
