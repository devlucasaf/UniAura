"use client";

import { useEffect }            from "react";
import { useProcessing }        from "@bernardo-dias/react-cloudsupport";
import { registrarProcessing }  from "@/lib/api";

// --- REGISTRA notifyStart/notifyEnd DO ProcessingProvider PARA lib/api.js PODER USÁ-LOS FORA DE COMPONENTES ---
export default function ProcessingBridge() {
    const processing = useProcessing();

    useEffect(() => {
        registrarProcessing(processing);
        return () => registrarProcessing(null);
    }, [processing]);

    return null;
}
