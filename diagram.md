classDiagram
direction TB
class App {
  + App() 
  + actualizarVentana(String, int, String, char[][]) void
  + ventanaGUI(String, int, String, char[][], int) void
  + main(String[]) void
}
class EngineGUI {
  + EngineGUI() 
  + generarPartida(int) void
  + imprimirPanel(char[][]) void
  + showConsonant() void
  + menuPremioNumerico(int, int) boolean
  + isNumeric(String) boolean
  + getJugadorName(int) String
  + comprobarLetra(char) int
  + nuevoEnigmaYPista() void
  + setPanelSolved(boolean) boolean
  + comprobarVocal(char) boolean
   char[][] enigmaProgreso
   String pista
   char[][] enigmaPanel
   int numeroJugadores
   boolean isPanelSolved
}
class Enigma {
  + Enigma(SQLDriver) 
  ~ String pista
  ~ String frase
   String panelEnigma
   String pista
   String frase
   char[][] panel
}
class Player {
  + Player(String, int) 
  ~ String casillaRuleta
  ~ Logger logger
   String name
   Logger logger
   boolean turn
   int moneyAcumulado
   int money
   String casillaRuleta
   int comodin
}
class Ruleta {
  + Ruleta() 
  + girarRuleta() StringBuilder
   String ruletaTemplate
   String resultadoRuleta
   String[] ruleta
}
class SQLDriver {
  + SQLDriver() 
  ~ String pista
  ~ String enigma
   String pista
   String enigma
}

App  ..>  EngineGUI : «create»
EngineGUI  ..>  App 
EngineGUI  ..>  Enigma : «create»
EngineGUI  ..>  Player : «create»
EngineGUI "1" *--> "players *" Player 
EngineGUI "1" *--> "ruleta 1" Ruleta 
EngineGUI  ..>  Ruleta : «create»
EngineGUI  ..>  SQLDriver : «create»
Enigma  ..>  SQLDriver 
