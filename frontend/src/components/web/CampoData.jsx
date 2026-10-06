"use client";

import { useState }     from "react";
import { Calendar }     from "primereact/calendar";
import { Dropdown }     from "primereact/dropdown";
import { Button }       from "primereact/button";
import { addLocale }    from "primereact/api";

// --- SELETORES DE MÊS E ANO DO CABEÇALHO, COM DROPDOWN DO PRIMEREACT ---
const SeletorMes = (opcoes) => (
    <Dropdown
        value={opcoes.value}
        options={opcoes.options}
        optionLabel="label"
        optionValue="value"
        onChange={(e) => opcoes.onChange(e.originalEvent ?? e, e.value)}
        appendTo="self"
        className="ua-datepicker-select"
    />
);

const SeletorAno = (opcoes) => (
    <Dropdown
        value={opcoes.value}
        options={opcoes.options}
        optionLabel="label"
        optionValue="value"
        onChange={(e) => opcoes.onChange(e.originalEvent ?? e, e.value)}
        appendTo="self"
        className="ua-datepicker-select"
    />
);

// --- IDIOMA PORTUGUÊS DO CALENDÁRIO ---
addLocale("pt", {
    firstDayOfWeek: 0,
    dayNames: [
        "domingo",
        "segunda-feira",
        "terça-feira",
        "quarta-feira",
        "quinta-feira",
        "sexta-feira",
        "sábado"
    ],
    dayNamesShort: [
        "dom",
        "seg",
        "ter",
        "qua",
        "qui",
        "sex",
        "sáb"
    ],
    dayNamesMin: [
        "D",
        "S",
        "T",
        "Q",
        "Q",
        "S",
        "S"
    ],
    monthNames: [
        "janeiro",
        "fevereiro",
        "março",
        "abril",
        "maio",
        "junho",
        "julho",
        "agosto",
        "setembro",
        "outubro",
        "novembro",
        "dezembro"
    ],
    monthNamesShort: [
        "jan",
        "fev",
        "mar",
        "abr",
        "mai",
        "jun",
        "jul",
        "ago",
        "set",
        "out",
        "nov",
        "dez"
    ],
    today: "Hoje",
    clear: "Limpar",
    weekHeader: "Sem",
    dateFormat: "dd/mm/yy",
    chooseDate: "Escolha a data",
    prevMonth: "Mês anterior",
    nextMonth: "Próximo mês",
    prevYear: "Ano anterior",
    nextYear: "Próximo ano"
});

// --- CONVERTE A DATA PARA ISO, O MESMO FORMATO QUE ENTREGA AO FORMULÁRIO ---
const paraIso = (data) => {
    if (!data) {
        return "";
    }

    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const dia = String(data.getDate()).padStart(2, "0");

    return `${ano}-${mes}-${dia}`;
};

// --- CAMPO DE DATA COM MÁSCARA, CALENDÁRIO COM MÊS E ANO ---
export default function CampoData({ id, name, onChange, invalido = false, yearRange = "1920:2030", required = false }) {
    const [data, setData] = useState(null);

    const limiteHoje = new Date();
    limiteHoje.setHours(23, 59, 59, 999);

    // --- DEFINE A DATA E INFORMA O FORMULÁRIO ---
    const aplicar = (novaData) => {
        setData(novaData);
        onChange?.(paraIso(novaData));
    };

    // --- RODAPÉ COM BOTÕES ---
    const rodape = () => (
        <div className="ua-calendario-rodape">
            <Button
                type="button"
                label="Hoje"
                text
                rounded
                className="ua-calendario-botao"
                onClick={() => aplicar(new Date())}
            />
            <Button
                type="button"
                label="Limpar"
                text
                rounded
                className="ua-calendario-botao"
                onClick={() => aplicar(null)}
            />
        </div>
    );

    return (
        <div className="ua-campo-data">
            <Calendar
                inputId={id}
                value={data}
                onChange={(evento) => aplicar(evento.value)}
                locale="pt"
                dateFormat="dd/mm/yy"
                mask="99/99/9999"
                placeholder="dd/mm/aaaa"
                showIcon
                footerTemplate={rodape}
                monthNavigator
                yearNavigator
                monthNavigatorTemplate={SeletorMes}
                yearNavigatorTemplate={SeletorAno}
                yearRange={yearRange}
                maxDate={limiteHoje}
                className={invalido ? "p-invalid" : ""}
                aria-required={required}
            />
            <input
                type="hidden"
                name={name}
                value={paraIso(data)}
            />
        </div>
    );
}
