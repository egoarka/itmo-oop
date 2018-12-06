-- delete from products where name = 'vilk'

USE master;

IF DB_ID('shopdb') IS NULL
  CREATE DATABASE shopdb;

use shopdb;

CREATE TABLE products
(
  name nvarchar(50),

  CONSTRAINT pk_products
    PRIMARY KEY (name)
);

CREATE TABLE shops
(
  id int IDENTITY(1,1),
  name nvarchar(50) NOT NULL,
  address nvarchar(100) NOT NULL

  CONSTRAINT pk_shops
    PRIMARY KEY (id),
);


CREATE TABLE shop_product
(
  shop_id int,
  product_name nvarchar(50),
  price int,
  quantity int

  CONSTRAINT fk_sp_pd_shop_id
    FOREIGN KEY (shop_id)
    REFERENCES shops (id),
  CONSTRAINT fk_sp_pd_product_name
    FOREIGN KEY (product_name)
    REFERENCES products (name)
);