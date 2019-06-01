# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table badge (
  id                            serial not null,
  name                          varchar(255),
  description                   varchar(255),
  topic                         varchar(255),
  final_date                    timestamptz,
  tier                          integer,
  level_id                      integer,
  status                        varchar(9),
  constraint ck_badge_status check ( status in ('EARNED','SUBMITTED','REJECTED','NEW')),
  constraint pk_badge primary key (id)
);

create table course (
  id                            serial not null,
  name                          varchar(255),
  insituicao                    varchar(255),
  data_prevista_termino         timestamptz,
  constraint pk_course primary key (id)
);

create table evidence (
  id                            serial not null,
  description                   TEXT,
  feedback                      TEXT,
  submission_date               timestamptz,
  feedback_date                 timestamptz,
  file_name                     varchar(255),
  file_path                     varchar(255),
  user_badge_id                 integer,
  status                        varchar(8),
  constraint ck_evidence_status check ( status in ('NEW','REJECTED','ACCEPTED')),
  constraint pk_evidence primary key (id)
);

create table level (
  id                            serial not null,
  final_date                    timestamptz,
  level_name                    varchar(255),
  boss_fight_name               varchar(255),
  course_id                     integer,
  constraint pk_level primary key (id)
);

create table usuario (
  id                            serial not null,
  username                      varchar(255),
  password                      varchar(255),
  role                          varchar(7),
  constraint ck_usuario_role check ( role in ('STUDENT','TUTOR','ADMIN')),
  constraint uq_usuario_username unique (username),
  constraint pk_usuario primary key (id)
);

create table user_badge (
  id                            serial not null,
  user_id                       integer,
  badge_id                      integer,
  status                        varchar(9),
  last_update                   timestamptz,
  constraint ck_user_badge_status check ( status in ('EARNED','SUBMITTED','REJECTED','NEW')),
  constraint pk_user_badge primary key (id)
);

create table user_course (
  id                            serial not null,
  course_id                     integer,
  user_id                       integer,
  role                          varchar(7),
  score                         integer,
  constraint ck_user_course_role check ( role in ('STUDENT','TUTOR','ADMIN')),
  constraint pk_user_course primary key (id)
);

create index ix_badge_level_id on badge (level_id);
alter table badge add constraint fk_badge_level_id foreign key (level_id) references level (id) on delete restrict on update restrict;

create index ix_evidence_user_badge_id on evidence (user_badge_id);
alter table evidence add constraint fk_evidence_user_badge_id foreign key (user_badge_id) references user_badge (id) on delete restrict on update restrict;

create index ix_level_course_id on level (course_id);
alter table level add constraint fk_level_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_user_badge_user_id on user_badge (user_id);
alter table user_badge add constraint fk_user_badge_user_id foreign key (user_id) references usuario (id) on delete restrict on update restrict;

create index ix_user_badge_badge_id on user_badge (badge_id);
alter table user_badge add constraint fk_user_badge_badge_id foreign key (badge_id) references badge (id) on delete restrict on update restrict;

create index ix_user_course_course_id on user_course (course_id);
alter table user_course add constraint fk_user_course_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_user_course_user_id on user_course (user_id);
alter table user_course add constraint fk_user_course_user_id foreign key (user_id) references usuario (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists badge drop constraint if exists fk_badge_level_id;
drop index if exists ix_badge_level_id;

alter table if exists evidence drop constraint if exists fk_evidence_user_badge_id;
drop index if exists ix_evidence_user_badge_id;

alter table if exists level drop constraint if exists fk_level_course_id;
drop index if exists ix_level_course_id;

alter table if exists user_badge drop constraint if exists fk_user_badge_user_id;
drop index if exists ix_user_badge_user_id;

alter table if exists user_badge drop constraint if exists fk_user_badge_badge_id;
drop index if exists ix_user_badge_badge_id;

alter table if exists user_course drop constraint if exists fk_user_course_course_id;
drop index if exists ix_user_course_course_id;

alter table if exists user_course drop constraint if exists fk_user_course_user_id;
drop index if exists ix_user_course_user_id;

drop table if exists badge cascade;

drop table if exists course cascade;

drop table if exists evidence cascade;

drop table if exists level cascade;

drop table if exists usuario cascade;

drop table if exists user_badge cascade;

drop table if exists user_course cascade;

