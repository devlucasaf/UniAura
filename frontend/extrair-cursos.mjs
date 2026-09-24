import { readFileSync, writeFileSync } from "fs";

const html = readFileSync("C:/Users/lucas/AppData/Local/Temp/migracao/old-src/templates/web/graduacao/cursos.html", "utf-8");

const regexCard = /<a href="#\/graduacao\/([\w-]+)" class="curso-card" data-area="([\w-]+)" style="--curso-cor: (#[0-9a-fA-F]+);">\s*<div class="curso-card-icon" aria-hidden="true">\s*<svg[^>]*>([\s\S]*?)<\/svg>\s*<\/div>\s*<h2>([^<]+)<\/h2>\s*<p>([^<]+)<\/p>\s*<span class="curso-card-tag">([^<]+)<\/span>\s*<\/a>/g;

const cursos = [];
let m;
while ((m = regexCard.exec(html)) !== null) {
    cursos.push({
        rota: m[1],
        area: m[2],
        cor: m[3],
        icone: m[4].trim().replace(/\s+/g, " "),
        titulo: m[5],
        texto: m[6],
        tag: m[7]
    });
}

console.log("total extraido:", cursos.length);
writeFileSync("C:/Users/lucas/AppData/Local/Temp/migracao/cursos-extraidos.json", JSON.stringify(cursos, null, 2));
