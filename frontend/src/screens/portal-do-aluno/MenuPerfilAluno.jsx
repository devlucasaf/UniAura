"use client";

import { useEffect, useRef, useState }  from "react";
import { useRouter }                    from "next/navigation";
import { Button }                       from "primereact/button";
import { Menu }                         from "primereact/menu";
import { Dialog }                       from "primereact/dialog";
import CampoSenha                       from "@/components/web/CampoSenha";
import { api }                          from "@/lib/api";
import { notificar }                    from "@/lib/notificar";

const TURNOS = { MANHA: "Manhã", TARDE: "Tarde", NOITE: "Noite" };

// --- LINHA DE INFORMAÇÃO DO ALUNO (ÍCONE + RÓTULO + VALOR) ---
const LinhaInfo = ({ icone, rotulo, valor }) => (
    <div className="ua-perfil-linha">
        <i className={icone} aria-hidden="true" />
        <span className="ua-perfil-linha__rotulo">{rotulo}</span>
        <strong>{valor || "—"}</strong>
    </div>
);

// --- BOTÃO DE PERFIL DO CABEÇALHO: MENU COM OS DADOS DO ALUNO, ATALHOS, ALTERAR SENHA E SAIR ---
export default function MenuPerfilAluno({ usuario, sair }) {
    const router = useRouter();
    const menuRef = useRef(null);
    const [perfil, setPerfil] = useState(null);
    const [senhaAberta, setSenhaAberta] = useState(false);

    // --- BUSCA NO BACKEND OS DADOS DO ALUNO LOGADO (CURSO, TURNO, RA E SEMESTRE) ---
    useEffect(() => {
        api("/alunos/me").then(setPerfil).catch(() => setPerfil(null));
    }, []);

    const itens = [
        {
            template: () => (
                <div className="ua-perfil-cabecalho">
                    <strong className="ua-perfil-nome">{perfil?.nome || usuario?.nome || "Aluno"}</strong>
                    <LinhaInfo icone="pi pi-book" rotulo="Curso" valor={perfil?.curso} />
                    <LinhaInfo icone="pi pi-clock" rotulo="Turno" valor={TURNOS[perfil?.turno]} />
                    <LinhaInfo icone="pi pi-hashtag" rotulo="RA" valor={perfil?.matriculaRA} />
                    <LinhaInfo icone="pi pi-calendar" rotulo="Semestre" valor={perfil?.semestreAtual ? `${perfil.semestreAtual}º semestre` : null} />
                </div>
            )
        },
        { separator: true },
        { label: "Meu perfil", icon: "pi pi-chart-bar", command: () => router.push("/portal-do-aluno/perfil") },
        { label: "Dados pessoais", icon: "pi pi-id-card", command: () => router.push("/portal-do-aluno/cadastro?editar=1") },
        { label: "Alterar senha", icon: "pi pi-key", command: () => setSenhaAberta(true) },
        { separator: true },
        { label: "Sair", icon: "pi pi-sign-out", className: "ua-menu-item-sair", command: sair }
    ];

    return (
        <>
            <Menu model={itens} popup ref={menuRef} id="menuPerfil" popupAlignment="right" />
            <Button
                icon="pi pi-user"
                rounded
                className="ua-botao-perfil"
                aria-label="Abrir menu do perfil"
                aria-haspopup="menu"
                aria-controls="menuPerfil"
                onClick={(evento) => menuRef.current?.toggle(evento)}
            />
            <DialogAlterarSenha visivel={senhaAberta} aoFechar={() => setSenhaAberta(false)} />
        </>
    );
}

// --- DIÁLOGO DE TROCA DE SENHA (PUT /auth/senha): EXIGE A SENHA ATUAL E CONFIRMAÇÃO DA NOVA ---
function DialogAlterarSenha({ visivel, aoFechar }) {
    const [atual, setAtual] = useState("");
    const [nova, setNova] = useState("");
    const [confirmar, setConfirmar] = useState("");
    const [erros, setErros] = useState({});
    const [erro, setErro] = useState("");
    const [salvando, setSalvando] = useState(false);

    // --- LIMPA O FORMULÁRIO AO FECHAR ---
    const fechar = () => {
        setAtual("");
        setNova("");
        setConfirmar("");
        setErros({});
        setErro("");
        setSalvando(false);
        aoFechar();
    };

    const erroDe = (nome) => erros[nome] && <small className="ua-erro-campo" role="alert">{erros[nome]}</small>;

    // --- VALIDA E ENVIA A NOVA SENHA ---
    const salvar = async (evento) => {
        evento.preventDefault();
        setErro("");

        const novos = {};
        if (!atual) {
            novos.atual = "Informe a senha atual.";
        }
        if (!nova) {
            novos.nova = "Informe a nova senha.";
        } else if (nova.length < 6) {
            novos.nova = "Use pelo menos 6 caracteres.";
        }
        if (!confirmar) {
            novos.confirmar = "Confirme a nova senha.";
        } else if (nova && confirmar !== nova) {
            novos.confirmar = "As senhas informadas não conferem.";
        }
        setErros(novos);
        if (Object.keys(novos).length > 0) {
            return;
        }

        setSalvando(true);
        try {
            await api("/auth/senha", { metodo: "PUT", corpo: { senhaAtual: atual, novaSenha: nova } });
            notificar("Senha alterada com sucesso!", "success");
            fechar();
        } catch (excecao) {
            setErro(excecao.message);
            setSalvando(false);
        }
    };

    return (
        <Dialog header="Alterar senha" visible={visivel} onHide={fechar} style={{ width: "min(26rem, 92vw)" }} draggable={false} dismissableMask>
            <form className="ua-form-login" noValidate onSubmit={salvar}>
                <div className="field">
                    <label htmlFor="senhaAtual">Senha atual <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                    <CampoSenha
                        id="senhaAtual"
                        name="senhaAtual"
                        autoComplete="current-password"
                        value={atual}
                        className={erros.atual ? "p-invalid" : ""}
                        onChange={(e) => setAtual(e.target.value)}
                    />
                    {erroDe("atual")}
                </div>

                <div className="field">
                    <label htmlFor="novaSenha">Nova senha <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                    <CampoSenha
                        id="novaSenha"
                        name="novaSenha"
                        autoComplete="new-password"
                        value={nova}
                        className={erros.nova ? "p-invalid" : ""}
                        onChange={(e) => setNova(e.target.value)}
                    />
                    {erroDe("nova")}
                </div>

                <div className="field">
                    <label htmlFor="confirmarNovaSenha">Confirmar nova senha <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                    <CampoSenha
                        id="confirmarNovaSenha"
                        name="confirmarNovaSenha"
                        autoComplete="new-password"
                        value={confirmar}
                        className={erros.confirmar ? "p-invalid" : ""}
                        onChange={(e) => setConfirmar(e.target.value)}
                    />
                    {erroDe("confirmar")}
                </div>

                {erro && <small className="ua-erro-campo ua-erro-login" role="alert">{erro}</small>}

                <Button type="submit" label={salvando ? "Salvando..." : "Salvar nova senha"} icon="pi pi-check" loading={salvando} className="ua-botao-entrar" />
            </form>
        </Dialog>
    );
}
