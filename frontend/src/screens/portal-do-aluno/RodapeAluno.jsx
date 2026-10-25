"use client";

import Link from "next/link";

const PORTAL = [
    { 
        nome: "Home",       
        rota: "/portal-do-aluno/home" 
    },
    { 
        nome: "Meu perfil", 
        rota: "/portal-do-aluno/perfil" 
    },
    { 
        nome: "Carreiras",  
        rota: "/portal-do-aluno/carreiras" 
    },
    { 
        nome: "Parcerias",  
        rota: "/portal-do-aluno/parcerias" 
    }
];

const AJUDA = [
    { 
        nome: "Perguntas frequentes (FAQ)", 
        rota: "/portal-do-aluno/faq" 
    },
    { 
        nome: "Fale conosco",               
        rota: "/web/contato" 
    },
    { 
        nome: "Site da UniAura",            
        rota: "/web/home" 
    }
];

const LEGAL = [
    { 
        nome: "Licença de uso",         
        rota: "/portal-do-aluno/licenca" 
    },
    { 
        nome: "Política de privacidade", 
        rota: "/web/privacidade" 
    },
    { 
        nome: "Regimento universitário", 
        rota: "/web/sobre/regimento-universitario" 
    }
];

const REDES = [
    { 
        nome: "Instagram", 
        icone: "pi pi-instagram", 
        href: "https://www.instagram.com/__.fr3it4s.__/" 
    },
    { 
        nome: "Facebook",  
        icone: "pi pi-facebook",  
        href: "#" 
    },
    { 
        nome: "GitHub",    
        icone: "pi pi-github",    
        href: "https://github.com/devlucasaf/ERP-University-Academic-System" 
    },
    { 
        nome: "E-mail",    
        icone: "pi pi-envelope",  
        href: "/web/contato", 
        interno: true 
    }
];

// --- RODAPÉ DO PORTAL DO ALUNO ---
export default function RodapeAluno() {
    return (
        <footer className="ua-rodape-aluno">
            <div className="ua-rodape-aluno__grade">
                <div className="ua-rodape-aluno__marca">
                    <strong>Uni<span>Aura</span></strong>
                    <p>Educação que transforma vidas há mais de 35 anos.</p>
                    <div className="ua-rodape-aluno__redes">
                        {REDES.map((rede) => rede.interno ? (
                            <Link key={rede.nome} href={rede.href} aria-label={rede.nome} title={rede.nome}>
                                <i className={rede.icone} aria-hidden="true" />
                            </Link>
                        ) : (
                            <a key={rede.nome} href={rede.href} target="_blank" rel="noopener noreferrer" aria-label={rede.nome} title={rede.nome}>
                                <i className={rede.icone} aria-hidden="true" />
                            </a>
                        ))}
                    </div>
                </div>

                <nav aria-label="Portal">
                    <h3>Portal</h3>
                    {PORTAL.map((link) => <Link key={link.nome} href={link.rota}>{link.nome}</Link>)}
                </nav>

                <nav aria-label="Ajuda">
                    <h3>Ajuda</h3>
                    {AJUDA.map((link) => <Link key={link.nome} href={link.rota}>{link.nome}</Link>)}
                </nav>

                <nav aria-label="Legal">
                    <h3>Legal</h3>
                    {LEGAL.map((link) => <Link key={link.nome} href={link.rota}>{link.nome}</Link>)}
                </nav>
            </div>

            <div className="ua-rodape-aluno__base">© 2026 UniAura — Todos os direitos reservados.</div>
        </footer>
    );
}
