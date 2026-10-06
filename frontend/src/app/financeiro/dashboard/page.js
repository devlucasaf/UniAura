"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

// --- COMPONENTE DE PAINEL FINANCEIRO ---
export default function FinanceiroPage() {
    return (
        <AppShell titulo="Painel Financeiro" perfis={["FINANCEIRO"]}>
            <Dashboard titulo="Painel Financeiro" />
        </AppShell>
    );
}
