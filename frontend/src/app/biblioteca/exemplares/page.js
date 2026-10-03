"use client";

import { useState }                 from "react";
import { InputText }                from "primereact/inputtext";
import { Button }                   from "primereact/button";
import { Message }                  from "primereact/message";
import { DataTable }                from "primereact/datatable";
import { Column }                   from "primereact/column";
import AppShell                     from "@/components/interno/AppShell";
import Badge                        from "@/components/interno/Badge";
import { livroApi, exemplarApi }    from "@/lib/api/biblioteca";
import { formatarData }             from "@/lib/formato";

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
            const page = await livroApi.buscar({ titulo: tituloBusca, size: 10 });
            setResultados(page.content || []);
        } catch (erro) {
            setErroBusca(erro.message);
        }
    }

    // --- SELECIONA O LIVRO E CARREGA SEUS EXEMPLARES ---
    async function selecionarLivro(livro) {
        setLivroSelecionado({ id: livro.id, titulo: livro.titulo });
        await listarExemplares(livro.id);
    }

    // --- LISTA OS EXEMPLARES DE UM LIVRO ---
    async function listarExemplares(livroId) {
        const page = await exemplarApi.listarPorLivro(livroId, { size: 50 });
        setExemplares(page.content || []);
    }

    // --- GERA UM CÓDIGO DE BARRAS PARA O NOVO EXEMPLAR ---
    async function gerarCodigo() {
        const r = await exemplarApi.gerarCodigoBarras();
        setCodigoBarras(r.codigoBarras);
    }

    // --- ADICIONA UM NOVO EXEMPLAR AO LIVRO SELECIONADO ---
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
            setMensagemExemplar({ texto: "Exemplar criado.", tipo: "ok" });
            listarExemplares(livroSelecionado.id);
        } catch (erro) {
            setMensagemExemplar({ texto: erro.message, tipo: "error" });
        }
    }

    // --- EXCLUI UM EXEMPLAR APÓS CONFIRMAÇÃO DO USUÁRIO ---
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
                    <label className="field">
                        Título
                        <InputText
                            value={tituloBusca}
                            required
                            onChange={(e) => setTituloBusca(e.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Buscar" />
                </form>
                {erroBusca && <Message severity="error" text={erroBusca} />}
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
                            <InputText
                                placeholder="deixe vazio para gerar"
                                value={codigoBarras}
                                onChange={(e) => setCodigoBarras(e.target.value)}
                            />
                        </label>
                        <label className="field">Localização
                            <InputText
                                placeholder="ex.: Estante A3"
                                value={localizacao}
                                onChange={(e) => setLocalizacao(e.target.value)}
                            />
                        </label>
                        <Button type="button" label="Gerar código" severity="secondary" outlined onClick={gerarCodigo} />
                        <Button type="submit" label="Adicionar exemplar" />
                        {mensagemExemplar.texto && <Message severity={mensagemExemplar.tipo === "error" ? "error" : "success"} text={mensagemExemplar.texto} />}
                    </form>
                    <DataTable value={exemplares} emptyMessage="Sem exemplares." dataKey="id">
                        <Column header="Código" body={(ex) => ex.codigoBarras} />
                        <Column header="Localização" body={(ex) => ex.localizacao || "-"} />
                        <Column header="Status" body={(ex) => <Badge status={ex.status} />} />
                        <Column header="Criado" body={(ex) => formatarData(ex.criadoEm)} />
                        <Column header="" body={(ex) => <Button label="Excluir" size="small" severity="danger" outlined onClick={() => excluirExemplar(ex.id)} />} />
                    </DataTable>
                </section>
            )}
        </AppShell>
    );
}
