import { api, query } from "@/lib/api";

const BASE = "/funcionarios";

export const funcionariosApi = {
    listar: (parametros) => api(`${BASE}${query(parametros)}`),
    buscarPorId: (id) => api(`${BASE}/${id}`),
    criar: (dados) => api(BASE, { metodo: "POST", corpo: dados }),
    atualizar: (id, dados) => api(`${BASE}/${id}`, { metodo: "PUT", corpo: dados }),
    excluir: (id) => api(`${BASE}/${id}`, { metodo: "DELETE" })
};
