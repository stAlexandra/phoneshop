-- User level
insert into levels(number, minXP, maxXP) values (1, 0, 20);
insert into levels(number, minXP, maxXP) values (2, 20, 50);

insert into users (username, password, enabled, level, xp) values ( 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', true, 1, 0);
insert into users (username, password, enabled, level, xp) values ( 'customer', 'b39f008e318efd2bb988d724a161b61c6909677f', true , 1, 5);
insert into users (username, password, enabled, level, xp) values ('test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', true, 2, 25);

insert into authorities(username, authority) values ( 'admin', 'ADMIN' );
insert into authorities(username, authority) values ( 'customer', 'CUSTOMER' );
insert into authorities(username, authority) values ( 'test', 'CUSTOMER' );

-- Level Discounts
insert into discounts(id, value, valueType, applicableFor, enabled)
values (1000, 0.01, 'PERCENTAGE', 'CART', true);
insert into discounts(id, value, valueType, applicableFor, enabled)
values (1001, 0.03, 'PERCENTAGE', 'CART', true);

-- Coupons
insert into discounts(id, value, valueType, applicableFor, enabled, code) values ( 1002, 0.50, 'PERCENTAGE', 'CART', true, 'TEST_PERCENTAGE');
insert into discounts(id, value, valueType, applicableFor, enabled, code) values ( 1003, 10, 'AMOUNT', 'CART', true, 'TEST_AMOUNT');

insert into discounts2userLevel(discountId, userLevel) values (1000, 1);
insert into discounts2userLevel(discountId, userLevel) values (1001, 2);

-- Achievements
insert into achievements(id, name, description, levelPoints) values ( 1000, 'Fresh start!', 'Create a profile', 5);

-- User to achievements
insert into users2achievements(username, achievementId) values ( 'customer', 1000 );