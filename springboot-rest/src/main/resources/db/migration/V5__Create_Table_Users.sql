CREATE TABLE IF NOT EXISTS public.users (
    id bigserial NOT NULL,
    user_name varchar(255) UNIQUE,
    full_name varchar(255),
    password varchar(255),
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean
);

ALTER TABLE public.users ADD CONSTRAINT uk_users_id UNIQUE (id);
