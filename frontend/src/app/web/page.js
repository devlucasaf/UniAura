import { redirect } from "next/navigation";

// --- A RAIZ DO SISTEMA WEB LEVA PARA A PÁGINA INICIAL DO SITE ---
export default function WebRaizPage() {
    redirect("/web/home");
}
