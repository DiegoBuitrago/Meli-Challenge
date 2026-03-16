---------------------------------
-- CATEGORY
---------------------------------

INSERT INTO category (id, name)
VALUES (1, 'Smartphone');

INSERT INTO category (id, name)
VALUES (2, 'Laptop');

INSERT INTO category (id, name)
VALUES (3, 'Smartwatch');

---------------------------------
-- CATEGORY ATTRIBUTE DEFINITIONS
---------------------------------

-- Smartphone attributes

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (1, 'battery', 1);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (2, 'memory', 1);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (3, 'storage', 1);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (4, 'camera', 1);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (5, 'operating_system', 1);


-- Laptop attributes

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (6, 'cpu', 2);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (7, 'ram', 2);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (8, 'storage', 2);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (9, 'gpu', 2);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (10, 'operating_system', 2);


-- Smartwatch attributes

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (11, 'battery_life', 3);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (12, 'display_type', 3);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (13, 'water_resistance', 3);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (14, 'connectivity', 3);

INSERT INTO category_attribute_definition (id, name, category_id)
VALUES (15, 'health_features', 3);

---------------------------------
-- PRODUCTS
---------------------------------

-- Smartphones

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(1, 'iPhone 14', 'Apple flagship smartphone', 999.0, 4.7,
'https://example.com/iphone14.jpg',
'6.1 inch', '172g', 'Black', 1);

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(2, 'Samsung Galaxy S23', 'Samsung premium smartphone', 899.0, 4.6,
'https://example.com/s23.jpg',
'6.1 inch', '168g', 'Phantom Black', 1);

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(3, 'Google Pixel 8', 'Google AI powered smartphone', 799.0, 4.5,
'https://example.com/pixel8.jpg',
'6.2 inch', '187g', 'Hazel', 1);


-- Laptops

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(4, 'MacBook Pro M3', 'Apple professional laptop', 2499.0, 4.8,
'https://example.com/macbook.jpg',
'14 inch', '1.6kg', 'Silver', 2);

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(5, 'Dell XPS 13', 'Dell ultrabook laptop', 1999.0, 4.6,
'https://example.com/xps13.jpg',
'13 inch', '1.2kg', 'Black', 2);

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(6, 'Lenovo ThinkPad X1', 'Business performance laptop', 1799.0, 4.5,
'https://example.com/thinkpad.jpg',
'14 inch', '1.3kg', 'Matte Black', 2);


-- Smartwatches

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(7, 'Apple Watch Series 9', 'Apple smartwatch with health tracking', 499.0, 4.8,
'https://example.com/applewatch9.jpg',
'45mm', '51g', 'Midnight', 3);

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(8, 'Samsung Galaxy Watch 6', 'Samsung smartwatch with fitness tracking', 399.0, 4.6,
'https://example.com/watch6.jpg',
'44mm', '52g', 'Graphite', 3);

INSERT INTO product
(id, name, description, price, rating, image_url, size, weight, color, category_id)
VALUES
(9, 'Garmin Venu 3', 'Garmin advanced fitness smartwatch', 449.0, 4.7,
'https://example.com/venu3.jpg',
'45mm', '47g', 'Black', 3);

---------------------------------
-- PRODUCT ATTRIBUTES
---------------------------------

-- iPhone attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (1, '3279mAh', 1, 1);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (2, '6GB', 1, 2);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (3, '128GB', 1, 3);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (4, '12MP Dual Camera', 1, 4);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (5, 'iOS', 1, 5);


-- Samsung attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (6, '3900mAh', 2, 1);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (7, '8GB', 2, 2);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (8, '256GB', 2, 3);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (9, '50MP Triple Camera', 2, 4);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (10, 'Android', 2, 5);


-- Pixel attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (11, '4575mAh', 3, 1);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (12, '8GB', 3, 2);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (13, '128GB', 3, 3);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (14, '50MP Dual Camera', 3, 4);

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (15, 'Android', 3, 5);


-- MacBook attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id) 
VALUES (16,'Apple M3',4,6);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id) 
VALUES (17,'18GB',4,7);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (18,'512GB SSD',4,8);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (19,'Integrated GPU',4,9);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (20,'macOS',4,10);

-- Dell attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (21,'Intel i7',5,6);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (22,'16GB',5,7);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (23,'1TB SSD',5,8);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (24,'Intel Iris Xe',5,9);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (25,'Windows 11',5,10);

-- ThinkPad attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (26,'Intel i7',6,6);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (27,'32GB',6,7);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (28,'1TB SSD',6,8);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (29,'Intel Iris Xe',6,9);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (30,'Windows 11',6,10);

-- Apple Watch attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (31,'18 hours',7,11);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (32,'OLED',7,12);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (33,'50m',7,13);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (34,'Bluetooth/WiFi',7,14);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id) 
VALUES (35,'ECG, Heart Rate',7,15);

-- Galaxy Watch attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (36,'40 hours',8,11);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (37,'AMOLED',8,12);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (38,'50m',8,13);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (39,'Bluetooth/WiFi/LTE',8,14);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id) 
VALUES (40,'Sleep Tracking, Heart Rate',8,15);

-- Garmin attributes

INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (41,'14 days',9,11);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (42,'AMOLED',9,12);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (43,'50m',9,13);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (44,'Bluetooth/WiFi',9,14);
INSERT INTO product_attribute (id, attr_value, product_id, attribute_definition_id)
VALUES (45,'VO2 Max, HRV',9,15);