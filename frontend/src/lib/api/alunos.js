import { criarApiDeCadastro } from "@/lib/api/cadastroUniaura";

export const alunosApi = criarApiDeCadastro({
    pesquisar: "pesquisarAlunos",
    detalhar: "detalharAluno",
    cadastrar: "cadastrarAluno",
    alterar: "alterarAluno",
    remover: "removerAluno"
});