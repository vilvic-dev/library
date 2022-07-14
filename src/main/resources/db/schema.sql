DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS category;

CREATE TABLE author
(
    id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(100),
    about VARCHAR(2000)
);

CREATE TABLE category
(
    id VARCHAR(40) PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(1000)
);

CREATE TABLE book
(
    id VARCHAR(40) PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(1000),
    isbn VARCHAR(13),
    author_id VARCHAR(40),
    category_id VARCHAR(40),
    constraint authorIdFK foreign key (author_id) references author (id),
    constraint categoryIdFK foreign key (category_id) references category (id)
);