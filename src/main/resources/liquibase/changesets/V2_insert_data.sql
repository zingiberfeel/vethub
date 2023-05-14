INSERT INTO vethub.users (username, password)
VALUES ('john', '$2a$12$6htforEr47M4LVsqKBrQLurvI3Sk8A7upNo7FsKHUWuI7jWJrtoG6'),
       ('jane', '$2a$12$6htforEr47M4LVsqKBrQLurvI3Sk8A7upNo7FsKHUWuI7jWJrtoG6'),
       ('admin', '$2a$12$6htforEr47M4LVsqKBrQLurvI3Sk8A7upNo7FsKHUWuI7jWJrtoG6');


INSERT INTO vethub.animals (name, kind, birthdate)
VALUES ('Fluffy', 'Cat', '2019-03-15'),
       ('Max', 'Dog', '2018-06-10'),
       ('Buddy', 'Dog', '2020-01-25');


INSERT INTO vethub.vetrecords (name, description, date, reminder)
VALUES ('Fluffy Checkup', 'Routine checkup for Fluffy', '2023-04-10 10:00:00', '2024-04-10 10:00:00'),
       ('Max Vaccination', 'Annual vaccination for Max', '2023-05-05 15:30:00', NULL),
       ('Buddy Surgery', 'Surgery for Buddy', '2023-03-20 14:15:00', '2023-06-20 14:15:00');


INSERT INTO vethub.users_animals (user_id, animal_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 1);


INSERT INTO vethub.animals_vetrecords (animal_id, record_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);


INSERT INTO roles (user_id, role)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER');
