"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

// --- PÁGINA DE DASHBOARD DA SECRETARIA ---
export default function SecretariaDashboardPage() {
    return (
        <AppShell titulo="Painel da Secretaria" perfis={["SECRETARIA"]}>
            <Dashboard titulo="Painel da Secretaria" />
        </AppShell>
    );
}
