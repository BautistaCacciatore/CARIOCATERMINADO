package modelo;

import Enumerados.Color;
import Enumerados.Forma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Jugador implements Serializable {
    private String nickname;
    private ArrayList<Carta> cartas;
    private ArrayList<Bajada> formasArmadas;
    private boolean seBajo;
    private boolean seBajoRecien;
    private int puntos;

    /*Constructor*/
    public Jugador(String nickname){
        this.nickname= nickname;
        this.cartas= new ArrayList<>();
        this.formasArmadas= new ArrayList<>();
        this.puntos= 0;
        this.seBajo= false;
        this.seBajoRecien= false;
    }

    /**
     * Dado un mazo y un booleano, recoge una carta, si donde==true, debe recoger una carta del mazo,
     * sino, recoge una carta del pozo.
     * Se ordenan las cartas del jugador.
     * @param mazo: el mazo de donde se tomara la carta;
     * @param donde: indica si la carta se toma del mazo o el pozo.
     * @return void
     **/
    public void recogerCarta(Mazo mazo, boolean donde){
        this.cartas.add(mazo.siguienteCarta(donde));
        this.cartas= ordenarCartas();
    }

    /**
     * Dado un mazo y un indice, añade al mazo la carta que indica el indice, y elimina la carta al jugador.
     * (el mazo internamente se encarga de añadir la carta al pozo, no al mazo en si)
     * @param mazo: el mazo donde se añadira la carta;
     * @param i: indice que indica que carta debe tomarse del jugador
     * @return void
     **/
    public void dejarCarta(Mazo mazo, int i){
        if (i>=1 && i<=cartas.size()){
            mazo.añadirCarta(this.cartas.get(i-1));
            this.cartas.remove(i-1);
        }
    }

    /**
     * Dado un indice de carta, y los demas jugadores de la partida, toma la carta que indica el indice
     * e intenta bajarla, ya sea en alguna de sus cartas bajadas, o en las de los demas jugadores.
     * Si la carta pudo ser bajada, se elimina de la mano del jugador y se añade a la bajada dode fue bajada.
     * @param indiceCarta: el indice de la carta que debe tomarse;
     * @param jugadores: resto de jugadores de la partida;
     * @return boolean: indica si la carta pudo ser bajada o ni
     **/
    public boolean bajarCarta(int indiceCarta, ArrayList<Jugador> jugadores){
        indiceCarta= indiceCarta-1;
        boolean sePudoBajar= false;
        Carta cartaABajar= cartas.get(indiceCarta);
        if (cartaABajar != null){
            for (int i=0; i<formasArmadas.size(); i++){
                boolean bajo= formasArmadas.get(i).añadirCarta(cartaABajar);
                if (bajo==true){
                    this.cartas.remove(cartaABajar);
                    sePudoBajar= true;
                    return sePudoBajar;
                }
            }
            for (int j=0; j<jugadores.size(); j++){
                ArrayList<Bajada> formasArmadasOtroJugador= jugadores.get(j).getFormasArmadas();
                for (int k=0; k<formasArmadasOtroJugador.size(); k++){
                    boolean bajo2= formasArmadasOtroJugador.get(k).añadirCarta(cartaABajar);
                    if (bajo2== true){
                        this.cartas.remove(cartaABajar);
                        sePudoBajar= true;
                        return sePudoBajar;
                    }
                }
            }
        }
        else{
            return false;
        }
        return sePudoBajar;
    }

    /**
     * incrementa los puntos del jugador en cierta cantidad
     * @param puntos: la cantidad de puntos a incrementar;
     * @return void
     **/
    public void incrementarPuntos(int puntos){
        this.puntos += puntos;
    }

    /**
     * verifica si el jugador no tiene cartas
     * @return boolean: indica si el jugador no tiene cartas
     **/
    public boolean sinCartas(){
        if (this.cartas.size()==0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Recorre las cartas del jugador y cuenta los puntos que posee en la ronda
     * @return int: cantidad de puntos del jugador en la ronda
     **/
    public int calcularPuntosRonda(){
        int puntos= 0;
        for (int i=0; i<cartas.size(); i++){
            if (cartas.get(i).getValor().equals("A")){
                puntos+=20;
            }
            else if (cartas.get(i).getValor().equals("J") || cartas.get(i).getValor().equals("Q") || cartas.get(i).getValor().equals("K")){
                puntos+=10;
            } else if (cartas.get(i).esJoker()) {
                puntos+=50;
            }
            else{
                puntos+= Integer.parseInt(cartas.get(i).getValor());
            }
        }
        return puntos;
    }

    /**
     * El metodo recibe una ronda y recorre todas las formas y cantidad de la misma que deben
     * formarse en ella, intenta formar todas las formas correspondientes, si en algun caso no es posible,
     * pasa todas las cartas que fueron formadas a la mano nuevamente y marca todas las cartas como disponibles.
     * @param ronda : ronda correspondiente
     * @return boolean: indica si se pudieron formar todas las formas correspondientes
     **/
    public boolean bajarse(Ronda ronda){
        boolean puedeBajarse=false;
        int numeroRonda= ronda.getNumero();
        boolean trio1= false;
        boolean trio2= false;
        boolean trio3= false;
        boolean trio4= false;
        boolean escala1= false;
        boolean escala2= false;
        boolean escala3= false;
        boolean escaleraSucia= false;
        boolean escaleraReal= false;
        switch (numeroRonda){
            case 1:
                trio1= verificarTrio();
                trio2= verificarTrio();
                if (!trio1 || !trio2){
                    pasarCartasDeBajadasALaMano();
                    marcarCartasComoDisponibles();
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 2:
                trio1= verificarTrio();
                escala1= verificarEscala();
                if (!trio1 || !escala1){
                    pasarCartasDeBajadasALaMano();
                    marcarCartasComoDisponibles();
                    escala1= verificarEscala();
                    trio1= verificarTrio();
                    if (!trio1 || !escala1){
                        pasarCartasDeBajadasALaMano();
                        marcarCartasComoDisponibles();
                        return false;
                    }
                    else{
                        this.seBajo= true;
                        return true;
                    }
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 3:
                escala1= verificarEscala();
                escala2= verificarEscala();
                if (!escala1 || !escala2){
                    pasarCartasDeBajadasALaMano();
                    marcarCartasComoDisponibles();
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 4:
                trio1= verificarTrio();
                trio2= verificarTrio();
                trio3= verificarTrio();
                if (!trio1 || !trio2 || !trio3){
                    pasarCartasDeBajadasALaMano();
                    marcarCartasComoDisponibles();
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 5:
                trio1= verificarTrio();
                trio2= verificarTrio();
                escala1= verificarEscala();
                if (!trio1 || !trio2 || !escala1){
                    pasarCartasDeBajadasALaMano();
                    marcarCartasComoDisponibles();
                    escala1= verificarEscala();
                    trio1= verificarTrio();
                    trio2= verificarTrio();
                    if (!trio1 || !trio2 || !escala1){
                        pasarCartasDeBajadasALaMano();
                        marcarCartasComoDisponibles();
                        return false;
                    }
                    else{
                        this.seBajo= true;
                        return true;
                    }
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 6:
                escala1= verificarEscala();
                escala2= verificarEscala();
                trio1= verificarTrio();
                if (!trio1 || !escala1 || !escala2){
                    pasarCartasDeBajadasALaMano();
                    marcarCartasComoDisponibles();
                    trio1= verificarTrio();
                    escala1= verificarEscala();
                    escala2= verificarEscala();
                    if (!trio1 || !escala1 || !escala2){
                        pasarCartasDeBajadasALaMano();
                        marcarCartasComoDisponibles();
                        return false;
                    }
                    else{
                        this.seBajo= true;
                        return true;
                    }
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 7:
                escala1= verificarEscala();
                escala2= verificarEscala();
                escala3= verificarEscala();
                if (!escala1 || !escala2 || !escala3){
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 8:
                trio1= verificarTrio();
                trio2= verificarTrio();
                trio3= verificarTrio();
                trio4= verificarTrio();
                if (!trio1 || !trio2 || !trio3 || !trio4){
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 9:
                escaleraSucia= verificarEscaleraSucia();
                if (!escaleraSucia){
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            case 10:
                escaleraReal= verificarEscaleraReal();
                if (!escaleraReal){
                    return false;
                }
                else{
                    this.seBajo= true;
                    return true;
                }
            default:
                break;
        }
        return puedeBajarse;
    }

    /**
     * Verifica si el jugador puede formar un trio de cartas (mismo valor) con las cartas que posee en su mano.
     * Copia las cartas a un arrayAuxiliar, las ordena y elimina las que estan usadas en otra forma para no tenerlas en cuenta.
     * Tambien elimina los jokers para no generar problemas en el recorrido.
     * Cuenta los jokers que tiene el jugador en mano por si existiera la posibilidad de usarlo.
     * Recorre todas las cartas y cuenta las apariencias de cada una de ellas (basandose solo en su valor).
     * Si el contador llega a 2, y posee un joker, lo utiliza para formar el trio.
     * Si el contador llega a 3, forma el trio.
     * Siempre que forma un trio, las cartas son pasadas al metodo "pasarCartasABajadas" para formar el trio y las cartas son marcadas como usadas.
     * @return boolean: booleano que determina si pudo formar o no el trio
     **/
    public boolean verificarTrio()  {
        ArrayList<Carta> usadas= new ArrayList<>();
        ArrayList<Carta> copia= new ArrayList<>();
        copia= ordenarCartas();
        copia= eliminarUsadas(copia);
        copia= eliminarJokers(copia);
        int jokers= cantidadJoker();
        Carta joker = null;
        if (jokers >= 1){
            jokers= 1;
            joker= obtenerJoker();
        }

        for (int i = 0; i < copia.size(); i++) {
            Carta carta = copia.get(i);
            usadas.clear();
            int contador = 0;
            for (int j = 0; j < copia.size(); j++) {
                Carta otraCarta = copia.get(j);
                if (contador < 3 && carta.getValor().equals(otraCarta.getValor())) {
                    contador++;
                    usadas.add(otraCarta);
                }
            }
            if (contador == 2 && jokers == 1) {
                usadas.add(joker);
                usadas = marcarCartasComoUsadas(usadas);
                pasarCartasABajadas(usadas);
                return true;
            } else if (contador >= 3) {
                usadas = marcarCartasComoUsadas(usadas);
                pasarCartasABajadas(usadas);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el jugador puede formar una escala de cartas (4 cartas en escala y del mismo color) con las cartas que posee en su mano.
     * Copia las cartas a un arrayAuxiliar, las ordena y elimina las que estan usadas en otra forma para no tenerlas en cuenta.
     * Divide las cartas en dos, las cartasRojas y las cartasNegras, tambien elimina los valores duplicados.
     * Cuenta los jokers que tiene el jugador en mano por si existiera la posibilidad de usarlo.
     * Recorre las cartasRojas verificando si el indiceNumerico de las cartas son consecutivos. Ejemplo: A(1) 2(2), son cartas consecutivas.
     * Si el contador llega a 3, y posee un joker, lo utiliza para formar la escala.
     * Si el contador llega a 4, forma la escala.
     * Siempre que forma una escala, almacena las cartas utilizadas y las marca como usadas.
     * Si en algun momento se corta la cadena y tengo un joker disponible que me la forma, lo utilizo. Ejemplo: 4(4) 6(6), utilizo el Joker simulando que es un 5(5).
     * Cuando uso un joker, decremento el indice del bucle, para que continue en la posicion del Joker. Es decir, el for seguiria en la carta donde se uso el joker,
     * en este caso, 5(5), y la siguiente seria 6(6).
     * Si se vuelve a cortar la cadena, y ya utilice el Joker, reinicio todoo y decremento el indice del bucle.
     * Ejemplo: A J(2) 3 5 6 8. Yo utilice el Joker como 2, cuando llego al 5, se corta la cadena. Decremento el indice para que comience en 3 de nuevo y no en 5,
     * ya que, podria utilizar el Joker como 4: 3 J(4) 5 6.
     * Aplica el proceso a las cartasRojas y luego a las cartasNegras.
     * Siempre que se forma una escala, las cartas usadas son pasadas al metodo pasarCartasABajadas para formar la forma correspondiente.
     * @return boolean: booleano que determina si pudo formar o no la esccala.
     **/
    public boolean verificarEscala() {
        ArrayList<Carta> copiaCartas= ordenarCartas();
        copiaCartas= eliminarUsadas(copiaCartas);
        ArrayList<Carta> cartasRojas= obtenerCartasRojas(copiaCartas);
        cartasRojas= eliminarValoresDuplicados(cartasRojas);
        ArrayList<Carta> cartasNegras= obtenerCartasNegras(copiaCartas);
        cartasNegras= eliminarValoresDuplicados(cartasNegras);
        ArrayList<Carta> usadas= new ArrayList<>();
        Carta joker= null;
        int jokers= cantidadJoker();
        if (jokers>=1){
            jokers=1;
            joker= obtenerJoker();
        }
        boolean jokerUsado= false;
        boolean paso= false;

        int contador= 1;
        //recorro todas las cartas
        for (int i=0; i<cartasRojas.size()-1; i++){
            //tomo la primera como carta actual
            Carta cartaActual= cartasRojas.get(i);
            //si el joker esta en uso, lo asigno a la carta actual
            if (jokerUsado && !paso){
                cartaActual= joker;
                paso= true;
            }
            //si la carta actual no es el joker, lo agrego a las cartas usadas
            if (!cartaActual.esJoker()){
                usadas.add(cartaActual);
            }
            //tomo la siguiente carta
            Carta cartaSiguiente= cartasRojas.get(i+1);
            if ((cartaActual.getIndiceNumerico()+1==cartaSiguiente.getIndiceNumerico())) {
                //si las cartas son consecutivas, incremento y agrego a las cartas usadas
                contador+=1;
                if (contador==3 && jokers>=1 && !jokerUsado){
                    usadas.add(cartaSiguiente);
                    joker.setIndiceNumerico(cartaSiguiente.getIndiceNumerico()+1);
                    usadas.add(joker);
                    usadas= marcarCartasComoUsadas(usadas);
                    pasarCartasABajadas(usadas);
                    return true;
                } else if (contador>=4) {
                    usadas.add(cartaSiguiente);
                    usadas= marcarCartasComoUsadas(usadas);
                    pasarCartasABajadas(usadas);
                    return true;
                }
            }
            else{
                //si las cartas no son consecutivas y la carta actual no es un joker
                if (!cartaActual.esJoker()){
                    //si tengo algun joker disponible y me arma la cadena. Ejemplo: 2 Joker(3) 4
                    //cuento, le asigno al joker el indice numerico que esta ocupando y lo agrego a las cartas usadas
                    //lo marco como usado, y decremento i para que la carta actual ahora sea el Joker(3)
                    if (jokers>=1 && !jokerUsado && (cartaActual.getIndiceNumerico()+2==cartaSiguiente.getIndiceNumerico())){
                        contador+=1;
                        joker.setIndiceNumerico(cartaActual.getIndiceNumerico()+1);
                        usadas.add(joker);
                        jokerUsado= true;
                        i--;
                    }
                    //si se corto la cadena consecutiva, reinicio todoo
                    else{
                        contador=1;
                        usadas.clear();
                        jokerUsado= false;
                        paso= false;
                        //si la carta actual no es joker, y me haca falta un valor intermedio, decremento i porque tal vez pueda usar el joker
                        if (!cartaActual.esJoker() && cartaActual.getIndiceNumerico()+2==cartaSiguiente.getIndiceNumerico() && jokers>=1){
                            i--;
                            paso= false;
                        }
                    }
                }
                else{
                    contador=1;
                    usadas.clear();
                    jokerUsado= false;
                    paso= false;
                    if (!cartaActual.esJoker() && cartaActual.getIndiceNumerico()+2==cartaSiguiente.getIndiceNumerico() && jokers>=1){
                        i--;
                        paso= false;
                    }
                }
            }
        }

        Carta joker1= null;
        if (jokers>=1){
            joker1= obtenerJoker();
        }
        boolean jokerUsado1= false;
        boolean paso1= false;
        contador=1;
        usadas.clear();
        for (int i=0; i<cartasNegras.size()-1; i++){
            Carta cartaActual1= cartasNegras.get(i);
            if (jokerUsado1 && !paso1){
                cartaActual1= joker1;
                paso1= true;
            }
            if (!cartaActual1.esJoker()){
                usadas.add(cartaActual1);
            }
            Carta cartaSiguiente1= cartasNegras.get(i+1);
            if ((cartaActual1.getIndiceNumerico()+1==cartaSiguiente1.getIndiceNumerico())) {
                contador+=1;
                if (contador==3 && jokers>=1 && !jokerUsado1){
                    usadas.add(cartaSiguiente1);
                    joker1.setIndiceNumerico(cartaSiguiente1.getIndiceNumerico()+1);
                    usadas.add(joker1);
                    usadas= marcarCartasComoUsadas(usadas);
                    pasarCartasABajadas(usadas);
                    return true;
                } else if (contador>=4) {
                    usadas.add(cartaSiguiente1);
                    usadas= marcarCartasComoUsadas(usadas);
                    pasarCartasABajadas(usadas);
                    return true;
                }
            }
            else{
                if (!cartaActual1.esJoker()){
                    if (jokers>=1 && !jokerUsado1 && cartaActual1.getIndiceNumerico()+2==cartaSiguiente1.getIndiceNumerico()){
                        contador+=1;
                        joker1.setIndiceNumerico(cartaActual1.getIndiceNumerico()+1);
                        usadas.add(joker1);
                        jokerUsado1= true;
                        i--;
                    }
                    else{
                        contador=1;
                        usadas.clear();
                        jokerUsado1= false;
                        paso1= false;
                        if (!cartaActual1.esJoker() && cartaActual1.getIndiceNumerico()+2==cartaSiguiente1.getIndiceNumerico() && jokers>=1){
                            i--;
                            paso1= false;
                        }
                    }
                }
                else{
                    contador=1;
                    usadas.clear();
                    jokerUsado1= false;
                    paso1= false;
                    if (!cartaActual1.esJoker() && cartaActual1.getIndiceNumerico()+2==cartaSiguiente1.getIndiceNumerico() && jokers>=1){
                        i--;
                        paso1= false;
                    }
                }
            }
        }
        return false;
    }

    /*public boolean verificarEscalaSinJoker(){
        ArrayList<Carta> copiaCartas= ordenarCartas();
        copiaCartas= eliminarUsadas(copiaCartas);
        ArrayList<Carta> cartasRojas= obtenerCartasRojas(copiaCartas);
        cartasRojas= eliminarJokers(cartasRojas);
        cartasRojas= eliminarValoresDuplicados(cartasRojas);
        ArrayList<Carta> cartasNegras= obtenerCartasNegras(copiaCartas);
        cartasNegras= eliminarJokers(cartasNegras);
        cartasNegras= eliminarValoresDuplicados(cartasNegras);
        ArrayList<Carta> usadas= new ArrayList<>();

        int contador= 1;
        //recorro todas las cartas
        for (int i=0; i<cartasRojas.size()-1; i++){
            //tomo la primera como carta actual
            Carta cartaActual= cartasRojas.get(i);
            usadas.add(cartaActual);

            //tomo la siguiente carta
            Carta cartaSiguiente= cartasRojas.get(i+1);
            if ((cartaActual.getIndiceNumerico()+1==cartaSiguiente.getIndiceNumerico()) && !cartaActual.esUsada() && !cartaSiguiente.esUsada()) {
                //si las cartas son consecutivas, incremento y agrego a las cartas usadas
                contador+=1;
                if (contador>=4) {
                    usadas.add(cartaSiguiente);
                    usadas= marcarCartasComoUsadas(usadas);
                    pasarCartasABajadas(usadas);
                    return true;
                }
            }
            else{
                contador=1;
                usadas.clear();
            }
        }

        contador=1;
        usadas.clear();
        for (int i=0; i<cartasNegras.size()-1; i++){
            Carta cartaActual1= cartasNegras.get(i);
            usadas.add(cartaActual1);
            Carta cartaSiguiente1= cartasNegras.get(i+1);
            if ((cartaActual1.getIndiceNumerico()+1==cartaSiguiente1.getIndiceNumerico()) && !cartaActual1.esUsada() && !cartaSiguiente1.esUsada()) {
                contador+=1;
                if (contador>=4) {
                    usadas.add(cartaSiguiente1);
                    usadas= marcarCartasComoUsadas(usadas);
                    pasarCartasABajadas(usadas);
                    return true;
                }
            }
            else{
                contador=1;
                usadas.clear();
            }
        }

        return false;
    }/*

    /**
     * Verifica si el jugador puede formar una EscaleraSucia (13 cartas de la A a la K) con las cartas que posee en su mano.
     * Copia las cartas a un arrayAuxiliar, las ordena y elimina las que estan usadas en otra forma para no tenerlas en cuenta.
     * Elimina los jokers en mano si existieran (no pueden ser utilizados en la EscaleraSucia) y los valores duplicados.
     * Luego de los controles, verifica si el jugador tiene 13 cartas.
     * Recorre las cartas en orden, verificando que sean consecutivas, y las cuenta. Si en algun momento, las cartas no son consecutivas, retorna falso.
     * Si el contador llega a 13, forma la EscaleraSucia.
     * A medida que se forma la escalera, almacena las cartas utilizadas y las marca como usadas.
     * * Siempre que se forma una EscalaraSucia, las cartas usadas son pasadas al metodo pasarCartasABajadas para formar la forma correspondiente.
     * @return boolean: booleano que determina si pudo formar o no la EscaleraSucia
     **/
    public boolean verificarEscaleraSucia(){
        ArrayList<Carta> copia= new ArrayList<>();
        ArrayList<Carta> usadas= new ArrayList<>();
        copia= ordenarCartas();
        copia= eliminarUsadas(copia);
        copia= eliminarJokers(copia);
        copia= eliminarValoresDuplicados(copia);
        if (copia.size()==13){
            int contador=1;
            for (int i=0; i<copia.size()-1; i++){
                Carta actual= copia.get(i);
                usadas.add(actual);
                Carta siguiente= copia.get(i+1);
                if (actual.getIndiceNumerico()+1==siguiente.getIndiceNumerico()){
                    contador+=1;
                    if (contador==13){
                        usadas.add(siguiente);
                        usadas= marcarCartasComoUsadas(copia);
                        pasarCartasABajadas(usadas);
                        return true;
                    }
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
        return false;
    }

    /**
     * Verifica si el jugador puede formar una EscaleraReal (13 cartas de la A a la K y del mismo color) con las cartas que posee en su mano.
     * Copia las cartas a un arrayAuxiliar, las ordena y elimina las que estan usadas en otra forma para no tenerlas en cuenta.
     * Elimina los jokers en mano si existieran (no pueden ser utilizados en la EscaleraSucia) y los valores duplicados.
     * Divide las cartas en cartasRojas y cartasNegras.
     * Luego de los controles, verifica si en las cartasRojas o las cartasNegras el jugador tiene 13 cartas.
     * Recorre el color que posea 13 cartas en orden, verificando que sean consecutivas, y las cuenta. Si en algun momento, las cartas no son consecutivas, retorna falso.
     * Si el contador llega a 13, forma la EscaleraReal.
     * A medida que se forma la escalera, almacena las cartas utilizadas y las marca como usadas.
     * * Siempre que se forma una EscaleraReal, las cartas usadas son pasadas al metodo pasarCartasABajadas para formar la forma correspondiente.
     * @return boolean: booleano que determina si pudo formar o no la EscaleraReal
     **/
    public boolean verificarEscaleraReal(){
        ArrayList<Carta> copia= new ArrayList<>();
        ArrayList<Carta> usadas= new ArrayList<>();
        copia= ordenarCartas();
        copia= eliminarUsadas(copia);
        copia= eliminarJokers(copia);
        copia= eliminarValoresDuplicados(copia);
        if (copia.size()==13){
            ArrayList<Carta> cartasRojas= obtenerCartasRojas(copia);
            ArrayList<Carta> cartasNegras= obtenerCartasNegras(copia);
            int contador=1;
            if (cartasRojas.size()==13){
                for (int i=0; i<cartasRojas.size()-1; i++){
                    Carta actual= cartasRojas.get(i);
                    usadas.add(actual);
                    Carta siguiente= cartasRojas.get(i+1);
                    if (actual.getIndiceNumerico()+1==siguiente.getIndiceNumerico()){
                        contador+=1;
                        if (contador==13){
                            usadas.add(siguiente);
                            usadas= marcarCartasComoUsadas(usadas);
                            pasarCartasABajadas(usadas);
                            return true;
                        }
                    }
                    else{
                        return false;
                    }
                }
            } else if (cartasNegras.size()==13) {
                for (int i=0; i<cartasNegras.size()-1; i++){
                    Carta actual= cartasNegras.get(i);
                    usadas.add(actual);
                    Carta siguiente= cartasNegras.get(i+1);
                    if (actual.getIndiceNumerico()+1==siguiente.getIndiceNumerico()){
                        contador+=1;
                        if (contador==13){
                            usadas.add(siguiente);
                            marcarCartasComoUsadas(usadas);
                            pasarCartasABajadas(usadas);
                            return true;
                        }
                    }
                    else{
                        return false;
                    }
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
        return false;
    }

    /**
     * Verifica si todas las cartas de un array son del mismo color.
     * @param cartas: el array de cartas;
     * @return boolean: determina si todas las cartas son del mismo color o no.
     **/
    private boolean mismoColor(ArrayList<Carta> cartas){
        boolean si= true;
        Color primerColor = null;
        for (int i=0; i<cartas.size(); i++){
            if (i==0){
                primerColor= cartas.get(i).getColor();
            }
            else{
                if (!cartas.get(i).getColor().equals(primerColor)){
                    return false;
                }
            }
        }
        return si;
    }

    /**
     * Recibe un array de cartas, elimina estas cartas de la mano del jugador y crea una forma que formo el jugador,
     * añade la forma al array de formas armadas que posee el jugador.
     * @param cartasUsadasParaLaForma: las cartas que el jugador uso para formar una forma;
     * @return
     **/
    private void pasarCartasABajadas(ArrayList<Carta> cartasUsadasParaLaForma) {
        for (Carta carta : cartasUsadasParaLaForma) {
            this.cartas.remove(carta);
        }

        Forma forma = null;
        if (cartasUsadasParaLaForma.size()==3){
            forma= Forma.TRIO;
        } else if (cartasUsadasParaLaForma.size()==4) {
            forma= Forma.ESCALA;
        } else if (mismoColor(cartasUsadasParaLaForma) && cartasUsadasParaLaForma.size()==13) {
            forma= Forma.ESCALERAREAL;
        }
        else if (!mismoColor(cartasUsadasParaLaForma) && cartasUsadasParaLaForma.size()==13){
            forma= Forma.ESCALERASUCIA;
        }

        this.formasArmadas.add(new Bajada(forma, cartasUsadasParaLaForma));
    }

    /**
     * Recorre todas las formas armadas por el jugador, y pasa las cartas a la mano del mismo.
     * Elimina las formas que habia armado.
     * @return
     **/
    private void pasarCartasDeBajadasALaMano(){
        for (int i=0; i<formasArmadas.size(); i++){
            ArrayList<Carta> cartas= formasArmadas.get(i).getCartasQueLaForman();
            for (int j=0; j<cartas.size(); j++){
                this.cartas.add(cartas.get(j));
            }
            formasArmadas.remove(i);
        }
    }

    /**
     * Elimina los jokers de un Array de Cartas.
     * @param cartas: el array de donde se eliminaran los Jokers;
     * @return ArrayList</Carta>: el mismo array sin Jokers.
     **/
    private ArrayList<Carta> eliminarJokers(ArrayList<Carta> cartas){
        ArrayList<Carta> copia= new ArrayList<>();
        for (int i=0; i<cartas.size(); i++){
            if (!cartas.get(i).esJoker()){
                copia.add(cartas.get(i));
            }
        }
        return copia;
    }

    /**
     * Elimina los valores duplicados de un Array de Cartas.
     * @param cartas: el array de donde se eliminaran las cartas duplicadas;
     * @return ArrayList</Carta>: el mismo array sin cartas duplicadas.
     **/
    private ArrayList<Carta> eliminarValoresDuplicados(ArrayList<Carta> cartas){
        for (int i=0; i<cartas.size(); i++){
            Carta actual= cartas.get(i);
            for (int j= 0; j<cartas.size(); j++){
                if (i != j){
                    if (actual.getValor().equals(cartas.get(j).getValor())){
                        cartas.remove(j);
                    }
                }
            }
        }
        return cartas;
    }

    /**
     * Ordena las cartas en mano del jugador.
     * @return ArrayList</Carta>: las cartas del jugador ordenadas.
     **/
    public ArrayList<Carta> ordenarCartas(){
        ArrayList<Carta> nuevoArray= new ArrayList<>();
        nuevoArray.addAll(this.cartas);
        Collections.sort(nuevoArray, this::compararCartas);

        return nuevoArray;
    }

    /**
     * Elimina las Cartas que estan usadas de un Array de Cartas.
     * @param cartas: el array de donde se eliminaran las cartas usadas;
     * @return ArrayList</Carta>: el mismo array sin cartas usadas.
     **/
    private ArrayList<Carta> eliminarUsadas(ArrayList<Carta> cartas) {
        ArrayList<Carta> cartasNoUsadas = new ArrayList<>();

        for (int i=0; i<cartas.size(); i++) {
            if (!cartas.get(i).esUsada()) {
                cartasNoUsadas.add(cartas.get(i));
            }
        }

        return cartasNoUsadas;
    }

    /**
     * Obtiene las cartas rojas de un array de Cartas.
     * @param cartas: Array de cartas;
     * @return ArrayList</Carta>: array que solo contiene a las cartas rojas del array original.
     **/
    public ArrayList<Carta> obtenerCartasRojas(ArrayList<Carta> cartas){
        ArrayList<Carta> rojas= new ArrayList<>();
        for (int i=0; i<cartas.size(); i++){
            if ((cartas.get(i).getColor().equals(Color.ROJO) || cartas.get(i).getColor().equals(Color.JOKER))){
                rojas.add(cartas.get(i));
            }
        }
        return rojas;
    }

    /**
     * Obtiene las cartas negras de un array de Cartas.
     * @param cartas: Array de cartas;
     * @return ArrayList</Carta>: array que solo contiene a las cartas negras del array original.
     **/
    public ArrayList<Carta> obtenerCartasNegras(ArrayList<Carta> cartas){
        ArrayList<Carta> negras= new ArrayList<>();
        for (int i=0; i<cartas.size(); i++){
            if ((cartas.get(i).getColor().equals(Color.NEGRO) || cartas.get(i).getColor().equals(Color.JOKER))){
                negras.add(cartas.get(i));
            }
        }
        return negras;
    }

    /**
     * Compara dos cartas en base a su indiceNumerico
     * @param carta1: carta;
     * @param carta2: carta;
     * @return int: indica que carta es mayor. Si carta1<carta2, retorna -1. Si carta1>carta2, retorna 1. Si carta1==carta2, retorna 0.
     **/
    private int compararCartas(Carta carta1, Carta carta2) {
        return Integer.compare(carta1.getIndiceNumerico(), carta2.getIndiceNumerico());
    }

    /**
     * Cuenta la cantidad de Jokers que un jugador tiene en mano.
     * @return int: cantidad de Jokers.
     **/
    private int cantidadJoker(){
        int contador=0;
        for (int i=0; i<cartas.size(); i++){
            if (cartas.get(i).getValor().equals("$") && !cartas.get(i).esUsada()){
                contador+=1;
            }
        }
        return contador;
    }

    /**
     * Recorre las cartas en mano del jugador y devuelve el Joker en caso de que lo tuviera.
     * @return Carta: carta Joker, null si no tiene.
     **/
    private Carta obtenerJoker(){
        for (int i=0; i<cartas.size(); i++){
            if (cartas.get(i).getValor().equals("$")){
                return cartas.get(i);
            }
        }
        return null;
    }

    /**
     * Recorre el array de cartas que recibe y marca todas las cartas como usadas.
     * @param cartas: Array de cartas;
     * @return ArrayList</Carta>: cartas marcadas como usadas.
     **/
    public ArrayList<Carta> marcarCartasComoUsadas(ArrayList<Carta> cartas){
        for (int i=0; i<this.cartas.size(); i++){
            Carta actual= this.cartas.get(i);
            for (int j=0; j<cartas.size(); j++){
                Carta actual2= cartas.get(j);
                if (actual.equals(actual2)){
                    actual.setUsada(true);
                }
            }
        }
        return cartas;
    }

    /**
     * Recorre las cartas del jugador y marca todas como disponibles
     * @return
     **/
    public void marcarCartasComoDisponibles(){
        for (int i=0; i<this.cartas.size(); i++){
            cartas.get(i).setUsada(false);
        }
    }

    /**
     * Limpia las cartas bajadas del jugador, es decir, elimina las formas que armo.
     * @return
     **/
    public void limpiarCartasBajadas(){
        this.formasArmadas.clear();
    }

    /*Setters*/
    public void setCartas(ArrayList<Carta> cartas){
        this.cartas= cartas;
        this.cartas= ordenarCartas();
    }

    public void setSeBajo(boolean seBajo) {
        this.seBajo = seBajo;
    }

    public void setSeBajoRecien(boolean seBajoRecien){
        this.seBajoRecien=seBajoRecien;
    }

    /*Getters*/
    public String getNickname() {
        return nickname;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getCantidadCartas(){
        return cartas.size();
    }

    public ArrayList<Bajada> getFormasArmadas() {
        return formasArmadas;
    }

    public boolean getSeBajo() {
        return seBajo;
    }

    public boolean getSeBajoRecien(){ return  seBajoRecien; }
}
