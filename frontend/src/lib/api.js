import { obterToken } from "./auth";

const BASE_API = "/api";

let processingRef = null;

// --- REGISTRA O ProcessingContext (notifyStart/notifyEnd) PARA ESTA FUNÇÃO PODER SINALIZAR O ProcessingIndicator ---
export function registrarProcessing(processing) {
    processingRef = processing;
}

// --- CHAMA A API ANEXANDO O ACCESS TOKEN OIDC ATUAL (KEYCLOAK) QUANDO HOUVER ---
export async function api(caminho, { metodo = "GET", corpo, cabecalhos = {}, multipart = false } = {}) {
    const opcoes = {
        method: metodo,
        headers: {
            ...cabecalhos
        }
    };

    const token = obterToken();
    if (token) {
        opcoes.headers.Authorization = `Bearer ${token}`;
    }

    if (corpo !== undefined && corpo !== null) {
        if (multipart) {
            opcoes.body = corpo;
        } else {
            opcoes.headers["Content-Type"] = "application/json";
            opcoes.body = JSON.stringify(corpo);
        }
    }

    processingRef?.notifyStart();
    try {
        const resposta = await fetch(`${BASE_API}${caminho}`, opcoes);
        const texto = await resposta.text();
        const dados = texto ? JSON.parse(texto) : null;

        if (resposta.status === 401) {
            throw new Error((dados && dados.message) || "Sessão expirada. Faça login novamente.");
        }

        if (!resposta.ok) {
            throw new Error((dados && (dados.message || dados.error)) || `Erro ${resposta.status}`);
        }

        return resposta.status === 204 ? null : dados;
    } finally {
        processingRef?.notifyEnd();
    }
}

// --- MONTA UMA QUERY STRING IGNORANDO VALORES VAZIOS ---
export function query(parametros) {
    const p = new URLSearchParams();
    Object.entries(parametros || {}).forEach(([chave, valor]) => {
        if (valor !== undefined && valor !== null && valor !== "") {
            p.append(chave, valor);
        }
    });
    const texto = p.toString();
    return texto ? `?${texto}` : "";
}

// --- MONTA O FORMDATA MULTIPART ---
export function corpoMultipart(dadosObjeto, arquivo, campoArquivo = "arquivo", campoDados = "dados") {
    const formData = new FormData();
    formData.append(campoDados, new Blob([JSON.stringify(dadosObjeto)], { type: "application/json" }));
    if (arquivo) {
        formData.append(campoArquivo, arquivo);
    }
    return formData;
}
