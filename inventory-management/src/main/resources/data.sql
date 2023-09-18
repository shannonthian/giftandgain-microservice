insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10001, 'Rice', 'Grains', 'kg', 200, '2025-09-10','2023-09-10', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10002, 'Noodles', 'Grains', 'kg', 30.5, '2025-09-11','2023-09-10', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10003, 'Tuna Can', 'Canned Food', 'counts', 10, '2025-09-12','2022-09-10', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10004, 'Milk', 'Beverages', 'ml', 300.89, '2025-10-10','2023-09-10', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10005, 'Frozen Strawberries', 'Frozen Food', 'packs', 10, '2025-09-20','2023-09-10', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10006, 'Noodles 12', 'Grains', 'kg', 100.6, '2025-09-11','2023-09-10', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10007, 'Milo Drinks', 'Beverages', 'ml', 30.67, '2025-09-10','2023-10-10', 'this is a dry food');
insert into tb_target_inventory
(target_id, category, unit, target_quantity, target_month_year)
values(101, 'Grains', 'kg', 100.00, '2023-09-10');
insert into tb_target_inventory
(target_id, category, unit, target_quantity, target_month_year)
values(102, 'Frozen Food', 'packs', 250.12, '2023-09-10');
insert into tb_target_inventory
(target_id, category, unit, target_quantity, target_month_year)
values(103, 'Beverages', 'ml', 250.43, '2023-10-10');


