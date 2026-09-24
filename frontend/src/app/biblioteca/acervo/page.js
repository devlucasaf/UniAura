"use client";

import { useEffect, useState } from "react";
import AppShell from "@/components/interno/AppShell";
import { livroApi } from "@/lib/api/biblioteca";
import { formatarData } from "@/lib/formato";

const CAMPOS_LIVRO = ["titulo", "autor", "isbn", "editora", "anoPublicacao", "edicao", "paginas", "categoria", "sinopse"];

const FORM_VAZIO = {
    titulo: "", autor: "", isbn: "", editora: "", anoPublicacao: "",
    edicao: "", paginas: "", categoria: "", sinopse: ""
};

const BUSCA_VAZIA = { titulo: "", autor: "", categoria: "", isbn: "" };

export default function BibliotecaAcervoPage() {
    const [livros, setLivros] = useState([]);
    const [erroLista, setErroLista] = useState("");
    const [form, setForm] = useState(FORM_VAZIO);
    const [capa, setCapa] = useState(null);
    const [msgForm, setMsgForm] = useState({ texto: "", tipo: "" });
    const [busca, setBusca] = useState(BUSCA_VAZIA);
    const [livroEmEdicao, setLivroEmEdicao] = useState(null);

    async function listar(parametros = {}) {
        try {
            setErroLista("");
            const page = await livroApi.buscar({ size: 20, ...parametros });
            setLivros(page.content || page || []);
        } catch (erro) {
            setLivros([]);
            setErroLista(erro.message);
        }
    }

    useEffect(() => { listar(); }, []);

    function preencherForm(livro) {
        const novo = { ...FORM_VAZIO };
        CAMPOS_LIVRO.forEach((campo) => { novo[campo] = livro[campo] ?? ""; });
        setForm(novo);
    }

    function editar(livro) {
        setLivroEmEdicao(livro);
        preencherForm(livro);
        window.scrollTo(0, 0);
    }

    async function excluir(id) {
        if (!confirm("Confirmar exclusão do livro?")) {
            return;
        }
        try {
            await livroApi.deletar(id);
            setLivroEmEdicao(null);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    async function salvar(evento) {
        evento.preventDefault();
        const dados = {
            titulo: form.titulo,
            autor: form.autor,
            isbn: form.isbn || null,
            editora: form.editora || null,
            anoPublicacao: form.anoPublicacao ? Number(form.anoPublicacao) : null,
            edicao: form.edicao || null,
            paginas: form.paginas ? Number(form.paginas) : null,
            categoria: form.categoria || null,
            sinopse: form.sinopse || null
        };

        try {
            if (livroEmEdicao) {
                await livroApi.atualizar(livroEmEdicao.id, dados, capa);
                setMsgForm({ texto: "Atualizado.", tipo: "ok" });
            } else {
                await livroApi.criar(dados, capa);
                setMsgForm({ texto: "Cadastrado.", tipo: "ok" });
            }
            setLivroEmEdicao(null);
            setForm(FORM_VAZIO);
            setCapa(null);
            listar();
        } catch (erro) {
            setMsgForm({ texto: erro.message, tipo: "error" });
        }
    }

    function limparForm() {
        setLivroEmEdicao(null);
        setForm(FORM_VAZIO);
        setCapa(null);
    }

    function buscar(evento) {
        evento.preventDefault();
        const parametros = {};
        Object.entries(busca).forEach(([chave, valor]) => { if (valor) parametros[chave] = valor; });
        listar(parametros);
    }

    return (
        <AppShell titulo="Biblioteca — Acervo" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Cadastro de Livros</h2>
                <form className="form-grid" onSubmit={salvar}>
                    <div className="field">Título
                        <input value={form.titulo} required onChange={(e) => setForm({ ...form, titulo: e.target.value })} />
                    </div>
                    <div className="field">Autor
                        <input value={form.autor} required onChange={(e) => setForm({ ...form, autor: e.target.value })} />
                    </div>
                    <div className="field">ISBN
                        <input value={form.isbn} onChange={(e) => setForm({ ...form, isbn: e.target.value })} />
                    </div>
                    <div className="field">Editora
                        <input value={form.editora} onChange={(e) => setForm({ ...form, editora: e.target.value })} />
                    </div>
                    <div className="field">Ano
                        <input type="number" value={form.anoPublicacao} onChange={(e) => setForm({ ...form, anoPublicacao: e.target.value })} />
                    </div>
                    <div className="field">Edição
                        <input value={form.edicao} onChange={(e) => setForm({ ...form, edicao: e.target.value })} />
                    </div>
                    <div className="field">Páginas
                        <input type="number" value={form.paginas} onChange={(e) => setForm({ ...form, paginas: e.target.value })} />
                    </div>
                    <div className="field">Categoria
                        <input value={form.categoria} onChange={(e) => setForm({ ...form, categoria: e.target.value })} />
                    </div>
                    <div className="field">Capa (imagem)
                        <input type="file" accept="image/*" onChange={(e) => setCapa(e.target.files[0] || null)} />
                    </div>
                    <div className="field field-full">Sinopse
                        <textarea value={form.sinopse} onChange={(e) => setForm({ ...form, sinopse: e.target.value })} />
                    </div>
                    <div className="toolbar field-full">
                        <button type="submit" className="btn">Salvar</button>
                        <button type="button" className="btn secondary" onClick={limparForm}>Limpar</button>
                        {msgForm.texto && <span className={msgForm.tipo === "error" ? "msg-error" : "msg-ok"}>{msgForm.texto}</span>}
                    </div>
                </form>
            </section>

            <section className="card">
                <h2>Buscar</h2>
                <form className="toolbar" onSubmit={buscar}>
                    <label className="field">Título<input value={busca.titulo} onChange={(e) => setBusca({ ...busca, titulo: e.target.value })} /></label>
                    <label className="field">Autor<input value={busca.autor} onChange={(e) => setBusca({ ...busca, autor: e.target.value })} /></label>
                    <label className="field">Categoria<input value={busca.categoria} onChange={(e) => setBusca({ ...busca, categoria: e.target.value })} /></label>
                    <label className="field">ISBN<input value={busca.isbn} onChange={(e) => setBusca({ ...busca, isbn: e.target.value })} /></label>
                    <button type="submit" className="btn">Filtrar</button>
                    <button type="button" className="btn secondary" onClick={() => { setBusca(BUSCA_VAZIA); listar(); }}>Limpar</button>
                </form>
            </section>

            <section className="card">
                <h2>Resultados</h2>
                {erroLista && <p className="msg-error">{erroLista}</p>}
                <table>
                    <thead>
                        <tr><th>Título</th><th>Autor</th><th>Categoria</th><th>ISBN</th><th>Exemplares</th><th>Criado</th><th></th></tr>
                    </thead>
                    <tbody>
                        {livros.length === 0 && <tr><td colSpan={7}>Nenhum livro encontrado.</td></tr>}
                        {livros.map((livro) => (
                            <tr key={livro.id}>
                                <td>{livro.titulo}</td>
                                <td>{livro.autor}</td>
                                <td>{livro.categoria || "-"}</td>
                                <td>{livro.isbn || "-"}</td>
                                <td>{livro.exemplaresDisponiveis}/{livro.totalExemplares}</td>
                                <td>{formatarData(livro.criadoEm)}</td>
                                <td className="acoes">
                                    <button className="btn secondary btn-sm" onClick={() => editar(livro)}>Editar</button>
                                    <button className="btn danger btn-sm" onClick={() => excluir(livro.id)}>Excluir</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </AppShell>
    );
}
