import { api, query } from "@/lib/api";

const BASE = "/responsaveis";

export const responsaveisApi = {
    listar: (parametros) => api(`${BASE}${query(parametros)}`),
    buscarPorId: (id) => api(`${BASE}/${id}`),
    criar: (dados) => api(BASE, { metodo: "POST", corpo: dados }),
    atualizar: (id, dados) => api(`${BASE}/${id}`, { metodo: "PUT", corpo: dados }),
    excluir: (id) => api(`${BASE}/${id}`, { metodo: "DELETE" })
};
