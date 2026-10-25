"use client";

import Link             from "next/link";
import { Button }       from "primereact/button";
import { useRouter }    from "next/navigation";

// --- PÁGINA TEMPORÁRIA DE UMA OPÇÃO DO MENU DO ALUNO QUE AINDA NÃO TEM TELA PRÓPRIA ---
export default function SecaoEmConstrucao({ item }) {
    const router = useRouter();

    return (
        <div className="ua-aluno-home">
            <nav className="ua-aluno-migalhas" aria-label="Você está aqui">
                <Link href="/portal-do-aluno/home">Home</Link>
                {item.grupo && (
                    <>
                        <span aria-hidden="true">›</span>
                        <span>{item.grupo}</span>
                    </>
                )}
                <span aria-hidden="true">›</span>
                <span aria-current="page">{item.rotulo}</span>
            </nav>

            <div className="ua-card ua-aluno-construcao">
                <span className="ua-aluno-carreira__icone ua-aluno-carreira__icone--grande" aria-hidden="true">
                    <i className={item.icone} />
                </span>
                <h1>{item.rotulo}</h1>
                {item.descricao && <p className="muted">{item.descricao}</p>}
                <p>Esta área está em construção e ficará disponível em breve.</p>
                <Button 
                    label="Voltar para a Home" 
                    icon="pi pi-arrow-left" 
                    outlined 
                    onClick={() => router.push("/portal-do-aluno/home")} 
                />
            </div>
        </div>
    );
}
