"use client";

import { useState }     from "react";
import { InputText }    from "primereact/inputtext";
import { Button }       from "primereact/button";
import { DataTable }    from "primereact/datatable";
import { Column }       from "primereact/column";
import AppShell         from "@/components/interno/AppShell";
import Badge            from "@/components/interno/Badge";
import { reservaApi }   from "@/lib/api/biblioteca";
import { formatarData } from "@/lib/formato";

// --- COMPONENTE DE GESTÃO DA FILA DE RESERVAS DA BIBLIOTECA ---
export default function BibliotecaReservasPage() {
    const [livroId, setLivroId] = useState("");
    const [fila,    setFila]    = useState(null);

    // --- CARREGA A FILA DE RESERVAS DO LIVRO INFORMADO ---
    async function carregarFila(evento) {
        evento?.preventDefault();
        const lista = await reservaApi.filaDoLivro(livroId.trim());
        setFila(lista);
    }

    // --- CANCELA UMA RESERVA APÓS CONFIRMAÇÃO DO USUÁRIO ---
    async function cancelar(id) {
        if (!confirm("Cancelar reserva?")) {
            return;
        }

        try {
            await reservaApi.cancelar(id);
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
                    <label className="field">ID do livro
                        <InputText
                            required
                            value={livroId}
                            onChange={(e) => setLivroId(e.target.value)}
                        />
                    </label>
                    <Button type="submit" label="Ver fila" />
                </form>
                <DataTable value={fila || []} emptyMessage="Fila vazia." dataKey="id">
                    <Column header="Pos." body={(r) => r.posicaoFila} />
                    <Column header="Usuário" body={(r) => r.usuarioNome} />
                    <Column header="Data" body={(r) => formatarData(r.dataReserva)} />
                    <Column header="Status" body={(r) => <Badge status={r.status} />} />
                    <Column
                        header=""
                        body={(r) => <Button label="Cancelar" size="small" severity="danger" outlined onClick={() => cancelar(r.id)} />}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
