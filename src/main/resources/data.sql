INSERT INTO users (username, password, enabled)
VALUES
--('user', '{noop}p4ssw0rd', TRUE),
--('admin', '{noop}p4ssw0rd', TRUE);
--in plaats hiervan gaan we encrypted ww gebruiken:
('user', '$2a$12$/qvUQlMHbJ0mc.BYL.NUme41on.RaDNkPIswk6Md1mAduUdvemnKi', TRUE),
('admin', '$2a$12$/qvUQlMHbJ0mc.BYL.NUme41on.RaDNkPIswk6Md1mAduUdvemnKi', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');
