const BASE_API = "/uniaura/app/api";

let processamentoReferencia = null;

// --- REGISTRA O ProcessingContext PARA ESTA FUNÇÃO PODER SINALIZAR O ProcessingIndicator ---
export function registrarProcessing(processing) {
    processamentoReferencia = processing;
}

// --- CHAMA A API ANEXANDO O ACCESS TOKEN DA SESSÃO QUANDO HOUVER ---
export async function api(caminho, { metodo = "GET", corpo, cabecalhos = {}, multipart = false } = {}) {
    const opcoes = {
        method: metodo,
        headers: {
            ...cabecalhos
        }
    };

    const token = typeof window !== "undefined" ? localStorage.getItem("token") : null;
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

    processamentoReferencia?.notifyStart();
    try {
        const resposta = await fetch(`${BASE_API}${caminho}`, opcoes);
        const texto = await resposta.text();
        let dados = null;
        try {
            dados = texto ? JSON.parse(texto) : null;
        } catch {
            throw new Error(`Não foi possível comunicar com o servidor (resposta inesperada, código ${resposta.status}).`);
        }

        if (resposta.status === 401) {
            ["token", "refreshToken", "user", "usuarioId"].forEach((chave) => localStorage.removeItem(chave));
            if (typeof window !== "undefined" && !window.location.pathname.endsWith("/login")) {
                window.location.href = "/uniaura/app/login";
            }
            throw new Error((dados && dados.message) || "Sessão expirada. Faça login novamente.");
        }

        if (!resposta.ok) {
            const mensagensDosCampos = (dados?.fieldMessages || []).map((item) => item.message).join(" ");

            throw new Error(mensagensDosCampos || (dados && (dados.message || dados.error)) || `Erro ${resposta.status}`);
        }

        return resposta.status === 204 ? null : dados;
    } finally {
        processamentoReferencia?.notifyEnd();
    }
}

// --- MONTA UMA QUERY STRING IGNORANDO VALORES VAZIOS ---
export function query(parametros) {
    const parametrosDaUrl = new URLSearchParams();
    Object.entries(parametros || {}).forEach(([chave, valor]) => {
        if (valor !== undefined && valor !== null && valor !== "") {
            parametrosDaUrl.append(chave, valor);
        }
    });
    const texto = parametrosDaUrl.toString();
    return texto ? `?${texto}` : "";
}

// --- MONTA O FORMDATA MULTIPART ---
export function corpoMultipart(dadosObjeto, arquivo, campoArquivo = "arquivo", campoDados = "dados") {
    const dadosDoFormulario = new FormData();
    dadosDoFormulario.append(campoDados, new Blob([JSON.stringify(dadosObjeto)], { type: "application/json" }));
    if (arquivo) {
        dadosDoFormulario.append(campoArquivo, arquivo);
    }
    return dadosDoFormulario;
}
