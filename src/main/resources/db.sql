create table if not exists engine
(
    id          serial not null
        constraint engine_pk
            primary key,
    fuel_type   varchar(50),
    horse_power integer
);

create table if not exists car
(
    id            serial not null
        constraint car_pk
            primary key,
    name          varchar(100),
    color         varchar(100),
    year_of_issue varchar(100),
    km_age        varchar(200),
    handlebar     varchar(20),
    transmission  varchar(100),
    wheel_drive   varchar(100),
    engine_id     integer
        constraint fk_engine
            references engine,
    car_body      varchar(200)
);

create table if not exists users
(
    id       serial not null
        constraint users_pk
            primary key,
    name     varchar(200),
    email    varchar(200),
    password varchar(200),
    phone    varchar(50),
    address  varchar(200)
);

create table if not exists advertisement
(
    id          serial not null
        constraint advertisement_pk
            primary key,
    description text,
    sale_status boolean default false,
    price       varchar(20),
    create_date timestamp,
    car_id      integer
        constraint fk_car
            references car,
    user_id     integer
        constraint fk_user
            references users
);

create table if not exists images
(
    id               serial not null
        constraint images_pk
            primary key,
    file_name        varchar(200),
    image            bytea,
    advertisement_id integer
        constraint fk_advertisement
            references advertisement
);

