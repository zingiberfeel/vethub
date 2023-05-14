create table if not exists users(
                                    id bigserial primary key,
                                    username varchar(255) not null unique,
                                    password varchar(255) not null
);

create table if not exists animals(
                                      id bigserial primary key,
                                      name varchar(255) not null,
                                      kind varchar(255) not null,
                                      birthdate date not null
);

create table if not exists vetrecords(
                                         id bigserial primary key,
                                         name varchar(255),
                                         description varchar(255),
                                         date timestamp not null,
                                         reminder timestamp
);

create table if not exists users_animals(
                                            user_id bigint references users(id) on delete cascade on update no action,
                                            animal_id bigint references animals(id) on delete cascade on update no action,
                                            primary key(user_id, animal_id)
);

create table if not exists animals_vetrecords(
                                                 animal_id bigint references animals(id) on delete cascade on update no action,
                                                 record_id bigint references vetrecords(id) on delete cascade on update no action,
                                                 primary key(animal_id, record_id)
);

create table if not exists roles(
                                    user_id bigint not null,
                                    role varchar(255) not null,
                                    primary key (user_id, role),
                                    constraint fk_users_roles_user foreign key (user_id) references users(id) on delete cascade on update no action
)