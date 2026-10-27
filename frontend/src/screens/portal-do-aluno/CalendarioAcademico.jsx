"use client";

import { useEffect, useMemo, useState }   from "react";
import Link                               from "next/link";
import { Button }                         from "primereact/button";
import { Calendar }                       from "primereact/calendar";
import { Tag }                            from "primereact/tag";
import { pesquisarEventosAcademicos }     from "@/app/remotes/uniaura/calendario/pesquisarEventosAcademicosRemote";
import { notificar }                      from "@/lib/notificar";
import { chaveDoDia }                     from "./dados";
import "@/lib/localePt";

const POR_PAGINA = 100;

// --- ROTULO E COR DE CADA TIPO DE EVENTO ---
const TIPOS = {
    PROVA: {
        rotulo: "Prova",
        severidade: "danger"
    },
    FERIADO: {
        rotulo: "Feriado",
        severidade: "success"
    },
    MATRICULA: {
        rotulo: "Matrícula",
        severidade: "info"
    },
    RECESSO: {
        rotulo: "Recesso",
        severidade: "warning"
    },
    EVENTO: {
        rotulo: "Evento",
        severidade: "secondary"
    },
    OUTRO: {
        rotulo: "Outro",
        severidade: "contrast"
    }
};

// --- "2026-03-15" VIRA A DATA LOCAL ---
const lerData = (texto) => {
    const [ano, mes, dia] = texto.split("-").map(Number);
    return new Date(ano, mes - 1, dia);
};

// --- TODOS OS DIAS ENTRE O INÍCIO E O FIM DO EVENTO ---
const diasDoEvento = (evento) => {
    const dias = [];
    const cursor = new Date(evento.inicio);

    while (cursor <= evento.fim) {
        dias.push(chaveDoDia(cursor));
        cursor.setDate(cursor.getDate() + 1);
    }

    return dias;
};

const formatarPeriodo = (evento) => {
    const inicio = evento.inicio.toLocaleDateString("pt-BR", { day: "2-digit", month: "short" });

    if (chaveDoDia(evento.inicio) === chaveDoDia(evento.fim)) {
        return inicio;
    }

    return `${inicio} a ${evento.fim.toLocaleDateString("pt-BR", { day: "2-digit", month: "short" })}`;
};

// --- DOMINGO DE PÁSCOA, BASE DA SEXTA-FEIRA SANTA ---
const pascoa = (ano) => {
    const a = ano % 19;
    const b = Math.floor(ano / 100);
    const c = ano % 100;
    const d = Math.floor(b / 4);
    const e = b % 4;
    const g = Math.floor((8 * b + 13) / 25);
    const h = (19 * a + b - d - g + 15) % 30;
    const i = Math.floor(c / 4);
    const k = c % 4;
    const l = (32 + 2 * e + 2 * i - h - k) % 7;
    const m = Math.floor((a + 11 * h + 22 * l) / 451);
    const mes = Math.floor((h + l - 7 * m + 114) / 31);
    const dia = ((h + l - 7 * m + 114) % 31) + 1;

    return new Date(ano, mes - 1, dia);
};

// --- FERIADOS NACIONAIS DO BRASIL DE UM ANO ---
const feriadosNacionais = (ano) => {
    const sextaSanta = pascoa(ano);
    sextaSanta.setDate(sextaSanta.getDate() - 2);

    const fixos = [
        [0, 1, "Confraternização Universal"],
        [3, 21, "Tiradentes"],
        [4, 1, "Dia do Trabalho"],
        [8, 7, "Independência do Brasil"],
        [9, 12, "Nossa Senhora Aparecida"],
        [10, 2, "Finados"],
        [10, 15, "Proclamação da República"],
        [10, 20, "Dia da Consciência Negra"],
        [11, 25, "Natal"]
    ];

    const datas = fixos.map(([mes, dia, titulo]) => ({ data: new Date(ano, mes, dia), titulo }));
    datas.push({ data: sextaSanta, titulo: "Sexta-feira Santa" });

    return datas.map(({ data, titulo }) => ({
        uid: `feriado-${chaveDoDia(data)}`,
        titulo,
        descricao: "Feriado nacional",
        tipo: "FERIADO",
        inicio: data,
        fim: data
    }));
};

