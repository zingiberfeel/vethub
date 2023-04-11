-- Insert users
INSERT INTO users (username, password) VALUES
                                           ('john_doe', '$2a$10$7aMU0c/G/hkwTlPhnl/RJu/v4K5qP94eTFHI45Cw./BI1dbvs/SwG'),
                                           ('jane_doe', '$2a$10$7aMU0c/G/hkwTlPhnl/RJu/v4K5qP94eTFHI45Cw./BI1dbvs/SwG'),
                                           ('bob_smith', '$2a$10$7aMU0c/G/hkwTlPhnl/RJu/v4K5qP94eTFHI45Cw./BI1dbvs/SwG');

-- Insert animals
INSERT INTO animals (name, kind, birthdate, picture_file_path) VALUES
                                                                                  ('Fido', 'Dog', '2022-02-09', '/pictures/fido.jpg'),
                                                                                  ('Mittens', 'Cat', '2015-01-11', '/pictures/mittens.jpg'),
                                                                                  ('Buddy', 'Dog', '2011-05-22', '/pictures/buddy.jpg'),
                                                                                  ('Whiskers', 'Cat', '2017-02-16', '/pictures/whiskers.jpg');

-- Insert vetrecords
INSERT INTO vetrecords (name, description, date) VALUES
                                                             ('Annual check-up', 'Routine check-up and vaccinations', '2022-03-15 10:30:00'),
                                                             ('Broken leg', 'Leg was broken and required surgery', '2022-03-15 10:30:00'),
                                                             ('Dental cleaning', 'Cleaning and scaling of teeth', '2022-03-15 10:30:00'),
                                                             ('Ear infection', 'Ear was infected and required medication', '2022-03-15 10:30:00');

-- Insert users_animals
INSERT INTO users_animals (user_id, animal_id) VALUES
                                                   (1, 1),
                                                   (1, 2),
                                                   (2, 3),
                                                   (3, 4),
                                                   (3, 1);

-- Insert animals_vetrecords
INSERT INTO animals_vetrecords (animal_id, record_id) VALUES
                                                          (1, 1),
                                                          (1, 2),
                                                          (2, 1),
                                                          (3, 3),
                                                          (4, 4);

-- Insert roles
INSERT INTO roles (user_id, role) VALUES
                                      (1, 'ROLE_ADMIN'),
                                      (2, 'ROLE_USER'),
                                      (3, 'ROLE_USER');
