DROP TABLE enigmas;
-- Crear tabla enigmas
CREATE TABLE enigmas (
  id_enigma INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  contestada BOOLEAN NOT NULL DEFAULT FALSE,
  enunciado VARCHAR(255) NOT NULL
);

-- Añadir datos a la tabla enigmas, estos serán 30 refranes
INSERT INTO enigmas (id_enigma, contestada, enunciado) VALUES
  (1, FALSE, 'No hay mal que por bien no venga'),
  (2, FALSE, 'A caballo regalado no se le miran los dientes'),
  (3, FALSE, 'El que mucho abarca, poco aprieta'),
  (4, FALSE, 'A buen entendedor, pocas palabras bastan'),
  (5, FALSE, 'A mal tiempo, buena cara'),
  (6, FALSE, 'A quien madruga, Dios le ayuda'),
  (7, FALSE, 'A ratos buenos, ratos malos'),
  (8, FALSE, 'A todo hay que darle tiempo'),
  (9, FALSE, 'Aunque la mona se vista de seda, mona se queda'),
  (10, FALSE, 'Aunque la vaca sea blanca, leche da'),
  (11, FALSE, 'Aunque la venda sea más grande que la herida, la herida sigue ahí'),
  (12, FALSE, 'Aunque sea de noche, el día amanece'),
  (13, FALSE, 'Aunque te cueste la vida, no mires atrás'),
  (14, FALSE, 'Boca cerrada, no entran moscas'),
  (15, FALSE, 'Cada loco con su tema'),
  (16, FALSE, 'Cada maestrillo tiene su librillo'),
  (17, FALSE, 'Cada uno en su casa y Dios en la de todos'),
  (18, FALSE, 'Cada uno a su santo'),
  (19, FALSE, 'Cada uno a su vez'),
  (20, FALSE, 'Cada uno a su manera'),
  (21, FALSE, 'Cada uno a su gusto'),
  (22, FALSE, 'Cada uno a su aire');