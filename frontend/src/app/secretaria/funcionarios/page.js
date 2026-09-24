"use client";

import AppShell from "@/components/interno/AppShell";
import CrudEntityPage from "@/components/interno/crud/CrudEntityPage";
import { funcionariosApi } from "@/lib/api/funcionarios";
import { formatarData } from "@/lib/formato";

const CARGOS = ["COORDENADOR", "SECRETARIA", "BIBLIOTECARIO", "FINANCEIRO", "ADMIN"];

const CAMPOS = [
    { name: "nome", label: "Nome", origem: "usuario", required: true },
    { name: "email", label: "E-mail", origem: "usuario", type: "email", required: true },
    { name: "cpf", label: "CPF", origem: "usuario" },
    { name: "telefone", label: "Telefone", origem: "usuario" },
    { name: "dataNascimento", label: "Nascimento", origem: "usuario", type: "date" },
    { name: "cargo", label: "Cargo", origem: "entidade", type: "select", required: true, options: CARGOS, default: "SECRETARIA" },
    { name: "dataAdmissao", label: "Data de admissão", origem: "entidade", type: "date", required: true },
    { name: "departamento", label: "Departamento", origem: "entidade" }
];

const COLUNAS = [
    { header: "Nome", render: (item) => item.usuario?.nome || "-" },
    { header: "E-mail", render: (item) => item.usuario?.email || "-" },
    { header: "Cargo", render: (item) => item.cargo },
    { header: "Departamento", render: (item) => item.departamento || "-" },
    { header: "Admissão", render: (item) => formatarData(item.dataAdmissao) }
];

export default function SecretariaFuncionariosPage() {
    return (
        <AppShell titulo="Funcionários" perfis={["SECRETARIA", "COORDENADOR", "ADMIN"]}>
            <CrudEntityPage
                titulo="Funcionários"
                tituloSingular="Funcionário"
                api={funcionariosApi}
                sort="dataAdmissao,desc"
                colunas={COLUNAS}
                campos={CAMPOS}
                filtroSelect={{ name: "cargo", label: "Cargo", options: CARGOS }}
                mensagemColunaVazia="Nenhum funcionário encontrado."
            />
        </AppShell>
    );
}
