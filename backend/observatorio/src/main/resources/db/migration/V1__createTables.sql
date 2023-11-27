CREATE TABLE curso
(
    id   BIGINT NOT NULL,
    nome VARCHAR(255),
    tipo VARCHAR(255),
    CONSTRAINT curso_pkey PRIMARY KEY (id)
);

CREATE TABLE projeto
(
    id                 BIGINT NOT NULL,
    area_tematica      VARCHAR(255),
    conclusao          TEXT,
    data_fim           date,
    data_inicio        date,
    fundamentacao      TEXT,
    introducao         TEXT,
    memoria_visual     TEXT,
    modalidade         VARCHAR(255),
    objetivos          TEXT,
    pessoas_atendidas  INTEGER,
    publico_alvo       TEXT,
    resumo             TEXT,
    suporte_financeiro VARCHAR(255),
    titulo             TEXT,
    campus_id          BIGINT,
    publicacao_id      BIGINT,
    usuario_id         BIGINT,
    CONSTRAINT projeto_pkey PRIMARY KEY (id)
);

CREATE TABLE usuario
(
    id        BIGINT NOT NULL,
    email     VARCHAR(255),
    matricula VARCHAR(255),
    nome      VARCHAR(255),
    perfil    VARCHAR(255),
    senha     VARCHAR(255),
    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

CREATE TABLE publicacao
(
    id            BIGINT NOT NULL,
    curtidas      INTEGER,
    descurtidas   INTEGER,
    visualizacoes INTEGER,
    projeto_id    BIGINT,
    usuario_id    BIGINT,
    CONSTRAINT publicacao_pkey PRIMARY KEY (id)
);

CREATE TABLE campus
(
    id     BIGINT NOT NULL,
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    nome   VARCHAR(255),
    rua    VARCHAR(255),
    CONSTRAINT campus_pkey PRIMARY KEY (id)
);

CREATE TABLE campus_curso
(
    id        BIGINT NOT NULL,
    id_campus BIGINT,
    id_curso  BIGINT,
    CONSTRAINT campus_curso_pkey PRIMARY KEY (id)
);

CREATE TABLE comentario
(
    id            BIGINT NOT NULL,
    curtidas      INTEGER,
    descurtidas   INTEGER,
    mensagem      VARCHAR(255),
    publicacao_id BIGINT,
    CONSTRAINT comentario_pkey PRIMARY KEY (id)
);

CREATE TABLE curso_projeto
(
    id         BIGINT NOT NULL,
    id_curso   BIGINT,
    id_projeto BIGINT,
    CONSTRAINT curso_projeto_pkey PRIMARY KEY (id)
);