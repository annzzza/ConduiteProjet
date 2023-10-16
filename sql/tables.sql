CREATE TABLE user
(
    id_user   MEDIUMINT NOT NULL AUTO_INCREMENT,
    username  VARCHAR,
    firstname VARCHAR,
    lastname  VARCHAR,
    password  VARCHAR,
    role      VARCHAR, -- p-e enum,
    PRIMARY KEY (idUser)
);

CREATE TABLE assistance
(
    id_assistance MEDIUMINT NOT NULL AUTO_INCREMENT,
    description   TEXT DEFAULT '',
    created_at    DATETIME,
    due_date      DATETIME,
    creator     INT NOT NULL,
    FOREIGN KEY (creator)
        REFERENCES user(user_id)
        ON DELETE CASCADE
)