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
                                            user_id bigint references users(id),
                                            animal_id bigint references animals(id),
                                            primary key(user_id, animal_id),
                                            constraint fk_users_animals_users foreign key (user_id) references users(id) on delete cascade on update no action,
                                            constraint fk_users_animals_animals foreign key (animal_id) references animals(id) on delete cascade on update no action
);

create table if not exists animals_vetrecords(
                                                 animal_id bigint references animals(id),
                                                 record_id bigint references vetrecords(id),
                                                 primary key(animal_id, record_id),
                                                 constraint fk_animals_vetrecords_animals foreign key (animal_id) references animals(id) on delete cascade on update no action,
                                                 constraint fk_animals_vetrecords_vetrecords foreign key (record_id) references vetrecords(id) on delete cascade on update no action
);

create table if not exists roles(
                                    user_id bigint not null,
                                    role varchar(255) not null,
                                    primary key (user_id, role),
                                    constraint fk_users_roles_user foreign key (user_id) references users(id) on delete cascade on update no action
)