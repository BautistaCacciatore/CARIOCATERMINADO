package modelo;
import Enumerados.Forma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Bajada implements Serializable {
    private Forma nombreForma;
    private ArrayList<Carta> cartasQueLaForman;

    /*Constructor*/
    public Bajada(Forma nombreForma, ArrayList<Carta> cartas){
        this.nombreForma= nombreForma;
        cartasQueLaForman= cartas;
        cartasQueLaForman= ordenarCartas();
    }

    /*Getters*/
    public Forma getNombreForma() {
        return nombreForma;
    }

    public ArrayList<Carta> getCartasQueLaForman() {
        return cartasQueLaForman;
    }

    /**
     * Recibe una carta e intenta añadirla a la bajada. Si puede agregarla, la marca como usada y la añade, ademas ordena la bajada.
     * @param carta: carta a añadir;
     * @return boolean: indica si la carta pudo ser añadida a la bajada.
     **/
    public boolean añadirCarta(Carta carta){
        if (this.nombreForma.equals(Forma.TRIO)){
            if (!cartasQueLaForman.get(0).getValor().equals("$")){
                if (carta.getValor().equals(cartasQueLaForman.get(0).getValor()) || carta.esJoker()){
                    carta.setUsada(true);
                    cartasQueLaForman.add(carta);
                    this.cartasQueLaForman= ordenarCartas();
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                if (carta.getValor().equals(cartasQueLaForman.get(1).getValor()) || carta.esJoker()){
                    carta.setUsada(true);
                    cartasQueLaForman.add(carta);
                    this.cartasQueLaForman= ordenarCartas();
                    return true;
                }
                else{
                    return false;
                }
            }
        } else if (this.nombreForma.equals(Forma.ESCALA)) {
            if (carta.getIndiceNumerico()+1==cartasQueLaForman.get(0).getIndiceNumerico() || cartasQueLaForman.get(cartasQueLaForman.size()-1).getIndiceNumerico()+1==carta.getIndiceNumerico() || carta.esJoker()){
                carta.setUsada(true);
                cartasQueLaForman.add(carta);
                this.cartasQueLaForman= ordenarCartas();
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Ordena las cartas en mano del jugador.
     * @return ArrayList</Carta>: las cartas del jugador ordenadas.
     **/
    public ArrayList<Carta> ordenarCartas(){
        ArrayList<Carta> nuevoArray= new ArrayList<>();
        nuevoArray.addAll(this.cartasQueLaForman);
        Collections.sort(nuevoArray, this::compararCartas);

        return nuevoArray;
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

}
