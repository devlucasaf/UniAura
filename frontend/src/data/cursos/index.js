import administracao                        from "./negocios/administracao";
import analiseEDesenvolvimentoDeSistemas    from "./tecnologia/analise-e-desenvolvimento-de-sistemas";
import artesCenicas                         from "./artes/artes-cenicas";
import artesVisuais                         from "./artes/artes-visuais";
import biologia                             from "./saude/biologia";
import biomedicina                          from "./saude/biomedicina";
import cienciaDaComputacao                  from "./tecnologia/ciencia-da-computacao";
import cienciaDeDados                       from "./tecnologia/ciencia-de-dados";
import cienciasAeronauticas                 from "./engenharias/ciencias-aeronauticas";
import design                               from "./artes/design";
import direito                              from "./humanas/direito";
import educacaoFisica                       from "./saude/educacao-fisica";
import engenhariaCivil                      from "./engenharias/engenharia-civil";
import engenhariaDeSoftware                 from "./tecnologia/engenharia-de-software";
import engenhariaEletrica                   from "./engenharias/engenharia-eletrica";
import engenhariaFlorestal                  from "./engenharias/engenharia-florestal";
import engenhariaMecanica                   from "./engenharias/engenharia-mecanica";
import engenhariaMecatronica                from "./engenharias/engenharia-mecatronica";
import engenhariaProducao                   from "./engenharias/engenharia-producao";
import engenhariaQuimica                    from "./engenharias/engenharia-quimica";
import farmacia                             from "./saude/farmacia";
import fisioterapia                         from "./saude/fisioterapia";
import fonoaudiologia                       from "./saude/fonoaudiologia";
import fotografia                           from "./artes/fotografia";
import medicinaVeterinaria                  from "./saude/medicina-veterinaria";
import medicina                             from "./saude/medicina";
import moda                                 from "./artes/moda";
import nutricao                             from "./saude/nutricao";
import odontologia                          from "./saude/odontologia";
import publicidadePropaganda                from "./artes/publicidade-propaganda";
import relacoesInternacionais               from "./humanas/relacoes-internacionais";
import terapiaOcupacional                   from "./saude/terapia-ocupacional";
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

