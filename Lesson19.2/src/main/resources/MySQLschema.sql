USE Lesson19_2Demo;

drop table IF EXISTS EMPLOYEE;
drop table IF EXISTS DEPARTMENT;
drop table IF EXISTS ORGANIZATION;

create table ORGANIZATION (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name varchar(50) NOT NULL,
    country varchar(50) NOT NULL
);

create unique index UNIQUE_ORGANIZATION ON ORGANIZATION(name);

create table DEPARTMENT (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name varchar(70) NOT NULL,
    internal_code smallint NOT NULL,
    organization_id INT NOT NULL,
    foreign key (organization_id) references ORGANIZATION(id)
);

create unique index UNIQUE_DEPARTMENT ON DEPARTMENT(internal_code);

create table EMPLOYEE (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name varchar(100) not null,
    position varchar(100) not null,
    age tinyint not null,
    hiring_date DATE,
    department_id INT NOT NULL,
    foreign key (department_id) references DEPARTMENT(id)
);

create index EMPLOYEE ON EMPLOYEE(name, position);