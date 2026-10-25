"use client";

import { useEffect, useState }  from "react";
import { DataTable }            from "primereact/datatable";
import { Column }               from "primereact/column";
import { Dropdown }             from "primereact/dropdown";
import { InputSwitch }          from "primereact/inputswitch";
import AppShell                 from "@/components/interno/AppShell";
import { pesquisarUsuarios }    from "@/app/remotes/uniaura/usuario/pesquisarUsuariosRemote";
import { detalharUsuario }      from "@/app/remotes/uniaura/usuario/detalharUsuarioRemote";
import { alterarUsuario }       from "@/app/remotes/uniaura/usuario/alterarUsuarioRemote";
import { obterUsuario }         from "@/lib/auth";
import { notificar }            from "@/lib/notificar";

const TAMANHO_PAGINA = 10;

// --- PERFIS DE ACESSO QUE UM USUÁRIO PODE TER ---
const PERFIS = [
    { 
        value: "ADMIN",         
        label: "Administrador" 
    },
    { 
        value: "COORDENADOR",   
        label: "Coordenação" 
    },
    { 
        value: "SECRETARIA",    
        label: "Secretaria" 
    },
    { 
        value: "PROFESSOR",     
        label: "Professor" 
    },
    { 
        value: "BIBLIOTECARIO", 
        label: "Biblioteca" 
    },
    { 
        value: "FINANCEIRO",    
        label: "Financeiro" 
    },
    { 
        value: "RESPONSAVEL",   
        label: "Responsável" 
    },
    { 
        value: "ALUNO",         
        label: "Aluno" 
    }
];

// --- TELA DE PERMISSÕES ---
export default function SistemasPermissoesPage() {
    const [pagina,      setPagina]      = useState(0);
    const [filtro,      setFiltro]      = useState("");
    const [resposta,    setResposta]    = useState({ total: 0, lista: [] });
    const [salvando,    setSalvando]    = useState(null);

    const uidLogado = obterUsuario()?.uid;

    // --- CARREGA A PÁGINA DE USUÁRIOS, FILTRADA POR PERFIL QUANDO ESCOLHIDO ---
    async function carregar(novaPagina = pagina, perfil = filtro) {
        const dados = await pesquisarUsuarios({ role: perfil || undefined, de: novaPagina * TAMANHO_PAGINA, qtd: TAMANHO_PAGINA });
        setResposta(dados);
        setPagina(novaPagina);
    }

    useEffect(() => { carregar(0, ""); }, []);

    // --- BUSCA O CADASTRO COMPLETO, MUDA SÓ O CAMPO ESCOLHIDO E SALVA SEM MEXER NA SENHA ---
    async function atualizar(usuario, mudanca) {
        setSalvando(usuario.uid);

        try {
            const completo = await detalharUsuario(usuario.uid);

            await alterarUsuario({
                uid: completo.uid,
                nome: completo.nome,
                email: completo.email,
                cpf: completo.cpf,
                telefone: completo.telefone,
                dataNascimento: completo.dataNascimento,
                ativo: completo.ativo,
                role: completo.role,
                ...mudanca
            });

            notificar("Permissão atualizada.", "success");
            await carregar();
        } catch (erro) {
            notificar(erro.message, "error");
        } finally {
            setSalvando(null);
        }
    }

    return (
        <AppShell titulo="Sistemas — Permissões" perfis={["ADMIN"]}>
            <section className="card">
                <h2>Permissões dos usuários</h2>

                <div className="toolbar">
                    <label className="field">Perfil
                        <Dropdown
                            value={filtro}
                            options={[{ value: "", label: "Todos" }, ...PERFIS]}
                            onChange={(evento) => { setFiltro(evento.value); carregar(0, evento.value); }}
                        />
                    </label>
                </div>

                <DataTable value={resposta.lista} dataKey="uid" emptyMessage="Nenhum usuário encontrado." paginator lazy
                        rows={TAMANHO_PAGINA} first={pagina * TAMANHO_PAGINA} totalRecords={resposta.total} onPage={(evento) => carregar(evento.page)}>
                    <Column header="Nome" body={(usuario) => usuario.nome} />
                    <Column header="E-mail" body={(usuario) => usuario.email} />
                    <Column
                        header="Perfil de acesso"
                        body={(usuario) => (
                            <Dropdown
                                value={usuario.role}
                                options={PERFIS}
                                disabled={salvando === usuario.uid || usuario.uid === uidLogado}
                                tooltip={usuario.uid === uidLogado ? "Você não pode mudar o próprio perfil." : undefined}
                                onChange={(evento) => atualizar(usuario, { role: evento.value })}
                            />
                        )}
                    />
                    <Column
                        header="Acesso liberado"
                        body={(usuario) => (
                            <InputSwitch
                                checked={usuario.ativo !== false}
                                disabled={salvando === usuario.uid || usuario.uid === uidLogado}
                                onChange={(evento) => atualizar(usuario, { ativo: evento.value })}
                            />
                        )}
                    />
                </DataTable>
            </section>
        </AppShell>
    );
}