// --- DATA NO FORMATO DO ARQUIVO .ics ---
const dataIcs = (data) => chaveDoDia(data).replaceAll("-", "");

const escaparIcs = (texto) => (texto ?? "").replace(/\\/g, "\\\\").replace(/;/g, "\\;").replace(/,/g, "\\,").replace(/\r?\n/g, "\\n");

// --- MONTA O ARQUIVO .ics, QUE O GOOGLE AGENDA, O OUTLOOK E O CELULAR IMPORTAM ---
const montarIcs = (eventos) => {
    const agora = new Date().toISOString().replace(/[-:]/g, "").replace(/\.\d{3}/, "");

    const linhas = [
        "BEGIN:VCALENDAR",
        "VERSION:2.0",
        "PRODID:-//UniAura//Calendario Academico//PT-BR",
        "CALSCALE:GREGORIAN",
        "X-WR-CALNAME:Calendário acadêmico UniAura"
    ];

    eventos.forEach((evento) => {
        const fimExclusivo = new Date(evento.fim);
        fimExclusivo.setDate(fimExclusivo.getDate() + 1);

        linhas.push(
            "BEGIN:VEVENT",
            `UID:${evento.uid}@uniaura`,
            `DTSTAMP:${agora}`,
            `DTSTART;VALUE=DATE:${dataIcs(evento.inicio)}`,
            `DTEND;VALUE=DATE:${dataIcs(fimExclusivo)}`,
            `SUMMARY:${escaparIcs(evento.titulo)}`,
            `DESCRIPTION:${escaparIcs(evento.descricao)}`,
            `CATEGORIES:${TIPOS[evento.tipo]?.rotulo ?? "Evento"}`,
            "END:VEVENT"
        );
    });

    linhas.push("END:VCALENDAR");

    return linhas.join("\r\n");
};

