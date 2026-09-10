import { montarPaginaDeCurso } from "./graduacao/_curso.js";

// --- ALFABETO SEM VOGAIS E SEM CARACTERES AMBÍGUOS, IGUAL AO GERADOR DE PROTOCOLO DO BACKEND ---
const ALFABETO_PROTOCOLO = "23456789BCDFGHJKLMNPQRSTVWXZ";

const TAMANHO_SUFIXO = 8;

// --- MONTA A PÁGINA PÚBLICA DE MATRÍCULAS ---
export function montar(raiz) {
    montarPaginaDeCurso(raiz);

    aplicarMascaras(raiz);
    configurarFormulario(raiz);
}

// --- VALIDA E ENVIA A INSCRIÇÃO ---
function configurarFormulario(raiz) {
    const formulario = raiz.querySelector("#formMatricula");

    if (!formulario) {
        return;
    }

    const mensagem = raiz.querySelector("#matMensagem");
    const erro = raiz.querySelector("#matErro");
    const botao = raiz.querySelector("#btnEnviarMatricula");

    formulario.addEventListener("submit", (evento) => {
        evento.preventDefault();

        esconder(mensagem);
        esconder(erro);

        if (!formulario.checkValidity()) {
            formulario.reportValidity();
            mostrar(erro, "Revise os campos destacados antes de enviar.");
            return;
        }

        const cpf = formulario.cpf.value.replace(/\D/g, "");
        if (cpf.length !== 11) {
            mostrar(erro, "Informe um CPF com 11 dígitos.");
            formulario.cpf.focus();
            return;
        }

        if (!maiorDeIdadeOuResponsavel(formulario.dataNascimento.value)) {
            mostrar(erro, "Informe uma data de nascimento válida.");
            formulario.dataNascimento.focus();
            return;
        }

        botao.disabled = true;
        botao.textContent = "Enviando...";

        const protocolo = gerarProtocolo();

        mostrar(mensagem,
            `Inscrição registrada! Guarde o seu protocolo: ${protocolo}. ` +
            "Você receberá por e-mail as instruções para o envio da documentação.");

        formulario.reset();
        botao.disabled = false;
        botao.textContent = "Enviar inscrição";
        mensagem.scrollIntoView({ behavior: "smooth", block: "center" });
    });
}

// --- FORMATA CPF E TELEFONE ENQUANTO O CANDIDATO DIGITA ---
function aplicarMascaras(raiz) {
    const cpf = raiz.querySelector("#matCpf");
    const telefone = raiz.querySelector("#matTelefone");

    cpf?.addEventListener("input", () => {
        const digitos = cpf.value.replace(/\D/g, "").slice(0, 11);
        cpf.value = digitos
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})\.(\d{3})(\d)/, "$1.$2.$3")
            .replace(/(\d{3})\.(\d{3})\.(\d{3})(\d)/, "$1.$2.$3-$4");
    });

    telefone?.addEventListener("input", () => {
        const digitos = telefone.value.replace(/\D/g, "").slice(0, 11);
        telefone.value = digitos
            .replace(/(\d{2})(\d)/, "($1) $2")
            .replace(/(\d{5})(\d)/, "$1-$2");
    });
}

// --- RECUSA DATAS NO FUTURO E IDADES IMPLAUSÍVEIS ---
function maiorDeIdadeOuResponsavel(valor) {
    if (!valor) {
        return false;
    }

    const nascimento = new Date(valor);
    if (Number.isNaN(nascimento.getTime())) {
        return false;
    }

    const hoje = new Date();
    const anos = (hoje - nascimento) / (1000 * 60 * 60 * 24 * 365.25);
    return anos > 14 && anos < 120;
}

// --- GERA UM PROTOCOLO NO FORMATO MAT-ANO-SUFIXO ---
function gerarProtocolo() {
    let sufixo = "";
    for (let i = 0; i < TAMANHO_SUFIXO; i++) {
        sufixo += ALFABETO_PROTOCOLO.charAt(Math.floor(Math.random() * ALFABETO_PROTOCOLO.length));
    }
    return `MAT-${new Date().getFullYear()}-${sufixo}`;
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
