"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function AlunoDashboardPage() {
    return (
        <AppShell titulo="Painel do Aluno" perfis={["ALUNO"]}>
            <Dashboard titulo="Painel do Aluno" />
        </AppShell>
    );
}
