create table if not exits customer (
    id int auto_increment  primary key,
    name varchar(45),
    password varchar(45)

);

insert into customer(name, password)

values ("tupac", "qwerty")