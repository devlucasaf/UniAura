"use client";

import AppShell from "@/components/interno/AppShell";
import Dashboard from "@/components/interno/Dashboard";

export default function ProfessorDashboardPage() {
    return (
        <AppShell titulo="Painel do Professor" perfis={["PROFESSOR"]}>
            <Dashboard titulo="Painel do Professor" />
        </AppShell>
    );
}
