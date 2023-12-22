package modelo;

import Enumerados.Forma;

import java.io.Serializable;
import java.util.HashMap;

public class Ronda implements Serializable {
    private int numero;
    private HashMap<Forma, Integer> formas;
    private int totalCartas;

    /*Constructor*/
    public Ronda(int numero){
        this.numero= numero;
        formas= new HashMap<>();
    }

    /**
     * Dada una Forma y la cantidad de veces que hay que formarla, se añade a la ronda.
     * Acumula en el totalCartas la cantidad de cartas necesarias para formar dicha forma y cantidad.
     * @param forma: la forma a formar;
     * @param cantidad: la cantidad de veces que hay que formar la forma.
     * @return void
     **/
    public void añadirForma(Forma forma, int cantidad){
        formas.put(forma, cantidad);
        if (forma.equals(Forma.TRIO)){
            totalCartas+= 3*cantidad;
        }
        else if(forma.equals(Forma.ESCALA)){
            totalCartas+= 4*cantidad;
        }
        else{
            totalCartas+= 13;
        }
    }

    /*Getters*/
    public int getNumero() {
        return numero;
    }

    public HashMap<Forma, Integer> getFormas() {
        return formas;
    }

    public int getTotalCartas() {
        return totalCartas;
    }

}
