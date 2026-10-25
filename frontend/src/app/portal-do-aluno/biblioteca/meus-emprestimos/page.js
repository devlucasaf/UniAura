"use client";

import { useEffect, useState }              from "react";
import { DataTable }                        from "primereact/datatable";
import { Column }                           from "primereact/column";
import { Button }                           from "primereact/button";
import AppShell                             from "@/components/interno/AppShell";
import Badge                                from "@/components/interno/Badge";
import { pesquisarMeusEmprestimos }         from "@/app/remotes/uniaura/biblioteca/pesquisarMeusEmprestimosRemote";
import { pesquisarMinhasMultasPendentes }   from "@/app/remotes/uniaura/biblioteca/pesquisarMinhasMultasPendentesRemote";
import { renovarEmprestimo }                from "@/app/remotes/uniaura/biblioteca/renovarEmprestimoRemote";
import { formatarData, formatarMoeda }      from "@/lib/formato";

// --- PÁGINA DE CONSULTA DE EMPRÉSTIMOS E MULTAS DO ALUNO ---
export default function AlunoMeusEmprestimosPage() {
    const [emprestimos, setEmprestimos] = useState([]);
    const [multas,      setMultas]      = useState([]);

    // --- CARREGAMENTO DE EMPRÉSTIMOS E MULTAS DO USUÁRIO LOGADO ---
    async function carregar() {
        const resultadoDeEmprestimos = await pesquisarMeusEmprestimos({ qtd: 100, ordem: "-dataEmprestimo" });
        setEmprestimos(resultadoDeEmprestimos.lista || []);
        const resultadoDeMultas = await pesquisarMinhasMultasPendentes();
        setMultas(resultadoDeMultas.lista || []);
    }

    useEffect(() => { carregar(); }, []);

    // --- RENOVAÇÃO DE EMPRÉSTIMO ---
    async function renovar(uid) {
        try {
            await renovarEmprestimo(uid);
            carregar();
        } catch (erro) {
            alert(erro.message);
        }
    }

    return (
        <AppShell titulo="Portal do Aluno — Meus empréstimos" perfis={["ALUNO"]}>
            <section className="card">
                <h2>Meus empréstimos</h2>
                <DataTable value={emprestimos} emptyMessage="Nenhum empréstimo." dataKey="uid">
                    <Column header="Livro" body={(evento) => evento.livroTitulo} />
                    <Column header="Empréstimo" body={(evento) => formatarData(evento.dataEmprestimo)} />
                    <Column header="Devolução prevista" body={(evento) => formatarData(evento.dataDevolucaoPrevista)} />
                    <Column header="Devolvido em" body={(evento) => formatarData(evento.dataDevolucaoEfetiva)} />
                    <Column header="Status" body={(evento) => <Badge status={evento.status} />} />
                    <Column header="Renovações" body={(evento) => evento.renovacoes} />
                    <Column
                        header=""
                        body={(evento) => evento.status !== "DEVOLVIDO" && <Button label="Renovar" size="small" onClick={() => renovar(evento.uid)} />}
                    />
                </DataTable>
            </section>

            <section className="card">
                <h2>Minhas multas pendentes</h2>
                <DataTable value={multas} emptyMessage="Sem multas pendentes." dataKey="uid">
                    <Column header="Livro" body={(multa) => multa.livroTitulo} />
                    <Column header="Dias atraso" body={(multa) => multa.diasAtraso} />
                    <Column header="Valor" body={(multa) => formatarMoeda(multa.valor)} />
                    <Column header="Gerada em" body={(multa) => formatarData(multa.geradaEm)} />
                </DataTable>
            </section>
        </AppShell>
    );
}
