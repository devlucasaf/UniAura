import "../styles/global.css";
import "../styles/components.css";
import "../styles/layout.css";
import "../styles/erp-central.css";
import "../styles/graduacao.css";
import "../styles/web.css";

export const metadata = {
    title: "ERP University Academic System",
    description: "Sistema de gestão acadêmica da Universidade Aura de Xique Xique"
};

export default function RootLayout({ children }) {
    return (
        <html lang="pt-BR">
            <body>{children}</body>
        </html>
    );
}
