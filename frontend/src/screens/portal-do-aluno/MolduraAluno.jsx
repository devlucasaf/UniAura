"use client";

import AppShell         from "@/components/interno/AppShell";
import TopoAluno        from "./TopoAluno";
import MenuPerfilAluno  from "./MenuPerfilAluno";

// --- MOLDURA PADRÃO DAS TELAS DO ALUNO ---
export default function MolduraAluno({ titulo = "Portal do Aluno", children }) {
    return (
        <AppShell
            titulo={titulo}
            perfis={["ALUNO"]}
            semMenuLateral
            topoEsquerda={(usuario) => <TopoAluno usuario={usuario} />}
            topoDireita={(usuario, sair) => <MenuPerfilAluno usuario={usuario} sair={sair} />}
        >
            {children}
        </AppShell>
    );
}
