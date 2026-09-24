import Link from "next/link";

const LINKS_INSTITUCIONAIS = [
    { nome: "A Universidade", rota: "/" },
    { nome: "Cursos", rota: "/graduacao/cursos" },
    { nome: "Matrículas", rota: "/matriculas" },
    { nome: "Contato", rota: "/contato" }
];

const REDES_SOCIAIS = [
    { nome: "Facebook", href: "#", externo: false, icone: "facebook" },
    { nome: "Instagram", href: "https://www.instagram.com/__.fr3it4s.__/", externo: false, icone: "instagram" },
    { nome: "GitHub", href: "https://github.com/devlucasaf/ERP-University-Academic-System", externo: true, icone: "github" }
];

export default function SiteFooter() {
    return (
        <footer className="site-footer">
            <div className="site-container site-footer-inner">
                <div className="site-footer-brand">
                    <div className="site-brand">
                        <img
                            src="/img/uniaura.png"
                            alt=""
                            className="site-brand-logo"
                        />
                        <span className="site-brand-text">Uni<strong>Aura</strong></span>
                    </div>
                    <p className="muted">Educação que transforma vidas há mais de 35 anos.</p>
                </div>

                <div className="site-footer-col">
                    <h4>Institucional</h4>
                    {LINKS_INSTITUCIONAIS.map((link) => (
                        <Link key={link.nome} href={link.rota}>{link.nome}</Link>
                    ))}
                </div>

                <div className="site-footer-col">
                    <h4>Redes sociais</h4>
                    {REDES_SOCIAIS.map((rede) => (
                        <a key={rede.nome} href={rede.href} target={rede.externo ? "_blank" : undefined} rel="noopener" aria-label={rede.nome}>
                            <IconeRede nome={rede.icone} />
                            {rede.nome}
                        </a>
                    ))}
                </div>
            </div>

            <div className="site-footer-bottom">© 2026 UniAura — Todos os direitos reservados.</div>
        </footer>
    );
}

function IconeRede({ nome }) {
    if (nome === "facebook") {
        return (
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                 strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3Z"></path>
            </svg>
        );
    }

    if (nome === "instagram") {
        return (
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                 strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                <rect x="2" y="2" width="20" height="20" rx="5"></rect>
                <circle cx="12" cy="12" r="4"></circle>
                <path d="M17.5 6.5h.01"></path>
            </svg>
        );
    }

    return (
        <svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor" aria-hidden="true">
            <path d="M12 .5C5.65.5.5 5.65.5 12c0 5.08 3.29 9.39 7.86 10.91.57.1.78-.25.78-.55
                 0-.27-.01-1.17-.02-2.12-3.2.69-3.88-1.36-3.88-1.36-.52-1.32-1.28-1.67-1.28-1.67
                 -1.05-.72.08-.71.08-.71 1.16.08 1.77 1.19 1.77 1.19 1.03 1.76 2.69 1.25
                 3.34.96.1-.75.4-1.25.72-1.54-2.55-.29-5.23-1.27-5.23-5.66
                 0-1.25.45-2.28 1.18-3.08-.12-.29-.51-1.46.11-3.05 0 0 .96-.31 3.15 1.18
                 .91-.25 1.89-.38 2.86-.38.97 0 1.95.13 2.86.38 2.19-1.49 3.15-1.18
                 3.15-1.18.62 1.59.23 2.76.11 3.05.73.8 1.18 1.83 1.18 3.08
                 0 4.4-2.69 5.36-5.25 5.65.41.35.77 1.04.77 2.1
                 0 1.52-.01 2.75-.01 3.12 0 .3.21.66.79.55A11.5 11.5 0 0 0 23.5 12
                 C23.5 5.65 18.35.5 12 .5z"/>
        </svg>
    );
}
