drop database if exists grouptalkdb;
create database grouptalkdb;

use grouptalkdb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered','admin'),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
);

CREATE TABLE stings (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    groupid BINARY(16) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    content text NOT NULL,
    creation_timestamp TIMESTAMP NOT NULL,
    last_modified datetime not null default current_timestamp,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (groupid) REFERENCES groups(id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE groups (
    id BINARY(16) NOT NULL,
    groupname VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE group_users (
    userid BINARY(16) NOT NULL,
    groupid BINARY(16) NOT NULL,
    joined TIMESTAMP NOT NULL default current_timestamp,
    FOREIGN KEY (userid) REFERENCES users (id) on delete cascade,
    FOREIGN KEY (groupid) REFERENCES groups (id) on delete cascade,
    PRIMARY KEY (userid,groupid)
);

CREATE TABLE thread (
    id BINARY(16) NOT NULL,
    stingid BINARY(16) NOT NULL,
    FOREIGN KEY (stingid) REFERENCES stings(id) on delete cascade,
    PRIMARY KEY (id)
);