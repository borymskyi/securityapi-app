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

INSERT INTO persons(email, password)
VALUES ('admin@gmail.com', '$2a$10$ssYfD8xm/j.H70cCYiPLLebAI0G6qCd7JCWDw3zPO9.FWjv33i57C');
-- password = qwerty

INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO person_roles(person_id, role_id)
VALUES (1, 2);