"use client";

import MenuDashboards from "./MenuDashboards";

// --- PARTE ESQUERDA DO CABEÇALHO DO ALUNO ---
export default function TopoAluno() {
    return (
        <div className="ua-aluno-topo-cabecalho">
            <MenuDashboards />
            <span className="ua-aluno-marca">UniAura</span>
        </div>
    );
}
