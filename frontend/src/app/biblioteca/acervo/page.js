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
import { pesquisarLivros }      from "@/app/remotes/uniaura/biblioteca/pesquisarLivrosRemote";
import { detalharLivro }        from "@/app/remotes/uniaura/biblioteca/detalharLivroRemote";
import { cadastrarLivro }       from "@/app/remotes/uniaura/biblioteca/cadastrarLivroRemote";
import { alterarLivro }         from "@/app/remotes/uniaura/biblioteca/alterarLivroRemote";
import { removerLivro }         from "@/app/remotes/uniaura/biblioteca/removerLivroRemote";
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

const FORMULARIO_VAZIO = {
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

const BUSCA_VAZIA = {
    titulo: "",
    autor: "",
    categoria: "",
    isbn: ""
};

// --- PÁGINA DE CONSULTA E CADASTRO DE LIVROS ---
export default function BibliotecaAcervoPage() {
    const [livros,              setLivros]              = useState([]);
    const [erroLista,           setErroLista]           = useState("");
    const [formulario,          setFormulario]          = useState(FORMULARIO_VAZIO);
    const [capa,                setCapa]                = useState(null);
    const [mensagemFormulario,  setMensagemFormulario]  = useState({ texto: "", tipo: "" });
    const [busca,               setBusca]               = useState(BUSCA_VAZIA);
    const [livroEmEdicao,       setLivroEmEdicao]       = useState(null);

    // --- LISTAGEM DE LIVROS ---
    async function listar(parametros = {}) {
        try {
            setErroLista("");
            const resultado = await pesquisarLivros({ qtd: 20, ...parametros });
            setLivros(resultado.lista || []);
        } catch (erro) {
            setLivros([]);
            setErroLista(erro.message);
        }
    }

    useEffect(() => { listar(); }, []);

    // --- PREENCHIMENTO DO FORMULÁRIO COM OS DADOS DO LIVRO ---
    function preencherFormulario(livro) {
        const novo = { ...FORMULARIO_VAZIO };
        CAMPOS_LIVRO.forEach((campo) => { novo[campo] = livro[campo] ?? ""; });
        setFormulario(novo);
    }

    // --- EDIÇÃO DE LIVRO: ---
    async function editar(livro) {
        try {
            const completo = await detalharLivro(livro.uid);
            setLivroEmEdicao(completo);
            preencherFormulario(completo);
            window.scrollTo(0, 0);
        } catch (erro) {
            alert(erro.message);
        }
    }

    // --- EXCLUSÃO DE LIVRO ---
    async function excluir(uid) {
        if (!confirm("Confirmar exclusão do livro?")) {
            return;
        }

        try {
            await removerLivro(uid);
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
            titulo: formulario.titulo,
            autor: formulario.autor,
            isbn: formulario.isbn || null,
            editora: formulario.editora || null,
            anoPublicacao: formulario.anoPublicacao ? Number(formulario.anoPublicacao) : null,
            edicao: formulario.edicao || null,
            paginas: formulario.paginas ? Number(formulario.paginas) : null,
            categoria: formulario.categoria || null,
            sinopse: formulario.sinopse || null
        };

        try {
            if (livroEmEdicao) {
                await alterarLivro(livroEmEdicao.uid, dados, capa);
                setMensagemFormulario({
                    texto: "Atualizado.",
                    tipo: "ok"
                });
            } else {
                await cadastrarLivro(dados, capa);
                setMensagemFormulario({
                    texto: "Cadastrado.",
                    tipo: "ok"
                });
            }
            setLivroEmEdicao(null);
            setFormulario(FORMULARIO_VAZIO);
            setCapa(null);
            listar();
        } catch (erro) {
            setMensagemFormulario({
                texto: erro.message,
                tipo: "error"
            });
        }
    }

    // --- LIMPEZA DO FORMULÁRIO ---
    function limparFormulario() {
        setLivroEmEdicao(null);
        setFormulario(FORMULARIO_VAZIO);
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
                            value={formulario.titulo}
                            required
                            onChange={(evento) => setFormulario({ ...formulario, titulo: evento.target.value })}
                        />
                    </div>
                    <div className="field">Autor
                        <InputText
                            value={formulario.autor}
                            required
                            onChange={(evento) => setFormulario({ ...formulario, autor: evento.target.value })}
                        />
                    </div>
                    <div className="field">ISBN
                        <InputText
                            value={formulario.isbn}
                            onChange={(evento) => setFormulario({ ...formulario, isbn: evento.target.value })}
                        />
                    </div>
                    <div className="field">Editora
                        <InputText
                            value={formulario.editora}
                            onChange={(evento) => setFormulario({ ...formulario, editora: evento.target.value })}
                        />
                    </div>
                    <div className="field">Ano
                        <InputNumber
                            value={formulario.anoPublicacao === "" ? null : Number(formulario.anoPublicacao)}
                            useGrouping={false}
                            onValueChange={(evento) => setFormulario({ ...formulario, anoPublicacao: evento.value ?? "" })}
                        />
                    </div>
                    <div className="field">Edição
                        <InputText
                            value={formulario.edicao}
                            onChange={(evento) => setFormulario({ ...formulario, edicao: evento.target.value })}
                        />
                    </div>
                    <div className="field">Páginas
                        <InputNumber
                            value={formulario.paginas === "" ? null : Number(formulario.paginas)}
                            onValueChange={(evento) => setFormulario({ ...formulario, paginas: evento.value ?? "" })}
                        />
                    </div>
                    <div className="field">Categoria
                        <InputText
                            value={formulario.categoria}
                            onChange={(evento) => setFormulario({ ...formulario, categoria: evento.target.value })}
                        />
                    </div>
                    <div className="field">Capa (imagem)
                        <input
                            type="file"
                            accept="image/*"
                            onChange={(evento) => setCapa(evento.target.files[0] || null)}
                        />
                    </div>
                    <div className="field field-full">Sinopse
                        <InputTextarea
                            value={formulario.sinopse}
                            onChange={(evento) => setFormulario({ ...formulario, sinopse: evento.target.value })}
                        />
                    </div>
                    <div className="toolbar field-full">
                        <Button type="submit" label="Salvar" />
                        <Button
                            type="button"
                            label="Limpar"
                            severity="secondary"
                            outlined
                            onClick={limparFormulario}
                        />
                        {mensagemFormulario.texto && <Message severity={mensagemFormulario.tipo === "error" ? "error" : "success"} text={mensagemFormulario.texto} />}
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
                            onChange={(evento) => setBusca({ ...busca, titulo: evento.target.value })}
                        />
                    </label>
                    <label className="field">
                        Autor
                        <InputText
                            value={busca.autor}
                            onChange={(evento) => setBusca({ ...busca, autor: evento.target.value })}
                        />
                    </label>
                    <label className="field">
                        Categoria
                        <InputText
                            value={busca.categoria}
                            onChange={(evento) => setBusca({ ...busca, categoria: evento.target.value })}
                        />
                    </label>
                    <label className="field">
                        ISBN
                        <InputText
                            value={busca.isbn}
                            onChange={(evento) => setBusca({ ...busca, isbn: evento.target.value })}
                        />
                    </label>
                    <Button type="submit" label="Filtrar" />
                    <Button
                        type="button"
                        label="Limpar"
                        severity="secondary"
                        outlined
                        onClick={() => { setBusca(BUSCA_VAZIA); listar(); }}
                    />
                </form>
            </section>

            <section className="card">
                <h2>Resultados</h2>
                {erroLista && <Message severity="error" text={erroLista} />}
                <DataTable value={livros} emptyMessage="Nenhum livro encontrado." dataKey="uid">
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
                                <Button
                                    label="Editar"
                                    size="small"
                                    severity="secondary"
                                    outlined
                                    onClick={() => editar(livro)}
                                />
                                <Button
                                    label="Excluir"
                                    size="small"
                                    severity="danger"
                                    outlined
                                    onClick={() => excluir(livro.uid)}
                                />
                            </div>
                        )}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
