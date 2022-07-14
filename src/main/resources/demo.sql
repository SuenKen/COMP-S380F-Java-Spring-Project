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

CREATE TABLE menu (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    price  DOUBLE NOT NULL,
    available VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE menu_comments (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    menu_id INTEGER ,
    context VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (menu_id) REFERENCES menu(id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE attachment (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(255) DEFAULT NULL,
    content_type VARCHAR(255) DEFAULT NULL,
    content BLOB DEFAULT NULL,
    menu_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (menu_id) REFERENCES menu(id)
);
CREATE TABLE users_favorite  (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    menu_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (menu_id) REFERENCES menu(id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE order_table (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    order_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    menu_name VARCHAR(50) NOT NULL,
    menu_id INTEGER ,
    qty INTEGER,
    price  DOUBLE NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (menu_id) REFERENCES menu(id)
);

INSERT INTO users VALUES ('admin', '{noop}admin','1','2','3');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO users VALUES ('user', '{noop}user','1','1','1');
INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');

INSERT INTO users VALUES ('123', '{noop}123','1','1','1');
INSERT INTO user_roles(username, role) VALUES ('123', 'ROLE_USER');

INSERT INTO menu(name, description,price,available) VALUES ('test', '123',200,'Yes');

INSERT INTO menu(name, description,price,available) VALUES ('test2', 'OUHK',500,'Yes');