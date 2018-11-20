# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table badge (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  topic                         varchar(255),
  final_date                    timestamp,
  tier                          integer,
  level_id                      integer,
  status                        varchar(9),
  constraint ck_badge_status check ( status in ('EARNED','SUBMITTED','REJECTED','NEW')),
  constraint pk_badge primary key (id)
);

create table course (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_course primary key (id)
);

create table evidence (
  id                            integer auto_increment not null,
  description                   TEXT,
  feedback                      TEXT,
  submission_date               timestamp,
  feedback_date                 timestamp,
  file_name                     varchar(255),
  file_path                     varchar(255),
  user_badge_id                 integer,
  status                        varchar(8),
  constraint ck_evidence_status check ( status in ('NEW','REJECTED','ACCEPTED')),
  constraint pk_evidence primary key (id)
);

create table level (
  id                            integer auto_increment not null,
  final_date                    varchar(255),
  level_name                    varchar(255),
  boss_fight_name               varchar(255),
  course_id                     integer,
  constraint pk_level primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  role                          varchar(7),
  constraint ck_user_role check ( role in ('STUDENT','TUTOR','ADMIN')),
  constraint uq_user_username unique (username),
  constraint pk_user primary key (id)
);

create table user_badge (
  id                            integer auto_increment not null,
  user_id                       integer,
  badge_id                      integer,
  status                        varchar(9),
  last_update                   timestamp,
  constraint ck_user_badge_status check ( status in ('EARNED','SUBMITTED','REJECTED','NEW')),
  constraint pk_user_badge primary key (id)
);

create index ix_badge_level_id on badge (level_id);
alter table badge add constraint fk_badge_level_id foreign key (level_id) references level (id) on delete restrict on update restrict;

create index ix_evidence_user_badge_id on evidence (user_badge_id);
alter table evidence add constraint fk_evidence_user_badge_id foreign key (user_badge_id) references user_badge (id) on delete restrict on update restrict;

create index ix_level_course_id on level (course_id);
alter table level add constraint fk_level_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_user_badge_user_id on user_badge (user_id);
alter table user_badge add constraint fk_user_badge_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_user_badge_badge_id on user_badge (badge_id);
alter table user_badge add constraint fk_user_badge_badge_id foreign key (badge_id) references badge (id) on delete restrict on update restrict;


# --- !Downs

alter table badge drop constraint if exists fk_badge_level_id;
drop index if exists ix_badge_level_id;

alter table evidence drop constraint if exists fk_evidence_user_badge_id;
drop index if exists ix_evidence_user_badge_id;

alter table level drop constraint if exists fk_level_course_id;
drop index if exists ix_level_course_id;

alter table user_badge drop constraint if exists fk_user_badge_user_id;
drop index if exists ix_user_badge_user_id;

alter table user_badge drop constraint if exists fk_user_badge_badge_id;
drop index if exists ix_user_badge_badge_id;

drop table if exists badge;

drop table if exists course;

drop table if exists evidence;

drop table if exists level;

drop table if exists user;

drop table if exists user_badge;

