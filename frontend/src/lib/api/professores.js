import { criarApiDeCadastro } from "@/lib/api/cadastroUniaura";

export const professoresApi = criarApiDeCadastro({
    pesquisar: "pesquisarProfessores",
    detalhar: "detalharProfessor",
    cadastrar: "cadastrarProfessor",
    alterar: "alterarProfessor",
    remover: "removerProfessor"
});
