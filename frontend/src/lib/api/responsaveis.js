import { criarApiDeCadastro } from "@/lib/api/cadastroUniaura";

export const responsaveisApi = criarApiDeCadastro({
    pesquisar: "pesquisarResponsaveis",
    detalhar: "detalharResponsavel",
    cadastrar: "cadastrarResponsavel",
    alterar: "alterarResponsavel",
    remover: "removerResponsavel"
});
