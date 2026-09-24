import SiteHeader from "./SiteHeader";
import SiteFooter from "./SiteFooter";

// --- ENVELOPE PADRÃO DAS PÁGINAS PÚBLICAS DO SITE ---
export default function SiteChrome({ ancoras = false, children }) {
    return (
        <div className="site">
            <SiteHeader ancoras={ancoras} />
            {children}
            <SiteFooter />
        </div>
    );
}
