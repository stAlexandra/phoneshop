drop table if exists phone2color;
drop table if exists colors;
drop table if exists stocks;
drop table if exists phones;
drop table if exists orderItems;
drop table if exists orders;
drop table if exists discounts2userLevel;
drop table if exists discounts2users;
drop table if exists discounts;
drop table if exists users;
drop table if exists levels;
drop table if exists authorities;
drop table if exists users2achievements;
drop table if exists achievements;

create table colors
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50),
    UNIQUE (code)
);

create table phones
(
    id                    BIGINT AUTO_INCREMENT primary key,
    brand                 VARCHAR(50)  NOT NULL,
    model                 VARCHAR(254) NOT NULL,
    price                 FLOAT,
    displaySizeInches     FLOAT,
    weightGr              SMALLINT,
    lengthMm              FLOAT,
    widthMm               FLOAT,
    heightMm              FLOAT,
    announced             DATETIME,
    deviceType            VARCHAR(50),
    os                    VARCHAR(100),
    displayResolution     VARCHAR(50),
    pixelDensity          SMALLINT,
    displayTechnology     VARCHAR(50),
    backCameraMegapixels  FLOAT,
    frontCameraMegapixels FLOAT,
    ramGb                 FLOAT,
    internalStorageGb     FLOAT,
    batteryCapacityMah    SMALLINT,
    talkTimeHours         FLOAT,
    standByTimeHours      FLOAT,
    bluetooth             VARCHAR(50),
    positioning           VARCHAR(100),
    imageUrl              VARCHAR(254),
    description           VARCHAR(4096),
    CONSTRAINT UC_phone UNIQUE (brand, model)
);

create table phone2color
(
    phoneId BIGINT,
    colorId BIGINT,
    CONSTRAINT FK_phone2color_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_phone2color_colorId FOREIGN KEY (colorId) REFERENCES colors (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table stocks
(
    phoneId  BIGINT   NOT NULL,
    stock    SMALLINT NOT NULL,
    reserved SMALLINT NOT NULL,
    UNIQUE (phoneId),
    CONSTRAINT FK_stocks_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table orders
(
    id              BIGINT AUTO_INCREMENT primary key,
    secureId        VARCHAR(50) NOT NULL,
    orderDate       DATETIME,
    subtotal        FLOAT,
    deliveryPrice   FLOAT,
    totalPrice      FLOAT,
    firstName       VARCHAR(50),
    lastName        VARCHAR(50),
    deliveryAddress VARCHAR(254),
    contactPhoneNo  VARCHAR(30),
    status          VARCHAR(30) NOT NULL,
    UNIQUE (secureId)
);

create table orderItems
(
    phoneId  BIGINT   NOT NULL,
    orderId  BIGINT   NOT NULL,
    quantity SMALLINT NOT NULL,
    UNIQUE (phoneId, orderId),
    CONSTRAINT FK_orderItems_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_orderItems_orderId FOREIGN KEY (orderId) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE levels
(
    number SMALLINT NOT NULL PRIMARY KEY,
    minXP  LONG     NOT NULL,
    maxXP  LONG     NOT NULL
);

create table users
(
    username VARCHAR(50)  not null primary key,
    password VARCHAR(100) not null,
    enabled  BOOLEAN      not null,
    level    SMALLINT,
    xp       BIGINT,
    CONSTRAINT FK_users_level foreign key (level) references levels (number)
);

create table authorities
(
    username  VARCHAR(50) not null,
    authority VARCHAR(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);

CREATE TABLE discounts
(
    id            BIGINT AUTO_INCREMENT primary key,
    value         FLOAT,
    valueType     VARCHAR(30) NOT NULL,
    applicableFor VARCHAR(30) NOT NULL,
    enabled       BOOLEAN     NOT NULL,
    code          VARCHAR(30)
);

CREATE TABLE discounts2userLevel
(
    discountId BIGINT,
    userLevel  SMALLINT,
    UNIQUE (userLevel),
    CONSTRAINT FK_discounts2userLevel_discountId FOREIGN KEY (discountId) REFERENCES discounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_discounts2userLevel_userLevel FOREIGN KEY (userLevel) REFERENCES users (level) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE discounts2users
(
    discountId BIGINT,
    username   VARCHAR(50),
    UNIQUE (username, discountId),
    CONSTRAINT FK_discounts2user_discountId FOREIGN KEY (discountId) REFERENCES discounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_discounts2user_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE achievements
(
    id          BIGINT AUTO_INCREMENT primary key,
    name        VARCHAR(30) NOT NULL,
    description VARCHAR(200),
    levelPoints INTEGER,
    imageUrl    VARCHAR(254)
);

CREATE TABLE users2achievements
(
    username      VARCHAR(50),
    achievementId BIGINT,
    UNIQUE (username, achievementId),
    CONSTRAINT FK_users2achievements_achievementId FOREIGN KEY (achievementId) REFERENCES achievements (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_users2achievements_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE ON UPDATE CASCADE
);