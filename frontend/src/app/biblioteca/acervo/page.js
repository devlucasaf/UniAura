"use client";

import { useEffect, useState }  from "react";
import { DataTable }            from "primereact/datatable";
import { Column }               from "primereact/column";
import { InputText }            from "primereact/inputtext";
import { InputNumber }          from "primereact/inputnumber";
import { InputTextarea }        from "primereact/inputtextarea";
import { Button }               from "primereact/button";
import { Message }              from "primereact/message";
import AppShell                 from "@/components/interno/AppShell";
import { livroApi }             from "@/lib/api/biblioteca";
import { formatarData }         from "@/lib/formato";

// --- CAMPOS DO LIVRO ---
const CAMPOS_LIVRO = [
    "titulo",
    "autor",
    "isbn",
    "editora",
    "anoPublicacao",
    "edicao",
    "paginas",
    "categoria",
    "sinopse"
];

const FORM_VAZIO = {
    titulo: "",
    autor: "",
    isbn: "",
    editora: "",
    anoPublicacao: "",
    edicao: "",
    paginas: "",
    categoria: "",
    sinopse: ""
};

const BUSCA_VAZIA = { titulo: "", autor: "", categoria: "", isbn: "" };

// --- PÁGINA DE CONSULTA E CADASTRO DE LIVROS ---
export default function BibliotecaAcervoPage() {
    const [livros,          setLivros]          = useState([]);
    const [erroLista,       setErroLista]       = useState("");
    const [form,            setForm]            = useState(FORM_VAZIO);
    const [capa,            setCapa]            = useState(null);
    const [mensagemForm,    setMensagemForm]    = useState({ texto: "", tipo: "" });
    const [busca,           setBusca]           = useState(BUSCA_VAZIA);
    const [livroEmEdicao,   setLivroEmEdicao]   = useState(null);

    // --- LISTAGEM DE LIVROS ---
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

    // --- PREENCHIMENTO DO FORMULÁRIO COM OS DADOS DO LIVRO ---
    function preencherForm(livro) {
        const novo = { ...FORM_VAZIO };
        CAMPOS_LIVRO.forEach((campo) => { novo[campo] = livro[campo] ?? ""; });
        setForm(novo);
    }

    // --- EDIÇÃO DE LIVRO ---
    function editar(livro) {
        setLivroEmEdicao(livro);
        preencherForm(livro);
        window.scrollTo(0, 0);
    }

    // --- EXCLUSÃO DE LIVRO ---
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

    // --- SALVAMENTO DE LIVRO ---
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
                setMensagemForm({ texto: "Atualizado.", tipo: "ok" });
            } else {
                await livroApi.criar(dados, capa);
                setMensagemForm({ texto: "Cadastrado.", tipo: "ok" });
            }
            setLivroEmEdicao(null);
            setForm(FORM_VAZIO);
            setCapa(null);
            listar();
        } catch (erro) {
            setMensagemForm({ texto: erro.message, tipo: "error" });
        }
    }

    // --- LIMPEZA DO FORMULÁRIO ---
    function limparForm() {
        setLivroEmEdicao(null);
        setForm(FORM_VAZIO);
        setCapa(null);
    }

    // --- BUSCA DE LIVROS ---
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
                        <InputText
                            value={form.titulo}
                            required
                            onChange={(e) => setForm({ ...form, titulo: e.target.value })}
                        />
                    </div>
                    <div className="field">Autor
                        <InputText
                            value={form.autor}
                            required
                            onChange={(e) => setForm({ ...form, autor: e.target.value })}
                        />
                    </div>
                    <div className="field">ISBN
                        <InputText
                            value={form.isbn}
                            onChange={(e) => setForm({ ...form, isbn: e.target.value })}
                        />
                    </div>
                    <div className="field">Editora
                        <InputText
                            value={form.editora}
                            onChange={(e) => setForm({ ...form, editora: e.target.value })}
                        />
                    </div>
                    <div className="field">Ano
                        <InputNumber
                            value={form.anoPublicacao === "" ? null : Number(form.anoPublicacao)}
                            useGrouping={false}
                            onValueChange={(e) => setForm({ ...form, anoPublicacao: e.value ?? "" })}
                        />
                    </div>
                    <div className="field">Edição
                        <InputText
                            value={form.edicao}
                            onChange={(e) => setForm({ ...form, edicao: e.target.value })}
                        />
                    </div>
                    <div className="field">Páginas
                        <InputNumber
                            value={form.paginas === "" ? null : Number(form.paginas)}
                            onValueChange={(e) => setForm({ ...form, paginas: e.value ?? "" })}
                        />
                    </div>
                    <div className="field">Categoria
                        <InputText
                            value={form.categoria}
                            onChange={(e) => setForm({ ...form, categoria: e.target.value })}
                        />
                    </div>
                    <div className="field">Capa (imagem)
                        <input
                            type="file"
                            accept="image/*"
                            onChange={(e) => setCapa(e.target.files[0] || null)}
                        />
                    </div>
                    <div className="field field-full">Sinopse
                        <InputTextarea
                            value={form.sinopse}
                            onChange={(e) => setForm({ ...form, sinopse: e.target.value })}
                        />
                    </div>
                    <div className="toolbar field-full">
                        <Button type="submit" label="Salvar" />
                        <Button type="button" label="Limpar" severity="secondary" outlined onClick={limparForm} />
                        {mensagemForm.texto && <Message severity={mensagemForm.tipo === "error" ? "error" : "success"} text={mensagemForm.texto} />}
                    </div>
                </form>
            </section>

            <section className="card">
                <h2>Buscar</h2>
                <form className="toolbar" onSubmit={buscar}>
                    <label className="field">
                        Título
                        <InputText
                            value={busca.titulo}
                            onChange={(e) => setBusca({ ...busca, titulo: e.target.value })}
                        />
                    </label>
                    <label className="field">
                        Autor
                        <InputText
                            value={busca.autor}
                            onChange={(e) => setBusca({ ...busca, autor: e.target.value })}
                        />
                    </label>
                    <label className="field">
                        Categoria
                        <InputText
                            value={busca.categoria}
                            onChange={(e) => setBusca({ ...busca, categoria: e.target.value })}
                        />
                    </label>
                    <label className="field">
                        ISBN
                        <InputText
                            value={busca.isbn}
                            onChange={(e) => setBusca({ ...busca, isbn: e.target.value })}
                        />
                    </label>
                    <Button type="submit" label="Filtrar" />
                    <Button type="button" label="Limpar" severity="secondary" outlined onClick={() => { setBusca(BUSCA_VAZIA); listar(); }} />
                </form>
            </section>

            <section className="card">
                <h2>Resultados</h2>
                {erroLista && <Message severity="error" text={erroLista} />}
                <DataTable value={livros} emptyMessage="Nenhum livro encontrado." dataKey="id">
                    <Column header="Título" body={(livro) => livro.titulo} />
                    <Column header="Autor" body={(livro) => livro.autor} />
                    <Column header="Categoria" body={(livro) => livro.categoria || "-"} />
                    <Column header="ISBN" body={(livro) => livro.isbn || "-"} />
                    <Column header="Exemplares" body={(livro) => `${livro.exemplaresDisponiveis}/${livro.totalExemplares}`} />
                    <Column header="Criado" body={(livro) => formatarData(livro.criadoEm)} />
                    <Column
                        header=""
                        body={(livro) => (
                            <div className="acoes">
                                <Button label="Editar" size="small" severity="secondary" outlined onClick={() => editar(livro)} />
                                <Button label="Excluir" size="small" severity="danger" outlined onClick={() => excluir(livro.id)} />
                            </div>
                        )}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
