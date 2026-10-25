"use client";

import { useRef, useState }  from "react";
import { Button }            from "primereact/button";
import { OverlayPanel }      from "primereact/overlaypanel";
import { Badge }             from "primereact/badge";
import { NOTIFICACOES }      from "./dados";

// --- SININHO DO CABEÇALHO ---
export default function MenuNotificacoes() {
    const painelReferencia = useRef(null);
    const [lidas, setLidas] = useState(() => new Set());

    const naoLidas = NOTIFICACOES.filter((notificacao) => !lidas.has(notificacao.id)).length;

    const marcar = (id) => setLidas((atual) => new Set(atual).add(id));
    const marcarTodas = () => setLidas(new Set(NOTIFICACOES.map((notificacao) => notificacao.id)));

    return (
        <>
            <span className="ua-sino">
                <Button
                    icon="pi pi-bell"
                    rounded
                    text
                    className="ua-botao-topo"
                    aria-label={`Notificações${naoLidas ? `, ${naoLidas} não lidas` : ""}`}
                    tooltip="Notificações"
                    tooltipOptions={{ position: "bottom" }}
                    onClick={(evento) => painelReferencia.current?.toggle(evento)}
                />
                {naoLidas > 0 && <Badge value={naoLidas} severity="danger" className="ua-sino__contador" />}
            </span>

            <OverlayPanel ref={painelReferencia} className="ua-painel-flutuante" aria-label="Notificações">
                <div className="ua-notificacoes">
                    <div className="ua-notificacoes__cabecalho">
                        <strong>Notificações</strong>
                        <Button
                            type="button"
                            label="Marcar todas como lidas"
                            size="small"
                            text
                            disabled={naoLidas === 0}
                            onClick={marcarTodas}
                        />
                    </div>

                    <ul>
                        {NOTIFICACOES.map((notificacao) => (
                            <li key={notificacao.id} className={lidas.has(notificacao.id) ? "lida" : undefined}>
                                <button type="button" onClick={() => marcar(notificacao.id)}>
                                    <i className={notificacao.icone} aria-hidden="true" />
                                    <span>
                                        <strong>{notificacao.titulo}</strong>
                                        <small>{notificacao.texto}</small>
                                        <small className="ua-notificacoes__quando">{notificacao.quando}</small>
                                    </span>
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            </OverlayPanel>
        </>
    );
}
