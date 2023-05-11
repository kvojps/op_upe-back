BEGIN;

CREATE TABLE IF NOT EXISTS public.campus
(
    id bigint NOT NULL,
    bairro character varying(255),
    cidade character varying(255),
    nome character varying(255),
    rua character varying(255),
    CONSTRAINT campus_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.campus_curso
(
    id bigint NOT NULL,
    id_campus bigint,
    id_curso bigint,
    CONSTRAINT campus_curso_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.comentario
(
    id bigint NOT NULL,
    curtidas integer,
    descurtidas integer,
    mensagem character varying(255),
    publicacao_id bigint,
    CONSTRAINT comentario_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.curso
(
    id bigint NOT NULL,
    nome character varying(255),
    tipo character varying(255),
    CONSTRAINT curso_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.curso_projeto
(
    id bigint NOT NULL,
    id_curso bigint,
    id_projeto bigint,
    CONSTRAINT curso_projeto_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.projeto
(
    id bigint NOT NULL,
    area_tematica character varying(255),
    conclusao text,
    data_fim date,
    data_inicio date,
    fundamentacao text,
    introducao text,
    memoria_visual character varying(255),
    modalidade character varying(255),
    objetivos text,
    pessoas_atendidas integer,
    publico_alvo character varying(255),
    resumo text,
    suporte_financeiro double precision,
    titulo character varying(255),
    campus_id bigint,
    publicacao_id bigint,
    usuario_id bigint,
    CONSTRAINT projeto_pkey PRIMARY KEY (id),
    CONSTRAINT uk_gvcpvx9o3ho08yb313vfmgj98 UNIQUE (titulo)
);

CREATE TABLE IF NOT EXISTS public.publicacao
(
    id bigint NOT NULL,
    curtidas integer,
    descurtidas integer,
    visualizacoes integer,
    projeto_id bigint,
    usuario_id bigint,
    CONSTRAINT publicacao_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.usuario
(
    id bigint NOT NULL,
    email character varying(255),
    matricula character varying(255),
    nome character varying(255),
    perfil character varying(255),
    senha character varying(255),
    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.campus_curso
    ADD CONSTRAINT fk3mshepgm6quv4vq1rtjf6gwqo FOREIGN KEY (id_campus)
    REFERENCES public.campus (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.campus_curso
    ADD CONSTRAINT fkfygfubuxt308yshd017kqy64l FOREIGN KEY (id_curso)
    REFERENCES public.curso (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.comentario
    ADD CONSTRAINT fkquvu8vnyn0jf7eiippmwjmwnd FOREIGN KEY (publicacao_id)
    REFERENCES public.publicacao (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.curso_projeto
    ADD CONSTRAINT fk9kok39eopmige40chgyiet6y4 FOREIGN KEY (id_projeto)
    REFERENCES public.projeto (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.curso_projeto
    ADD CONSTRAINT fkaqqkuykyeg5po3f1byx7nll4s FOREIGN KEY (id_curso)
    REFERENCES public.curso (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.projeto
    ADD CONSTRAINT fkao2lwtl29x64xu85u6p70yrq1 FOREIGN KEY (publicacao_id)
    REFERENCES public.publicacao (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.projeto
    ADD CONSTRAINT fkc1mv8pryipgeci4am2ek47a47 FOREIGN KEY (usuario_id)
    REFERENCES public.usuario (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.projeto
    ADD CONSTRAINT fkf18x95kn0edj7gp58moiliduj FOREIGN KEY (campus_id)
    REFERENCES public.campus (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.publicacao
    ADD CONSTRAINT fkao85atqepi201xcjkx13hetn5 FOREIGN KEY (projeto_id)
    REFERENCES public.projeto (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.publicacao
    ADD CONSTRAINT fkj8wpgag5bhvbvi556dukc55ag FOREIGN KEY (usuario_id)
    REFERENCES public.usuario (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;