"use client";

import { useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { livroApi, exemplarApi } from "@/lib/api/biblioteca";
import { formatarData } from "@/lib/formato";

export default function BibliotecaExemplaresPage() {
    const [tituloBusca, setTituloBusca] = useState("");
    const [resultados, setResultados] = useState(null);
    const [erroBusca, setErroBusca] = useState("");
    const [livroSelecionado, setLivroSelecionado] = useState(null);
    const [exemplares, setExemplares] = useState([]);
    const [codigoBarras, setCodigoBarras] = useState("");
    const [localizacao, setLocalizacao] = useState("");
    const [msgExemplar, setMsgExemplar] = useState({ texto: "", tipo: "" });

    async function buscarLivros(evento) {
        evento.preventDefault();
        setErroBusca("");
        setResultados(null);
        try {
            const page = await livroApi.buscar({ titulo: tituloBusca, size: 10 });
            setResultados(page.content || []);
        } catch (erro) {
            setErroBusca(erro.message);
        }
    }

    async function selecionarLivro(livro) {
        setLivroSelecionado({ id: livro.id, titulo: livro.titulo });
        await listarExemplares(livro.id);
    }

    async function listarExemplares(livroId) {
        const page = await exemplarApi.listarPorLivro(livroId, { size: 50 });
        setExemplares(page.content || []);
    }

    async function gerarCodigo() {
        const r = await exemplarApi.gerarCodigoBarras();
        setCodigoBarras(r.codigoBarras);
    }

    async function adicionarExemplar(evento) {
        evento.preventDefault();
        try {
            await exemplarApi.criar({
                livroId: livroSelecionado.id,
                codigoBarras: codigoBarras || null,
                localizacao: localizacao || null
            });
            setCodigoBarras("");
            setLocalizacao("");
            setMsgExemplar({ texto: "Exemplar criado.", tipo: "ok" });
            listarExemplares(livroSelecionado.id);
        } catch (erro) {
            setMsgExemplar({ texto: erro.message, tipo: "error" });
        }
    }

    async function excluirExemplar(id) {
        if (!confirm("Excluir exemplar?")) {
            return;
        }
        try {
            await exemplarApi.deletar(id);
            listarExemplares(livroSelecionado.id);
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Biblioteca — Exemplares" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Selecionar livro</h2>
                <form className="toolbar" onSubmit={buscarLivros}>
                    <label className="field">Título<input value={tituloBusca} required onChange={(e) => setTituloBusca(e.target.value)} /></label>
                    <button type="submit" className="btn">Buscar</button>
                </form>
                {erroBusca && <p className="msg-error">{erroBusca}</p>}
                {resultados && (
                    <ul>
                        {resultados.length === 0 && <li>Nenhum livro encontrado.</li>}
                        {resultados.map((livro) => (
                            <li key={livro.id}>
                                <a href="#" onClick={(e) => { e.preventDefault(); selecionarLivro(livro); }}>
                                    {livro.titulo} — {livro.autor}
                                </a>
                            </li>
                        ))}
                    </ul>
                )}
            </section>

            {livroSelecionado && (
                <section className="card">
                    <h2>Exemplares de {livroSelecionado.titulo}</h2>
                    <form className="toolbar" onSubmit={adicionarExemplar}>
                        <label className="field">Código de barras
                            <input placeholder="deixe vazio para gerar" value={codigoBarras} onChange={(e) => setCodigoBarras(e.target.value)} />
                        </label>
                        <label className="field">Localização
                            <input placeholder="ex.: Estante A3" value={localizacao} onChange={(e) => setLocalizacao(e.target.value)} />
                        </label>
                        <button type="button" className="btn secondary" onClick={gerarCodigo}>Gerar código</button>
                        <button type="submit" className="btn">Adicionar exemplar</button>
                        {msgExemplar.texto && <span className={msgExemplar.tipo === "error" ? "msg-error" : "msg-ok"}>{msgExemplar.texto}</span>}
                    </form>
                    <table>
                        <thead>
                            <tr><th>Código</th><th>Localização</th><th>Status</th><th>Criado</th><th></th></tr>
                        </thead>
                        <tbody>
                            {exemplares.length === 0 && <tr><td colSpan={5}>Sem exemplares.</td></tr>}
                            {exemplares.map((ex) => (
                                <tr key={ex.id}>
                                    <td>{ex.codigoBarras}</td>
                                    <td>{ex.localizacao || "-"}</td>
                                    <td><Badge status={ex.status} /></td>
                                    <td>{formatarData(ex.criadoEm)}</td>
                                    <td><button className="btn danger btn-sm" onClick={() => excluirExemplar(ex.id)}>Excluir</button></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>
            )}
        </AppShell>
    );
}
