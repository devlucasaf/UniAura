"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function CoordenacaoDashboardPage() {
    return (
        <AppShell titulo="Painel da Coordenação" perfis={["COORDENADOR"]}>
            <Dashboard titulo="Painel da Coordenação" />
        </AppShell>
    );
}
