# UniAura: Guia de Design System

## 1. Diagnóstico do estado atual
- Duas paletas paralelas: `global.css` (tokens `--color-*`, `--bg-*`) e `erp-central.css` (`--cor-*`). Mudanças de cor exigem editar os dois.
- Componentes PrimeReact (tema `lara-light-blue`) são claros e fixam cores; foram sobrescritos em `primereact-tema.css`.
- Tema aplicado só após a hidratação, o que causava piscada (corrigido com script no `<head>`).
- Sem foco visível padronizado, sem link de pular menu, sem tratamento de movimento reduzido.
- Alvos de toque pequenos em celulares e cabeçalho sem responsividade definida.

## 2. Lista de melhorias
| Prioridade | Melhoria | Estado |
|---|---|---|
| Alta | Tokens únicos de cor para claro e escuro | Feito em `global.css` |
| Alta | Foco visível, movimento reduzido | Feito em `design-system.css` |
| Alta | Link "pular para o conteúdo" | Feito em `layout.js` (alvo `#conteudo`) |
| Média | `--cor-*` derivados dos tokens globais | Pendente (edição recusada) |
| Média | Cabeçalho com vidro e fixo | Feito |
| Média | Cartões e indicadores reutilizáveis (`.ua-card`, `.ua-indicador`) | Feito |
| Média | Home, dashboard, área do aluno e do professor redesenhados | Pendente |
| Baixa | Calendário, biblioteca digital, perfil acadêmico | Pendente |

## 3. Arquitetura visual
- **Camadas (ordem de carregamento em `layout.js`):** PrimeReact → `global.css` (tokens) → componentes/layout → `erp-central.css` (interno) → `web.css` (público) → `primereact-tema.css` (ponte PrimeReact) → `design-system.css` (vence tudo).
- **Público:** cabeçalho de vidro, seções com hierarquia clara.
- **Interno:** shell com menu lateral, cartões e indicadores.

## 4. Identidade visual
- **Primária:** azul-marinho institucional `#1e3a8a` (claro) / `#60a5fa` (escuro).
- **Apoio:** verde-azulado `#0d9488` (claro) / `#2dd4bf` (escuro), associado a inovação e aprendizado.
- **Destaque:** amarelo `#facc15`, usado com moderação.
- **Tipografia:** pilha do sistema (`--font-base`), que é rápida e legível.

## 5. Wireframes conceituais
**Home (desktop)**
```
[ Logo  Universidade ▾  Cursos ▾  Notícias  Contato        ☾  Portal do Aluno ]
[ HERO: título, subtítulo, CTA "Quero me matricular" | CTA "Conhecer cursos"  ]
[ Carrossel de destaques ]
[ Cards de áreas: Tecnologia | Engenharias | Saúde | Negócios | Humanas | Artes ]
[ Eventos e notícias ]                    [ Depoimentos de alunos ]
[ Rodapé: links, contato, LGPD ]
```

**Dashboard do aluno**
```
[ Saudação + próximos compromissos ]
[ Indicadores: média | frequência | mensalidade | biblioteca ]
[ Calendário da semana ]        [ Notificações ]
[ Disciplinas do semestre (cartões com progresso) ]
```

## 6. Componentes reutilizáveis
- `.ua-card` (cartão), `.ua-card--vidro` (cartão de vidro).
- `.ua-indicador` (valor e rótulo para dashboards).
- `.ua-skip-link` (acessibilidade).
- Já existentes: `Badge` (`Tag` com severidade), `CrudEntityPage`, `Modal`, `Paginacao`, `CursoTemplate`.

## 7. Responsividade
- Breakpoints: 480px (celular), 768px (tablet), 960px (menu do cabeçalho passa a hambúrguer, `breakpoint` do `MegaMenu`).
- Alvos de toque com 44px mínimo em `pointer: coarse`.
- Grades: 1 coluna no celular, 2 no tablet, 3 ou 4 no desktop.

## 8. Acessibilidade (WCAG 2.2 AA)
- Contraste: texto principal e muted atendem AA nos dois modos (`#0f172a` sobre `#ffffff`; `#475569` sobre `#ffffff`; `#e2e8f0` e `#94a3b8` sobre fundos escuros).
- Foco visível em todos os elementos interativos (2.4.7).
- Movimento reduzido respeitado (2.3.3).
- Pendente: revisar rótulos de ícones sem texto e a ordem do foco nos dialogs.

## 9. Próximos passos
1. Alinhar `--cor-*` em `erp-central.css` aos tokens globais.
2. Inserir `.ua-skip-link` no `layout.js`.
3. Redesenhar home e dashboards com `.ua-card` e `.ua-indicador`.
4. Validar visualmente claro e escuro em desktop, tablet e celular.
