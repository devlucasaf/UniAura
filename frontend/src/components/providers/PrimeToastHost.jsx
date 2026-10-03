"use client";

import { useRef, useEffect }    from "react";
import { Toast }                from "primereact/toast";
import { registrarToastRef }    from "@/lib/notificar";

// --- MONTA O TOAST DO PRIMEREACT USADO POR notificar() EM QUALQUER PARTE DO APP ---
export default function PrimeToastHost() {
    const toastRef = useRef(null);

    useEffect(() => {
        registrarToastRef(toastRef.current);
        return () => registrarToastRef(null);
    }, []);

    return <Toast ref={toastRef} position="top-right" />;
}
