"use client";

import { useEffect, useState }  from "react";
import { DataTable }            from "primereact/datatable";
import { Column }               from "primereact/column";
import { InputText }            from "primereact/inputtext";
import { InputNumber }          from "primereact/inputnumber";
import { Dropdown }             from "primereact/dropdown";
import { Checkbox }             from "primereact/checkbox";
import { Button }               from "primereact/button";
import { Box }                  from "@bernardo-dias/react-cloudsupport/prime";
import { notificar }            from "@/lib/notificar";
import Modal                    from "./Modal";
import Paginacao                from "./Paginacao";

// --- LÊ O VALOR INICIAL DE UM CAMPO A PARTIR DO ITEM ---
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

// --- PÁGINA GENÉRICA DE CRUD, USADA PELOS CADASTROS DA SECRETARIA ---
export default function CrudEntityPage({titulo, tituloSingular, api, sort,
        colunas, campos, filtroSelect, mensagemColunaVazia}) {
    const [itens,       setItens]       = useState([]);
    const [page,        setPage]        = useState(null);
    const [paginaAtual, setPaginaAtual] = useState(0);
    const [busca,       setBusca]       = useState("");
    const [filtroValor, setFiltroValor] = useState("");
    const [modalAberto, setModalAberto] = useState(false);
    const [editando,    setEditando]    = useState(null);
    const [valores,     setValores]     = useState({});
    const [erroModal,   setErroModal]   = useState("");
    const [salvando,    setSalvando]    = useState(false);

    // --- CARREGA OS ITENS DA PÁGINA ---
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
    }, []);

    // --- FILTRA OS ITENS VISÍVEIS NA PÁGINA ---
    const linhasVisiveis = itens.filter((item) => {
        if (!busca.trim()) {
            return true;
        }
        const termo = busca.trim().toLowerCase();
        return colunas.some((coluna) => String(coluna.render(item) ?? "").toLowerCase().includes(termo));
    });

    // --- ABRE O MODAL PARA CRIAR UM NOVO ITEM ---
    function abrirNovo() {
        setEditando(null);
        setErroModal("");
        const iniciais = {};
        campos.forEach((campo) => { iniciais[campo.name] = valorInicial(campo, null); });
        setValores(iniciais);
        setModalAberto(true);
    }

    // --- ABRE O MODAL PARA EDITAR UM ITEM EXISTENTE ---
    function abrirEdicao(item) {
        setEditando(item);
        setErroModal("");
        const iniciais = {};
        campos.forEach((campo) => { iniciais[campo.name] = valorInicial(campo, item); });
        setValores(iniciais);
        setModalAberto(true);
    }

    // --- EXCLUI UM ITEM ---
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

    // --- ATUALIZA O VALOR DE UM CAMPO NO FORMULÁRIO ---
    function atualizarValor(nome, valor) {
        setValores((atual) => ({ ...atual, [nome]: valor }));
    }

    // --- SALVA O ITEM ---
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
                notificar(`${tituloSingular} criado.`, "success");
            }
            setModalAberto(false);
            carregar(editando ? paginaAtual : 0);
        } catch (erro) {
            setErroModal(erro.message);
        } finally {
            setSalvando(false);
        }
    }

    // --- RENDERIZA A PÁGINA ---
    return (
        <section className="card">
            <div className="toolbar toolbar-between">
                <h1>{titulo}</h1>
                <Button label={`+ Novo ${tituloSingular.toLowerCase()}`} onClick={abrirNovo} />
            </div>

            <Box>
                <form className="toolbar" onSubmit={(evento) => {
                        evento.preventDefault();
                        if (filtroSelect) {
                            carregar(0, filtroValor);
                        }
                    }}
                >
                    {filtroSelect && (
                        <label className="field">
                            {filtroSelect.label}
                            <Dropdown
                                value={filtroValor}
                                onChange={(e) => setFiltroValor(e.value)}
                                options={[{ label: "Todos", value: "" }, ...filtroSelect.options.map((o) => ({ label: o, value: o }))]}
                            />
                        </label>
                    )}
                    <label className="field">
                        Filtrar na página
                        <InputText
                            value={busca}
                            onChange={(e) => setBusca(e.target.value)}
                            placeholder="nome, e-mail..."
                        />
                    </label>
                    <Button type="submit" label="Aplicar" outlined />
                </form>
            </Box>

            <DataTable value={linhasVisiveis} emptyMessage={mensagemColunaVazia} dataKey="id">
                {colunas.map((coluna) => (
                    <Column key={coluna.header} header={coluna.header} body={(item) => coluna.render(item)} />
                ))}
                <Column
                    header=""
                    body={(item) => (
                        <div className="acoes">
                            <Button label="Editar" size="small" severity="secondary" outlined onClick={() => abrirEdicao(item)} />
                            <Button label="Excluir" size="small" severity="danger" outlined onClick={() => excluir(item)} />
                        </div>
                    )}
                />
            </DataTable>

            {/* --- PAGINAÇÃO --- */}
            <Paginacao page={page} aoIr={(pagina) => carregar(pagina)} />

            {/* --- MODAL DE EDIÇÃO/CRIAÇÃO DE ITEM ---  */}
            <Modal titulo={`${editando ? "Editar" : "Novo"} ${tituloSingular.toLowerCase()}`} aberto={modalAberto} onFechar={() => setModalAberto(false)}>
                <form className="form-grid" onSubmit={salvar}>
                    {campos.map((campo) => {
                        if (campo.type === "checkbox") {
                            return (
                                <label key={campo.name} className="field field-inline">
                                    <Checkbox
                                        checked={!!valores[campo.name]}
                                        onChange={(e) => atualizarValor(campo.name, e.checked)}
                                    />
                                    {campo.label}
                                </label>
                            );
                        }

                        return (
                            <div key={campo.name} className={`field${campo.full ? " field-full" : ""}`}>
                                {campo.label}
                                {campo.type === "select" && (
                                    <Dropdown
                                        value={valores[campo.name] ?? ""}
                                        required={campo.required}
                                        onChange={(e) => atualizarValor(campo.name, e.value)}
                                        options={campo.options.map((o) => ({ label: o, value: o }))}
                                    />
                                )}
                                {campo.type === "textarea" && (
                                    <textarea value={valores[campo.name] ?? ""} onChange={(e) => atualizarValor(campo.name, e.target.value)}/>
                                )}
                                {campo.type === "number" && (
                                    <InputNumber
                                        value={valores[campo.name] === "" || valores[campo.name] == null ? null : Number(valores[campo.name])}
                                        min={campo.min}
                                        onValueChange={(e) => atualizarValor(campo.name, e.value)}
                                    />
                                )}
                                {(!campo.type || ["text", "email", "date"].includes(campo.type)) && (
                                    <InputText
                                        type={campo.type || "text"}
                                        required={campo.required}
                                        value={valores[campo.name] ?? ""}
                                        onChange={(e) => atualizarValor(campo.name, e.target.value)}
                                    />
                                )}
                            </div>
                        );
                    })}

                    {erroModal && <p className="msg-error field-full">{erroModal}</p>}

                    <div className="toolbar field-full toolbar-between">
                        <span />
                        <span>
                            <Button type="button" label="Cancelar" severity="secondary" outlined onClick={() => setModalAberto(false)} />
                            <Button type="submit" label="Salvar" loading={salvando} />
                        </span>
                    </div>
                </form>
            </Modal>
        </section>
    );
}
