import { api, query, corpoMultipart } from "@/lib/api";

const BASE = "/biblioteca";

// --- LIVROS ---
export const livroApi = {
    buscar: (parametros = {}) => api(`${BASE}/livros${query(parametros)}`),
    buscarPorId: (id) => api(`${BASE}/livros/${id}`),
    criar: (dados, capa) => api(`${BASE}/livros`, { metodo: "POST", corpo: corpoMultipart(dados, capa, "capa"), multipart: true }),
    atualizar: (id, dados, capa) => api(`${BASE}/livros/${id}`, { metodo: "PUT", corpo: corpoMultipart(dados, capa, "capa"), multipart: true }),
    deletar: (id) => api(`${BASE}/livros/${id}`, { metodo: "DELETE" })
};

// --- EXEMPLARES ---
export const exemplarApi = {
    listarPorLivro: (livroId, parametros = {}) => api(`${BASE}/exemplares/livro/${livroId}${query(parametros)}`),
    buscarPorId: (id) => api(`${BASE}/exemplares/${id}`),
    buscarPorCodigo: (codigo) => api(`${BASE}/exemplares/codigo/${encodeURIComponent(codigo)}`),
    gerarCodigoBarras: () => api(`${BASE}/exemplares/gerar-codigo-barras`),
    criar: (dto) => api(`${BASE}/exemplares`, { metodo: "POST", corpo: dto }),
    atualizar: (id, dto, status) => api(`${BASE}/exemplares/${id}${status ? `?status=${status}` : ""}`, { metodo: "PUT", corpo: dto }),
    deletar: (id) => api(`${BASE}/exemplares/${id}`, { metodo: "DELETE" })
};

// --- EMPRÉSTIMOS ---
export const emprestimoApi = {
    registrar: (dto) => api(`${BASE}/emprestimos`, { metodo: "POST", corpo: dto }),
    devolver: (id) => api(`${BASE}/emprestimos/${id}/devolver`, { metodo: "POST" }),
    renovar: (id) => api(`${BASE}/emprestimos/${id}/renovar`, { metodo: "POST" }),
    buscarPorId: (id) => api(`${BASE}/emprestimos/${id}`),
    listarPorUsuario: (usuarioId, parametros = {}) => api(`${BASE}/emprestimos/usuario/${usuarioId}${query(parametros)}`)
};

// --- MULTAS ---
export const multaApi = {
    listar: (parametros = {}) => api(`${BASE}/multas${query(parametros)}`),
    pendentesDoUsuario: (usuarioId) => api(`${BASE}/multas/usuario/${usuarioId}/pendentes`),
    pagar: (id) => api(`${BASE}/multas/${id}/pagar`, { metodo: "POST" }),
    cancelar: (id) => api(`${BASE}/multas/${id}/cancelar`, { metodo: "POST" })
};

// --- RESERVAS ---
export const reservaApi = {
    reservar: (dto) => api(`${BASE}/reservas`, { metodo: "POST", corpo: dto }),
    cancelar: (id) => api(`${BASE}/reservas/${id}`, { metodo: "DELETE" }),
    filaDoLivro: (livroId) => api(`${BASE}/reservas/livro/${livroId}/fila`),
    doUsuario: (usuarioId) => api(`${BASE}/reservas/usuario/${usuarioId}`)
};

// --- CONFIGURAÇÕES ---
export const configuracaoBibliotecaApi = {
    obter: () => api(`${BASE}/configuracoes`),
    atualizar: (dto) => api(`${BASE}/configuracoes`, { metodo: "PUT", corpo: dto })
};
