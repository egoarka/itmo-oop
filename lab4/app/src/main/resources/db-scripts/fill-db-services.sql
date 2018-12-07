use shopdb;
-- so few data for sake of simplicity

SET IDENTITY_INSERT shops ON;
INSERT INTO shops (id, name, address) 
  VALUES (111, 'service-shop-1', 'SPB');
INSERT INTO shops (id, name, address) 
  VALUES (222, 'service-shop-2', 'SPB');
INSERT INTO shops (id, name, address) 
  VALUES (333, 'service-shop-3', 'SPB');
SET IDENTITY_INSERT shops OFF;

-- INSERT INTO shops (name, address) 
--   VALUES ('Karusel', 'SPB');

INSERT INTO products (name) 
  VALUES ('milk');
INSERT INTO products (name) 
  VALUES ('salt');
-- INSERT INTO products (name) 
--   VALUES ('chocolate');
-- INSERT INTO products (name) 
--   VALUES ('ice-cream');


INSERT INTO shop_product (shop_id, product_name, quantity, price) 
  VALUES (111, 'milk', 3, 15);
INSERT INTO shop_product (shop_id, product_name, quantity, price) 
  VALUES (111, 'salt', 10, 5);

INSERT INTO shop_product (shop_id, product_name, quantity, price) 
  VALUES (222, 'milk', 7, 35);
INSERT INTO shop_product (shop_id, product_name, quantity, price) 
  VALUES (222, 'salt', 1, 2);

INSERT INTO shop_product (shop_id, product_name, quantity, price) 
  VALUES (333, 'milk', 3, 5);
INSERT INTO shop_product (shop_id, product_name, quantity, price) 
  VALUES (333, 'salt', 4, 2);

-- UPDATE shop_product
--   SET price = 15, quantity = 3
-- WHERE shop_id = 1 AND product_name = 'milk';

-- UPDATE shop_product
--   SET price = 35, quantity = 7
-- WHERE shop_id = 2 AND product_name = 'milk';

-- UPDATE shop_product
--   SET price = 2, quantity = 1
-- WHERE shop_id = 2 AND product_name = 'salt';