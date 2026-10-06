"use client";

import { useEffect, useState }  from "react";
import { InputText }            from "primereact/inputtext";
import { Button }               from "primereact/button";
import { DataTable }            from "primereact/datatable";
import { Column }               from "primereact/column";
import { Paginator }            from "primereact/paginator";
import { Tag }                  from "primereact/tag";
import AppShell                 from "@/components/interno/AppShell";
import { livroApi, reservaApi } from "@/lib/api/biblioteca";

const FILTRO_VAZIO = { titulo: "", autor: "", categoria: "", isbn: "" };
const TAMANHO_PAGINA = 10;

// --- PÁGINA DE CONSULTA DE LIVROS DO ACERVO DA BIBLIOTECA ---
export default function AlunoBibliotecaConsultarPage() {
    const [filtro,      setFiltro]      = useState(FILTRO_VAZIO);
    const [filtroAtivo, setFiltroAtivo] = useState({});
    const [pagina,      setPagina]      = useState(0);
    const [page,        setPage]        = useState(null);

    // --- BUSCA DE LIVROS COM FILTRO E PAGINAÇÃO ---
    async function buscar(paginaAlvo = pagina, filtroAlvo = filtroAtivo) {
        const resultado = await livroApi.buscar({ ...filtroAlvo, page: paginaAlvo, size: TAMANHO_PAGINA });
        setPage(resultado);
    }

    useEffect(() => { buscar(0, {}); }, []);

    // --- RESERVA DE LIVRO ---
    async function reservar(livroId) {
        try {
            const r = await reservaApi.reservar({ livroId });
            alert(`Reservado! Posição na fila: ${r.posicaoFila}`);
        } catch (erro) {
            alert(erro.message);
        }
    }

    // --- APLICAÇÃO DE FILTRO E PAGINAÇÃO ---
    function aplicarFiltro(evento) {
        evento.preventDefault();
        const proximo = {};
        Object.entries(filtro).forEach(([chave, valor]) => { if (valor) proximo[chave] = valor; });
        setFiltroAtivo(proximo);
        setPagina(0);
        buscar(0, proximo);
    }

    const livros = page?.content || [];

    return (
        <AppShell titulo="Portal do Aluno — Biblioteca" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Consultar acervo</h2>
                <form className="toolbar" onSubmit={aplicarFiltro}>
                    <label className="field">
                        Título
                        <InputText
                            value={filtro.titulo}
                            onChange={(e) => setFiltro({ ...filtro, titulo: e.target.value })}
                        />
                    </label>
                    <label className="field">
                        Autor
                        <InputText
                            value={filtro.autor}
                            onChange={(e) => setFiltro({ ...filtro, autor: e.target.value })}
                        />
                    </label>
                    <label className="field">
                        Categoria
                        <InputText
                            value={filtro.categoria}
                            onChange={(e) => setFiltro({ ...filtro, categoria: e.target.value })}
                        />
                    </label>
                    <label className="field">
                        ISBN
                        <InputText
                            value={filtro.isbn}
                            onChange={(e) => setFiltro({ ...filtro, isbn: e.target.value })}
                        />
                    </label>
                    <Button type="submit" label="Buscar" />
                </form>
            </section>

            <section className="card">
                <DataTable value={livros} emptyMessage="Nada encontrado." dataKey="id">
                    <Column header="Título" body={(livro) => livro.titulo} />
                    <Column header="Autor" body={(livro) => livro.autor} />
                    <Column header="Categoria" body={(livro) => livro.categoria || "-"} />
                    <Column header="Disponíveis" body={(livro) => `${livro.exemplaresDisponiveis}/${livro.totalExemplares}`} />
                    <Column
                        header=""
                        body={(livro) => (
                            livro.exemplaresDisponiveis > 0
                                ? <Tag value="Disponível" severity="success" />
                                : <Button label="Reservar" size="small" onClick={() => reservar(livro.id)} />
                        )}
                    />
                </DataTable>
                {page && (
                    <Paginator
                        first={pagina * TAMANHO_PAGINA}
                        rows={TAMANHO_PAGINA}
                        totalRecords={page.totalElements}
                        onPageChange={(e) => { setPagina(e.page); buscar(e.page); }}
                    />
                )}
            </section>
        </AppShell>
    );
}
