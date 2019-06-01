# --- !Ups

ALTER TABLE public.course ADD COLUMN data_prevista_termino timestamptz;
ALTER TABLE public.course ADD COLUMN insituicao VARCHAR(255);

# --- !Downs

ALTER TABLE DROP COLUMN data_prevista_termino;
ALTER TABLE DROP COLUMN insituicao;
