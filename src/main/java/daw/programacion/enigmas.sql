DROP TABLE enigmas;
-- Crear tabla enigmas
CREATE TABLE enigmas (
  id_enigma INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  contestada BOOLEAN NOT NULL DEFAULT FALSE,
  enunciado VARCHAR(255) NOT NULL
);

-- Añadir datos a la tabla enigmas, estos serán 30 refranes
INSERT INTO enigmas (contestada, enunciado) VALUES
  (FALSE, 'No hay mal que por bien no venga'),
  (FALSE, 'A caballo regalado no se le miran los dientes'),
  (FALSE, 'El que mucho abarca, poco aprieta'),
  (FALSE, 'A buen entendedor, pocas palabras bastan'),
  (FALSE, 'A mal tiempo, buena cara'),
  (FALSE, 'A quien madruga, Dios le ayuda'),
  (FALSE, 'A ratos buenos, ratos malos'),
  (FALSE, 'A todo hay que darle tiempo'),
  (FALSE, 'Aunque la mona se vista de seda, mona se queda'),
  (FALSE, 'Aunque la vaca sea blanca, leche da'),
  (FALSE, 'Aunque la venda sea más grande que la herida, la herida sigue ahí'),
  (FALSE, 'Aunque sea de noche, el día amanece'),
  (FALSE, 'Aunque te cueste la vida, no mires atrás'),
  (FALSE, 'Boca cerrada, no entran moscas'),
  (FALSE, 'Cada loco con su tema'),
  (FALSE, 'Cada maestrillo tiene su librillo'),
  (FALSE, 'Cada uno en su casa y Dios en la de todos'),
  (FALSE, 'Cada uno a su santo'),
  (FALSE, 'Cada uno a su vez'),
  (FALSE, 'Cada uno a su manera'),
  (FALSE, 'Cada uno a su gusto'),
  ( FALSE, 'Cada uno a su aire');