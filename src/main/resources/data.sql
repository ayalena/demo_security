INSERT INTO users (username, password, enabled)
VALUES
('user', '{noop}p4ssw0rd', TRUE),
('admin', '{noop}p4ssw0rd', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');
