"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function ResponsavelDashboardPage() {
    return (
        <AppShell titulo="Painel do Responsável" perfis={["RESPONSAVEL"]}>
            <Dashboard titulo="Painel do Responsável" />
        </AppShell>
    );
}
