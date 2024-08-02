CREATE DATABASE zipdb OWNER postgres ENCODING 'UTF-8';

\c zipdb

DROP TABLE account;
CREATE TABLE account(
    id  SERIAL PRIMARY KEY,
    login_id    TEXT UNIQUE,
    name    TEXT,
    password    TEXT
);

INSERT INTO account(login_id,name,password) VALUES('test1','test aa','12dad323sl');
INSERT INTO account(login_id,name,password) VALUES('test2','test ab','12dad323sq');
INSERT INTO account(login_id,name,password) VALUES('test3','test ac','12dad323sz');