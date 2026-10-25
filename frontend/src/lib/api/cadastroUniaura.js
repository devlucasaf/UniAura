import { api, query } from "@/lib/api";

// --- TRANSFORMA O FORMATO DA ORDENAÇÃO DAS FUNCIONALIDADES ---
function converterOrdem(ordenacao) {
    if (!ordenacao) {
        return undefined;
    }

    const [campo, direcao] = ordenacao.split(",");
    if (direcao === "desc") {
        return `-${campo}`;
    }
    return `+${campo}`;
}

// --- DEIXA O ITEM NO FORMATO QUE A PÁGINA DE CADASTRO ESPERA ---
function converterItem(item) {
    const { uid, nome, email, cpf, telefone, dataNascimento, ...resto } = item;

    return {
        ...resto,
        id: uid,
        uid,
        usuario: { nome, email, cpf, telefone, dataNascimento }
    };
}

// --- MONTA A API DE UM CADASTRO SOBRE AS FUNCIONALIDADES DO BACKEND ---
export function criarApiDeCadastro({ pesquisar, detalhar, cadastrar, alterar, remover }) {
    return {
        listar: async (parametros = {}) => {
            const { page = 0, size = 10, sort, ...filtros } = parametros;

            const resposta = await api(`/uniaura/${pesquisar}.v1${query({
                ...filtros,
                de: page * size,
                qtd: size,
                ordem: converterOrdem(sort)
            })}`);

            return {
                content: (resposta.lista || []).map(converterItem),
                totalElements: resposta.total,
                number: page,
                size
            };
        },

        buscarPorId: async (uid) => converterItem(await api(`/uniaura/${detalhar}.v1${query({ uid })}`)),

        criar: (dados) => api(`/uniaura/${cadastrar}.v1`, { metodo: "POST", corpo: dados }),

        atualizar: (uid, dados) => api(`/uniaura/${alterar}.v1`, { metodo: "POST", corpo: { uid, ...dados } }),

        excluir: (uid) => api(`/uniaura/${remover}.v1`, { metodo: "POST", corpo: { uid } })
    };
}
