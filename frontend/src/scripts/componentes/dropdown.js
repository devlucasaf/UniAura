const CLASSE_ABERTO = "aberto";

// --- CONVERTE TODOS OS SELECTS DENTRO DE .site-select ---
export function ativarDropdowns(raiz) {
    raiz.querySelectorAll(".site-select").forEach(montarDropdown);
}

// --- VALIDA OS DROPDOWNS OBRIGATÓRIOS DE UM FORMULÁRIO ---
export function validarDropdowns(formulario) {
    const pendentes = formulario.querySelectorAll('.site-select select[data-obrigatorio="true"]');

    for (const select of pendentes) {
        const caixa = select.closest(".site-select");

        if (select.value) {
            caixa.classList.remove("invalido");
            continue;
        }

        caixa.classList.add("invalido");
        caixa.querySelector(".site-dropdown-gatilho")?.focus();
        return false;
    }

    return true;
}

function montarDropdown(caixa) {
    const select = caixa.querySelector("select");

    if (!select || caixa.querySelector(".site-dropdown-gatilho")) {
        return;
    }

    if (select.required) {
        select.required = false;
        select.dataset.obrigatorio = "true";
    }

    const opcoes = Array.from(select.options);
    const idLista = `${select.id || select.name}Lista`;

    select.hidden = true;
    select.tabIndex = -1;

    const gatilho = criarGatilho(select, idLista);
    const lista = criarLista(opcoes, idLista, select);

    caixa.append(gatilho, lista);

    caixa.querySelector(":scope > .site-select-seta")?.remove();

    ligarEventos(caixa, select, gatilho, lista);
    sincronizarRotulo(select, gatilho);
}

function criarGatilho(select, idLista) {
    const gatilho = document.createElement("button");
    gatilho.type = "button";
    gatilho.className = "site-dropdown-gatilho";
    gatilho.setAttribute("role", "combobox");
    gatilho.setAttribute("aria-haspopup", "listbox");
    gatilho.setAttribute("aria-expanded", "false");
    gatilho.setAttribute("aria-controls", idLista);

    if (select.id) {
        gatilho.setAttribute("aria-labelledby", `${select.id}Rotulo ${select.id}Valor`);
    }

    gatilho.innerHTML = `
        <span class="site-dropdown-valor"${select.id ? ` id="${select.id}Valor"` : ""}></span>
        <span class="site-dropdown-seta" aria-hidden="true">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                 stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="6 9 12 15 18 9"></polyline>
            </svg>
        </span>`;

    return gatilho;
}

function criarLista(opcoes, idLista, select) {
    const lista = document.createElement("ul");
    lista.className = "site-dropdown-lista";
    lista.id = idLista;
    lista.setAttribute("role", "listbox");
    lista.hidden = true;

    opcoes.forEach((opcao, indice) => {
        const item = document.createElement("li");
        item.className = "site-dropdown-opcao";
        item.setAttribute("role", "option");
        item.dataset.valor = opcao.value;
        item.id = `${idLista}-${indice}`;
        item.textContent = opcao.textContent.trim();

        if (!opcao.value) {
            item.classList.add("placeholder");
        }

        item.setAttribute("aria-selected", String(opcao.value === select.value));
        lista.append(item);
    });

    return lista;
}

function ligarEventos(caixa, select, gatilho, lista) {
    const opcoes = Array.from(lista.children);

    const abrir = () => {
        fecharOutros(caixa);
        lista.hidden = false;
        caixa.classList.add(CLASSE_ABERTO);
        gatilho.setAttribute("aria-expanded", "true");
        destacar(indiceSelecionado(opcoes, select));
    };

    const fechar = () => {
        lista.hidden = true;
        caixa.classList.remove(CLASSE_ABERTO);
        gatilho.setAttribute("aria-expanded", "false");
        gatilho.removeAttribute("aria-activedescendant");
    };

    const escolher = (item) => {
        select.value = item.dataset.valor;
        select.dispatchEvent(new Event("change", { bubbles: true }));

        opcoes.forEach((o) => o.setAttribute("aria-selected", String(o === item)));
        caixa.classList.toggle("invalido", !select.value && select.dataset.obrigatorio === "true");

        sincronizarRotulo(select, gatilho);
        fechar();
        gatilho.focus();
    };

    const destacar = (indice) => {
        const alvo = opcoes[Math.max(0, Math.min(indice, opcoes.length - 1))];
        opcoes.forEach((o) => o.classList.toggle("ativo", o === alvo));
        gatilho.setAttribute("aria-activedescendant", alvo.id);
        alvo.scrollIntoView({ block: "nearest" });
    };

    const indiceAtivo = () => opcoes.findIndex((o) => o.classList.contains("ativo"));

    gatilho.addEventListener("click", () => {
        if (lista.hidden) {
            abrir();
        } else {
            fechar();
        }
    });

    opcoes.forEach((item) => {
        item.addEventListener("click", () => escolher(item));
        item.addEventListener("pointerenter", () => destacar(opcoes.indexOf(item)));
    });

    gatilho.addEventListener("keydown", (evento) => {
        const teclasQueAbrem = ["ArrowDown", "ArrowUp", "Enter", " "];

        if (lista.hidden) {
            if (teclasQueAbrem.includes(evento.key)) {
                evento.preventDefault();
                abrir();
            }
            return;
        }

        switch (evento.key) {
            case "ArrowDown":
                evento.preventDefault();
                destacar(indiceAtivo() + 1);
                break;
            case "ArrowUp":
                evento.preventDefault();
                destacar(indiceAtivo() - 1);
                break;
            case "Home":
                evento.preventDefault();
                destacar(0);
                break;
            case "End":
                evento.preventDefault();
                destacar(opcoes.length - 1);
                break;
            case "Enter":
            case " ":
                evento.preventDefault();
                escolher(opcoes[indiceAtivo()]);
                break;
            case "Escape":
                evento.preventDefault();
                fechar();
                break;
            case "Tab":
                fechar();
                break;
            default:
                break;
        }
    });

    document.addEventListener("click", (evento) => {
        if (!caixa.contains(evento.target)) {
            fechar();
        }
    });
}

// --- FECHA QUALQUER OUTRO DROPDOWN ABERTO NA PÁGINA ---
function fecharOutros(atual) {
    document.querySelectorAll(`.site-select.${CLASSE_ABERTO}`).forEach((caixa) => {
        if (caixa === atual) {
            return;
        }
        caixa.classList.remove(CLASSE_ABERTO);
        caixa.querySelector(".site-dropdown-lista").hidden = true;
        caixa.querySelector(".site-dropdown-gatilho")?.setAttribute("aria-expanded", "false");
    });
}

function indiceSelecionado(opcoes, select) {
    const indice = opcoes.findIndex((o) => o.dataset.valor === select.value);
    return indice >= 0 ? indice : 0;
}

// --- ESCREVE NO BOTÃO O TEXTO DA OPÇÃO ESCOLHIDA ---
function sincronizarRotulo(select, gatilho) {
    const escolhida = select.options[select.selectedIndex];
    const valor = gatilho.querySelector(".site-dropdown-valor");

    valor.textContent = escolhida ? escolhida.textContent.trim() : "";
    valor.classList.toggle("placeholder", !select.value);
}
