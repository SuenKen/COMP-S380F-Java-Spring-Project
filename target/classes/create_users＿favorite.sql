CREATE TABLE users_favorite  (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    menu_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (menu_id) REFERENCES menu(id),
    FOREIGN KEY (username) REFERENCES users(username)
);
