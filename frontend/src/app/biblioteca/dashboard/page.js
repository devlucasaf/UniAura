"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function BibliotecaDashboardPage() {
    return (
        <AppShell titulo="Painel da Biblioteca" perfis={["BIBLIOTECARIO"]}>
            <Dashboard titulo="Painel da Biblioteca" />
        </AppShell>
    );
}
