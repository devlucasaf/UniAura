const OIDC_AUTHORITY = "http://localhost:8081/realms/uniaura";
const OIDC_CLIENT_ID = "uniaura-frontend";
const CHAVE_OIDC_USER = `oidc.user:${OIDC_AUTHORITY}:${OIDC_CLIENT_ID}`;

// --- ROTA DE DASHBOARD DE CADA PERFIL ---
export const DASHBOARD_POR_PERFIL = {
    ALUNO: "/aluno/dashboard",
    PROFESSOR: "/professor/dashboard",
    COORDENADOR: "/coordenacao/dashboard",
    SECRETARIA: "/secretaria/dashboard",
    BIBLIOTECARIO: "/biblioteca/dashboard",
    FINANCEIRO: "/financeiro/dashboard",
    RESPONSAVEL: "/responsavel/dashboard",
    ADMIN: "/admin/dashboard"
};

// --- RESOLVE A ROTA DO DASHBOARD DE UM PERFIL ---
export function dashboardDoPerfil(perfil) {
    return DASHBOARD_POR_PERFIL[perfil] || "/login";
}

// --- LÊ O USUÁRIO OIDC QUE A BIBLIOTECA cloudsupport-react (oidc-client-ts) ARMAZENA NO SESSIONSTORAGE ---
function lerUsuarioOidc() {
    if (typeof window === "undefined") {
        return null;
    }
    const bruto = sessionStorage.getItem(CHAVE_OIDC_USER);
    return bruto ? JSON.parse(bruto) : null;
}

// --- RETORNA O ACCESS TOKEN ATUAL (USADO PELO wrapper de fetch em lib/api.js) ---
export function obterToken() {
    return lerUsuarioOidc()?.access_token || null;
}

// --- RETORNA OS DADOS DO USUÁRIO LOGADO, A PARTIR DAS CLAIMS DO TOKEN OIDC ---
export function obterUsuario() {
    const profile = lerUsuarioOidc()?.profile;
    if (!profile) {
        return null;
    }
    const roles = Array.isArray(profile.roles) ? profile.roles : [];
    return {
        id: profile.sub,
        nome: profile.name || profile.preferred_username,
        email: profile.email,
        role: roles[0],
        roles
    };
}

// --- INDICA SE HÁ SESSÃO ATIVA E NÃO EXPIRADA ---
export function estaAutenticado() {
    const oidcUser = lerUsuarioOidc();
    return !!oidcUser && !oidcUser.expired;
}

// --- VERIFICA SE O USUÁRIO POSSUI ALGUM DOS PERFIS INFORMADOS ---
export function possuiPerfil(perfis) {
    const usuario = obterUsuario();
    if (!usuario) {
        return false;
    }
    const lista = Array.isArray(perfis) ? perfis : [perfis];
    return lista.some((perfil) => usuario.roles.includes(perfil));
}
