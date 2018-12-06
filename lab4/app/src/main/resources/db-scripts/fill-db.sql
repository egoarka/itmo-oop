use shopdb;
-- so few data for sake of simplicity

INSERT INTO shops (name, address) 
  VALUES ('5ka', 'SPB');
INSERT INTO shops (name, address) 
  VALUES ('lenta', 'SPB');
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

-- Найти магазин, в котором определенный товар самый дешевый
INSERT INTO shop_product (shop_id, product_name) 
  VALUES (1, 'milk');
UPDATE shop_product
  SET price = 15, quantity = 3
WHERE shop_id = 1 AND product_name = 'milk';

INSERT INTO shop_product (shop_id, product_name) 
  VALUES (2, 'milk');
UPDATE shop_product
  SET price = 35, quantity = 7
WHERE shop_id = 2 AND product_name = 'milk';

INSERT INTO shop_product (shop_id, product_name) 
  VALUES (2, 'salt');
UPDATE shop_product
  SET price = 2, quantity = 1
WHERE shop_id = 2 AND product_name = 'salt';