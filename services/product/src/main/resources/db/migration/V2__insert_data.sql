INSERT INTO category (id, description, name)
VALUES (nextval('category_seq'), 'Electronics and gadgets', 'Electronics'),
       (nextval('category_seq'), 'Clothing and accessories', 'Apparel'),
       (nextval('category_seq'), 'Home and kitchen appliances', 'Home & Kitchen'),
       (nextval('category_seq'), 'Books and stationery items', 'Books'),
       (nextval('category_seq'), 'Health and personal care products', 'Health & Personal Care');


INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Smartphone with 6.5-inch display', 'Smartphone', 50, 599.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'Wireless over-ear headphones', 'Headphones', 150, 149.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), '4K Ultra HD Smart TV', 'Smart TV', 30, 999.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'Bluetooth Speaker', 'Speaker', 200, 49.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'Laptop with 15-inch display', 'Laptop', 25, 1099.99,
        (SELECT id FROM category WHERE name = 'Electronics'));

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Cotton T-Shirt', 'T-Shirt', 200, 19.99,
        (SELECT id FROM category WHERE name = 'Apparel')),
       (nextval('product_seq'), 'Denim Jeans', 'Jeans', 150, 39.99, (SELECT id FROM category WHERE name = 'Apparel')),
       (nextval('product_seq'), 'Leather Jacket', 'Jacket', 50, 89.99,
        (SELECT id FROM category WHERE name = 'Apparel')),
       (nextval('product_seq'), 'Sneakers', 'Shoes', 100, 59.99, (SELECT id FROM category WHERE name = 'Apparel')),
       (nextval('product_seq'), 'Wool Scarf', 'Scarf', 75, 14.99, (SELECT id FROM category WHERE name = 'Apparel'));


INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Blender with 5-speed settings', 'Blender', 80, 89.99,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Stainless Steel Cookware Set', 'Cookware Set', 60, 129.99,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Vacuum Cleaner', 'Vacuum', 45, 199.99,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Air Fryer', 'Air Fryer', 90, 99.99,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Electric Kettle', 'Kettle', 110, 29.99,
        (SELECT id FROM category WHERE name = 'Home & Kitchen'));


INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Self-help book on productivity', 'Productivity Book', 100, 14.99,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'Mystery thriller novel', 'Thriller Novel', 85, 9.99,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'Science fiction novel', 'Sci-Fi Novel', 90, 12.99,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'Cooking recipes book', 'Cookbook', 70, 19.99,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'Children`s storybook', 'Storybook', 120, 7.99,
        (SELECT id FROM category WHERE name = 'Books'));

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Vitamin C supplement, 1000mg', 'Vitamin C', 120, 12.99,
        (SELECT id FROM category WHERE name = 'Health & Personal Care')),
       (nextval('product_seq'), 'Moisturizing lotion, 500ml', 'Lotion', 150, 8.99,
        (SELECT id FROM category WHERE name = 'Health & Personal Care')),
       (nextval('product_seq'), 'Electric toothbrush', 'Toothbrush', 60, 29.99,
        (SELECT id FROM category WHERE name = 'Health & Personal Care')),
       (nextval('product_seq'), 'Protein powder, 1kg', 'Protein Powder', 75, 24.99,
        (SELECT id FROM category WHERE name = 'Health & Personal Care')),
       (nextval('product_seq'), 'Sunscreen SPF 50', 'Sunscreen', 100, 14.99,
        (SELECT id FROM category WHERE name = 'Health & Personal Care'));
