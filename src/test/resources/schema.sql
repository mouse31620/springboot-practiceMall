CREATE TABLE IF NOT EXISTS product
(
    product_id         bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(255) NOT NULL,
    category           VARCHAR(255)  NOT NULL,
    image_url          VARCHAR(255) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    version            INT          DEFAULT 0,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS "user"
(
    id                 BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE,
    password           VARCHAR(256) NOT NULL,
    user_name          VARCHAR(20)  NOT NULL,
    customer_type_id   BIGINT       NOT NULL,
    role_id            BIGINT       NOT NULL,
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
    type_name          VARCHAR(20)  NOT NULL UNIQUE,
    type_chinese       VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );

CREATE TABLE IF NOT EXISTS user_order
(
  id                   bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
  created_date         TIMESTAMP    DEFAULT NULL,
  last_modified_date   TIMESTAMP    DEFAULT NULL,
  order_price          bigint       DEFAULT NULL,
  receiver_address     varchar(255) DEFAULT NULL,
  receiver_email       varchar(255) DEFAULT NULL,
  receiver_name        varchar(255) DEFAULT NULL,
  user_id              bigint       DEFAULT NULL,
  order_state          varchar(255) DEFAULT NULL
  );

CREATE TABLE IF NOT EXISTS order_product
(
  id                   BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
  order_id             BIGINT       NOT NULL,
  product_id           BIGINT       NOT NULL,
  quantity             INT          NOT NULL
);

