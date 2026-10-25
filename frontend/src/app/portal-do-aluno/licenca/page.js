"use client";

import MolduraAluno from "@/screens/portal-do-aluno/MolduraAluno";
import PainelAluno  from "@/screens/portal-do-aluno/PainelAluno";

// --- LICENÇA DE USO DO PORTAL DO ALUNO ---
export default function AlunoLicencaPage() {
    return (
        <MolduraAluno titulo="Licença de uso">
            <div className="ua-aluno-pagina-estreita">
                <PainelAluno id="tituloLicenca" titulo="Licença de uso" icone="pi pi-file">
                    <div className="ua-aluno-texto">
                        <p>
                            O UniAura é um sistema acadêmico de demonstração. Todos os nomes, e-mails, empresas e dados
                            exibidos são fictícios e não representam pessoas ou instituições reais.
                        </p>
                        <h3>Uso do portal</h3>
                        <p>
                            O acesso é pessoal e intransferível. Não compartilhe sua senha nem o código de autorização
                            de recuperação de senha.
                        </p>
                        <h3>Conteúdo</h3>
                        <p>
                            Marcas, textos e imagens do portal pertencem aos seus respectivos autores. O código-fonte do
                            projeto está disponível no repositório oficial, sujeito à licença nele indicada.
                        </p>
                        <h3>Responsabilidade</h3>
                        <p>O sistema é fornecido como está, sem garantias, para fins educacionais.</p>
                    </div>
                </PainelAluno>
            </div>
        </MolduraAluno>
    );
}
