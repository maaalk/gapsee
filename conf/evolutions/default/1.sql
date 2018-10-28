# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table badge (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  category                      varchar(255),
  date                          varchar(255),
  points                        integer,
  constraint pk_badge primary key (id)
);


# --- !Downs

drop table if exists badge;

