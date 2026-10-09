import Link             from "next/link";
import { notFound }     from "next/navigation";
import { INSTITUCIONAL } from "@/data/institucional";

// --- BUSCA O ITEM PELO SLUG (SÓ OS ITENS SEM PÁGINA PRÓPRIA USAM ESTA PÁGINA GENÉRICA) ---
const buscarItem = (slug) => INSTITUCIONAL.find((item) => item.slug === slug && !item.rota);

// --- GERA OS PARÂMETROS ESTÁTICOS DAS PÁGINAS DO INSTITUCIONAL ---
export function generateStaticParams() {
    return INSTITUCIONAL.filter((item) => !item.rota).map((item) => ({ slug: item.slug }));
}

// --- METADADOS DA PÁGINA ---
export async function generateMetadata({ params }) {
    const { slug } = await params;
    const item = buscarItem(slug);
    return item ? { title: `${item.titulo} — Universidade Aura` } : {};
}

// --- PÁGINA GENÉRICA DE UM ITEM DO INSTITUCIONAL ---
export default async function InstitucionalPage({ params }) {
    const { slug } = await params;
    const item = buscarItem(slug);

    if (!item) {
        notFound();
    }

    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span>Institucional</span>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">{item.titulo}</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Institucional</span>
                    <h1>{item.titulo}</h1>
                    <p className="site-pagina-hero-lead">{item.resumo}</p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <article className="site-card ua-card">
                        <h2>Em breve</h2>
                        <p className="muted">
                            O conteúdo completo desta página ainda está sendo preparado. Enquanto isso, fale com a
                            secretaria pelo canal de <Link href="/web/contato" className="ua-link-destaque">contato</Link>.
                        </p>
                    </article>
                </div>
            </section>
        </>
    );
}
