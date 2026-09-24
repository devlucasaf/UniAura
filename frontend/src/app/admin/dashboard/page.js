"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function AdminDashboardPage() {
    return (
        <AppShell titulo="Painel do Administrador" perfis={["ADMIN"]}>
            <Dashboard titulo="Painel do Administrador" />
        </AppShell>
    );
}
