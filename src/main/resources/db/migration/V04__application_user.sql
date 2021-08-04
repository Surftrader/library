create table application_user
(
    id bigserial not null,
    first_name varchar(20),
    last_name varchar(20),
    email varchar(50) not null,
    password varchar(64) not null,
    locked bool not null,
    enable bool not null
);

insert into application_user (first_name, last_name, email, password, locked, enable)
    values ('admin', 'admin', 'admin@gmail.com', '$2a$12$26/NcI1xDkZQNNBrDG9xcOFTTaPH.ns5eYBNBIxg5NwFNDPcurC5u', false, true );