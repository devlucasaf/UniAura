"use client";

import { useState }                     from "react";
import { InputText }                    from "primereact/inputtext";
import { Button }                       from "primereact/button";
import { Message }                      from "primereact/message";
import { DataTable }                    from "primereact/datatable";
import { Column }                       from "primereact/column";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { pesquisarLivros }              from "@/app/remotes/uniaura/biblioteca/pesquisarLivrosRemote";
import { pesquisarExemplaresDoLivro }   from "@/app/remotes/uniaura/biblioteca/pesquisarExemplaresDoLivroRemote";
import { gerarCodigoDeBarras }          from "@/app/remotes/uniaura/biblioteca/gerarCodigoDeBarrasRemote";
import { cadastrarExemplar }            from "@/app/remotes/uniaura/biblioteca/cadastrarExemplarRemote";
import { removerExemplar }              from "@/app/remotes/uniaura/biblioteca/removerExemplarRemote";
import { formatarData }                 from "@/lib/formato";

// --- COMPONENTE DE GESTÃO DE EXEMPLARES DA BIBLIOTECA ---
export default function BibliotecaExemplaresPage() {
    const [tituloBusca,         setTituloBusca]         = useState("");
    const [resultados,          setResultados]          = useState(null);
    const [erroBusca,           setErroBusca]           = useState("");
    const [livroSelecionado,    setLivroSelecionado]    = useState(null);
    const [exemplares,          setExemplares]          = useState([]);
    const [codigoBarras,        setCodigoBarras]        = useState("");
    const [localizacao,         setLocalizacao]         = useState("");
    const [mensagemExemplar,    setMensagemExemplar]    = useState({ texto: "", tipo: "" });

    // --- BUSCA LIVROS PELO TÍTULO INFORMADO ---
    async function buscarLivros(evento) {
        evento.preventDefault();
        setErroBusca("");
        setResultados(null);
        try {
            const resultado = await pesquisarLivros({ titulo: tituloBusca, qtd: 10 });
            setResultados(resultado.lista || []);
        } catch (erro) {
            setErroBusca(erro.message);
        }
    }

    // --- SELECIONA O LIVRO E CARREGA SEUS EXEMPLARES ---
    async function selecionarLivro(livro) {
        setLivroSelecionado({ uid: livro.uid, titulo: livro.titulo });
        await listarExemplares(livro.uid);
    }

    // --- LISTA OS EXEMPLARES DE UM LIVRO ---
    async function listarExemplares(livroUid) {
        const resultado = await pesquisarExemplaresDoLivro(livroUid, { qtd: 50 });
        setExemplares(resultado.lista || []);
    }

    // --- GERA UM CÓDIGO DE BARRAS PARA O NOVO EXEMPLAR ---
    async function gerarCodigo() {
        const resultado = await gerarCodigoDeBarras();
        setCodigoBarras(resultado.codigoBarras);
    }

    // --- ADICIONA UM NOVO EXEMPLAR AO LIVRO SELECIONADO ---
    async function adicionarExemplar(evento) {
        evento.preventDefault();
        try {
            await cadastrarExemplar({
                livroUid: livroSelecionado.uid,
                codigoBarras: codigoBarras || null,
                localizacao: localizacao || null
            });
            setCodigoBarras("");
            setLocalizacao("");
            setMensagemExemplar({
                texto: "Exemplar criado.",
                tipo: "ok"
            });
            listarExemplares(livroSelecionado.uid);
        } catch (erro) {
            setMensagemExemplar({
                texto: erro.message,
                tipo: "error"
            });
        }
    }

    // --- EXCLUI UM EXEMPLAR APÓS CONFIRMAÇÃO DO USUÁRIO ---
    async function excluirExemplar(uid) {
        if (!confirm("Excluir exemplar?")) {
            return;
        }

        try {
            await removerExemplar(uid);
            listarExemplares(livroSelecionado.uid);
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Biblioteca — Exemplares" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Selecionar livro</h2>
                <form className="toolbar" onSubmit={buscarLivros}>
                    <label className="field">
                        Título
                        <InputText
                            value={tituloBusca}
                            required
                            onChange={(evento) => setTituloBusca(evento.target.value)}
                        />
                    </label>
                    <Button 
                        type="submit" 
                        label="Buscar" 
                    />
                </form>
                {erroBusca && <Message severity="error" text={erroBusca} />}
                {resultados && (
                    <ul>
                        {resultados.length === 0 && <li>Nenhum livro encontrado.</li>}
                        {resultados.map((livro) => (
                            <li key={livro.uid}>
                                <a href="#" onClick={(evento) => { evento.preventDefault(); selecionarLivro(livro); }}>
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
                            <InputText
                                placeholder="deixe vazio para gerar"
                                value={codigoBarras}
                                onChange={(evento) => setCodigoBarras(evento.target.value)}
                            />
                        </label>
                        <label className="field">Localização
                            <InputText
                                placeholder="ex.: Estante A3"
                                value={localizacao}
                                onChange={(evento) => setLocalizacao(evento.target.value)}
                            />
                        </label>
                        <Button
                            type="button"
                            label="Gerar código"
                            severity="secondary"
                            outlined
                            onClick={gerarCodigo}
                        />
                        <Button 
                            type="submit" 
                            label="Adicionar exemplar" 
                        />
                        {mensagemExemplar.texto && <Message severity={mensagemExemplar.tipo === "error" ? "error" : "success"} text={mensagemExemplar.texto} />}
                    </form>
                    <DataTable value={exemplares} emptyMessage="Sem exemplares." dataKey="uid">
                        <Column header="Código" body={(exemplar) => exemplar.codigoBarras} />
                        <Column header="Localização" body={(exemplar) => exemplar.localizacao || "-"} />
                        <Column header="Status" body={(exemplar) => <Badge status={exemplar.status} />} />
                        <Column header="Criado" body={(exemplar) => formatarData(exemplar.criadoEm)} />
                        <Column header="" body={(exemplar) => <Button label="Excluir" size="small" severity="danger" outlined onClick={() => excluirExemplar(exemplar.uid)} />} />
                    </DataTable>
                </section>
            )}
        </AppShell>
    );
}
