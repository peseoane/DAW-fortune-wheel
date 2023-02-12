DROP TABLE enigmas;
-- Crear tabla enigmas
CREATE TABLE enigmas
(
    id_enigma  INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
    contestada BOOLEAN      NOT NULL DEFAULT FALSE,
    enigma     VARCHAR(255) NOT NULL,
    pista      VARCHAR(255) NOT NULL DEFAULT 'No hay pista'
);

DROP TABLE jugadores;
CREATE TABLE jugadores
(
    id_jugador INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
    nombre     VARCHAR(255) NOT NULL,
    dinero     INTEGER      NOT NULL DEFAULT 0,
    victorias  INTEGER      NOT NULL DEFAULT 0
);

-- Añadir datos a la tabla enigmas, estos serán 30 refranes, la columna 0 tendrá la pista y la columna 1 tendrá el refrán en si.
INSERT INTO enigmas (enigma)
VALUES ('No hay mal que por bien no venga'),
       ('A caballo regalado no se le miran los dientes'),
       ('El que mucho abarca, poco aprieta'),
       ('A buen entendedor, pocas palabras bastan'),
       ('A mal tiempo, buena cara'),
       ('A quien madruga, Dios le ayuda'),
       ('A ratos buenos, ratos malos'),
       ('A todo hay que darle tiempo'),
       ('Aunque la mona se vista de seda, mona se queda'),
       ('Aunque la vaca sea blanca, leche da'),
       ('Aunque la venda sea más grande que la herida, la herida sigue ahí'),
       ('Aunque sea de noche, el día amanece'),
       ('Aunque te cueste la vida, no mires atrás'),
       ('Boca cerrada, no entran moscas'),
       ('Cada loco con su tema'),
       ('Cada maestrillo tiene su librillo'),
       ('Cada uno en su casa y Dios en la de todos'),
       ('Cada uno a su santo'),
       ('Cada uno a su vez'),
       ('Cada uno a su manera'),
       ('Cada uno a su gusto'),
       ('Cada uno a su aire');

