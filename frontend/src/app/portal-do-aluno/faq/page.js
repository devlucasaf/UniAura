"use client";

import { Accordion, AccordionTab }  from "primereact/accordion";
import MolduraAluno                 from "@/screens/portal-do-aluno/MolduraAluno";
import PainelAluno                  from "@/screens/portal-do-aluno/PainelAluno";

const PERGUNTAS = [
    {
        p: "Como altero minha senha?",
        r: "Clique no seu perfil, no canto superior direito, e escolha \"Alterar senha\"." },
    {
        p: "Esqueci minha senha. E agora?",
        r: "Na tela de login, use \"Esqueci a senha\". Você confirma e-mail, CPF e data de nascimento e recebe um código de autorização na própria tela."
    },
    {
        p: "Como atualizo meus dados pessoais?",
        r: "No menu do perfil, escolha \"Dados pessoais\" e salve as alterações."
    },
    {
        p: "Onde vejo minhas faltas e menções?",
        r: "Na Home, no painel \"Faltas e menções\". O detalhe por disciplina fica em \"Meu perfil\"."
    },
    {
        p: "Como ativo o alto contraste?",
        r: "Use o botão de acessibilidade (ícone de olho) no cabeçalho: há contraste claro, contraste escuro, cursor grande, daltonismo e pausa de animações."
    },
    {
        p: "Como falo com a secretaria?",
        r: "Pela página de contato do site da UniAura, ou pelas redes sociais listadas no rodapé."
    }
];

// --- PERGUNTAS FREQUENTES DO PORTAL DO ALUNO ---
export default function AlunoFaqPage() {
    return (
        <MolduraAluno titulo="FAQ">
            <div className="ua-aluno-pagina-estreita">
                <PainelAluno id="tituloFaq" titulo="Perguntas frequentes" icone="pi pi-question-circle">
                    <Accordion multiple>
                        {PERGUNTAS.map((item) => (
                            <AccordionTab key={item.p} header={item.p}>
                                <p>{item.r}</p>
                            </AccordionTab>
                        ))}
                    </Accordion>
                </PainelAluno>
            </div>
        </MolduraAluno>
    );
}
