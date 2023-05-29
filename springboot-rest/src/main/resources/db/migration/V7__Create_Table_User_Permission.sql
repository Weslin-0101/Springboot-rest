CREATE TABLE IF NOT EXISTS public.user_permission (
    id_user bigserial NOT NULL,
    id_permission bigserial NOT NULL,
    CONSTRAINT pk_user_permission PRIMARY KEY (id_user),
    CONSTRAINT uk_user_permission UNIQUE (id_permission),
    CONSTRAINT fk_user_permission FOREIGN KEY (id_user) REFERENCES public.users (id),
    CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission) REFERENCES public.permission (id)
);