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