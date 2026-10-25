"use client";

import { useMemo, useState }            from "react";
import { Calendar }                     from "primereact/calendar";
import PainelAluno                      from "./PainelAluno";
import { chaveDoDia, montarEventos }    from "./dados";
import useDisciplinasDoSemestre         from "./useDisciplinasDoSemestre";
import "@/lib/localePt";

// --- CALENDÁRIO ACADÊMICO ---
export default function CalendarioAluno() {
    const [diaEscolhido, setDiaEscolhido] = useState(new Date());
    const { disciplinas } = useDisciplinasDoSemestre();
    const eventosDoSemestre = useMemo(() => montarEventos(disciplinas), [disciplinas]);

    // --- CHAVES DOS DIAS QUE TÊM EVENTO ---
    const diasComEvento = useMemo(() => new Set(eventosDoSemestre.map((evento) => chaveDoDia(evento.data))), [eventosDoSemestre]);

    // --- DESENHA CADA DIA, COM UM PONTO NOS DIAS QUE TÊM EVENTO ---
    const modeloDoDia = (dia) => {
        const chave = chaveDoDia(new Date(dia.year, dia.month, dia.day));
        return (
            <span className={diasComEvento.has(chave) ? "ua-aluno-dia-evento" : undefined}>
                {dia.day}
            </span>
        );
    };

    const eventosDoDia = eventosDoSemestre.filter((evento) => chaveDoDia(evento.data) === chaveDoDia(diaEscolhido));
    const proximos = eventosDoSemestre.filter((evento) => evento.data >= new Date(new Date().setHours(0, 0, 0, 0))).slice(0, 3);
    const listaAMostrar = eventosDoDia.length > 0 ? eventosDoDia : proximos;

    return (
        <PainelAluno id="tituloCalendario" titulo="Calendário acadêmico" icone="pi pi-calendar">
            <div className="ua-aluno-calendario">
                <Calendar
                    inline
                    locale="pt"
                    value={diaEscolhido}
                    onChange={(evento) => evento.value && setDiaEscolhido(evento.value)}
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
        </PainelAluno>
    );
}
