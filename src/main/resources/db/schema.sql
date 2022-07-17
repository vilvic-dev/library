DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS customer;

CREATE TABLE author
(
    id    VARCHAR(40) PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    about VARCHAR(2000)
);

CREATE TABLE category
(
    id          VARCHAR(40) PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(1000)
);

CREATE TABLE book
(
    id          VARCHAR(40) PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    isbn        VARCHAR(13)  NOT NULL,
    author_id   VARCHAR(40),
    category_id VARCHAR(40),
    constraint authorIdFK foreign key (author_id) references author (id),
    constraint categoryIdFK foreign key (category_id) references category (id)
);

CREATE TABLE customer
(
    id        VARCHAR(40) PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    address1  VARCHAR(100) NOT NULL,
    address2  VARCHAR(100) NOT NULL,
    address3  VARCHAR(100),
    address4  VARCHAR(100),
    post_code VARCHAR(16)  NOT NULL,
    telephone VARCHAR(16),
    email     VARCHAR(255) NOT NULL
);
