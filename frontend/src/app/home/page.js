import { redirect } from "next/navigation";

// --- COMPATIBILIDADE COM LINKS ANTIGOS ---
export default function HomeRedirect() {
    redirect("/");
}
