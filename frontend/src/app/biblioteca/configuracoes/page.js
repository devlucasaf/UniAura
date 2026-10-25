"use client";

import { useEffect, useState }                  from "react";
import { InputNumber }                          from "primereact/inputnumber";
import { Button }                               from "primereact/button";
import { Message }                              from "primereact/message";
import AppShell                                 from "@/components/interno/AppShell";
import { consultarConfiguracaoDaBiblioteca }    from "@/app/remotes/uniaura/biblioteca/consultarConfiguracaoDaBibliotecaRemote";
import { alterarConfiguracaoDaBiblioteca }      from "@/app/remotes/uniaura/biblioteca/alterarConfiguracaoDaBibliotecaRemote";

const CAMPOS = [
    "prazoEmprestimoAluno",
    "prazoEmprestimoProfessor",
    "maximosEmprestimosSimultaneos",
    "maximasRenovacoes",
    "valorMultaDia"
];

const FORMULARIO_VAZIO = {
    prazoEmprestimoAluno: "",
    prazoEmprestimoProfessor: "",
    maximosEmprestimosSimultaneos: "",
    maximasRenovacoes: "",
    valorMultaDia: ""
};

// --- COMPONENTE DE CONFIGURAÇÕES DA BIBLIOTECA ---
export default function BibliotecaConfiguracoesPage() {
    const [formulario,  setFormulario]  = useState(FORMULARIO_VAZIO);
    const [mensagem,    setMensagem]    = useState({ texto: "", tipo: "" });

    // --- CARREGA AS CONFIGURAÇÕES ATUAIS DA API ---
    useEffect(() => {
        (async () => {
            try {
                const configuracao = await consultarConfiguracaoDaBiblioteca();
                const novo = { ...FORMULARIO_VAZIO };
                CAMPOS.forEach((campo) => { novo[campo] = configuracao[campo]; });
                setFormulario(novo);
            } catch (erro) {
                setMensagem({
                    texto: erro.message,
                    tipo: "error"
                });
            }
        })();
    }, []);

    // --- SALVA AS CONFIGURAÇÕES ALTERADAS NA API ---
    async function salvar(evento) {
        evento.preventDefault();
        try {
            const dados = {};
            CAMPOS.forEach((campo) => { dados[campo] = Number(formulario[campo]); });
            await alterarConfiguracaoDaBiblioteca(dados);
            setMensagem({
                texto: "Salvo.",
                tipo: "ok"
            });
        } catch (erro) {
            setMensagem({
                texto: erro.message,
                tipo: "error"
            });
        }
    }

    // --- ATUALIZA UM CAMPO NUMÉRICO DO FORMULÁRIO ---
    function atualizar(nome, valor) {
        setFormulario({ ...formulario, [nome]: valor ?? "" });
    }

    return (
        <AppShell titulo="Biblioteca — Configurações" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Parâmetros gerais</h2>
                <form className="form-grid" onSubmit={salvar}>
                    <div className="field">Prazo empréstimo aluno (dias)
                        <InputNumber
                            required
                            min={1}
                            value={formulario.prazoEmprestimoAluno === "" ? null : Number(formulario.prazoEmprestimoAluno)}
                            onValueChange={(evento) => atualizar("prazoEmprestimoAluno", evento.value)}
                        />
                    </div>
                    <div className="field">Prazo empréstimo professor (dias)
                        <InputNumber
                            required
                            min={1}
                            value={formulario.prazoEmprestimoProfessor === "" ? null : Number(formulario.prazoEmprestimoProfessor)}
                            onValueChange={(evento) => atualizar("prazoEmprestimoProfessor", evento.value)}
                        />
                    </div>
                    <div className="field">Máx. empréstimos simultâneos
                        <InputNumber
                            required
                            min={1}
                            value={formulario.maximosEmprestimosSimultaneos === "" ? null : Number(formulario.maximosEmprestimosSimultaneos)}
                            onValueChange={(evento) => atualizar("maximosEmprestimosSimultaneos", evento.value)}
                        />
                    </div>
                    <div className="field">Máx. renovações
                        <InputNumber
                            required
                            min={0}
                            value={formulario.maximasRenovacoes === "" ? null : Number(formulario.maximasRenovacoes)}
                            onValueChange={(evento) => atualizar("maximasRenovacoes", evento.value)}
                        />
                    </div>
                    <div className="field">Valor da multa por dia (R$)
                        <InputNumber
                            required
                            min={0}
                            minFractionDigits={2}
                            maxFractionDigits={2}
                            value={formulario.valorMultaDia === "" ? null : Number(formulario.valorMultaDia)}
                            onValueChange={(evento) => atualizar("valorMultaDia", evento.value)}
                        />
                    </div>
                    <div className="toolbar field-full">
                        <Button 
                            type="submit" 
                            label="Salvar" 
                        />
                        {mensagem.texto && <Message severity={mensagem.tipo === "error" ? "error" : "success"} text={mensagem.texto} />}
                    </div>
                </form>
            </section>
        </AppShell>
    );
}
