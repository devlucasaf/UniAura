"use client";

import { useEffect, useState }                              from "react";
import { useRouter, usePathname }                           from "next/navigation";
import Link                                                 from "next/link";
import { Button }                                           from "primereact/button";
import { Avatar }                                           from "primereact/avatar";
import { obterUsuario, possuiPerfil, dashboardDoPerfil, loginDoPerfil, estaAutenticado, encerrarSessao } from "@/lib/auth";
import { alternarTemaComOnda }                              from "@/lib/tema";
import { notificar }                                        from "@/lib/notificar";
import { GRUPOS_MENU }                                      from "./menu";

const CHAVE_TEMA = "theme";

// --- SHELL DA ÁREA INTERNA, COM GUARDA DE AUTENTICAÇÃO E PERFIL ---
// semMenuLateral: esconde a barra lateral "ERP Acadêmico" (usado pelo portal do aluno).
// topoEsquerda: função (usuario) => JSX exibida no cabeçalho no lugar do título.
// topoDireita: função (usuario, sair) => JSX que substitui o avatar, o nome e o botão "Sair".
export default function AppShell({ titulo, perfis, semMenuLateral = false, topoEsquerda, topoDireita, children }) {
    const router = useRouter();
    const pathname = usePathname();
    const [usuario,     setUsuario]     = useState(null);
    const [pronto,      setPronto]      = useState(false);
    const [menuAberto,  setMenuAberto]  = useState(false);
    const [temaEscuro,  setTemaEscuro]  = useState(false);

    useEffect(() => {
        const tema = localStorage.getItem(CHAVE_TEMA) || "light";
        document.documentElement.setAttribute("data-theme", tema);
        setTemaEscuro(tema === "dark");

        if (!estaAutenticado()) {
            router.replace(loginDoPerfil(perfis?.[0]));
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
    }, [pathname]);

    // --- ALTERNA O TEMA COM A MESMA ONDA CIRCULAR DO SITE (lib/tema.js) ---
    const alternarTema = (evento) => {
        alternarTemaComOnda(evento.currentTarget, temaEscuro ? "dark" : "light", (novo) => setTemaEscuro(novo === "dark"));
    };

    // --- ENCERRA A SESSÃO E VOLTA PARA A TELA DE LOGIN DA ÁREA ---
    const sair = () => {
        encerrarSessao();
        router.replace(loginDoPerfil(usuario?.role));
    };

    if (!pronto) {
        return null;
    }

    const iniciaisUsuario = (usuario?.nome || "?").trim().charAt(0).toUpperCase();

    return (
        <div className={`app-shell${menuAberto ? " sidebar-open" : ""}${semMenuLateral ? " app-shell--sem-menu" : ""}`}>
            {!semMenuLateral && <aside className="sidebar">
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
            </aside>}

            <div className="app-main">
                <header className="topbar">
                    {!semMenuLateral && (
                        <Button
                            icon="pi pi-bars"
                            rounded
                            text
                            aria-label="Abrir menu"
                            onClick={() => setMenuAberto((v) => !v)}
                        />
                    )}
                    {topoEsquerda ? topoEsquerda(usuario) : <div className="topbar-title">{titulo}</div>}
                    <div className="topbar-actions">
                        <Button
                            icon={temaEscuro ? "pi pi-sun" : "pi pi-moon"}
                            rounded
                            text
                            className="site-tema-toggle"
                            aria-label="Alternar tema claro e escuro"
                            onClick={alternarTema}
                        />
                        {topoDireita ? topoDireita(usuario, sair) : (
                            <>
                                <Avatar label={iniciaisUsuario} shape="circle" />
                                <span className="user-nome">{usuario?.nome || ""}</span>
                                <Button label="Sair" size="small" severity="danger" outlined onClick={sair} />
                            </>
                        )}
                    </div>
                </header>
                <main className="content">{children}</main>
            </div>
        </div>
    );
}
