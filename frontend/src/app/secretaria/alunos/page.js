"use client";

import AppShell from "@/components/interno/AppShell";
import CrudEntityPage from "@/components/interno/crud/CrudEntityPage";
import { alunosApi } from "@/lib/api/alunos";
import { formatarData } from "@/lib/formato";

const CAMPOS = [
    { name: "nome", label: "Nome", origem: "usuario", required: true },
    { name: "email", label: "E-mail", origem: "usuario", type: "email", required: true },
    { name: "cpf", label: "CPF", origem: "usuario" },
    { name: "telefone", label: "Telefone", origem: "usuario" },
    { name: "dataNascimento", label: "Nascimento", origem: "usuario", type: "date" },
    { name: "matriculaRA", label: "Matrícula (RA)", origem: "entidade", required: true },
    { name: "dataIngresso", label: "Data de ingresso", origem: "entidade", type: "date", required: true },
    { name: "status", label: "Status", origem: "entidade", type: "select", required: true, options: ["ATIVO", "TRANCADO", "FORMADO", "EVADIDO"], default: "ATIVO" },
    { name: "observacoes", label: "Observações", origem: "entidade", type: "textarea", full: true }
];

const COLUNAS = [
    { header: "Nome", render: (item) => item.usuario?.nome || "-" },
    { header: "E-mail", render: (item) => item.usuario?.email || "-" },
    { header: "RA", render: (item) => item.matriculaRA },
    { header: "Status", render: (item) => item.status },
    { header: "Ingresso", render: (item) => formatarData(item.dataIngresso) }
];

export default function SecretariaAlunosPage() {
    return (
        <AppShell titulo="Alunos" perfis={["SECRETARIA", "COORDENADOR", "ADMIN"]}>
            <CrudEntityPage
                titulo="Alunos"
                tituloSingular="Aluno"
                api={alunosApi}
                sort="matriculaRA,asc"
                colunas={COLUNAS}
                campos={CAMPOS}
                filtroSelect={{ name: "status", label: "Status", options: ["ATIVO", "TRANCADO", "FORMADO", "EVADIDO"] }}
                mensagemColunaVazia="Nenhum aluno encontrado."
            />
        </AppShell>
    );
}
