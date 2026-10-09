"use client";

import MuralNoticias        from "./MuralNoticias";
import CalendarioAluno      from "./CalendarioAluno";
import GradeHoraria         from "./GradeHoraria";

// --- HOME DO PORTAL DO ALUNO: MURAL DE NOTÍCIAS, CALENDÁRIO À DIREITA E GRADE DE CURSOS ABAIXO ---
// (O BOTÃO DE 3 BARRAS E A SAUDAÇÃO FICAM NO CABEÇALHO, MONTADOS POR TopoAluno)
export default function HomeAluno() {
    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-colunas">
                <MuralNoticias />
                <CalendarioAluno />
            </div>

            <GradeHoraria />
        </div>
    );
}
