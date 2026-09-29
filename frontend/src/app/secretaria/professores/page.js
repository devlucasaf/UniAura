"use client";

import AppShell             from "@/components/interno/AppShell";
import CrudEntityPage       from "@/components/interno/crud/CrudEntityPage";
import { professoresApi }   from "@/lib/api/professores";
import { formatarData }     from "@/lib/formato";

// --- CAMPOS DO FORMULÁRIO DE PROFESSORES ---
const CAMPOS = [
    { 
        name: "nome", 
        label: "Nome", 
        origem: "usuario", 
        required: true 
    },
    { 
        name: "email", 
        label: "E-mail", 
        origem: "usuario", 
        type: "email", 
        required: true 
    },
    { 
        name: "cpf", 
        label: "CPF", 
        origem: "usuario" 
    },
    { 
        name: "telefone", 
        label: "Telefone", 
        origem: "usuario" 
    },
    { 
        name: "dataNascimento", 
        label: "Nascimento", 
        origem: "usuario", 
        type: "date" 
    },
    { 
        name: "formacao", 
        label: "Formação", 
        origem: "entidade" 
    },
    { 
        name: "areaAtuacao", 
        label: "Área de atuação", 
        origem: "entidade" 
    },
    { 
        name: "cargaHorariaSemanal", 
        label: "Carga horária semanal", 
        origem: "entidade", 
        type: "number", 
        min: 1 
    },
    { 
        name: "dataAdmissao", 
        label: "Data de admissão", 
        origem: "entidade", 
        type: "date", 
        required: true 
    },
    { 
        name: "ativo", 
        label: "Ativo", 
        origem: "entidade", 
        type: "checkbox", 
        default: true 
    }
];

// --- COLUNAS DA TABELA DE PROFESSORES ---
const COLUNAS = [
    { header: "Nome", render: (item) => item.usuario?.nome || "-" },
    { header: "E-mail", render: (item) => item.usuario?.email || "-" },
    { header: "Formação", render: (item) => item.formacao || "-" },
    { header: "Área", render: (item) => item.areaAtuacao || "-" },
    { header: "Admissão", render: (item) => formatarData(item.dataAdmissao) },
    { header: "Ativo", render: (item) => (item.ativo ? "Sim" : "Não") }
];

// --- PÁGINA DE LISTAGEM DE PROFESSORES ---
export default function SecretariaProfessoresPage() {
    return (
        <AppShell titulo="Professores" perfis={["SECRETARIA", "COORDENADOR", "ADMIN"]}>
            <CrudEntityPage
                titulo="Professores"
                tituloSingular="Professor"
                api={professoresApi}
                sort="dataAdmissao,desc"
                colunas={COLUNAS}
                campos={CAMPOS}
                mensagemColunaVazia="Nenhum professor encontrado."
            />
        </AppShell>
    );
}
