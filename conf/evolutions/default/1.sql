# Users schema

# --- !Ups
--  "id", "title", "fuel", "price", "isNew", "mileage", "firstReg"
CREATE TABLE car_adverts (
    id int NOT NULL AUTO_INCREMENT,
    title varchar(128) NOT NULL,
    fuel  varchar(32) NOT NULL,
    price int NOT NULL DEFAULT 0,
    isNew bool not NULL DEFAULT TRUE,
    mileage int NULL,
    firstReg date NULL,
    PRIMARY KEY (id)
);

INSERT INTO car_adverts(title, fuel, price)
VALUES ('AUDI A4 avant', 'gasoline', 10000);

INSERT INTO car_adverts(title, fuel, price, isNew, mileage, firstReg)
VALUES ('AUDI A6 avant', 'gasoline', 12000, FALSE, 55320, '2017-01-01');

INSERT INTO car_adverts(title, fuel, price, isNew, mileage, firstReg)
VALUES ('VW Golf 5', 'gasoline', 8000, FALSE, 155320, '205-01-01');

# --- !Downs

DROP TABLE car_adverts;