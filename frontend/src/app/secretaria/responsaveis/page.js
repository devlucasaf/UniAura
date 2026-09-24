"use client";

import AppShell from "@/components/interno/AppShell";
import CrudEntityPage from "@/components/interno/crud/CrudEntityPage";
import { responsaveisApi } from "@/lib/api/responsaveis";

const CAMPOS = [
    { name: "nome", label: "Nome", origem: "usuario", required: true },
    { name: "email", label: "E-mail", origem: "usuario", type: "email", required: true },
    { name: "cpf", label: "CPF", origem: "usuario" },
    { name: "telefone", label: "Telefone", origem: "usuario" },
    { name: "dataNascimento", label: "Nascimento", origem: "usuario", type: "date" },
    { name: "parentesco", label: "Parentesco", origem: "entidade", type: "select", required: true, options: ["PAI", "MAE", "TUTOR", "OUTRO"], default: "PAI" }
];

const COLUNAS = [
    { header: "Nome", render: (item) => item.usuario?.nome || "-" },
    { header: "E-mail", render: (item) => item.usuario?.email || "-" },
    { header: "Telefone", render: (item) => item.usuario?.telefone || "-" },
    { header: "Parentesco", render: (item) => item.parentesco }
];

export default function SecretariaResponsaveisPage() {
    return (
        <AppShell titulo="Responsáveis" perfis={["SECRETARIA", "COORDENADOR", "ADMIN"]}>
            <CrudEntityPage
                titulo="Responsáveis"
                tituloSingular="Responsável"
                api={responsaveisApi}
                sort="criadoEm,desc"
                colunas={COLUNAS}
                campos={CAMPOS}
                mensagemColunaVazia="Nenhum responsável encontrado."
            />
        </AppShell>
    );
}
