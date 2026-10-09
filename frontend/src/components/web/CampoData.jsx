"use client";

import { useState }     from "react";
import { Calendar }     from "primereact/calendar";
import { Dropdown }     from "primereact/dropdown";
import { Button }       from "primereact/button";
import "@/lib/localePt";

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

// --- CONVERTE aaaa-mm-dd EM Date NO FUSO LOCAL ---
const deIso = (iso) => {
    if (!iso) {
        return null;
    }
    const [ano, mes, dia] = iso.split("-").map(Number);
    return new Date(ano, mes - 1, dia);
};

// --- CAMPO DE DATA COM MÁSCARA, CALENDÁRIO COM MÊS E ANO ---
export default function CampoData({ id, name, onChange, invalido = false, yearRange = "1920:2030", required = false, valorInicial = "" }) {
    const [data, setData] = useState(() => deIso(valorInicial));

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
