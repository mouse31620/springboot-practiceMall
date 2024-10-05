CREATE TABLE product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128) NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS user
(
    id                 BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE,
    password           VARCHAR(256) NOT NULL,
    user_name          VARCHAR(20)  NOT NULL,
    customer_type_id   BIGINT       NOT NULL,
    user_id            BIGINT       NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    id                 BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role_name          VARCHAR(256) NOT NULL UNIQUE,
    role_chinese       VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);
insert into role (id, role_name, created_date, last_modified_date)
VALUES (1, 'SUPER_ADMIN', now(), now());
insert into role (id, role_name, created_date, last_modified_date)
VALUES (2, 'ADMIN', now(), now());
insert into role (id, role_name, created_date, last_modified_date)
VALUES (3, 'COMMON_USER', now(), now());

CREATE TABLE IF NOT EXISTS privilege
(
    id                 BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    privilege_name     VARCHAR(50)  NOT NULL,
    privilege_chinese  VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS role_privileges
(
    id                 BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role_id            BIGINT       NOT NULL,
    privilege_id       BIGINT       NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_type
(
    id                 BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type_name          VARCHAR(20) NOT NULL UNIQUE,
    type_chinese       VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );

 insert into privilege (id, privilege_name, privilege_chinese, created_date, last_modified_date)
 VALUES (1, 'PRODUCT_MANAGE', '產品管理', now(), now());

 insert into privilege (id, privilege_name, privilege_chinese, created_date, last_modified_date)
 VALUES (2, 'USER_MANAGE', '使用者管理', now(), now());

 insert into privilege (id, privilege_name, privilege_chinese, created_date, last_modified_date)
 VALUES (3, 'ORDER_MANAGE', '訂單管理', now(), now());