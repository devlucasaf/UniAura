"use client";

import { useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { reservaApi } from "@/lib/api/biblioteca";
import { formatarData } from "@/lib/formato";

export default function BibliotecaReservasPage() {
    const [livroId, setLivroId] = useState("");
    const [fila, setFila] = useState(null);

    async function carregarFila(evento) {
        evento?.preventDefault();
        const lista = await reservaApi.filaDoLivro(livroId.trim());
        setFila(lista);
    }

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
                        <input required value={livroId} onChange={(e) => setLivroId(e.target.value)} />
                    </label>
                    <button type="submit" className="btn">Ver fila</button>
                </form>
                <table>
                    <thead>
                        <tr><th>Pos.</th><th>Usuário</th><th>Data</th><th>Status</th><th></th></tr>
                    </thead>
                    <tbody>
                        {fila && fila.length === 0 && <tr><td colSpan={5}>Fila vazia.</td></tr>}
                        {fila?.map((r) => (
                            <tr key={r.id}>
                                <td>{r.posicaoFila}</td>
                                <td>{r.usuarioNome}</td>
                                <td>{formatarData(r.dataReserva)}</td>
                                <td><Badge status={r.status} /></td>
                                <td><button className="btn danger btn-sm" onClick={() => cancelar(r.id)}>Cancelar</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </AppShell>
    );
}
