import Link from "next/link";
import SiteChrome from "@/components/site/SiteChrome";

const MARCOS = [
    {
        ano: "1994",
        titulo: "Fundação do Colégio Aura",
        texto: `Três educadores apaixonados por transformação social fundam o Colégio Aura em Brasília,
            com uma turma de 32 alunos da educação infantil e a promessa de unir excelência acadêmica
            e formação humana.`
    },
    {
        ano: "2001",
        titulo: "Expansão para o Ensino Fundamental II",
        texto: `Com o crescimento das primeiras turmas, inauguramos um novo bloco pedagógico dedicado
            ao Fundamental II, com laboratórios de ciências, biblioteca ampliada e quadra
            poliesportiva coberta.`
    },
    {
        ano: "2008",
        titulo: "Chegada do Ensino Médio",
        texto: `O Colégio Aura passa a oferecer o Ensino Médio completo, com foco em protagonismo
            juvenil, aprofundamento científico e preparação para vestibulares nacionais.`
    },
    {
        ano: "2015",
        titulo: "Programa de bolsas sociais",
        texto: `Criamos o programa "Semente Áurea" de bolsas de estudo integrais para alunos de
            comunidades vizinhas, reafirmando nosso compromisso com a educação como
            ferramenta de transformação.`
    },
    {
        ano: "2020",
        titulo: "Educação híbrida e tecnologia",
        texto: `Durante a pandemia, implementamos uma plataforma completa de ensino híbrido,
            equipamos todas as salas com tecnologia interativa e integramos ferramentas
            digitais ao cotidiano pedagógico.`
    },
    {
        ano: "2024",
        titulo: "30 anos de tradição e inovação",
        texto: `Celebramos três décadas de história com mais de 2.500 alunos matriculados,
            98% de aprovação em universidades públicas e privadas e uma comunidade escolar
            que se orgulha do caminho percorrido.`
    }
];

const NUMEROS = [
    { valor: "30+", rotulo: "Anos de tradição" },
    { valor: "2.500", rotulo: "Alunos matriculados" },
    { valor: "98%", rotulo: "Aprovação em universidades" },
    { valor: "120+", rotulo: "Profissionais na equipe" }
];

export default function HistoriaPage() {
    return (
        <SiteChrome>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span>Sobre</span>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Nossa História</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Sobre nós</span>
                    <h1>Nossa História</h1>
                    <p className="site-pagina-hero-lead">
                        Há mais de 30 anos formando cidadãos preparados para transformar o mundo através do conhecimento,
                        da ética e do respeito.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Linha do tempo</span>
                        <h2>Nossa trajetória em marcos</h2>
                        <p className="muted">Cada década trouxe conquistas que moldaram quem somos hoje.</p>
                    </div>

                    <ol className="site-timeline">
                        {MARCOS.map((marco) => (
                            <li key={marco.ano} className="site-timeline-item">
                                <div className="site-timeline-marco">{marco.ano}</div>
                                <div className="site-timeline-conteudo">
                                    <h3>{marco.titulo}</h3>
                                    <p>{marco.texto}</p>
                                </div>
                            </li>
                        ))}
                    </ol>
                </div>
            </section>

            <section className="site-section site-section-alt">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Números da nossa história</span>
                        <h2>Áurea em 30 anos</h2>
                    </div>

                    <div className="site-numeros">
                        {NUMEROS.map((numero) => (
                            <div key={numero.rotulo} className="site-numero-card">
                                <strong>{numero.valor}</strong>
                                <span>{numero.rotulo}</span>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container site-cta-final">
                    <h2>Quer conhecer nossa história pessoalmente?</h2>
                    <p className="muted">Agende uma visita e conheça de perto a estrutura, a equipe e o dia a dia do Colégio Aura.</p>
                    <Link className="btn btn-primary btn-lg" href="/contato">
                        Fale com a secretaria
                    </Link>
                </div>
            </section>
        </SiteChrome>
    );
}
