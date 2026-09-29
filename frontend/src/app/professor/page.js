"use client";

import AppShell     from "@/components/interno/AppShell";
import Dashboard    from "@/components/interno/Dashboard";

// --- PÁGINA DO PROFESSOR ---
export default function ProfessorPage() {
    return (
        <AppShell titulo="Painel do Professor" perfis={["PROFESSOR"]}>
            <Dashboard titulo="Painel do Professor" />
        </AppShell>
    );
}
