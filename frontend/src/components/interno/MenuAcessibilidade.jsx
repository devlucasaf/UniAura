"use client";

import { useEffect, useRef, useState }                                          from "react";
import { Button }                                                               from "primereact/button";
import { OverlayPanel }                                                         from "primereact/overlaypanel";
import { InputSwitch }                                                          from "primereact/inputswitch";
import { PADRAO_ACESSIBILIDADE, acessibilidadeSalva, aplicarAcessibilidade }    from "@/lib/acessibilidade";

const DALTONISMOS = [
    {
        valor: "protanopia",
        rotulo: "Protanopia (vermelho)"
    },
    {
        valor: "deuteranopia",
        rotulo: "Deuteranopia (verde)"
    },
    {
        valor: "tritanopia",
        rotulo: "Tritanopia (azul)"
    }
];

// --- MATRIZES DE SIMULAÇÃO/CORREÇÃO DE DALTONISMO, USADAS PELOS FILTROS SVG ---
const MATRIZES = {
    protanopia:   "0.567 0.433 0 0 0  0.558 0.442 0 0 0  0 0.242 0.758 0 0  0 0 0 1 0",
    deuteranopia: "0.625 0.375 0 0 0  0.7 0.3 0 0 0  0 0.3 0.7 0 0  0 0 0 1 0",
    tritanopia:   "0.95 0.05 0 0 0  0 0.433 0.567 0 0  0 0.475 0.525 0 0  0 0 0 1 0"
};

// --- BOTÃO DE ACESSIBILIDADE DO CABEÇALHO ---
export default function MenuAcessibilidade() {
    const painelReferencia = useRef(null);
    const [preferencias, setPreferencias] = useState(PADRAO_ACESSIBILIDADE);

    // --- REAPLICA O QUE O ALUNO ESCOLHEU NA ÚLTIMA VISITA ---
    useEffect(() => {
        const salvas = acessibilidadeSalva();
        setPreferencias(salvas);
        aplicarAcessibilidade(salvas, false);
    }, []);

    const mudar = (parte) => {
        const novas = { ...preferencias, ...parte };
        setPreferencias(novas);
        aplicarAcessibilidade(novas);
    };

    // --- CLICAR NO CONTRASTE JÁ ATIVO O DESLIGA ---
    const alternarContraste = (tipo) => mudar({ contraste: preferencias.contraste === tipo ? "" : tipo });

    return (
        <>
            {/* --- FILTROS SVG DO DALTONISMO --- */}
            <svg width="0" height="0" style={{ position: "absolute" }} aria-hidden="true" focusable="false">
                <defs>
                    {Object.entries(MATRIZES).map(([nome, matriz]) => (
                        <filter key={nome} id={`ua-filtro-${nome}`} colorInterpolationFilters="sRGB">
                            <feColorMatrix type="matrix" values={matriz} />
                        </filter>
                    ))}
                </defs>
            </svg>

            <Button
                icon="pi pi-eye"
                rounded
                text
                className="ua-botao-topo"
                aria-label="Acessibilidade"
                tooltip="Acessibilidade"
                tooltipOptions={{ position: "bottom" }}
                onClick={(evento) => painelReferencia.current?.toggle(evento)}
            />

            <OverlayPanel ref={painelReferencia} className="ua-painel-flutuante" aria-label="Opções de acessibilidade">
                <div className="ua-a11y">
                    <strong className="ua-a11y__titulo">Acessibilidade</strong>

                    <div className="ua-a11y__grupo">
                        <span className="ua-a11y__rotulo">Contraste</span>
                        <div className="ua-a11y__duplo">
                            <Button
                                type="button"
                                label="Contraste claro"
                                icon="pi pi-sun"
                                size="small"
                                outlined={preferencias.contraste !== "claro"}
                                aria-pressed={preferencias.contraste === "claro"}
                                onClick={() => alternarContraste("claro")}
                            />
                            <Button
                                type="button"
                                label="Contraste escuro"
                                icon="pi pi-moon"
                                size="small"
                                outlined={preferencias.contraste !== "escuro"}
                                aria-pressed={preferencias.contraste === "escuro"}
                                onClick={() => alternarContraste("escuro")}
                            />
                        </div>
                    </div>

                    <label className="ua-a11y__linha" htmlFor="a11yCursor">
                        <span>
                            <i className="pi pi-arrow-up-left" aria-hidden="true" /> Cursor grande
                        </span>
                        <InputSwitch
                            inputId="a11yCursor"
                            checked={preferencias.cursorGrande}
                            onChange={(evento) => mudar({ cursorGrande: evento.value })}
                        />
                    </label>

                    <div className="ua-a11y__grupo">
                        <span className="ua-a11y__rotulo">Daltonismo</span>
                        <div className="ua-a11y__lista">
                            {DALTONISMOS.map((opcao) => (
                                <Button
                                    key={opcao.valor}
                                    type="button"
                                    label={opcao.rotulo}
                                    size="small"
                                    text={preferencias.daltonismo !== opcao.valor}
                                    aria-pressed={preferencias.daltonismo === opcao.valor}
                                    onClick={() => mudar({ daltonismo: preferencias.daltonismo === opcao.valor ? "" : opcao.valor })}
                                />
                            ))}
                        </div>
                    </div>

                    <label className="ua-a11y__linha" htmlFor="a11yAnimacoes">
                        <span>
                            <i className="pi pi-pause" aria-hidden="true" /> Pausar animações
                        </span>
                        <InputSwitch
                            inputId="a11yAnimacoes"
                            checked={preferencias.pausarAnimacoes}
                            onChange={(evento) => mudar({ pausarAnimacoes: evento.value })}
                        />
                    </label>

                    <Button
                        type="button"
                        label="Restaurar padrão"
                        icon="pi pi-refresh"
                        size="small"
                        text
                        onClick={() => mudar({ ...PADRAO_ACESSIBILIDADE })}
                    />
                </div>
            </OverlayPanel>
        </>
    );
}
