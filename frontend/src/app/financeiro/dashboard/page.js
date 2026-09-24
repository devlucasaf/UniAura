"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function FinanceiroDashboardPage() {
    return (
        <AppShell titulo="Painel Financeiro" perfis={["FINANCEIRO"]}>
            <Dashboard titulo="Painel Financeiro" />
        </AppShell>
    );
}
