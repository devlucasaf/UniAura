const BASE_API = "/api";
const CHAVE_TOKEN = "token";

export async function api(caminho, { metodo = "GET", corpo, cabecalhos = {}, multipart = false } = {}) {
    const opcoes = {
        method: metodo,
        headers: {
            ...cabecalhos
        }
    };

    const token = typeof window !== "undefined" ? localStorage.getItem(CHAVE_TOKEN) : null;
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

    const resposta = await fetch(`${BASE_API}${caminho}`, opcoes);
    const texto = await resposta.text();
    const dados = texto ? JSON.parse(texto) : null;

    if (resposta.status === 401) {
        ["token", "refreshToken", "user", "usuarioId"].forEach((chave) => localStorage.removeItem(chave));
        if (typeof window !== "undefined" && window.location.pathname !== "/login") {
            window.location.href = "/login";
        }
        throw new Error((dados && dados.message) || "Sessão expirada. Faça login novamente.");
    }

    if (!resposta.ok) {
        throw new Error((dados && (dados.message || dados.error)) || `Erro ${resposta.status}`);
    }

    return resposta.status === 204 ? null : dados;
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

// --- MONTA O FORMDATA MULTIPART (DADOS JSON + ARQUIVO OPCIONAL) ---
export function corpoMultipart(dadosObjeto, arquivo, campoArquivo = "arquivo", campoDados = "dados") {
    const formData = new FormData();
    formData.append(campoDados, new Blob([JSON.stringify(dadosObjeto)], { type: "application/json" }));
    if (arquivo) {
        formData.append(campoArquivo, arquivo);
    }
    return formData;
}
