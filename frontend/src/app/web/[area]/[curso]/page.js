import { notFound }                         from "next/navigation";
import CursoTemplate                        from "@/components/web/CursoTemplate";
import { cursosPorSlug, areaUrlDoCurso }    from "@/data/cursos";

// --- GERA OS PARÂMETROS ESTÁTICOS DE ÁREA E CURSO PARA AS PÁGINAS ---
export function generateStaticParams() {
    return Object.keys(cursosPorSlug)
        .map((curso) => ({ area: areaUrlDoCurso(curso), curso }))
        .filter((params) => params.area);
}

// --- GERA OS METADADOS DA PÁGINA DE ACORDO COM O CURSO ---
export async function generateMetadata({ params }) {
    const { area, curso: slug } = await params;
    const curso = cursosPorSlug[slug];
    if (!curso || areaUrlDoCurso(slug) !== area) {
        return {};
    }
    return { title: `${curso.subnavLabel} — Universidade Aura` };
}

// --- COMPONENTE DE PÁGINA DO CURSO ---
export default async function CursoPage({ params }) {
    const { area, curso: slug } = await params;
    const curso = cursosPorSlug[slug];

    if (!curso || areaUrlDoCurso(slug) !== area) {
        notFound();
    }

    return (
        <>
            <CursoTemplate curso={curso} />
        </>
    );
}
