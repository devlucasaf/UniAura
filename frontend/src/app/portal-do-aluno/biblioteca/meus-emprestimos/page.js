"use client";

import { useEffect, useState }          from "react";
import { DataTable }                    from "primereact/datatable";
import { Column }                       from "primereact/column";
import { Button }                       from "primereact/button";
import AppShell                         from "@/components/interno/AppShell";
import Badge                            from "@/components/interno/Badge";
import { emprestimoApi, multaApi }      from "@/lib/api/biblioteca";
import { formatarData, formatarMoeda }  from "@/lib/formato";
import { api }                          from "@/lib/api";

// --- PÁGINA DE CONSULTA DE EMPRÉSTIMOS E MULTAS DO ALUNO ---
export default function AlunoMeusEmprestimosPage() {
    const [emprestimos, setEmprestimos] = useState([]);
    const [multas,      setMultas]      = useState([]);

    // --- CARREGAMENTO DE EMPRÉSTIMOS E MULTAS DO USUÁRIO LOGADO ---
    async function carregar() {
        const usuario = await api("/auth/me");
        if (!usuario?.id) {
            return;
        }
        const pageEmp = await emprestimoApi.listarPorUsuario(usuario.id, { size: 100, sort: "dataEmprestimo,desc" });
        setEmprestimos(pageEmp.content || []);
        const listaMultas = await multaApi.pendentesDoUsuario(usuario.id);
        setMultas(listaMultas);
    }

    useEffect(() => { carregar(); }, []);

    // --- RENOVAÇÃO DE EMPRÉSTIMO ---
    async function renovar(id) {
        try {
            await emprestimoApi.renovar(id);
            carregar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Portal do Aluno — Meus empréstimos" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Meus empréstimos</h2>
                <DataTable value={emprestimos} emptyMessage="Nenhum empréstimo." dataKey="id">
                    <Column header="Livro" body={(e) => e.livroTitulo} />
                    <Column header="Empréstimo" body={(e) => formatarData(e.dataEmprestimo)} />
                    <Column header="Devolução prevista" body={(e) => formatarData(e.dataDevolucaoPrevista)} />
                    <Column header="Devolvido em" body={(e) => formatarData(e.dataDevolucaoEfetiva)} />
                    <Column header="Status" body={(e) => <Badge status={e.status} />} />
                    <Column header="Renovações" body={(e) => e.renovacoes} />
                    <Column
                        header=""
                        body={(e) => e.status !== "DEVOLVIDO" && <Button label="Renovar" size="small" onClick={() => renovar(e.id)} />}
                    />
                </DataTable>
            </section>

            <section className="card">
                <h2>Minhas multas pendentes</h2>
                <DataTable value={multas} emptyMessage="Sem multas pendentes." dataKey="id">
                    <Column header="Livro" body={(m) => m.livroTitulo} />
                    <Column header="Dias atraso" body={(m) => m.diasAtraso} />
                    <Column header="Valor" body={(m) => formatarMoeda(m.valor)} />
                    <Column header="Gerada em" body={(m) => formatarData(m.geradaEm)} />
                </DataTable>
            </section>
        </AppShell>
    );
}
