"use client";

import MenuDashboards from "./MenuDashboards";

// --- PARTE ESQUERDA DO CABEÇALHO DO ALUNO: BOTÃO DE 3 BARRAS (DASHBOARDS) E A SAUDAÇÃO ---
export default function TopoAluno({ usuario }) {
    const primeiroNome = (usuario?.nome || "aluno").split(" ")[0];

    return (
        <div className="ua-aluno-topo-cabecalho">
            <MenuDashboards />
            <span className="ua-aluno-ola">Olá, {primeiroNome}!</span>
        </div>
    );
}
