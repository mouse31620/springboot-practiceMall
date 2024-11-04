insert into role (id, role_name, role_chinese, created_date, last_modified_date)
VALUES (1, 'SUPER_ADMIN', '最高管理者', '2024-09-13 22:00:45', '2024-09-13 22:00:45');
insert into role (id, role_name, role_chinese, created_date, last_modified_date)
VALUES (2, 'ADMIN', '管理者', '2024-09-13 22:00:45', '2024-09-13 22:00:45');
insert into role (id, role_name, role_chinese, created_date, last_modified_date)
VALUES (3, 'COMMON_USER', '一般使用者', '2024-09-13 22:00:45', '2024-09-13 22:00:45');

insert into privilege (id, privilege_name, privilege_chinese, created_date, last_modified_date)
 VALUES (1, 'PRODUCT_MANAGE', '產品管理', '2024-10-02 16:30:30', '2024-10-02 16:30:30');

 insert into privilege (id, privilege_name, privilege_chinese, created_date, last_modified_date)
 VALUES (2, 'USER_MANAGE', '使用者管理', '2024-10-02 16:30:30', '2024-10-02 16:30:30');

 insert into privilege (id, privilege_name, privilege_chinese, created_date, last_modified_date)
 VALUES (3, 'ORDER_MANAGE', '訂單管理', '2024-10-02 16:30:30', '2024-10-02 16:30:30');

 insert into customer_type (id, type_name, type_chinese, created_date, last_modified_date)
 VALUES (1, 'REGULAR', '一般會員', '2024-09-13 22:25:37', '2024-09-13 22:25:37');

 insert into customer_type (id, type_name, type_chinese, created_date, last_modified_date)
 VALUES (2, 'VIP', 'VIP會員', '2024-09-13 22:25:37', '2024-09-13 22:25:37');

 insert into "user" (id, email, password, user_name, customer_type_id, role_id, created_date, last_modified_date)
 VALUES (1, 'justin@gmail.com', '$2a$10$BbUMeg0Y1PGGIHL825BjeudtI0Ea1GE0XEWPms0AeddcsJ8HEygSC', 'justin', 2, 1, '2024-08-28 20:40:18', '2024-08-28 20:40:18');

 insert into product (product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date)
 VALUES (1, '蘋果', 'FOOD', 'https://cdn.pixabay.com/photo/2014/02/01/17/28/apple-256261__480.jpg', 20, 5, '這是蘋果', '2022-03-01 02:41:28', '2022-03-01 02:41:28')