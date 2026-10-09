import { addLocale } from "primereact/api";

// --- IDIOMA PORTUGUÊS DOS CALENDÁRIOS DO PRIMEREACT ---
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
