create table user
(
    id_user   int auto_increment
        primary key,
    firstname varchar(255)                             null,
    lastname  varchar(255)                             null,
    username  varchar(255)                             null,
    password  varchar(255)                             null,
    role      enum ('BENEVOLE', 'PATIENT', 'VALIDEUR') null
);

create table assistance
(
    id          int auto_increment
        primary key,
    title       text                                                null,
    creator     int                                                 not null,
    description text                                                null,
    type        enum ('REQUEST', 'OFFER')                           null,
    status      enum ('PENDING', 'ACCEPTED', 'REFUSED', 'FINISHED') null,
    isCancelled tinyint(1)                                          null,
    dueDate     datetime                                            null,
    createdAt   datetime                                            null,
    constraint assistance_user_id_user_fk
        foreign key (creator) references user (id_user)
            on update cascade on delete cascade
);