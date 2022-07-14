CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('admin', '{noop}admin','1','2','3');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO users VALUES ('user', '{noop}user','1','1','1');
INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');

INSERT INTO users VALUES ('123', '{noop}123','1','1','1');
INSERT INTO user_roles(username, role) VALUES ('123', 'ROLE_USER');
