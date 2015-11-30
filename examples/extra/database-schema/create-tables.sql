---
-- Noovodb Demo App database Schema
-- Author 	: NoovOp
-- Version 	1.0
--
-- Create tables
---

-- Clear database
drop table if exists contact;
drop sequence if exists contact_seq;

-- create sequences
create sequence contact_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 1
  cache 1;

 -- tables
create table contact
(
	id  		integer primary key default nextval('contact_seq'),
	firstname 	varchar(255),
	lastname 	varchar(255),
	phone		varchar(11),
	email		varchar(50)
);