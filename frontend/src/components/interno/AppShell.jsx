"use client";

import { useEffect, useState }       from "react";
import { useRouter, usePathname }    from "next/navigation";
import Link                          from "next/link";
import { Button }                    from "primereact/button";
import { Avatar }                    from "primereact/avatar";
import { useAuth }                   from "@bernardo-dias/react-cloudsupport";
import { obterUsuario, possuiPerfil, dashboardDoPerfil } from "@/lib/auth";
import { notificar }                 from "@/lib/notificar";
import { GRUPOS_MENU }               from "./menu";

const CHAVE_TEMA = "theme";

// --- SHELL DA ÁREA INTERNA, COM GUARDA DE AUTENTICAÇÃO E PERFIL ---
export default function AppShell({ titulo, perfis, children }) {
    const auth = useAuth();
    const router = useRouter();
    const pathname = usePathname();
    const [usuario, setUsuario] = useState(null);
    const [pronto, setPronto] = useState(false);
    const [menuAberto, setMenuAberto] = useState(false);

    useEffect(() => {
        const tema = localStorage.getItem(CHAVE_TEMA) || "light";
        document.documentElement.setAttribute("data-theme", tema);

        if (auth.isLoading) {
            return;
        }

        if (!auth.isAuthenticated) {
            auth.signinRedirect();
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
    }, [pathname, auth.isLoading, auth.isAuthenticated]);

    const alternarTema = () => {
        const atual = document.documentElement.getAttribute("data-theme") === "dark" ? "dark" : "light";
        const novo = atual === "dark" ? "light" : "dark";
        document.documentElement.setAttribute("data-theme", novo);
        localStorage.setItem(CHAVE_TEMA, novo);
    };

    const sair = () => {
        auth.signoutRedirect();
    };

    if (!pronto) {
        return null;
    }

    const iniciaisUsuario = (usuario?.nome || "?").trim().charAt(0).toUpperCase();

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
                                    <Link key={item.rota} href={item.rota} className={`nav-link${pathname === item.rota ? " active" : ""}`} onClick={() => setMenuAberto(false)}>
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
                    <Button icon="pi pi-bars" rounded text aria-label="Abrir menu" onClick={() => setMenuAberto((v) => !v)} />
                    <div className="topbar-title">{titulo}</div>
                    <div className="topbar-actions">
                        <Button icon="pi pi-circle-half" rounded text aria-label="Alternar tema" title="Alternar tema claro/escuro" onClick={alternarTema} />
                        <Avatar label={iniciaisUsuario} shape="circle" />
                        <span className="user-nome">{usuario?.nome || ""}</span>
                        <Button label="Sair" size="small" severity="danger" outlined onClick={sair} />
                    </div>
                </header>
                <main className="content">{children}</main>
            </div>
        </div>
    );
}
