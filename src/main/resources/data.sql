create table if not exits customer (
    id int auto_increment  primary key,
    name varchar(45),
    password varchar(45)

);

insert into customer(name, password)

values ("tupac", "qwerty")

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(50),
    account_type VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS resumes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    applicant_name VARCHAR(255),
    title VARCHAR(255),
    category VARCHAR(255),
    skills TEXT,
    expected_salary DOUBLE
    );


INSERT INTO users (name, email, password, phone, account_type)
VALUES ('Абдышукур Абдымомунов', 'abdy@mail.com', '123', '9991112233', 'APPLICANT');

INSERT INTO users (name, email, password, phone, account_type)
VALUES ('ТехКомпания', 'hr@tech.com', '321', '9995556677', 'EMPLOYER');

INSERT INTO resumes (applicant_name, title, category, skills, expected_salary)
VALUES ('Абдышукур Абдымомунов', 'Java Developer', 'IT', 'Java, Spring, SQL', 120000.0);