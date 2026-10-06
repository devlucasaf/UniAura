import SiteHeader from "@/components/web/SiteHeader";
import SiteFooter from "@/components/web/SiteFooter";

// --- LAYOUT COMPARTILHADO DE TODAS AS PÁGINAS DO SISTEMA WEB (CABEÇALHO E RODAPÉ DO SITE) ---
export default function WebLayout({ children }) {
    return (
        <div className="site">
            <SiteHeader />
            {children}
            <SiteFooter />
        </div>
    );
}
