"use client";

import { useEffect }     from "react";
import { useRouter }     from "next/navigation";
import { useAuth }       from "@bernardo-dias/react-cloudsupport";
import { dashboardDoPerfil, obterUsuario } from "@/lib/auth";

// --- PÁGINA DE RETORNO DO LOGIN NO KEYCLOAK: ENCAMINHA PARA O DASHBOARD DO PERFIL DO USUÁRIO ---
export default function LoginCallbackPage() {
    const auth = useAuth();
    const router = useRouter();

    useEffect(() => {
        if (auth.isLoading) {
            return;
        }

        if (!auth.isAuthenticated) {
            auth.signinRedirect();
            return;
        }

        const usuario = obterUsuario();
        router.replace(dashboardDoPerfil(usuario?.role));
    }, [auth.isLoading, auth.isAuthenticated]);

    return (
        <div className="auth-screen">
            <div className="auth-card">
                <p className="muted">Entrando...</p>
            </div>
        </div>
    );
}
