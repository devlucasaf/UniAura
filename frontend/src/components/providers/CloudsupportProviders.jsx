"use client";

import { PrimeReactProvider }                   from "primereact/api";
import { ProcessingProvider }                   from "@bernardo-dias/react-cloudsupport";
import { ProcessingIndicator }                  from "@bernardo-dias/react-cloudsupport/prime";
import PrimeToastHost                           from "./PrimeToastHost";
import ProcessingBridge                         from "./ProcessingBridge";

// --- ENVOLVE A APLICAÇÃO COM PRIMEREACT, AVISOS E A BARRA DE CARREGAMENTO GLOBAL ---
export default function CloudsupportProviders({ children }) {
    return (
        <PrimeReactProvider>
            <PrimeToastHost />
            <ProcessingProvider>
                <ProcessingBridge />
                {/* --- BARRA DE CARREGAMENTO GLOBAL ENQUANTO HÁ REQUISIÇÕES EM ANDAMENTO --- */}
                <ProcessingIndicator />
                {children}
            </ProcessingProvider>
        </PrimeReactProvider>
    );
}
