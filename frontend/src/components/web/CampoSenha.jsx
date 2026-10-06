"use client";

import { useState }     from "react";
import { InputText }    from "primereact/inputtext";

// --- CAMPO DE SENHA COM BOTÃO PARA MOSTRAR/OCULTAR ---
export default function CampoSenha({ id, name, ...resto }) {
    const [visivel, setVisivel] = useState(false);

    return (
        <div className="ua-campo-senha" style={{ position: "relative", display: "block" }}>
            <InputText id={id} name={name} type={visivel ? "text" : "password"} {...resto} />
            <button
                type="button"
                className="ua-campo-senha__botao"
                style={{ position: "absolute", top: "50%", right: "0.4rem", transform: "translateY(-50%)" }}
                aria-label={visivel ? "Ocultar senha" : "Mostrar senha"}
                aria-pressed={visivel}
                onClick={() => setVisivel((atual) => !atual)}
            >
                <i className={`pi ${visivel ? "pi-eye-slash" : "pi-eye"}`} aria-hidden="true" />
            </button>
        </div>
    );
}
