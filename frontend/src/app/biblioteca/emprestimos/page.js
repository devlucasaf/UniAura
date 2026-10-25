"use client";

import { useState }                         from "react";
import { InputText }                        from "primereact/inputtext";
import { Button }                           from "primereact/button";
import { Message }                          from "primereact/message";
import { DataTable }                        from "primereact/datatable";
import { Column }                           from "primereact/column";
import AppShell                             from "@/components/interno/AppShell";
import Badge                                from "@/components/interno/Badge";
import { registrarEmprestimo }              from "@/app/remotes/uniaura/biblioteca/registrarEmprestimoRemote";
import { pesquisarEmprestimosDoUsuario }    from "@/app/remotes/uniaura/biblioteca/pesquisarEmprestimosDoUsuarioRemote";
import { formatarData, formatarMoeda }      from "@/lib/formato";

export default function BibliotecaEmprestimosPage() {
    const [codigoBarras,        setCodigoBarras]        = useState("");
    const [usuarioUidNovo,      setUsuarioUidNovo]      = useState("");
    const [mensagem,            setMensagem]            = useState({ texto: "", tipo: "" });
    const [usuarioUidConsulta,  setUsuarioUidConsulta]  = useState("");
    const [emprestimos,         setEmprestimos]         = useState(null);

    async function registrar(evento) {
        evento.preventDefault();
        try {
            const emprestimo = await registrarEmprestimo({
                codigoBarras: codigoBarras.trim(),
                usuarioUid: usuarioUidNovo.trim()
            });
            setMensagem({
                texto: `Empréstimo ${emprestimo.uid} registrado. Devolução prevista: ${formatarData(emprestimo.dataDevolucaoPrevista)}`,
                tipo: "ok"
            });
            setCodigoBarras("");
            setUsuarioUidNovo("");
        } catch (erro) {
            setMensagem({
                texto: erro.message,
                tipo: "error"
            });
        }
    }

    async function consultar(evento) {
        evento.preventDefault();
        const resultado = await pesquisarEmprestimosDoUsuario(usuarioUidConsulta.trim(), { qtd: 50, ordem: "-dataEmprestimo" });
        setEmprestimos(resultado.lista || []);
    }

    return (
        <AppShell titulo="Biblioteca — Empréstimos" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Novo empréstimo</h2>
                <form className="toolbar" onSubmit={registrar}>
                    <label className="field">Código de barras do exemplar
                        <InputText
                            required
                            autoFocus
                            value={codigoBarras}
                            onChange={(evento) => setCodigoBarras(evento.target.value)}
                        />
                    </label>
                    <label className="field">UID do usuário (aluno/professor)
                        <InputText
                            required
                            value={usuarioUidNovo}
                            onChange={(evento) => setUsuarioUidNovo(evento.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Registrar" />
                    {mensagem.texto && <Message severity={mensagem.tipo === "error" ? "error" : "success"} text={mensagem.texto} />}
                </form>
            </section>

            <section className="card">
                <h2>Consultar empréstimos por usuário</h2>
                <form className="toolbar" onSubmit={consultar}>
                    <label className="field">UID do usuário
                        <InputText
                            required
                            value={usuarioUidConsulta}
                            onChange={(evento) => setUsuarioUidConsulta(evento.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Listar" />
                </form>
                <DataTable value={emprestimos || []} emptyMessage="Nenhum empréstimo." dataKey="uid">
                    <Column header="Livro" body={(emprestimo) => emprestimo.livroTitulo} />
                    <Column header="Código" body={(emprestimo) => emprestimo.exemplarCodigoBarras} />
                    <Column header="Empréstimo" body={(emprestimo) => formatarData(emprestimo.dataEmprestimo)} />
                    <Column header="Prev. devolução" body={(emprestimo) => formatarData(emprestimo.dataDevolucaoPrevista)} />
                    <Column header="Devolvido em" body={(emprestimo) => formatarData(emprestimo.dataDevolucaoEfetiva)} />
                    <Column header="Status" body={(emprestimo) => <Badge status={emprestimo.status} />} />
                    <Column header="Renovações" body={(emprestimo) => emprestimo.renovacoes} />
                    <Column header="Multa" body={(emprestimo) => formatarMoeda(emprestimo.valorMulta)} />
                </DataTable>
            </section>
        </AppShell>
    );
}
