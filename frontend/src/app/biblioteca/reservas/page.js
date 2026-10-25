"use client";

import { useState }                         from "react";
import { InputText }                        from "primereact/inputtext";
import { Button }                           from "primereact/button";
import { DataTable }                        from "primereact/datatable";
import { Column }                           from "primereact/column";
import AppShell                             from "@/components/interno/AppShell";
import Badge                                from "@/components/interno/Badge";
import { pesquisarFilaDeReservasDoLivro }   from "@/app/remotes/uniaura/biblioteca/pesquisarFilaDeReservasDoLivroRemote";
import { cancelarReserva }                  from "@/app/remotes/uniaura/biblioteca/cancelarReservaRemote";
import { formatarData }                     from "@/lib/formato";

// --- COMPONENTE DE GESTÃO DA FILA DE RESERVAS DA BIBLIOTECA ---
export default function BibliotecaReservasPage() {
    const [livroUid,    setLivroUid]    = useState("");
    const [fila,        setFila]        = useState(null);

    // --- CARREGA A FILA DE RESERVAS DO LIVRO INFORMADO ---
    async function carregarFila(evento) {
        evento?.preventDefault();
        const resultado = await pesquisarFilaDeReservasDoLivro(livroUid.trim());
        setFila(resultado.lista);
    }

    // --- CANCELA UMA RESERVA APÓS CONFIRMAÇÃO DO USUÁRIO ---
    async function cancelar(uid) {
        if (!confirm("Cancelar reserva?")) {
            return;
        }

        try {
            await cancelarReserva(uid);
            carregarFila();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Biblioteca — Reservas" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Fila de reservas de um livro</h2>
                <form className="toolbar" onSubmit={carregarFila}>
                    <label className="field">UID do livro
                        <InputText
                            required
                            value={livroUid}
                            onChange={(evento) => setLivroUid(evento.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Ver fila" />
                </form>
                <DataTable value={fila || []} emptyMessage="Fila vazia." dataKey="uid">
                    <Column header="Pos." body={(reserva) => reserva.posicaoFila} />
                    <Column header="Usuário" body={(reserva) => reserva.usuarioNome} />
                    <Column header="Data" body={(reserva) => formatarData(reserva.dataReserva)} />
                    <Column header="Status" body={(reserva) => <Badge status={reserva.status} />} />
                    <Column
                        header=""
                        body={(reserva) => <Button label="Cancelar" size="small" severity="danger" outlined onClick={() => cancelar(reserva.uid)} />}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
