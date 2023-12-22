package modelo;

import java.io.*;
import java.util.*;

public class AdministradorMejoresJugadores implements Serializable {
    HashMap<String, Integer> nicknameJPartidasGanadas;
    Serializador serializador;

    public AdministradorMejoresJugadores(){
        nicknameJPartidasGanadas= new HashMap<>();
        serializador= new Serializador();
    }

    /**
     * Agrega el nickname del jugador y la cantidad de partidas ganadas al HashMap.
     * Primero lee el archivo, y luego añade, esto lo hace para agregar los datos al HashMap serializado.
     * @param nickame: nickname del jugador
     * @param partidasGanadas: cantidad de partidas ganadas
     * @return void
     **/
    public void agregar(String nickame, Integer partidasGanadas){
        nicknameJPartidasGanadas= leer();
        nicknameJPartidasGanadas.put(nickame, partidasGanadas);
    }

    /**
     * Escribe en el archivo el HashMap serializado
     * @param
     * @return void
     **/
    public void escribir(){
        serializador.escribir("MJserializado.bin", nicknameJPartidasGanadas);
    }

    /**
     * Lee del archivo el HashMap serializado
     * @param
     * @return HashMap<String, Integer>
     **/
    public HashMap<String, Integer> leer(){
        return (HashMap<String, Integer>) serializador.leer("MJserializado.bin");
    }

    /**
     * Actualiza el archivo, primero verifica si el nickname ya se encuentra en el HashMap serializado,
     * si es asi, incrementa la cantidad de partidas ganadas del jugador y vuelve a escribir.
     * Si no se encuentra el nickname, se lo agrega y escribe el archivo.
     * @param nickname: Nickname del jugador
     * @return void
     **/
    public void actualizar(String nickname){
        boolean esta= false;
        nicknameJPartidasGanadas= leer();
        for (String nicknameJ : nicknameJPartidasGanadas.keySet()) {
            if (nicknameJ.equals(nickname)){
                int partidasGanadas= nicknameJPartidasGanadas.get(nicknameJ) + 1;
                nicknameJPartidasGanadas.put(nickname, partidasGanadas);
                esta= true;
            }
        }
        if (esta==true){
            escribir();
        }
        else{
            agregar(nickname, 1);
            escribir();
        }
    }

    /**
     * Metodo que ordena el HashMap de manera descendente en base a sus claves.
     * Primero convierto el HashMap en una lista para ordenarla.
     * Ordeno la lista en base a un comparador personalizadom basandome en los valores de cada entrada
     * del HashMap.
     * Creo un LinkedHashMap para guardar los resultados ordenados, ya que el LinkedHashMap mantiene
     * el orden de insercion.
     * @param mapa: HashMap a ordenar
     * @return HashMap<String, Integer>: El mismo mapa ordenado de manera descendente
     **/
    private HashMap<String, Integer> ordenar(HashMap<String, Integer> mapa) {
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(mapa.entrySet());

        //ordeno una lista
        lista.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        //creo un nuevo LinkedHashMap para almacenar los resultados ordenados
        LinkedHashMap<String, Integer> hashMapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : lista) {
            hashMapOrdenado.put(entry.getKey(), entry.getValue());
        }

        return hashMapOrdenado;
    }

    /**
     * Metodo que devuelve el HashMap de los mejores jugadores, con Los nicknames y cantidad de partidas ganadas.
     * Leo el archivo que contiene el HashMap serializado y luego lo ordeno.
     * @param
     * @return HashMap<String, Integer>
     **/
    public HashMap<String, Integer> obtenerLosMejoresJugadores(){
        HashMap<String, Integer> mj= leer();
        mj= ordenar(mj);
        return mj;
    }
}
