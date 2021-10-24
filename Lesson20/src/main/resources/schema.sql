USE Lesson20Demo;

DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS COMPANY;

CREATE TABLE COMPANY (
  id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name varchar(100) not null
);

CREATE UNIQUE INDEX UNIQUE_COMPANY on COMPANY(name);

CREATE TABLE EMPLOYEE (
    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name varchar(100) not null,
    age tinyint not null,
    position varchar(150),
    company_id int not null,
    foreign key (company_id) references COMPANY(id)
);

CREATE INDEX NAME_EMPLOYEE on EMPLOYEE(name);

