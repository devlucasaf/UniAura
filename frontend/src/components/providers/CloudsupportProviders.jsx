"use client";

import { Suspense }                                                                             from "react";
import { PrimeReactProvider }                                                                   from "primereact/api";
import { ProfileProvider, AuthAwareNetworking, DefaultConfiguredNetworking, ProcessingProvider} from "@bernardo-dias/react-cloudsupport";
import { AuthProvider }                                                                         from "@bernardo-dias/react-cloudsupport/next/client";
import { SessionExpiredBanner, ReloadBanner, ProcessingIndicator }                              from "@bernardo-dias/react-cloudsupport/prime";
import PrimeToastHost                                                                           from "./PrimeToastHost";
import ProcessingBridge                                                                         from "./ProcessingBridge";

const URL_PROFILE = "/uniaura/app/.well-known/profile.json";

// --- ENVOLVE A APLICAÇÃO COM PRIMEREACT, AUTENTICAÇÃO OIDC E O FETCH COM O TOKEN AUTOMÁTICO ---
export default function CloudsupportProviders({ children }) {
    return (
        <PrimeReactProvider>
            <PrimeToastHost />
            <ProfileProvider url={URL_PROFILE}>
                <DefaultConfiguredNetworking />
                {/* --- AVISA QUANDO O PROFILE MUDOU E É PRECISO RECARREGAR --- */}
                <ReloadBanner actionLabel="Nova versão disponível! Clique para atualizar" confirmText="O sistema será recarregado. Finalize o que estiver fazendo antes de continuar." />

                {/* --- AuthProvider USA useSearchParams, QUE EXIGE UM LIMITE DE SUSPENSE NO APP ROUTER --- */}
                <Suspense fallback={null}>
                    <AuthProvider>
                        <AuthAwareNetworking />

                        {/* --- AVISA QUANDO A SESSÃO XPIRA E PRECISA DE NOVO LOGIN --- */}
                        <SessionExpiredBanner actionLabel="Sessão expirada. Clique para entrar novamente." confirmText="O sistema será recarregado para renovar sua sessão." />

                        <ProcessingProvider>
                            <ProcessingBridge />
                            {/* --- BARRA DE CARREGAMENTO GLOBAL ENQUANTO HÁ REQUISIÇÕES EM ANDAMENTO --- */}
                            <ProcessingIndicator />
                            {children}
                        </ProcessingProvider>
                    </AuthProvider>
                </Suspense>
            </ProfileProvider>
        </PrimeReactProvider>
    );
}
