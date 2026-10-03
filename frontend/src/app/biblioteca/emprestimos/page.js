"use client";

import { useState }                     from "react";
import { InputText }                    from "primereact/inputtext";
import { Button }                       from "primereact/button";
import { Message }                      from "primereact/message";
import { DataTable }                    from "primereact/datatable";
import { Column }                       from "primereact/column";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { emprestimoApi, exemplarApi }   from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda }  from "@/lib/formato";

export default function BibliotecaEmprestimosPage() {
    const [codigoBarras,        setCodigoBarras]        = useState("");
    const [usuarioIdNovo,       setUsuarioIdNovo]       = useState("");
    const [mensagem,            setMensagem]            = useState({ texto: "", tipo: "" });
    const [usuarioIdConsulta,   setUsuarioIdConsulta]   = useState("");
    const [emprestimos,         setEmprestimos]         = useState(null);

    async function registrar(evento) {
        evento.preventDefault();
        try {
            const ex = await exemplarApi.buscarPorCodigo(codigoBarras.trim());
            const emp = await emprestimoApi.registrar({ exemplarId: ex.id, usuarioId: usuarioIdNovo.trim() });
            setMensagem({
                texto: `Empréstimo #${emp.id.toString().substring(0, 8)} registrado. Devolução prevista: ${formatarData(emp.dataDevolucaoPrevista)}`,
                tipo: "ok"
            });
            setCodigoBarras("");
            setUsuarioIdNovo("");
        } catch (erro) {
            setMensagem({ texto: erro.message, tipo: "error" });
        }
    }

    async function consultar(evento) {
        evento.preventDefault();
        const page = await emprestimoApi.listarPorUsuario(usuarioIdConsulta.trim(), { size: 50, sort: "dataEmprestimo,desc" });
        setEmprestimos(page.content || []);
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
                            onChange={(e) => setCodigoBarras(e.target.value)}
                        />
                    </label>
                    <label className="field">ID do usuário (aluno/professor)
                        <InputText
                            required
                            value={usuarioIdNovo}
                            onChange={(e) => setUsuarioIdNovo(e.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Registrar" />
                    {mensagem.texto && <Message severity={mensagem.tipo === "error" ? "error" : "success"} text={mensagem.texto} />}
                </form>
            </section>

            <section className="card">
                <h2>Consultar empréstimos por usuário</h2>
                <form className="toolbar" onSubmit={consultar}>
                    <label className="field">ID do usuário
                        <InputText
                            required
                            value={usuarioIdConsulta}
                            onChange={(e) => setUsuarioIdConsulta(e.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Listar" />
                </form>
                <DataTable value={emprestimos || []} emptyMessage="Nenhum empréstimo." dataKey="id">
                    <Column header="Livro" body={(emp) => emp.livroTitulo} />
                    <Column header="Código" body={(emp) => emp.exemplarCodigoBarras} />
                    <Column header="Empréstimo" body={(emp) => formatarData(emp.dataEmprestimo)} />
                    <Column header="Prev. devolução" body={(emp) => formatarData(emp.dataDevolucaoPrevista)} />
                    <Column header="Devolvido em" body={(emp) => formatarData(emp.dataDevolucaoEfetiva)} />
                    <Column header="Status" body={(emp) => <Badge status={emp.status} />} />
                    <Column header="Renovações" body={(emp) => emp.renovacoes} />
                    <Column header="Multa" body={(emp) => formatarMoeda(emp.valorMulta)} />
                </DataTable>
            </section>
        </AppShell>
    );
}
