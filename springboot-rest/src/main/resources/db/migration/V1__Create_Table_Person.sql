-- public.person definition

-- Drop table

-- DROP TABLE public.person;

CREATE TABLE public.person (
                               id bigserial NOT NULL,
                               first_name varchar(80) NOT NULL,
                               last_name varchar(80) NOT NULL,
                               address varchar(100) NOT NULL,
                               gender varchar(6) NOT NULL,
                               CONSTRAINT person_pkey PRIMARY KEY (id)
);