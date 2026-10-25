"use client";

import { useEffect, useRef }        from "react";
import { montarEfeitosDePagina }    from "@/lib/web/efeitosDePagina";

// --- LIGA AS ANIMAÇÕES COMPARTILHADAS NA RAIZ DEVOLVIDA E DESLIGA NO UNMOUNT ---
export function useEfeitosDePagina() {
    const referencia = useRef(null);

    useEffect(() => {
        const limpar = montarEfeitosDePagina(referencia.current);
        return limpar;
    }, []);

    return referencia;
}
