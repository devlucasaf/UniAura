"use client";

import { useEffect }        from "react";
import AppShell             from "@/components/interno/AppShell";
import MenuAcessibilidade   from "@/components/interno/MenuAcessibilidade";
import TopoAluno            from "./TopoAluno";
import MenuPerfilAluno      from "./MenuPerfilAluno";
import MenuNotificacoes     from "./MenuNotificacoes";
import RodapeAluno          from "./RodapeAluno";

// --- ATRIBUTOS DO <html> QUE SÓ EXISTEM DENTRO DO PORTAL DO ALUNO ---
const ATRIBUTOS_DO_PORTAL = [
    "data-area", 
    "data-contraste", 
    "data-daltonismo", 
    "data-cursor-grande", 
    "data-sem-animacao"
];

// --- MOLDURA PADRÃO DAS TELAS DO ALUNO ---
export default function MolduraAluno({ titulo = "Portal do Aluno", children }) {
    useEffect(() => {
        document.documentElement.setAttribute("data-area", "aluno");
        return () => ATRIBUTOS_DO_PORTAL.forEach((atributo) => document.documentElement.removeAttribute(atributo));
    }, []);

    return (
        <AppShell
            titulo={titulo}
            perfis={["ALUNO"]}
            semMenuLateral
            topoEsquerda={() => <TopoAluno />}
            antesDoTema={<MenuAcessibilidade />}
            rodape={<RodapeAluno />}
            topoDireita={(usuario, sair) => (
                <>
                    <MenuNotificacoes />
                    <MenuPerfilAluno usuario={usuario} sair={sair} />
                </>
            )}
        >
            {children}
        </AppShell>
    );
}
