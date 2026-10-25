import { autenticarUsuario } from "@/app/remotes/uniaura/autenticacao/autenticarUsuarioRemote";

const CHAVE_TOKEN = "token";
const CHAVE_REFRESH = "refreshToken";
const CHAVE_USUARIO = "user";

// --- ROTA DE DASHBOARD DE CADA PERFIL ---
export const DASHBOARD_POR_PERFIL = {
    ALUNO: "/portal-do-aluno/home",
    PROFESSOR: "/professor/dashboard",
    COORDENADOR: "/coordenacao/dashboard",
    SECRETARIA: "/secretaria/dashboard",
    BIBLIOTECARIO: "/biblioteca/dashboard",
    FINANCEIRO: "/financeiro/dashboard",
    RESPONSAVEL: "/responsavel/dashboard",
    ADMIN: "/admin/dashboard"
};

// --- ROTA DA TELA DE LOGIN DE CADA PERFIL ---
export const LOGIN_POR_PERFIL = {
    ALUNO: "/portal-do-aluno/login",
    PROFESSOR: "/professor/login",
    COORDENADOR: "/coordenacao/login",
    SECRETARIA: "/secretaria/login",
    BIBLIOTECARIO: "/biblioteca/login",
    FINANCEIRO: "/financeiro/login",
    RESPONSAVEL: "/responsavel/login",
    ADMIN: "/admin/login"
};

// --- RESOLVE A ROTA DA TELA DE LOGIN DE UM PERFIL ---
export function loginDoPerfil(perfil) {
    return LOGIN_POR_PERFIL[perfil] || "/login";
}

// --- RESOLVE A ROTA DO DASHBOARD DE UM PERFIL ---
export function dashboardDoPerfil(perfil) {
    return DASHBOARD_POR_PERFIL[perfil] || "/login";
}

// --- AUTENTICA NO BACKEND E GRAVA A SESSÃO ---
export async function autenticar(email, senha) {
    const resposta = await autenticarUsuario(email, senha);

    if (!resposta?.token || !resposta?.usuario) {
        throw new Error("Resposta de login inválida do servidor.");
    }

    // --- O BACKEND DEVOLVE O USUÁRIO NO CAMPO usuario ---
    gravarSessao({ token: resposta.token, refreshToken: resposta.refreshToken, usuario: resposta.usuario });
    return resposta.usuario;
}

// --- GRAVA TOKENS E DADOS DO USUÁRIO ---
export function gravarSessao({ token, refreshToken, usuario }) {
    localStorage.setItem(CHAVE_TOKEN, token);
    if (refreshToken) {
        localStorage.setItem(CHAVE_REFRESH, refreshToken);
    }

    if (usuario) {
        localStorage.setItem(CHAVE_USUARIO, JSON.stringify(usuario));
        if (usuario.uid) {
            localStorage.setItem("usuarioId", usuario.uid);
        }
    }
}

// --- ENCERRA A SESSÃO ---
export function encerrarSessao() {
    [CHAVE_TOKEN, CHAVE_REFRESH, CHAVE_USUARIO, "usuarioId"].forEach((chave) => localStorage.removeItem(chave));
}

// --- RETORNA O ACCESS TOKEN ARMAZENADO ---
export function obterToken() {
    if (typeof window === "undefined") {
        return null;
    }
    return localStorage.getItem(CHAVE_TOKEN);
}

// --- RETORNA O USUÁRIO ARMAZENADO ---
export function obterUsuario() {
    if (typeof window === "undefined") {
        return null;
    }

    try {
        const bruto = localStorage.getItem(CHAVE_USUARIO);
        return bruto ? JSON.parse(bruto) : null;
    } catch {
        return null;
    }
}

// --- INDICA SE HÁ SESSÃO ATIVA ---
export function estaAutenticado() {
    if (typeof window === "undefined") {
        return false;
    }

    const valida = !!obterToken() && !!obterUsuario()?.role;
    if (!valida && (localStorage.getItem(CHAVE_TOKEN) || localStorage.getItem(CHAVE_USUARIO))) {
        encerrarSessao();
    }
    return valida;
}

// --- VERIFICA SE O USUÁRIO POSSUI ALGUM DOS PERFIS INFORMADOS ---
export function possuiPerfil(perfis) {
    const usuario = obterUsuario();
    if (!usuario) {
        return false;
    }

    const lista = Array.isArray(perfis) ? perfis : [perfis];
    return lista.includes(usuario.role);
}
