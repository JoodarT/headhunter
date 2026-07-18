CREATE TABLE IF NOT EXISTS customer (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(45),
    password VARCHAR(45)
    );


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

CREATE TABLE IF NOT EXISTS vacancies (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         title VARCHAR(255),
    description TEXT,
    salary DOUBLE,
    category VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS responded_applicants (
                                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                    resume_id BIGINT,
                                                    vacancy_id BIGINT,
                                                    confirmation BOOLEAN DEFAULT FALSE
);



INSERT INTO customer(name, password) VALUES ('tupac', 'qwerty');

INSERT INTO users (name, email, password, phone, account_type)
VALUES ('Абдышукур Абдымомунов', 'abdy@mail.com', '123', '9991112233', 'APPLICANT');

INSERT INTO users (name, email, password, phone, account_type)
VALUES ('ТехКомпания', 'hr@tech.com', '321', '9995556677', 'EMPLOYER');

INSERT INTO resumes (applicant_name, title, category, skills, expected_salary)
VALUES ('Абдышукур Абдымомунов', 'Java Developer', 'IT', 'Java, Spring, SQL', 120000.0);

INSERT INTO resumes (applicant_name, title, category, skills, expected_salary)
VALUES ('Абдышукур Абдымомунов', 'Android Developer', 'IT', 'Kotlin, Android SDK', 100000.0);

INSERT INTO vacancies (title, description, salary, category)
VALUES ('Java Разработчик', 'Ищем специалиста на Spring Boot', 130000.0, 'IT');

INSERT INTO vacancies (title, description, salary, category)
VALUES ('Фронтенд Инженер', 'Требуется знание React', 110000.0, 'IT');

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
VALUES (1, 1, false);