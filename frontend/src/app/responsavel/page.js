"use client";

import AppShell     from "@/components/interno/AppShell";
import Dashboard    from "@/components/interno/Dashboard";

// --- PÁGINA DE DASHBOARD DO RESPONSÁVEL ---
export default function ResponsavelPage() {
    return (
        <AppShell titulo="Painel do Responsável" perfis={["RESPONSAVEL"]}>
            <Dashboard titulo="Painel do Responsável" />
        </AppShell>
    );
}
