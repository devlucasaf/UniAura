"use client";

import { useEffect, useState }          from "react";
import { Dropdown }                     from "primereact/dropdown";
import { Button }                       from "primereact/button";
import { DataTable }                    from "primereact/datatable";
import { Column }                       from "primereact/column";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { multaApi }                     from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda }  from "@/lib/formato";

const OPCOES_STATUS = ["PENDENTE", "PAGA", "CANCELADA"];

// --- COMPONENTE DE GESTÃO DE MULTAS DA BIBLIOTECA ---
export default function BibliotecaMultasPage() {
    const [status, setStatus] = useState("PENDENTE");
    const [multas, setMultas] = useState([]);

    // --- LISTA AS MULTAS DE ACORDO COM O STATUS SELECIONADO ---
    async function listar(statusAtual = status) {
        const page = await multaApi.listar({ status: statusAtual, size: 50 });
        setMultas(page.content || []);
    }

    useEffect(() => { listar(); }, []);

    // --- REGISTRA O PAGAMENTO DE UMA MULTA ---
    async function pagar(id) {
        try {
            await multaApi.pagar(id);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    // --- CANCELA UMA MULTA APÓS CONFIRMAÇÃO DO USUÁRIO ---
    async function cancelar(id) {
        if (!confirm("Cancelar multa?")) {
            return;
        }
        try {
            await multaApi.cancelar(id);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Biblioteca — Multas" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Multas</h2>
                <form className="toolbar" onSubmit={(e) => { e.preventDefault(); listar(); }}>
                    <label className="field">Status
                        <Dropdown value={status} onChange={(e) => setStatus(e.value)} options={OPCOES_STATUS.map((o) => ({ label: o, value: o }))} />
                    </label>
                    <Button type="submit" label="Listar" />
                </form>
                <DataTable value={multas} emptyMessage="Nenhuma multa." dataKey="id">
                    <Column header="Usuário" body={(m) => m.usuarioNome} />
                    <Column header="Livro" body={(m) => m.livroTitulo} />
                    <Column header="Dias atraso" body={(m) => m.diasAtraso} />
                    <Column header="Valor" body={(m) => formatarMoeda(m.valor)} />
                    <Column header="Gerada" body={(m) => formatarData(m.geradaEm)} />
                    <Column header="Paga" body={(m) => formatarData(m.pagaEm)} />
                    <Column header="Status" body={(m) => <Badge status={m.status} />} />
                    <Column
                        header=""
                        body={(m) => m.status === "PENDENTE" && (
                            <div className="acoes">
                                <Button label="Baixar pagto." size="small" onClick={() => pagar(m.id)} />
                                <Button label="Cancelar" size="small" severity="danger" outlined onClick={() => cancelar(m.id)} />
                            </div>
                        )}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
