-- Insert into tb_category first
insert into tb_category (category_id, category, unit, status) values(101, 'Grains', 'kg', 'A');
insert into tb_category (category_id, category, unit, status) values(102, 'Beverages', 'ml', 'A');
insert into tb_category (category_id, category, unit, status) values(103, 'Canned food', 'counts', 'A');
insert into tb_category (category_id, category, unit, status) values(104, 'Frozen Food', 'packs', 'A');

-- Insert into tb_inventory
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10001, 'Rice', 101, 200, '2025-09-10','2023-09-10', 'this is a dry food', 'admin123');
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10002, 'Noodles', 101, 30.5, '2025-09-11','2023-09-11', 'this is a dry food', 'admin123');
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10003, 'Tuna Can', 103, 500, '2025-09-12','2023-09-10', 'this is a dry food', 'admin');
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10004, 'Milk', 102, 300.89, '2025-10-10','2023-10-10', 'this is a dry food', 'admin');
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10005, 'Frozen Strawberries', 104, 10, '2025-09-20','2023-09-10', 'this is a dry food', 'admin');
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10006, 'Noodles 12', 101, 100.6, '2025-09-11','2023-09-10', 'this is a dry food', 'admin');
insert into tb_inventory (inventory_id, item_name, category_id, received_quantity, expiry_date, created_date, remarks, created_by) values(10007, 'Milo Drinks', 102, 30.67, '2025-09-10','2023-10-10', 'this is a dry food', 'admin');

-- Insert into tb_target_inventory
insert into tb_target_inventory (target_id, category_id, target_quantity, target_month_year) values(101, 101, 100000.00, '2023-09-10');
insert into tb_target_inventory (target_id, category_id, target_quantity, target_month_year) values(102, 103, 250.00, '2023-09-10');
insert into tb_target_inventory (target_id, category_id, target_quantity, target_month_year) values(103, 102, 250.43, '2023-10-10');
