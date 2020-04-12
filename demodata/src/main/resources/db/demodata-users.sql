insert into users (username, password, enabled, level) values ( 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', true , 10);
insert into users (username, password, enabled, level) values ( 'customer', 'b39f008e318efd2bb988d724a161b61c6909677f', true , 1);
insert into users (username, password, enabled, level) values ( 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', true , 0);

insert into authorities(username, authority) values ( 'admin', 'ADMIN' );
insert into authorities(username, authority) values ( 'customer', 'CUSTOMER' );
insert into authorities(username, authority) values ( 'test', 'CUSTOMER' );

insert into discounts(id, value, valueType, applicableFor, enabled) values ( 1000, 0, 'PERCENTAGE', 'CART', true );
insert into discounts(id, value, valueType, applicableFor, enabled) values ( 1001, 1, 'PERCENTAGE', 'CART', true );

insert into discounts2userLevel(discountId, userLevel) values ( 1000, 0 );
insert into discounts2userLevel(discountId, userLevel) values ( 1001, 1 );