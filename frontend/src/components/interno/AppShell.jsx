"use client";

import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";
import Link from "next/link";
import { estaAutenticado, obterUsuario, possuiPerfil, dashboardDoPerfil, encerrarSessao } from "@/lib/auth";
import { notificar } from "@/lib/notificar";
import { GRUPOS_MENU } from "./menu";

const CHAVE_TEMA = "theme";

// --- SHELL DA ÁREA INTERNA (SIDEBAR + TOPO), COM GUARDA DE AUTENTICAÇÃO E PERFIL ---
export default function AppShell({ titulo, perfis, children }) {
    const router = useRouter();
    const pathname = usePathname();
    const [usuario, setUsuario] = useState(null);
    const [pronto, setPronto] = useState(false);
    const [menuAberto, setMenuAberto] = useState(false);

    useEffect(() => {
        const tema = localStorage.getItem(CHAVE_TEMA) || "light";
        document.documentElement.setAttribute("data-theme", tema);

        if (!estaAutenticado()) {
            router.replace("/login");
            return;
        }

        const usuarioAtual = obterUsuario();

        if (perfis && !possuiPerfil(perfis)) {
            notificar("Você não tem permissão para acessar esta área.", "error");
            router.replace(dashboardDoPerfil(usuarioAtual?.role));
            return;
        }

        setUsuario(usuarioAtual);
        setPronto(true);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [pathname]);

    const alternarTema = () => {
        const atual = document.documentElement.getAttribute("data-theme") === "dark" ? "dark" : "light";
        const novo = atual === "dark" ? "light" : "dark";
        document.documentElement.setAttribute("data-theme", novo);
        localStorage.setItem(CHAVE_TEMA, novo);
    };

    const sair = () => {
        encerrarSessao();
        router.replace("/login");
    };

    if (!pronto) {
        return null;
    }

    return (
        <div className={`app-shell${menuAberto ? " sidebar-open" : ""}`}>
            <aside className="sidebar">
                <div className="sidebar-brand">ERP Acadêmico</div>
                <nav className="sidebar-nav">
                    {GRUPOS_MENU.map((grupo, indice) => {
                        if (grupo.perfis && !possuiPerfil(grupo.perfis)) {
                            return null;
                        }
                        const itens = grupo.itens.filter((item) => possuiPerfil(item.perfis));
                        if (!itens.length) {
                            return null;
                        }
                        return (
                            <div key={`${grupo.secao || "dash"}-${indice}`}>
                                {grupo.secao && <span className="nav-section">{grupo.secao}</span>}
                                {itens.map((item) => (
                                    <Link
                                        key={item.rota}
                                        href={item.rota}
                                        className={`nav-link${pathname === item.rota ? " active" : ""}`}
                                        onClick={() => setMenuAberto(false)}
                                    >
                                        {item.label}
                                    </Link>
                                ))}
                            </div>
                        );
                    })}
                </nav>
            </aside>

            <div className="app-main">
                <header className="topbar">
                    <button className="icon-btn" aria-label="Abrir menu" onClick={() => setMenuAberto((v) => !v)}>☰</button>
                    <div className="topbar-title">{titulo}</div>
                    <div className="topbar-actions">
                        <button className="icon-btn" aria-label="Alternar tema" title="Alternar tema claro/escuro" onClick={alternarTema}>◐</button>
                        <span className="user-nome">{usuario?.nome || ""}</span>
                        <button className="btn btn-sm danger" onClick={sair}>Sair</button>
                    </div>
                </header>
                <main className="content">{children}</main>
            </div>
        </div>
    );
}
