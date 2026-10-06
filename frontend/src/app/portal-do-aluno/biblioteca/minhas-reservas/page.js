"use client";

import { useEffect, useState }  from "react";
import { DataTable }            from "primereact/datatable";
import { Column }               from "primereact/column";
import { Button }               from "primereact/button";
import AppShell                 from "@/components/interno/AppShell";
import Badge                    from "@/components/interno/Badge";
import { reservaApi }           from "@/lib/api/biblioteca";
import { formatarData }         from "@/lib/formato";
import { api }                  from "@/lib/api";

// --- PÁGINA DE CONSULTA DE RESERVAS DO ALUNO ---
export default function AlunoMinhasReservasPage() {
    const [reservas, setReservas] = useState([]);

    // --- CARREGAMENTO DE RESERVAS DO USUÁRIO LOGADO ---
    async function carregar() {
        const usuario = await api("/auth/me");
        if (!usuario?.id) {
            return;
        }
        const lista = await reservaApi.doUsuario(usuario.id);
        setReservas(lista);
    }

    useEffect(() => { carregar(); }, []);

    // --- CANCELAMENTO DE RESERVA ---
    async function cancelar(id) {
        if (!confirm("Cancelar reserva?")) {
            return;
        }
        try {
            await reservaApi.cancelar(id);
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
                <DataTable value={reservas} emptyMessage="Nenhuma reserva." dataKey="id">
                    <Column header="Livro" body={(r) => r.livroTitulo} />
                    <Column header="Data" body={(r) => formatarData(r.dataReserva)} />
                    <Column header="Status" body={(r) => <Badge status={r.status} />} />
                    <Column header="Posição fila" body={(r) => r.posicaoFila} />
                    <Column
                        header=""
                        body={(r) => r.status === "AGUARDANDO" && <Button label="Cancelar" size="small" severity="danger" outlined onClick={() => cancelar(r.id)} />}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
