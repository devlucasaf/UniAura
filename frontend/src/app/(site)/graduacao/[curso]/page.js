import { notFound } from "next/navigation";
import SiteChrome from "@/components/site/SiteChrome";
import CursoTemplate from "@/components/site/CursoTemplate";
import { cursosPorSlug } from "@/data/cursos";

export function generateStaticParams() {
    return Object.keys(cursosPorSlug).map((curso) => ({ curso }));
}

export async function generateMetadata({ params }) {
    const { curso: slug } = await params;
    const curso = cursosPorSlug[slug];
    if (!curso) {
        return {};
    }
    return { title: `${curso.subnavLabel} — Universidade Aura` };
}

export default async function CursoPage({ params }) {
    const { curso: slug } = await params;
    const curso = cursosPorSlug[slug];

    if (!curso) {
        notFound();
    }

    return (
        <SiteChrome>
            <CursoTemplate curso={curso} />
        </SiteChrome>
    );
}
