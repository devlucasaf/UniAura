"use client";

import { useEffect, useState }          from "react";
import { pesquisarDisciplinasDoAluno }  from "@/app/remotes/uniaura/academico/pesquisarDisciplinasDoAlunoRemote";

// --- UMA ÚNICA CONSULTA POR CARREGAMENTO DA PÁGINA, COMPARTILHADA PELOS PAINÉIS DA HOME ---
let consultaEmAndamento = null;

function consultarCursoDoAluno() {
    if (!consultaEmAndamento) {
        consultaEmAndamento = pesquisarDisciplinasDoAluno().catch((erro) => {
            consultaEmAndamento = null;
            throw erro;
        });
    }

    return consultaEmAndamento;
}

// --- CURSO E DISCIPLINAS DO SEMESTRE ATUAL DO ALUNO LOGADO, CONFORME O CURSO EM QUE ELE ESTÁ MATRICULADO ---
export default function useDisciplinasDoSemestre() {
    const [estado, setEstado] = useState({ carregando: true, curso: null, disciplinas: [] });

    useEffect(() => {
        consultarCursoDoAluno()
            .then((dados) => {
                const matriz = dados.matriz ?? [];
                const posicao = Math.min(dados.semestreAtual ?? 1, matriz.length) - 1;

                setEstado({
                    carregando: false,
                    curso: dados.titulo,
                    disciplinas: matriz[posicao]?.disciplinas ?? []
                });
            })
            .catch(() => setEstado({ carregando: false, curso: null, disciplinas: [] }));
    }, []);

    return estado;
}
