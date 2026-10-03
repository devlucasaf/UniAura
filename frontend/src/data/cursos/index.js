import administracao                        from "./js/negocios/administracao";
import cienciasContabeis                    from "./js/negocios/ciencias-contabeis";
import cienciasEconomicas                   from "./js/negocios/ciencias-economicas";
import marketing                            from "./js/negocios/marketing";
import gestaoComercial                      from "./js/negocios/gestao-comercial";
import analiseEDesenvolvimentoDeSistemas    from "./js/tecnologia/analise-e-desenvolvimento-de-sistemas";
import artesCenicas                         from "./js/artes/artes-cenicas";
import artesVisuais                         from "./js/artes/artes-visuais";
import biologia                             from "./js/saude/biologia";
import biomedicina                          from "./js/saude/biomedicina";
import cienciaDaComputacao                  from "./js/tecnologia/ciencia-da-computacao";
import cienciaDeDados                       from "./js/tecnologia/ciencia-de-dados";
import cienciasAeronauticas                 from "./js/engenharias/ciencias-aeronauticas";
import design                               from "./js/artes/design";
import direito                              from "./js/humanas/direito";
import educacaoFisica                       from "./js/saude/educacao-fisica";
import engenhariaCivil                      from "./js/engenharias/engenharia-civil";
import engenhariaDeSoftware                 from "./js/tecnologia/engenharia-de-software";
import engenhariaEletrica                   from "./js/engenharias/engenharia-eletrica";
import engenhariaFlorestal                  from "./js/engenharias/engenharia-florestal";
import engenhariaMecanica                   from "./js/engenharias/engenharia-mecanica";
import engenhariaMecatronica                from "./js/engenharias/engenharia-mecatronica";
import engenhariaProducao                   from "./js/engenharias/engenharia-producao";
import engenhariaQuimica                    from "./js/engenharias/engenharia-quimica";
import farmacia                             from "./js/saude/farmacia";
import fisioterapia                         from "./js/saude/fisioterapia";
import fonoaudiologia                       from "./js/saude/fonoaudiologia";
import fotografia                           from "./js/artes/fotografia";
import medicinaVeterinaria                  from "./js/saude/medicina-veterinaria";
import medicina                             from "./js/saude/medicina";
import moda                                 from "./js/artes/moda";
import nutricao                             from "./js/saude/nutricao";
import odontologia                          from "./js/saude/odontologia";
import publicidadePropaganda                from "./js/artes/publicidade-propaganda";
import relacoesInternacionais               from "./js/humanas/relacoes-internacionais";
import terapiaOcupacional                   from "./js/saude/terapia-ocupacional";
import { cards }                            from "../cursosCatalogo";
import { AREA_URL_POR_VALOR }               from "../areasCursos";

// --- CURSOS QUE TEM PAGINA DE DETALHE MAS NAO APARECEM NO CATALOGO ---
const AREA_URL_SEM_CARD = {
    "relacoes-internacionais": "humanas"
};

// --- PALAVRA DE AREA USADA NA URL ---
const areaUrlPorSlug = { ...AREA_URL_SEM_CARD };
cards.forEach((card) => {
    if (card.slug) {
        areaUrlPorSlug[card.slug] = AREA_URL_POR_VALOR[card.area] || card.area;
    }
});

// --- RETORNA A PALAVRA DE AREA DA URL PARA UM SLUG DE CURSO ---
export function areaUrlDoCurso(slug) {
    return areaUrlPorSlug[slug];
}

// --- DADOS DA PAGINA DE CURSO ---
export const cursosPorSlug = {
    administracao,
    "ciencias-contabeis": cienciasContabeis,
    "ciencias-economicas": cienciasEconomicas,
    marketing,
    "gestao-comercial": gestaoComercial,
    "analise-e-desenvolvimento-de-sistemas": analiseEDesenvolvimentoDeSistemas,
    "artes-cenicas": artesCenicas,
    "artes-visuais": artesVisuais,
    biologia,
    biomedicina,
    "ciencia-da-computacao": cienciaDaComputacao,
    "ciencia-de-dados": cienciaDeDados,
    "ciencias-aeronauticas": cienciasAeronauticas,
    design,
    direito,
    "educacao-fisica": educacaoFisica,
    "engenharia-civil": engenhariaCivil,
    "engenharia-de-software": engenhariaDeSoftware,
    "engenharia-eletrica": engenhariaEletrica,
    "engenharia-florestal": engenhariaFlorestal,
    "engenharia-mecanica": engenhariaMecanica,
    "engenharia-mecatronica": engenhariaMecatronica,
    "engenharia-producao": engenhariaProducao,
    "engenharia-quimica": engenhariaQuimica,
    farmacia,
    fisioterapia,
    fonoaudiologia,
    fotografia,
    "medicina-veterinaria": medicinaVeterinaria,
    medicina,
    moda,
    nutricao,
    odontologia,
    "publicidade-propaganda": publicidadePropaganda,
    "relacoes-internacionais": relacoesInternacionais,
    "terapia-ocupacional": terapiaOcupacional
};

