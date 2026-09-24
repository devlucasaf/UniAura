import administracao from "./administracao";
import analiseEDesenvolvimentoDeSistemas from "./analise-e-desenvolvimento-de-sistemas";
import artesCenicas from "./artes-cenicas";
import artesVisuais from "./artes-visuais";
import biologia from "./biologia";
import biomedicina from "./biomedicina";
import cienciaDaComputacao from "./ciencia-da-computacao";
import cienciaDeDados from "./ciencia-de-dados";
import cienciasAeronauticas from "./ciencias-aeronauticas";
import design from "./design";
import direito from "./direito";
import educacaoFisica from "./educacao-fisica";
import engenhariaCivil from "./engenharia-civil";
import engenhariaDeSoftware from "./engenharia-de-software";
import engenhariaEletrica from "./engenharia-eletrica";
import engenhariaFlorestal from "./engenharia-florestal";
import engenhariaMecanica from "./engenharia-mecanica";
import engenhariaMecatronica from "./engenharia-mecatronica";
import engenhariaProducao from "./engenharia-producao";
import engenhariaQuimica from "./engenharia-quimica";
import farmacia from "./farmacia";
import fisioterapia from "./fisioterapia";
import fonoaudiologia from "./fonoaudiologia";
import fotografia from "./fotografia";
import medicinaVeterinaria from "./medicina-veterinaria";
import medicina from "./medicina";
import moda from "./moda";
import nutricao from "./nutricao";
import odontologia from "./odontologia";
import publicidadePropaganda from "./publicidade-propaganda";
import relacoesInternacionais from "./relacoes-internacionais";
import terapiaOcupacional from "./terapia-ocupacional";

// --- MAPA slug -> DADOS DA PAGINA DE CURSO ---
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
