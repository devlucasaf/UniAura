import { montarPaginaDeCurso } from "./graduacao/_curso.js";
import { ativarDropdowns, validarDropdowns } from "../../componentes/dropdown.js";

// --- MONTA A PÁGINA PÚBLICA DE CONTATO ---
export function montar(raiz) {
    // --- REAPROVEITA O CABEÇALHO, O TEMA E AS ANIMAÇÕES DAS DEMAIS PÁGINAS DO SITE ---
    montarPaginaDeCurso(raiz);

    // --- TROCA O SELECT NATIVO PELO DROPDOWN DO SISTEMA ---
    ativarDropdowns(raiz);

    aplicarMascaraDeTelefone(raiz);
    configurarFormulario(raiz);
}

// --- VALIDA E ENVIA A MENSAGEM ---
function configurarFormulario(raiz) {
    const formulario = raiz.querySelector("#formContato");

    if (!formulario) {
        return;
    }

    const sucesso = raiz.querySelector("#ctMensagemOk");
    const erro = raiz.querySelector("#ctErro");
    const botao = raiz.querySelector("#btnEnviarContato");

    formulario.addEventListener("submit", (evento) => {
        evento.preventDefault();

        esconder(sucesso);
        esconder(erro);

        if (!formulario.checkValidity()) {
            formulario.reportValidity();
            mostrar(erro, "Revise os campos destacados antes de enviar.");
            return;
        }

        // --- O DROPDOWN É VALIDADO À PARTE: O SELECT OCULTO NÃO ENTRA NO checkValidity ---
        if (!validarDropdowns(formulario)) {
            mostrar(erro, "Escolha o assunto da mensagem.");
            return;
        }

        botao.disabled = true;
        botao.textContent = "Enviando...";

        // --- AINDA NAO HA ENDPOINT DE CONTATO NO BACKEND: NADA E PERSISTIDO ---
        const primeiroNome = formulario.nome.value.trim().split(" ")[0];

        mostrar(sucesso,
            `Mensagem enviada, ${primeiroNome}! Nossa equipe responde no e-mail informado ` +
            "em até um dia útil.");

        formulario.reset();
        botao.disabled = false;
        botao.textContent = "Enviar mensagem";
        sucesso.scrollIntoView({ behavior: "smooth", block: "center" });
    });
}

// --- FORMATA O TELEFONE ENQUANTO O VISITANTE DIGITA ---
function aplicarMascaraDeTelefone(raiz) {
    const telefone = raiz.querySelector("#ctTelefone");

    telefone?.addEventListener("input", () => {
        const digitos = telefone.value.replace(/\D/g, "").slice(0, 11);
        telefone.value = digitos
            .replace(/(\d{2})(\d)/, "($1) $2")
            .replace(/(\d{5})(\d)/, "$1-$2");
    });
}

function mostrar(elemento, texto) {
    if (!elemento) {
        return;
    }
    elemento.textContent = texto;
    elemento.hidden = false;
}

function esconder(elemento) {
    if (!elemento) {
        return;
    }
    elemento.hidden = true;
    elemento.textContent = "";
}
