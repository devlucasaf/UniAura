"use client";

import { useState }                     from "react";
import { InputText }                    from "primereact/inputtext";
import { Button }                       from "primereact/button";
import { Message }                      from "primereact/message";
import { DataTable }                    from "primereact/datatable";
import { Column }                       from "primereact/column";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { emprestimoApi }                from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda }  from "@/lib/formato";

// --- COMPONENTE DE DEVOLUÇÃO E RENOVAÇÃO DE EMPRÉSTIMOS DA BIBLIOTECA ---
export default function BibliotecaDevolucoesPage() {
    const [emprestimoId,    setEmprestimoId]    = useState("");
    const [mensagem,        setMensagem]        = useState({ texto: "", tipo: "" });
    const [detalhe,         setDetalhe]         = useState(null);

    // --- PROCESSA A DEVOLUÇÃO OU RENOVAÇÃO DO EMPRÉSTIMO NA API ---
    async function processar(acao) {
        try {
            const r = acao === "devolver" ? await emprestimoApi.devolver(emprestimoId.trim()) : await emprestimoApi.renovar(emprestimoId.trim());
            setMensagem({ texto: "Operação concluída.", tipo: "ok" });
            setDetalhe(r);
        } catch (erro) {
            setMensagem({ texto: erro.message, tipo: "error" });
        }
    }

    return (
        <AppShell titulo="Biblioteca — Devoluções" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Registrar devolução</h2>
                <form className="toolbar" onSubmit={(e) => { e.preventDefault(); processar("devolver"); }}>
                    <label className="field">ID do empréstimo
                        <InputText
                            required
                            autoFocus
                            value={emprestimoId}
                            onChange={(e) => setEmprestimoId(e.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Devolver" />
                    <Button type="button" label="Renovar" severity="secondary" outlined onClick={() => processar("renovar")} />
                    {mensagem.texto && <Message severity={mensagem.tipo === "error" ? "error" : "success"} text={mensagem.texto} />}
                </form>

                {detalhe && (
                    <DataTable
                        style={{ marginTop: "1rem" }}
                        value={[
                            { campo: "Livro", valor: detalhe.livroTitulo },
                            { campo: "Exemplar", valor: detalhe.exemplarCodigoBarras },
                            { campo: "Usuário", valor: detalhe.usuarioNome },
                            { campo: "Status", valor: <Badge status={detalhe.status} /> },
                            { campo: "Prev. devolução", valor: formatarData(detalhe.dataDevolucaoPrevista) },
                            { campo: "Devolvido em", valor: formatarData(detalhe.dataDevolucaoEfetiva) },
                            { campo: "Dias de atraso", valor: detalhe.diasAtraso },
                            { campo: "Multa", valor: formatarMoeda(detalhe.valorMulta) },
                            { campo: "Renovações", valor: detalhe.renovacoes }
                        ]}
                        showHeaders={false}
                    >
                        <Column field="campo" body={(linha) => <strong>{linha.campo}</strong>} />
                        <Column field="valor" />
                    </DataTable>
                )}
            </section>
        </AppShell>
    );
}
