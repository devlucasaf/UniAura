"use client";

import { useEffect, useState }          from "react";
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

    return (
        <AppShell titulo="Biblioteca — Configurações" perfis={["BIBLIOTECARIO", "ADMIN"]}>
            <section className="card">
                <h2>Parâmetros gerais</h2>
                <form className="form-grid" onSubmit={salvar}>
                    <div className="field">Prazo empréstimo aluno (dias)
                        <input
                            type="number"
                            required
                            min="1"
                            value={form.prazoEmprestimoAluno}
                            onChange={(e) => setForm({ ...form, prazoEmprestimoAluno: e.target.value })}
                        />
                    </div>
                    <div className="field">Prazo empréstimo professor (dias)
                        <input
                            type="number"
                            required
                            min="1"
                            value={form.prazoEmprestimoProfessor}
                            onChange={(e) => setForm({ ...form, prazoEmprestimoProfessor: e.target.value })}
                        />
                    </div>
                    <div className="field">Máx. empréstimos simultâneos
                        <input
                            type="number"
                            required
                            min="1"
                            value={form.maxEmprestimosSimultaneos}
                            onChange={(e) => setForm({ ...form, maxEmprestimosSimultaneos: e.target.value })}
                        />
                    </div>
                    <div className="field">Máx. renovações
                        <input
                            type="number"
                            required
                            min="0"
                            value={form.maxRenovacoes}
                            onChange={(e) => setForm({ ...form, maxRenovacoes: e.target.value })}
                        />
                    </div>
                    <div className="field">Valor da multa por dia (R$)
                        <input
                            type="number"
                            step="0.01"
                            required
                            min="0"
                            value={form.valorMultaDia}
                            onChange={(e) => setForm({ ...form, valorMultaDia: e.target.value })}
                        />
                    </div>
                    <div className="toolbar field-full">
                        <button type="submit" className="btn">Salvar</button>
                        {mensagem.texto && <span className={mensagem.tipo === "error" ? "msg-error" : "msg-ok"}>{mensagem.texto}</span>}
                    </div>
                </form>
            </section>
        </AppShell>
    );
}
