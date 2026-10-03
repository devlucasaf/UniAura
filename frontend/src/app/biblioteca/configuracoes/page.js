"use client";

import { useEffect, useState }          from "react";
import { InputNumber }                  from "primereact/inputnumber";
import { Button }                       from "primereact/button";
import { Message }                      from "primereact/message";
import AppShell                         from "@/components/interno/AppShell";
import { configuracaoBibliotecaApi }    from "@/lib/api/biblioteca";

const CAMPOS = [
    "prazoEmprestimoAluno",
    "prazoEmprestimoProfessor",
    "maxEmprestimosSimultaneos",
    "maxRenovacoes",
    "valorMultaDia"
];

const FORM_VAZIO = {
    prazoEmprestimoAluno: "",
    prazoEmprestimoProfessor: "",
    maxEmprestimosSimultaneos: "",
    maxRenovacoes: "",
    valorMultaDia: ""
};

// --- COMPONENTE DE CONFIGURAÇÕES DA BIBLIOTECA ---
export default function BibliotecaConfiguracoesPage() {
    const [form,        setForm]        = useState(FORM_VAZIO);
    const [mensagem,    setMensagem]    = useState({ texto: "", tipo: "" });

    // --- CARREGA AS CONFIGURAÇÕES ATUAIS DA API ---
    useEffect(() => {
        (async () => {
            try {
                const c = await configuracaoBibliotecaApi.obter();
                const novo = { ...FORM_VAZIO };
                CAMPOS.forEach((campo) => { novo[campo] = c[campo]; });
                setForm(novo);
            } catch (erro) {
                setMensagem({ texto: erro.message, tipo: "error" });
            }
        })();
    }, []);

    // --- SALVA AS CONFIGURAÇÕES ALTERADAS NA API ---
    async function salvar(evento) {
        evento.preventDefault();
        try {
            const dados = {};
            CAMPOS.forEach((campo) => { dados[campo] = Number(form[campo]); });
            await configuracaoBibliotecaApi.atualizar(dados);
            setMensagem({ texto: "Salvo.", tipo: "ok" });
        } catch (erro) {
            setMensagem({ texto: erro.message, tipo: "error" });
        }
    }

    // --- ATUALIZA UM CAMPO NUMÉRICO DO FORMULÁRIO ---
    function atualizar(nome, valor) {
        setForm({ ...form, [nome]: valor ?? "" });
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
                            value={form.prazoEmprestimoAluno === "" ? null : Number(form.prazoEmprestimoAluno)}
                            onValueChange={(e) => atualizar("prazoEmprestimoAluno", e.value)}
                        />
                    </div>
                    <div className="field">Prazo empréstimo professor (dias)
                        <InputNumber
                            required
                            min={1}
                            value={form.prazoEmprestimoProfessor === "" ? null : Number(form.prazoEmprestimoProfessor)}
                            onValueChange={(e) => atualizar("prazoEmprestimoProfessor", e.value)}
                        />
                    </div>
                    <div className="field">Máx. empréstimos simultâneos
                        <InputNumber
                            required
                            min={1}
                            value={form.maxEmprestimosSimultaneos === "" ? null : Number(form.maxEmprestimosSimultaneos)}
                            onValueChange={(e) => atualizar("maxEmprestimosSimultaneos", e.value)}
                        />
                    </div>
                    <div className="field">Máx. renovações
                        <InputNumber
                            required
                            min={0}
                            value={form.maxRenovacoes === "" ? null : Number(form.maxRenovacoes)}
                            onValueChange={(e) => atualizar("maxRenovacoes", e.value)}
                        />
                    </div>
                    <div className="field">Valor da multa por dia (R$)
                        <InputNumber
                            required
                            min={0}
                            minFractionDigits={2}
                            maxFractionDigits={2}
                            value={form.valorMultaDia === "" ? null : Number(form.valorMultaDia)}
                            onValueChange={(e) => atualizar("valorMultaDia", e.value)}
                        />
                    </div>
                    <div className="toolbar field-full">
                        <Button type="submit" label="Salvar" />
                        {mensagem.texto && <Message severity={mensagem.tipo === "error" ? "error" : "success"} text={mensagem.texto} />}
                    </div>
                </form>
            </section>
        </AppShell>
    );
}
