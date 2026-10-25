import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import "primeflex/primeflex.css";

import "../styles/global.css";
import "../styles/components.css";
import "../styles/layout.css";
import "../styles/erp-central.css";
import "../styles/graduacao.css";
import "../styles/web.css";
import "../styles/primereact-tema.css";
import "../styles/design-system.css";
import "../styles/portal-aluno.css";

import CloudsupportProviders from "@/components/providers/CloudsupportProviders";

// --- METADATA PARA SEO E ABERTURA DE PÁGINA ---
export const metadata = {
    title: "ERP University Academic System",
    description: "Sistema de gestão acadêmica da Universidade Aura de Xique Xique"
};

// --- SCRIPT QUE RODA ANTES DA PRIMEIRA PINTURA ---
const SCRIPT_TEMA = `
try {
    var raiz = document.documentElement;
    var caminho = location.pathname;
    var temaSalvo = localStorage.getItem("theme");
    var noPortalDoAluno = caminho.indexOf("/portal-do-aluno/") > -1
        && !/\\/portal-do-aluno\\/(login|cadastro)\\/?$/.test(caminho);

    if (noPortalDoAluno) {
        raiz.setAttribute("data-area", "aluno");

        var sistemaEscuro = matchMedia("(prefers-color-scheme: dark)");
        var temaInicial = temaSalvo === "light" || temaSalvo === "dark"
            ? temaSalvo
            : (sistemaEscuro.matches ? "dark" : "light");
        raiz.setAttribute("data-theme", temaInicial);

        sistemaEscuro.addEventListener("change", function (evento) {
            if (!localStorage.getItem("theme")) {
                raiz.setAttribute("data-theme", evento.matches ? "dark" : "light");
            }
        });

        var acessibilidade = JSON.parse(localStorage.getItem("acessibilidade") || "{}");
        if (acessibilidade.contraste) {
            raiz.setAttribute("data-contraste", acessibilidade.contraste);
        }

        if (acessibilidade.daltonismo) {
            raiz.setAttribute("data-daltonismo", acessibilidade.daltonismo);
        }

        if (acessibilidade.cursorGrande) {
            raiz.setAttribute("data-cursor-grande", "sim");
        }

        if (acessibilidade.pausarAnimacoes) {
            raiz.setAttribute("data-sem-animacao", "sim");
        }
    } else {
        raiz.setAttribute("data-theme", temaSalvo || "light");
    }
} catch (erro) {
}
`;

// --- LAYOUT PADRÃO PARA TODAS AS PÁGINAS ---
export default function RootLayout({ children }) {
    return (
        <html lang="pt-BR">
            <head>
                <script dangerouslySetInnerHTML={{ __html: SCRIPT_TEMA }} />
            </head>
            <body>
                <a href="#conteudo" className="ua-skip-link">Pular para o conteúdo</a>
                <CloudsupportProviders>
                    <div id="conteudo" tabIndex={-1}>{children}</div>
                </CloudsupportProviders>
            </body>
        </html>
    );
}
