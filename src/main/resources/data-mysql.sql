INSERT INTO role (id, nom) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_REDACTEUR'),
(3, 'ROLE_ADMIN');

INSERT INTO utilisateur (id, prenom, nom, mot_de_passe) VALUES
(1, 'franky', 'bansept', '$2a$10$pmKgMydLRW6wkF6hoPDGZ.vyBKYgjsKEcwm2FO7eO9j0QoDmXhtNK'),
(2, 'john', 'doe', '$2a$10$pmKgMydLRW6wkF6hoPDGZ.vyBKYgjsKEcwm2FO7eO9j0QoDmXhtNK'),
(3, 'steeve', 'smith', '$2a$10$pmKgMydLRW6wkF6hoPDGZ.vyBKYgjsKEcwm2FO7eO9j0QoDmXhtNK');

INSERT INTO role_utilisateur (utilisateur_id, role_id) VALUES
(1,1),
(1,3),
(2,1),
(2,2),
(3,1);


INSERT INTO marque (id, nom) VALUES
(1, 'DELL'),
(2, 'BIC');

INSERT INTO materiel (id, nom, code, marque_id) VALUES
(1, 'Ecran', 'ECRANDELL001', 1),
(2, 'Clavier', 'CLAVIERDELL001', 1),
(3, 'Marqueur rouge', 'MARQUEURROUGE', 2);

INSERT INTO specificite (id, nom) VALUES
(1, 'A cadenasser'),
(2, 'Fragile');

INSERT INTO materiel_specificite (materiel_id, specificite_id) VALUES
(1, 1),
(1, 2),
(2, 2);
