# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table badge (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  topic                         varchar(255),
  final_date                    varchar(255),
  tier                          integer,
  constraint pk_badge primary key (id)
);


# --- !Downs

drop table if exists badge;

