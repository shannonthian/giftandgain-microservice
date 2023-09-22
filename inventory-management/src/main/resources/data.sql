-- Insert into tb_category first
insert into tb_category (category_id, category, status) values(101, 'Grains', 'A');
insert into tb_category (category_id, category, status) values(102, 'Beverages', 'A');
insert into tb_category (category_id, category, status) values(103, 'Canned food', 'A');
insert into tb_category (category_id, category, status) values(104, 'Frozen Food', 'A');

-- Insert into tb_unit next
insert into tb_unit (unit_id, unit, description) values(10001, 'kg', 'kilograms');
insert into tb_unit (unit_id, unit, description) values(10002, 'g', 'grams');
insert into tb_unit (unit_id, unit, description) values(10003, 'ton', 'tonnes');
insert into tb_unit (unit_id, unit, description) values(10004, 'counts', 'counts');
insert into tb_unit (unit_id, unit, description) values(10005, 'ml', 'milliliters');
insert into tb_unit (unit_id, unit, description) values(10006, 'packs', 'packs');

-- Insert into tb_inventory
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10001, 'Rice', 101, 10001, 200, '2025-09-10','2023-09-10', 'this is a dry food');
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10002, 'Noodles', 101, 10001, 30.5, '2025-09-11','2023-09-11', 'this is a dry food');
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10003, 'Tuna Can', 103, 10003, 500, '2025-09-12','2023-09-10', 'this is a dry food');
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10004, 'Milk', 102, 10005, 300.89, '2025-10-10','2023-10-10', 'this is a dry food');
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10005, 'Frozen Strawberries', 104, 10006, 10, '2025-09-20','2023-09-10', 'this is a dry food');
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10006, 'Noodles 12', 101, 10001, 100.6, '2025-09-11','2023-09-10', 'this is a dry food');
insert into tb_inventory (inventory_id, item_name, category_id, unit_id, received_quantity, expiry_date, created_date, remarks) values(10007, 'Milo Drinks', 102, 10005, 30.67, '2025-09-10','2023-10-10', 'this is a dry food');

-- Insert into tb_target_inventory
insert into tb_target_inventory (target_id, category_id, unit_id, target_quantity, target_month_year) values(101, 101, 10001, 100000.00, '2023-09-10');
insert into tb_target_inventory (target_id, category_id, unit_id, target_quantity, target_month_year) values(102, 103, 10003, 250.00, '2023-09-10');
insert into tb_target_inventory (target_id, category_id, unit_id, target_quantity, target_month_year) values(103, 102, 10002, 250.43, '2023-10-10');
