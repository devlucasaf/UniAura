"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

// --- COMPONENTE DE PAINEL DA COORDENAÇÃO ---
export default function CoordenacaoPage() {
    return (
        <AppShell titulo="Painel da Coordenação" perfis={["COORDENADOR"]}>
            <Dashboard titulo="Painel da Coordenação" />
        </AppShell>
    );
}
