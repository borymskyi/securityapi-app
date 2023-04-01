CREATE TABLE IF NOT EXISTS persons
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS person_roles
(
    person_id BIGINT REFERENCES persons (id),
    role_id   BIGINT REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS consumer
(
    id     BIGSERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS call_templates
(
    id        BIGSERIAL PRIMARY KEY,
    person_id BIGINT REFERENCES persons (id)
);

CREATE TABLE IF NOT EXISTS call_templates_consumers
(
    call_template_id BIGINT REFERENCES call_templates (id),
    consumer_id      BIGINT REFERENCES consumer (id)
);

CREATE TABLE IF NOT EXISTS reasons
(
    id               BIGSERIAL PRIMARY KEY,
    reason_message   VARCHAR(200) NOT NULL,
    call_template_id BIGINT REFERENCES call_templates (id)
);

CREATE TABLE IF NOT EXISTS calls
(
    id          BIGSERIAL PRIMARY KEY,
    consumer_id BIGINT REFERENCES consumer (id),
    reason_id    BIGINT REFERENCES reasons (id),
    created_at  TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO persons(email, password)
VALUES ('manager@gmail.com', '$2a$10$ssYfD8xm/j.H70cCYiPLLebAI0G6qCd7JCWDw3zPO9.FWjv33i57C'),
       ('operator@gmail.com', '$2a$10$ssYfD8xm/j.H70cCYiPLLebAI0G6qCd7JCWDw3zPO9.FWjv33i57C');
-- password = qwerty

INSERT INTO roles(name)
VALUES ('ROLE_OPERATOR'),
       ('ROLE_MANAGER');

INSERT INTO person_roles(person_id, role_id)
VALUES (1, 2),
       (2, 1);

INSERT INTO consumer(status)
VALUES ('INDIVIDUAL_ENTITY'),
       ('LEGAL_ENTITY');