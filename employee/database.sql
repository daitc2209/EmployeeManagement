create table employee
(
    emp_id     int auto_increment
        primary key,
    first_name varchar(45)  not null,
    last_name  varchar(20)  not null,
    email_id   varchar(45)  not null,
    dob        date         not null,
    address    varchar(100) not null,
    constraint email_id_UNIQUE
        unique (email_id)
);

create table user
(
    user_id  int auto_increment
        primary key,
    email    varchar(45) not null,
    name     varchar(45) not null,
    password varchar(45) not null,
    constraint email_UNIQUE
        unique (email)
);


