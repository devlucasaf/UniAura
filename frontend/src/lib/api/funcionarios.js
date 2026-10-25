import { criarApiDeCadastro } from "@/lib/api/cadastroUniaura";

export const funcionariosApi = criarApiDeCadastro({
    pesquisar: "pesquisarFuncionarios",
    detalhar: "detalharFuncionario",
    cadastrar: "cadastrarFuncionario",
    alterar: "alterarFuncionario",
    remover: "removerFuncionario"
});
