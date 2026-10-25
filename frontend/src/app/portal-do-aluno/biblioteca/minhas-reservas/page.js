"use client";

import { useEffect, useState }      from "react";
import { DataTable }                from "primereact/datatable";
import { Column }                   from "primereact/column";
import { Button }                   from "primereact/button";
import AppShell                     from "@/components/interno/AppShell";
import Badge                        from "@/components/interno/Badge";
import { pesquisarMinhasReservas }  from "@/app/remotes/uniaura/biblioteca/pesquisarMinhasReservasRemote";
import { cancelarReserva }          from "@/app/remotes/uniaura/biblioteca/cancelarReservaRemote";
import { formatarData }             from "@/lib/formato";

// --- PÁGINA DE CONSULTA DE RESERVAS DO ALUNO ---
export default function AlunoMinhasReservasPage() {
    const [reservas, setReservas] = useState([]);

    // --- CARREGAMENTO DE RESERVAS DO USUÁRIO LOGADO ---
    async function carregar() {
        const resultado = await pesquisarMinhasReservas({ qtd: 100 });
        setReservas(resultado.lista || []);
    }

    useEffect(() => { carregar(); }, []);

    // --- CANCELAMENTO DE RESERVA ---
    async function cancelar(uid) {
        if (!confirm("Cancelar reserva?")) {
            return;
        }

        try {
            await cancelarReserva(uid);
            carregar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    // --- RENDERIZAÇÃO DA PÁGINA ---
    return (
        <AppShell titulo="Portal do Aluno — Minhas reservas" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Minhas reservas</h2>
                <DataTable value={reservas} emptyMessage="Nenhuma reserva." dataKey="uid">
                    <Column header="Livro" body={(reserva) => reserva.livroTitulo} />
                    <Column header="Data" body={(reserva) => formatarData(reserva.dataReserva)} />
                    <Column header="Status" body={(reserva) => <Badge status={reserva.status} />} />
                    <Column header="Posição fila" body={(reserva) => reserva.posicaoFila} />
                    <Column
                        header=""
                        body={(reserva) => reserva.status === "AGUARDANDO" && <Button label="Cancelar" size="small" severity="danger" outlined onClick={() => cancelar(reserva.uid)} />}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
