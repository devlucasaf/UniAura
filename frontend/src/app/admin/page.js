"use client";

import AppShell     from "@/components/interno/AppShell";
import Dashboard    from "@/components/interno/Dashboard";

// --- PÁGINA DO PERFIL DE ADMINISTRADOR ---
export default function AdminPage() {
    return (
        <AppShell titulo="Painel do Administrador" perfis={["ADMIN"]}>
            <Dashboard titulo="Painel do Administrador" />
        </AppShell>
    );
}
