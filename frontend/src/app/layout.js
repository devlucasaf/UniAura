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

// --- LAYOUT PADRÃO PARA TODAS AS PÁGINAS ---
export default function RootLayout({ children }) {
    return (
        <html lang="pt-BR">
            <head>
                {/* --- APLICA O TEMA SALVO ANTES DA PRIMEIRA PINTURA (evita piscar em claro) --- */}
                <script
                    dangerouslySetInnerHTML={{
                        __html: `try{document.documentElement.setAttribute("data-theme",localStorage.getItem("theme")||"light")}catch(e){}`
                    }}
                />
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
