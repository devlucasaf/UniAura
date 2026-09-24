"use client";

import { useEffect, useState } from "react";
import AppShell from "@/components/interno/AppShell";
import { livroApi, reservaApi } from "@/lib/api/biblioteca";

const FILTRO_VAZIO = { titulo: "", autor: "", categoria: "", isbn: "" };

export default function AlunoBibliotecaConsultarPage() {
    const [filtro, setFiltro] = useState(FILTRO_VAZIO);
    const [filtroAtivo, setFiltroAtivo] = useState({});
    const [pagina, setPagina] = useState(0);
    const [page, setPage] = useState(null);

    async function buscar(paginaAlvo = pagina, filtroAlvo = filtroAtivo) {
        const resultado = await livroApi.buscar({ ...filtroAlvo, page: paginaAlvo, size: 10 });
        setPage(resultado);
    }

    useEffect(() => { buscar(0, {}); }, []);

    async function reservar(livroId) {
        try {
            const r = await reservaApi.reservar({ livroId });
            alert(`Reservado! Posição na fila: ${r.posicaoFila}`);
        } catch (erro) {
            alert(erro.message);
        }
    }

    function aplicarFiltro(evento) {
        evento.preventDefault();
        const proximo = {};
        Object.entries(filtro).forEach(([chave, valor]) => { if (valor) proximo[chave] = valor; });
        setFiltroAtivo(proximo);
        setPagina(0);
        buscar(0, proximo);
    }

    function irPagina(delta) {
        const novaPagina = pagina + delta;
        setPagina(novaPagina);
        buscar(novaPagina);
    }

    const livros = page?.content || [];

    return (
        <AppShell titulo="Portal do Aluno — Biblioteca" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Consultar acervo</h2>
                <form className="toolbar" onSubmit={aplicarFiltro}>
                    <label className="field">Título<input value={filtro.titulo} onChange={(e) => setFiltro({ ...filtro, titulo: e.target.value })} /></label>
                    <label className="field">Autor<input value={filtro.autor} onChange={(e) => setFiltro({ ...filtro, autor: e.target.value })} /></label>
                    <label className="field">Categoria<input value={filtro.categoria} onChange={(e) => setFiltro({ ...filtro, categoria: e.target.value })} /></label>
                    <label className="field">ISBN<input value={filtro.isbn} onChange={(e) => setFiltro({ ...filtro, isbn: e.target.value })} /></label>
                    <button type="submit" className="btn">Buscar</button>
                </form>
            </section>

            <section className="card">
                <table>
                    <thead>
                        <tr><th>Título</th><th>Autor</th><th>Categoria</th><th>Disponíveis</th><th></th></tr>
                    </thead>
                    <tbody>
                        {livros.length === 0 && <tr><td colSpan={5}>Nada encontrado.</td></tr>}
                        {livros.map((livro) => {
                            const disponivel = livro.exemplaresDisponiveis > 0;
                            return (
                                <tr key={livro.id}>
                                    <td>{livro.titulo}</td>
                                    <td>{livro.autor}</td>
                                    <td>{livro.categoria || "-"}</td>
                                    <td>{livro.exemplaresDisponiveis}/{livro.totalExemplares}</td>
                                    <td>
                                        {disponivel
                                            ? <span className="badge DISPONIVEL">Disponível</span>
                                            : <button className="btn btn-sm" onClick={() => reservar(livro.id)}>Reservar</button>}
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                {page && (
                    <div className="toolbar" style={{ marginTop: ".75rem" }}>
                        <button className="btn secondary" disabled={page.first} onClick={() => irPagina(-1)}>← Anterior</button>
                        <span>Página {page.number + 1} de {page.totalPages || 1}</span>
                        <button className="btn secondary" disabled={page.last} onClick={() => irPagina(1)}>Próxima →</button>
                    </div>
                )}
            </section>
        </AppShell>
    );
}
