"use client";

import { useEffect, useId, useRef, useState } from "react";

// --- ACHATA A LISTA DE OPÇÕES PARA NAVEGAÇÃO POR TECLADO ---
function achatarOpcoes(options) {
    const achatada = [];
    options.forEach((item) => {
        if (item.options) {
            item.options.forEach((opcao) => achatada.push(opcao));
        } else {
            achatada.push(item);
        }
    });
    return achatada;
}

// --- DROPDOWN CUSTOMIZADO ---
export default function Dropdown({id, name, value, onChange, options, placeholder = "Selecione",
    required = false, invalid = false, disabled = false}) {
    const [aberto, setAberto] = useState(false);
    const [indiceAtivo, setIndiceAtivo] = useState(0);
    const caixaRef = useRef(null);
    const listaId = useId();

    const planas = achatarOpcoes(options);
    const escolhida = planas.find((o) => o.value === value);

    useEffect(() => {
        if (!aberto) {
            return undefined;
        }

        const aoClicarFora = (evento) => {
            if (caixaRef.current && !caixaRef.current.contains(evento.target)) {
                setAberto(false);
            }
        };
        document.addEventListener("click", aoClicarFora);
        return () => document.removeEventListener("click", aoClicarFora);
    }, [aberto]);

    const abrir = () => {
        const indiceSelecionado = planas.findIndex((o) => o.value === value);
        setIndiceAtivo(Math.max(0, indiceSelecionado));
        setAberto(true);
    };

    const escolher = (opcao) => {
        onChange?.(opcao.value);
        setAberto(false);
    };

    const aoTeclar = (evento) => {
        if (disabled) {
            return;
        }

        if (!aberto) {
            if (["ArrowDown", "ArrowUp", "Enter", " "].includes(evento.key)) {
                evento.preventDefault();
                abrir();
            }
            return;
        }

        switch (evento.key) {
            case "ArrowDown":
                evento.preventDefault();
                setIndiceAtivo((i) => Math.min(i + 1, planas.length - 1));
                break;
            case "ArrowUp":
                evento.preventDefault();
                setIndiceAtivo((i) => Math.max(i - 1, 0));
                break;
            case "Home":
                evento.preventDefault();
                setIndiceAtivo(0);
                break;
            case "End":
                evento.preventDefault();
                setIndiceAtivo(planas.length - 1);
                break;
            case "Enter":
            case " ":
                evento.preventDefault();
                if (planas[indiceAtivo]) {
                    escolher(planas[indiceAtivo]);
                }
                break;
            case "Escape":
                evento.preventDefault();
                setAberto(false);
                break;
            case "Tab":
                setAberto(false);
                break;
            default:
                break;
        }
    };

    return (
        <div ref={caixaRef} className={`site-select${aberto ? " aberto" : ""}${invalid ? " invalido" : ""}`}>
            <select id={id} name={name} value={value || ""} required={required} hidden tabIndex={-1} onChange={() => {}} aria-hidden="true">
                <option value="">{placeholder}</option>
                {options.map((item) =>
                    item.options ? (
                        <optgroup key={item.group} label={item.group}>
                            {item.options.map((opcao) => (
                                <option key={opcao.value} value={opcao.value}>
                                    {opcao.label}
                                </option>
                            ))}
                        </optgroup>
                    ) : (
                        <option key={item.value} value={item.value}>
                            {item.label}
                        </option>
                    )
                )}
            </select>

            <button type="button" className="site-dropdown-gatilho" role="combobox" aria-haspopup="listbox" aria-expanded={aberto}
                aria-controls={listaId} disabled={disabled} onClick={() => (aberto ? setAberto(false) : abrir())} onKeyDown={aoTeclar}>
                <span className={`site-dropdown-valor${escolhida ? "" : " placeholder"}`}>
                    {escolhida ? escolhida.label : placeholder}
                </span>
                <span className="site-dropdown-seta" aria-hidden="true">
                    <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                         strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
                        <polyline points="6 9 12 15 18 9"></polyline>
                    </svg>
                </span>
            </button>

            <ul className="site-dropdown-lista" id={listaId} role="listbox" hidden={!aberto}>
                {options.map((item, indiceGrupo) =>
                    item.options ? (
                        <li key={item.group} role="group" aria-label={item.group} className="site-dropdown-grupo">
                            <span className="site-dropdown-grupo-titulo">{item.group}</span>
                            <ul>
                                {item.options.map((opcao) => {
                                    const indice = planas.indexOf(opcao);
                                    return (
                                        <li key={opcao.value} role="option" aria-selected={opcao.value === value} className={`site-dropdown-opcao${indice === indiceAtivo ? " ativo" : ""}`}
                                            onMouseEnter={() => setIndiceAtivo(indice)} onClick={() => escolher(opcao)}>
                                            {opcao.label}
                                        </li>
                                    );
                                })}
                            </ul>
                        </li>
                    ) : (
                        <li
                            key={item.value || `vazio-${indiceGrupo}`}
                            role="option"
                            aria-selected={item.value === value}
                            className={`site-dropdown-opcao${!item.value ? " placeholder" : ""}${planas.indexOf(item) === indiceAtivo ? " ativo" : ""}`}
                            onMouseEnter={() => setIndiceAtivo(planas.indexOf(item))}
                            onClick={() => escolher(item)}
                        >
                            {item.label}
                        </li>
                    )
                )}
            </ul>
        </div>
    );
}
