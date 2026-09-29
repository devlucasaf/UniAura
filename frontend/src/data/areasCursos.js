// --- MAPEIA O VALOR INTERNO DE ÁREA PARA A PALAVRA USADA NA URL ---
export const AREA_URL_POR_VALOR = {
    ti: "tecnologia",
    engenharias: "engenharias",
    saude: "saude",
    negocios: "negocios",
    humanas: "humanas",
    artes: "artes"
};

// --- PALAVRA DA URL ---
export const VALOR_POR_AREA_URL = Object.fromEntries(
    Object.entries(AREA_URL_POR_VALOR).map(([valor, url]) => [url, valor])
);
