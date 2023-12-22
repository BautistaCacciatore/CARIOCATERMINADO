package modelo;

import Enumerados.Color;
import Enumerados.Palo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Mazo implements Serializable {
    private ArrayList<Carta> cartas;
    private ArrayList<Carta> pozo;

    /*Constructor*/
    public Mazo(){
        this.cartas= new ArrayList<>();
        this.pozo= new ArrayList<>();
        cargarMazo();
        cargarMazo();
        mezclar();
        pozo.add(siguienteCarta(true));
    }

    /**
     * Carga el mazo con todos los palos y valores posibles
     * @return void
     **/
    private void cargarMazo(){
        //Creo un arreglo y agrego todos los palos posibles
        Palo[] palos= Palo.values();
        Color color;
        //Recorro todos los palos excepto el Joker
        for (int i=0; i<palos.length-1; i++){
            //Dentro de cada palo, obtengo el color correspondiente
            if (palos[i].equals(Palo.TREBOL) || palos[i].equals(Palo.PICA)){
                color= Color.NEGRO;
            }
            else{
                color= Color.ROJO;
            }
            //Agrego todas las cartas por palo
            for (int j=1; j<=13; j++){
                if (j==1){
                    cartas.add(new Carta("A", (Palo) palos[i], color, 1));
                }
                else if (j>=2 && j<=10){
                    cartas.add(new Carta(String.valueOf(j), (Palo) palos[i], color, j));
                } else if (j==11) {
                    cartas.add(new Carta("J", (Palo) palos[i], color, 11));
                } else if (j==12) {
                    cartas.add(new Carta("Q", (Palo) palos[i], color, 12));
                }
                else{
                    cartas.add(new Carta("K", (Palo) palos[i], color, 13));
                }
            }
        }
        //Agrego los comodines/joker
        cartas.add(new Carta("$", Palo.JOKER, Color.JOKER, -1));
        cartas.add(new Carta("$", Palo.JOKER, Color.JOKER, -1));
    }

    /**
     * Mezcla el mazo, intercambiando las posiciones de las cartas.
     * Si el mazo contiene 0 cartas, es decir, todas las cartas se encuentran en el pozo y en mano
     * de los jugadores, se pasan las cartas del pozo al mazo (excepto el tope) y se mezcla.
     * @return void
     **/
    public void mezclar(){
        Random random= new Random();
        int indiceCartaAleatoria;
        Carta c, c2;
        if (cantidadCartas()==0){
            for (int i=0; i<pozo.size()-1; i++){
                cartas.add(pozo.get(i));
                pozo.remove(i);
            }
        }
        for (int i=0; i<cartas.size(); i++){
            //Obtengo un indice aleatorio
            indiceCartaAleatoria= random.nextInt(cartas.size());
            //Recupero dos cartas e intercambio sus posiciones
            c= cartas.get(i);
            c2= cartas.get(indiceCartaAleatoria);
            cartas.set(i, c2);
            cartas.set(indiceCartaAleatoria, c);
        }
    }

    /**
     * Cuenta la cantidad de cartas que posee el mazo
     * @return int: cantidad de cartas del mazo
     **/
    public int cantidadCartas(){
        return cartas.size();
    }

    /**
     * Cuenta la cantidad de cartas que posee el pozo
     * @return int: cantidad de cartas del pozo
     **/
    public int cantidadCartasPozo(){
        return pozo.size();
    }

    /**
     * Da la siguiente carta, es decir, el tope del mazo o del pozo.
     * @param donde: indica si la carta debe ser tomada del mazo o del pozo
     * @return Carta: devuelve la carta tomada
     **/
    public Carta siguienteCarta(boolean donde){
        Carta c= null;
        //Si donde es true, significa que desea tomar una carta del mazo
        if (donde==true){
            //Si no hay cartas en el mazo, mezclo nuevamente
            if (cantidadCartas()==0){
                this.mezclar();
            }
            else{
                c= cartas.get(cartas.size()-1);
                cartas.remove(cartas.size()-1);
            }
        }
        //si es falso, retorno el tope del pozo
        else{
            c= pozo.get(pozo.size()-1);
            pozo.remove(pozo.size()-1);
        }

        return c;
    }

    /**
     * Este metodo retorna exactamente 12 cartas si es que el mazo contiene la cantidad necesaria
     * @return ArrayList</Carta>: 12 Cartas
     **/
    public ArrayList<Carta> darCartas(){
        ArrayList<Carta> cartasADar= new ArrayList<>();
        if (cartas.size()<12){
            return null;
        }
        else{
            for (int i=0; i<12; i++){
                cartasADar.add(siguienteCarta(true));
            }
            return cartasADar;
        }
    }

    /**
     * Añade una Carta al pozo
     * @param carta: carta a añadir en el pozo
     * @return void
     **/
    public void añadirCarta(Carta carta){
        this.pozo.add(carta);
    }

    /**
     * Obtiene el toString del tope del pozo
     * @param
     * @return String
     **/
    public String obtenerTopePozo(){
        return pozo.get(pozo.size()-1).toString();
    }
}
