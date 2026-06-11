CREATE DATABASE book_manager;

CREATE USER boot IDENTIFIED BY 'spring';

GRANT ALL PRIVILEGES ON book_manager.* TO boot;