"use client";

import { useEffect, useState } from "react";
import AppShell from "@/components/interno/AppShell";
import Badge from "@/components/interno/Badge";
import { reservaApi } from "@/lib/api/biblioteca";
import { formatarData } from "@/lib/formato";
import { obterUsuario } from "@/lib/auth";

export default function AlunoMinhasReservasPage() {
    const [reservas, setReservas] = useState([]);

    async function carregar() {
        const usuario = obterUsuario();
        if (!usuario?.id) {
            return;
        }
        const lista = await reservaApi.doUsuario(usuario.id);
        setReservas(lista);
    }

    useEffect(() => { carregar(); }, []);

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

    return (
        <AppShell titulo="Portal do Aluno — Minhas reservas" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Minhas reservas</h2>
                <table>
                    <thead>
                        <tr><th>Livro</th><th>Data</th><th>Status</th><th>Posição fila</th><th></th></tr>
                    </thead>
                    <tbody>
                        {reservas.length === 0 && <tr><td colSpan={5}>Nenhuma reserva.</td></tr>}
                        {reservas.map((r) => (
                            <tr key={r.id}>
                                <td>{r.livroTitulo}</td>
                                <td>{formatarData(r.dataReserva)}</td>
                                <td><Badge status={r.status} /></td>
                                <td>{r.posicaoFila}</td>
                                <td>{r.status === "AGUARDANDO" && <button className="btn danger btn-sm" onClick={() => cancelar(r.id)}>Cancelar</button>}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </AppShell>
    );
}
