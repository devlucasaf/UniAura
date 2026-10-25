"use client";

import { useRef, useEffect }        from "react";
import { Toast }                    from "primereact/toast";
import { registrarToastReferencia } from "@/lib/notificar";

// --- MONTA O TOAST DO PRIMEREACT USADO POR notificar() EM QUALQUER PARTE DO APP ---
export default function PrimeToastHost() {
    const toastReferencia = useRef(null);

    useEffect(() => {
        registrarToastReferencia(toastReferencia.current);
        return () => registrarToastReferencia(null);
    }, []);

    return <Toast ref={toastReferencia} position="top-right" />;
}
