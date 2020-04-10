insert into users (username, password, enabled) values ( 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', true );
insert into users (username, password, enabled) values ( 'customer', 'b39f008e318efd2bb988d724a161b61c6909677f', true );
insert into users (username, password, enabled) values ( 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', true );

insert into authorities(username, authority) values ( 'admin', 'ADMIN' );
insert into authorities(username, authority) values ( 'customer', 'CUSTOMER' );
insert into authorities(username, authority) values ( 'test', 'CUSTOMER' );

