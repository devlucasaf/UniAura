"use client";

import { useEffect, useState } from "react";
import { notificar } from "@/lib/notificar";
import Modal from "./Modal";
import Paginacao from "./Paginacao";

// --- LÊ O VALOR INICIAL DE UM CAMPO A PARTIR DO ITEM (ORIGEM "usuario" OU "entidade") ---
function valorInicial(campo, item) {
    if (!item) {
        return campo.type === "checkbox" ? campo.default ?? false : campo.default ?? "";
    }
    const bruto = campo.origem === "usuario" ? item.usuario?.[campo.name] : item[campo.name];
    if (campo.type === "checkbox") {
        return bruto !== false;
    }
    return bruto ?? "";
}

// --- PÁGINA GENÉRICA DE CRUD (TABELA + FILTRO + MODAL), USADA PELOS CADASTROS DA SECRETARIA ---
export default function CrudEntityPage({
    titulo,
    tituloSingular,
    api,
    sort,
    colunas,
    campos,
    filtroSelect,
    mensagemColunaVazia
}) {
    const [itens, setItens] = useState([]);
    const [page, setPage] = useState(null);
    const [paginaAtual, setPaginaAtual] = useState(0);
    const [busca, setBusca] = useState("");
    const [filtroValor, setFiltroValor] = useState("");
    const [modalAberto, setModalAberto] = useState(false);
    const [editando, setEditando] = useState(null);
    const [valores, setValores] = useState({});
    const [erroModal, setErroModal] = useState("");
    const [salvando, setSalvando] = useState(false);

    async function carregar(pagina = 0, filtro = filtroValor) {
        setPaginaAtual(pagina);
        try {
            const parametros = { page: pagina, size: 10, sort };
            if (filtroSelect) {
                parametros[filtroSelect.name] = filtro;
            }
            const resultado = await api.listar(parametros);
            setItens(resultado.content || []);
            setPage(resultado);
        } catch (erro) {
            notificar(erro.message, "error");
        }
    }

    useEffect(() => {
        carregar(0, "");
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const linhasVisiveis = itens.filter((item) => {
        if (!busca.trim()) {
            return true;
        }
        const termo = busca.trim().toLowerCase();
        return colunas.some((coluna) => String(coluna.render(item) ?? "").toLowerCase().includes(termo));
    });

    function abrirNovo() {
        setEditando(null);
        setErroModal("");
        const iniciais = {};
        campos.forEach((campo) => { iniciais[campo.name] = valorInicial(campo, null); });
        setValores(iniciais);
        setModalAberto(true);
    }

    function abrirEdicao(item) {
        setEditando(item);
        setErroModal("");
        const iniciais = {};
        campos.forEach((campo) => { iniciais[campo.name] = valorInicial(campo, item); });
        setValores(iniciais);
        setModalAberto(true);
    }

    async function excluir(item) {
        if (!confirm(`Excluir ${tituloSingular.toLowerCase()} "${item.usuario?.nome || item.nome}"?`)) {
            return;
        }
        try {
            await api.excluir(item.id);
            notificar(`${tituloSingular} excluído.`, "success");
            carregar(paginaAtual);
        } catch (erro) {
            notificar(erro.message, "error");
        }
    }

    function atualizarValor(nome, valor) {
        setValores((atual) => ({ ...atual, [nome]: valor }));
    }

    async function salvar(evento) {
        evento.preventDefault();
        setErroModal("");
        setSalvando(true);

        const dados = {};
        campos.forEach((campo) => {
            const valor = valores[campo.name];
            if (campo.type === "checkbox") {
                dados[campo.name] = !!valor;
            } else if (campo.type === "number") {
                dados[campo.name] = valor === "" || valor == null ? null : Number(valor);
            } else {
                dados[campo.name] = valor === "" || valor == null ? null : valor;
            }
        });

        try {
            if (editando) {
                await api.atualizar(editando.id, dados);
                notificar(`${tituloSingular} atualizado.`, "success");
            } else {
                await api.criar(dados);
                notificar(`${tituloSingular} criado. Senha temporária enviada por e-mail (ver log do servidor).`, "success");
            }
            setModalAberto(false);
            carregar(editando ? paginaAtual : 0);
        } catch (erro) {
            setErroModal(erro.message);
        } finally {
            setSalvando(false);
        }
    }

    return (
        <section className="card">
            <div className="toolbar toolbar-between">
                <h1>{titulo}</h1>
                <button className="btn" onClick={abrirNovo}>+ Novo {tituloSingular.toLowerCase()}</button>
            </div>

            <form
                className="toolbar"
                onSubmit={(evento) => {
                    evento.preventDefault();
                    if (filtroSelect) {
                        carregar(0, filtroValor);
                    }
                }}
            >
                {filtroSelect && (
                    <label className="field">
                        {filtroSelect.label}
                        <select value={filtroValor} onChange={(e) => setFiltroValor(e.target.value)}>
                            <option value="">Todos</option>
                            {filtroSelect.options.map((opcao) => (
                                <option key={opcao} value={opcao}>{opcao}</option>
                            ))}
                        </select>
                    </label>
                )}
                <label className="field">
                    Filtrar na página
                    <input value={busca} onChange={(e) => setBusca(e.target.value)} placeholder="nome, e-mail..." />
                </label>
                <button type="submit" className="btn">Aplicar</button>
            </form>

            <table>
                <thead>
                    <tr>
                        {colunas.map((coluna) => <th key={coluna.header}>{coluna.header}</th>)}
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {linhasVisiveis.length === 0 && (
                        <tr><td colSpan={colunas.length + 1}>{mensagemColunaVazia}</td></tr>
                    )}
                    {linhasVisiveis.map((item) => (
                        <tr key={item.id}>
                            {colunas.map((coluna) => <td key={coluna.header}>{coluna.render(item)}</td>)}
                            <td className="acoes">
                                <button className="btn secondary btn-sm" onClick={() => abrirEdicao(item)}>Editar</button>
                                <button className="btn danger btn-sm" onClick={() => excluir(item)}>Excluir</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <Paginacao page={page} aoIr={(pagina) => carregar(pagina)} />

            <Modal titulo={`${editando ? "Editar" : "Novo"} ${tituloSingular.toLowerCase()}`} aberto={modalAberto} onFechar={() => setModalAberto(false)}>
                <form className="form-grid" onSubmit={salvar}>
                    {campos.map((campo) => {
                        if (campo.type === "checkbox") {
                            return (
                                <label key={campo.name} className="field field-inline">
                                    <input
                                        type="checkbox"
                                        checked={!!valores[campo.name]}
                                        onChange={(e) => atualizarValor(campo.name, e.target.checked)}
                                    />
                                    {campo.label}
                                </label>
                            );
                        }

                        return (
                            <div key={campo.name} className={`field${campo.full ? " field-full" : ""}`}>
                                {campo.label}
                                {campo.type === "select" && (
                                    <select
                                        value={valores[campo.name] ?? ""}
                                        required={campo.required}
                                        onChange={(e) => atualizarValor(campo.name, e.target.value)}
                                    >
                                        {campo.options.map((opcao) => <option key={opcao} value={opcao}>{opcao}</option>)}
                                    </select>
                                )}
                                {campo.type === "textarea" && (
                                    <textarea
                                        value={valores[campo.name] ?? ""}
                                        onChange={(e) => atualizarValor(campo.name, e.target.value)}
                                    />
                                )}
                                {(!campo.type || ["text", "email", "date", "number"].includes(campo.type)) && (
                                    <input
                                        type={campo.type || "text"}
                                        required={campo.required}
                                        min={campo.min}
                                        value={valores[campo.name] ?? ""}
                                        onChange={(e) => atualizarValor(campo.name, e.target.value)}
                                    />
                                )}
                            </div>
                        );
                    })}

                    {erroModal && <p className="msg-error field-full">{erroModal}</p>}

                    <div className="toolbar field-full toolbar-between">
                        <span className="muted">A senha é gerada automaticamente e enviada por e-mail.</span>
                        <span>
                            <button type="button" className="btn secondary" onClick={() => setModalAberto(false)}>Cancelar</button>
                            <button type="submit" className="btn" disabled={salvando}>Salvar</button>
                        </span>
                    </div>
                </form>
            </Modal>
        </section>
    );
}
