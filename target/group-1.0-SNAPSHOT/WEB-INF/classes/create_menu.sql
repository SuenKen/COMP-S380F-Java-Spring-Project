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

INSERT INTO menu(name, description,price,available) VALUES ('test', '123',200,'Yes');

INSERT INTO menu(name, description,price,available) VALUES ('test2', 'OUHK',500,'Yes');