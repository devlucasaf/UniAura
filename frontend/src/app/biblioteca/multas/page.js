"use client";

import { useEffect, useState }          from "react";
import { Dropdown }                     from "primereact/dropdown";
import { Button }                       from "primereact/button";
import { DataTable }                    from "primereact/datatable";
import { Column }                       from "primereact/column";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { pesquisarMultas }              from "@/app/remotes/uniaura/biblioteca/pesquisarMultasRemote";
import { pagarMulta }                   from "@/app/remotes/uniaura/biblioteca/pagarMultaRemote";
import { cancelarMulta }                from "@/app/remotes/uniaura/biblioteca/cancelarMultaRemote";
import { formatarData, formatarMoeda }  from "@/lib/formato";

const OPCOES_STATUS = ["PENDENTE", "PAGA", "CANCELADA"];

// --- COMPONENTE DE GESTÃO DE MULTAS DA BIBLIOTECA ---
export default function BibliotecaMultasPage() {
    const [status, setStatus] = useState("PENDENTE");
    const [multas, setMultas] = useState([]);

    // --- LISTA AS MULTAS DE ACORDO COM O STATUS SELECIONADO ---
    async function listar(statusAtual = status) {
        const resultado = await pesquisarMultas({
            status: statusAtual,
            qtd: 50
        });
        setMultas(resultado.lista || []);
    }

    useEffect(() => { listar(); }, []);

    // --- REGISTRA O PAGAMENTO DE UMA MULTA ---
    async function pagar(uid) {
        try {
            await pagarMulta(uid);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    // --- CANCELA UMA MULTA APÓS CONFIRMAÇÃO DO USUÁRIO ---
    async function cancelar(uid) {
        if (!confirm("Cancelar multa?")) {
            return;
        }

        try {
            await cancelarMulta(uid);
            listar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Biblioteca — Multas" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Multas</h2>
                <form className="toolbar" onSubmit={(evento) => { evento.preventDefault(); listar(); }}>
                    <label className="field">Status
                        <Dropdown
                            value={status}
                            onChange={(evento) => setStatus(evento.value)}
                            options={OPCOES_STATUS.map((opcao) => ({
                                    label: opcao,
                                    value: opcao
                                }))}
                            />
                    </label>
                    <Button type="submit" label="Listar" />
                </form>
                <DataTable value={multas} emptyMessage="Nenhuma multa." dataKey="uid">
                    <Column header="Usuário" body={(multa) => multa.usuarioNome} />
                    <Column header="Livro" body={(multa) => multa.livroTitulo} />
                    <Column header="Dias atraso" body={(multa) => multa.diasAtraso} />
                    <Column header="Valor" body={(multa) => formatarMoeda(multa.valor)} />
                    <Column header="Gerada" body={(multa) => formatarData(multa.geradaEm)} />
                    <Column header="Paga" body={(multa) => formatarData(multa.pagaEm)} />
                    <Column header="Status" body={(multa) => <Badge status={multa.status} />} />
                    <Column
                        header=""
                        body={(multa) => multa.status === "PENDENTE" && (
                            <div className="acoes">
                                <Button
                                    label="Baixar pagto."
                                    size="small"
                                    onClick={() => pagar(multa.uid)}
                                />
                                <Button
                                    label="Cancelar"
                                    size="small"
                                    severity="danger"
                                    outlined
                                    onClick={() => cancelar(multa.uid)}
                                />
                            </div>
                        )}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
