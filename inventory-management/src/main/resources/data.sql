insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10001, 'Rice', 'Grains', 'kg', 200, '2025-09-10','2023-09-10T22:12:16.524557', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10002, 'Noodles', 'Grains', 'kg', 30, '2025-09-10','2023-09-10T22:12:16.524557', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10003, 'Tuna Can', 'Canned Food', 'counts', 10, '2025-09-10','2022-09-10T22:12:16.524557', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10004, 'Milk', 'Beverages', 'ml', 300, '2025-10-10','2023-09-10T22:12:16.524557', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10005, 'Frozen Strawberries', 'Frozen Food', 'packs', 10, '2025-09-10','2023-09-10T22:12:16.524557', 'this is a dry food');
insert into tb_inventory
(inventory_id, item_name, category, unit, received_quantity, expiry_date, created_date, remarks)
values(10006, 'Milo Drinks', 'Beverages', 'ml', 300, '2025-09-10','2023-10-10T22:12:16.524557', 'this is a dry food');
insert into tb_target_inventory
(target_id, category, unit, target_quantity, target_month, target_year)
values(101, 'Grains', 'kg', 100, '09', '2023');
insert into tb_target_inventory
(target_id, category, unit, target_quantity, target_month, target_year)
values(102, 'Frozen Food', 'packs', 250, '09', '2023');
insert into tb_target_inventory
(target_id, category, unit, target_quantity, target_month, target_year)
values(103, 'Beverages', 'ml', 250, '09', '2023');


