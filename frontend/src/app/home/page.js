import { redirect } from "next/navigation";

// --- COMPATIBILIDADE COM LINKS ANTIGOS (#/home): A HOME AGORA VIVE NA RAIZ "/" ---
export default function HomeRedirect() {
    redirect("/");
}
