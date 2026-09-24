"use client";

import { useEffect, useRef } from "react";
import { montarEfeitosDePagina } from "@/lib/site/efeitosDePagina";

// --- LIGA AS ANIMAÇÕES COMPARTILHADAS NA RAIZ DEVOLVIDA E DESLIGA NO UNMOUNT ---
export function useEfeitosDePagina() {
    const ref = useRef(null);

    useEffect(() => {
        const limpar = montarEfeitosDePagina(ref.current);
        return limpar;
    }, []);

    return ref;
}
