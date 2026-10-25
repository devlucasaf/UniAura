"use client";

import { useEffect, useState } from "react";
import { obterUsuario }        from "@/lib/auth";
import MuralNoticias           from "./MuralNoticias";
import CalendarioAluno         from "./CalendarioAluno";
import AulasAluno              from "./AulasAluno";
import FaltasMencoes           from "./FaltasMencoes";
import GradeHoraria            from "./GradeHoraria";

// --- HOME DO PORTAL DO ALUNO ---
export default function HomeAluno() {
    const [nome, setNome] = useState("");

    useEffect(() => {
        setNome(obterUsuario()?.nome || "");
    }, []);

    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-saudacao">
                <h1>Olá, {nome || "aluno"}!</h1>
                <span className="ua-aluno-migalha">Home</span>
            </div>

            <div className="ua-aluno-colunas">
                <div className="ua-aluno-lateral">
                    <MuralNoticias />
                    <FaltasMencoes />
                </div>

                <div className="ua-aluno-lateral">
                    <AulasAluno />
                    <CalendarioAluno />
                </div>
            </div>

            <GradeHoraria />
        </div>
    );
}
