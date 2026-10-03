import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import "primeflex/primeflex.css";

// --- CSS DA cloudsupport-react: ESTILIZA Box/Field/InlineField/LoadingBar/SessionExpiredBanner/ReloadBanner ---
import "@bernardo-dias/react-cloudsupport/resources/prime/theme.css";
import "@bernardo-dias/react-cloudsupport/resources/prime/tweaks.css";

import "../styles/global.css";
import "../styles/components.css";
import "../styles/layout.css";
import "../styles/erp-central.css";
import "../styles/graduacao.css";
import "../styles/web.css";

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
            <body>
                <CloudsupportProviders>{children}</CloudsupportProviders>
            </body>
        </html>
    );
}
