package modelo;
import Enumerados.*;

import java.io.Serializable;

public class Carta implements Serializable {
    private String valor;
    private Palo palo;
    private Color color;
    private int indiceNumerico;
    private boolean usada;

    /*Constructor*/
    public Carta(String valor, Palo palo, Color color, int indiceNumerico){
        this.valor= valor;
        this.palo = palo;
        this.color= color;
        this.indiceNumerico= indiceNumerico;
        this.usada= false;
    }

    /*Getters*/
    public String getValor() {
        return valor;
    }

    public Palo getPalo() {
        return palo;
    }

    public Color getColor() {
        return color;
    }

    public int getIndiceNumerico() {
        return indiceNumerico;
    }

    public boolean esUsada() {
        return usada;
    }

    /*Setters*/
    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public void setIndiceNumerico(int indiceNumerico) {
        this.indiceNumerico = indiceNumerico;
    }

    /**
     * Verifica si la carta es Joker/Comodin
     * @return boolean: indica si la carta es Joker.
     **/
    public boolean esJoker(){
        if (this.valor.equals("$")){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public String toString() {
        return "valor: " +  valor + ", palo: " + palo + ", color:" + color;
    }

}