// --- CALENDÁRIO ACADÊMICO: DATAS IMPORTANTES NO CALENDÁRIO, LISTA DO MÊS E DOWNLOAD PARA A AGENDA ---
export default function CalendarioAcademico() {
    const [eventosCadastrados, setEventosCadastrados] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [erro,       setErro]       = useState(false);
    const [dia,        setDia]        = useState(new Date());
    const [mesVisivel, setMesVisivel] = useState(new Date());

    // --- BUSCA TODAS AS PÁGINAS DE EVENTOS DO BACKEND ---
    useEffect(() => {
        async function carregar() {
            const lista = [];
            let total = 0;

            do {
                const resposta = await pesquisarEventosAcademicos({ de: lista.length, qtd: POR_PAGINA, ordem: "+dataInicio" });

                total = resposta.total;
                lista.push(...resposta.lista);

                if (resposta.lista.length === 0) {
                    break;
                }
            } while (lista.length < total);

            setEventosCadastrados(lista.map((evento) => ({
                ...evento,
                inicio: lerData(evento.dataInicio),
                fim: lerData(evento.dataFim ?? evento.dataInicio)
            })));
        }

        carregar()
            .catch(() => setErro(true))
            .finally(() => setCarregando(false));
    }, []);

    // --- OS FERIADOS NACIONAIS APARECEM SEMPRE, DE UM ANO ANTES A DOIS ANOS DEPOIS, MAIS OS EVENTOS CADASTRADOS ---
    const eventos = useMemo(() => {
        const anoAtual = new Date().getFullYear();
        const feriados = [anoAtual - 1, anoAtual, anoAtual + 1, anoAtual + 2].flatMap(feriadosNacionais);

        return [...feriados, ...eventosCadastrados].sort((a, b) => a.inicio - b.inicio);
    }, [eventosCadastrados]);

    // --- EVENTOS DAQUELE DIA ---
    const eventosPorDia = useMemo(() => {
        const mapa = new Map();

        eventos.forEach((evento) => {
            diasDoEvento(evento).forEach((chave) => {
                mapa.set(chave, [...(mapa.get(chave) ?? []), evento]);
            });
        });

        return mapa;
    }, [eventos]);

    const eventosDoDia = eventosPorDia.get(chaveDoDia(dia)) ?? [];

    const eventosDoMes = eventos.filter((evento) => (
        evento.inicio <= new Date(mesVisivel.getFullYear(), mesVisivel.getMonth() + 1, 0)
        && evento.fim >= new Date(mesVisivel.getFullYear(), mesVisivel.getMonth(), 1)
    ));

    // --- DESENHA CADA DIA, COM UM PONTO NOS DIAS QUE TÊM EVENTO ---
    const modeloDoDia = (data) => {
        const chave = chaveDoDia(new Date(data.year, data.month, data.day));

        return (
            <span className={eventosPorDia.has(chave) ? "ua-aluno-dia-evento" : undefined}>
                {data.day}
            </span>
        );
    };

    // --- GERA O ARQUIVO NO NAVEGADOR E O BAIXA ---
    const baixar = () => {
        const arquivo = new Blob([montarIcs(eventos)], { type: "text/calendar;charset=utf-8" });
        const endereco = URL.createObjectURL(arquivo);
        const ancora = document.createElement("a");

        ancora.href = endereco;
        ancora.download = "calendario-academico-uniaura.ics";
        ancora.click();
        URL.revokeObjectURL(endereco);

        notificar("Calendário baixado. Abra o arquivo para adicioná-lo à sua agenda.", "success");
    };

    const itemDeEvento = (evento) => (
        <li key={`${evento.uid}`} className="ua-calendario__evento">
            <div>
                <strong>{evento.titulo}</strong>
                <small>{formatarPeriodo(evento)}</small>
                {evento.descricao && <small>{evento.descricao}</small>}
            </div>
            <Tag value={TIPOS[evento.tipo]?.rotulo ?? "Evento"} severity={TIPOS[evento.tipo]?.severidade} rounded />
        </li>
    );

    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-saudacao">
                <h1>Calendário</h1>
                <nav className="ua-aluno-migalhas" aria-label="Você está aqui">
                    <Link href="/portal-do-aluno/home">Home</Link>
                    <span aria-hidden="true">/</span>
                    <span aria-current="page">Calendário</span>
                </nav>
            </div>

            <div className="ua-calendario__acoes">
                <p className="muted">Feriados nacionais do Brasil e as datas importantes cadastradas pela universidade.</p>
                <Button
                    type="button"
                    label="Baixar calendário acadêmico"
                    icon="pi pi-download"
                    onClick={baixar}
                />
            </div>

            {erro && <p className="muted">Não foi possível carregar os eventos cadastrados; mostrando apenas os feriados nacionais.</p>}

            {carregando && <p className="muted">Carregando eventos cadastrados...</p>}

            {(
                <div className="ua-aluno-colunas ua-calendario__colunas">
                    <section className="ua-card ua-calendario__grande" aria-label="Calendário do mês">
                        <Calendar
                            inline
                            locale="pt"
                            value={dia}
                            onChange={(evento) => evento.value && setDia(evento.value)}
                            onViewDateChange={(evento) => setMesVisivel(evento.value)}
                            dateTemplate={modeloDoDia}
                        />

                        <ul className="ua-calendario__legenda" aria-label="Legenda">
                            {Object.entries(TIPOS).map(([tipo, dados]) => (
                                <li key={tipo}>
                                    <Tag
                                        value={dados.rotulo}
                                        severity={dados.severidade}
                                        rounded
                                    />
                                </li>
                            ))}
                        </ul>
                    </section>

                    <div className="ua-aluno-lateral">
                        <section className="ua-card" aria-labelledby="tituloDia">
                            <h2 id="tituloDia" className="ua-complementares__titulo">
                                {dia.toLocaleDateString("pt-BR", { day: "2-digit", month: "long", year: "numeric" })}
                            </h2>
                            <ul className="ua-calendario__lista">
                                {eventosDoDia.map(itemDeEvento)}
                                {eventosDoDia.length === 0 && <li className="muted">Nenhum evento neste dia.</li>}
                            </ul>
                        </section>

                        <section className="ua-card" aria-labelledby="tituloMes">
                            <h2 id="tituloMes" className="ua-complementares__titulo">
                                Datas de {mesVisivel.toLocaleDateString("pt-BR", { month: "long", year: "numeric" })}
                            </h2>
                            <ul className="ua-calendario__lista">
                                {eventosDoMes.map(itemDeEvento)}
                                {eventosDoMes.length === 0 && <li className="muted">Nenhuma data importante neste mês.</li>}
                            </ul>
                        </section>
                    </div>
                </div>
            )}
        </div>
    );
}
