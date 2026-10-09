"use client";

import { useState }             from "react";
import { Calendar }             from "primereact/calendar";
import { EVENTOS, chaveDoDia }  from "./dados";
import "@/lib/localePt";

// --- CHAVES DOS DIAS QUE TÊM EVENTO, PARA MARCAR NO CALENDÁRIO ---
const DIAS_COM_EVENTO = new Set(EVENTOS.map((evento) => chaveDoDia(evento.data)));

// --- CALENDÁRIO ACADÊMICO: DIAS COM EVENTO FICAM MARCADOS E A LISTA MOSTRA OS EVENTOS DO DIA ESCOLHIDO ---
export default function CalendarioAluno() {
    const [diaEscolhido, setDiaEscolhido] = useState(new Date());

    // --- DESENHA CADA DIA, COM UM PONTO NOS DIAS QUE TÊM EVENTO ---
    const modeloDoDia = (dia) => {
        const chave = chaveDoDia(new Date(dia.year, dia.month, dia.day));
        return (
            <span className={DIAS_COM_EVENTO.has(chave) ? "ua-aluno-dia-evento" : undefined}>
                {dia.day}
            </span>
        );
    };

    const eventosDoDia = EVENTOS.filter((evento) => chaveDoDia(evento.data) === chaveDoDia(diaEscolhido));
    const proximos = EVENTOS.filter((evento) => evento.data >= new Date(new Date().setHours(0, 0, 0, 0))).slice(0, 3);
    const listaAMostrar = eventosDoDia.length > 0 ? eventosDoDia : proximos;

    return (
        <section aria-labelledby="tituloCalendario">
            <div className="ua-aluno-secao-cabecalho">
                <h2 id="tituloCalendario">Calendário acadêmico</h2>
            </div>

            <div className="ua-card ua-aluno-calendario">
                <Calendar
                    inline
                    locale="pt"
                    value={diaEscolhido}
                    onChange={(e) => e.value && setDiaEscolhido(e.value)}
                    dateTemplate={modeloDoDia}
                />

                <h3 className="ua-aluno-eventos-titulo">
                    {eventosDoDia.length > 0 ? "Eventos do dia" : "Próximos eventos"}
                </h3>
                <ul className="ua-aluno-eventos">
                    {listaAMostrar.map((evento) => (
                        <li key={`${chaveDoDia(evento.data)}-${evento.titulo}`}>
                            <strong>{evento.data.toLocaleDateString("pt-BR", { day: "2-digit", month: "short" })}</strong>
                            <span>{evento.titulo}</span>
                        </li>
                    ))}
                    {listaAMostrar.length === 0 && <li className="muted">Nenhum evento neste período.</li>}
                </ul>
            </div>
        </section>
    );
}
