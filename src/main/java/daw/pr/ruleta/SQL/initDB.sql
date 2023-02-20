DROP TABLE enigmas;
-- Crear tabla enigmas
CREATE TABLE enigmas
(
    id_enigma  INTEGER     NOT NULL PRIMARY KEY AUTOINCREMENT,
    contestada BOOLEAN     NOT NULL DEFAULT FALSE,
    enigma     VARCHAR(52) NOT NULL,
    pista      VARCHAR(12) NOT NULL DEFAULT 'REFRÁN'
);


DROP TABLE jugadores;
CREATE TABLE jugadores
(
    id_jugador INTEGER     NOT NULL PRIMARY KEY AUTOINCREMENT,
    nombre     VARCHAR(12) NOT NULL,
    dinero     INTEGER     NOT NULL DEFAULT 0,
    victorias  INTEGER     NOT NULL DEFAULT 0
);

-- Añadir a la tabla enigmas los siguientes enigmas con sus pistas correspondientes:
INSERT INTO enigmas (enigma, pista)
VALUES ('Cada loco con su tema', 'Dicho'),
       ('El que no salta es porque no quiere', 'Dicho'),
       ('A caballo regalado no le mires el diente', 'Dicho'),
       ('El que mucho abarca poco aprieta', 'Dicho'),
       ('A buen entendedor pocas palabras bastan', 'Dicho'),
       ('A quien madruga Dios le ayuda', 'Dicho'),
       ('A lo hecho pecho', 'Dicho'),
       ('A lo tonto tonto y a lo tonto tonto', 'Dicho'),
       ('A mal tiempo buena cara', 'Dicho'),
       ('A palos y a besos', 'Dicho'),
       ('A quien madruga Dios le ayuda', 'Dicho'),

       ('Saber no ocupa lugar', 'Refrán'),
       ('A caballo regalado no le mires el diente', 'Refrán'),
       ('A buen entendedor pocas palabras bastan', 'Refrán'),
       ('A mal tiempo buena cara', 'Refrán'),
       ('A quien madruga Dios le ayuda', 'Refrán'),
       ('A todo corcho le falta un tapón', 'Refrán'),
       ('A todo hay que sacarle partido', 'Refrán'),
       ('A todo le viene bien un cambio', 'Refrán');
