package modelo;
import Enumerados.*;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import controlador.Controlador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IJuego extends IObservableRemoto {
    void repartir() throws RemoteException;

    void agregarJugador(String nickname) throws RemoteException;

    void iniciarJuego() throws RemoteException;

    void asignarTurno() throws RemoteException;

    void cambiarTurno() throws RemoteException;

    Jugador jugadorActual() throws RemoteException;

    int cantidadCartasJugadorActual() throws RemoteException;


    boolean finRonda(Jugador actual) throws RemoteException;

    void nuevaRonda() throws RemoteException;

    boolean finJuego() throws RemoteException;

    Jugador ganadorRonda() throws RemoteException;

    Jugador ganadorJuego() throws RemoteException;

    boolean jugadorActualSeBajo() throws RemoteException;

    void jugadorActualTomarCartaMazo() throws RemoteException;

    void jugadorActualTomarCartaPozo() throws RemoteException;

    void jugadorActualDejarCarta(int indice) throws RemoteException;

    boolean jugadorActualPuedeBajarse() throws RemoteException;

    boolean jugadorActualPudoBajarCarta(int indice) throws RemoteException;

    boolean jugadorActualSinCartas() throws RemoteException;

    String obtenerTopePozo() throws RemoteException;

    HashMap<Forma, Integer> obtenerFormasRondaActual() throws RemoteException;

    ArrayList<Bajada> formasArmadas(int indice) throws RemoteException;

    ArrayList<Bajada> formasArmadas(Jugador jugador) throws RemoteException;

    String obtenerNickname(Jugador jugador) throws RemoteException;

    Forma obtenerNombreForma(Bajada bajada) throws RemoteException;

    ArrayList<Carta> cartasQueFormanLaBajada(Bajada bajada) throws RemoteException;

    int obtenerPuntosJugador(Jugador jugador) throws RemoteException;

    ArrayList<Carta> obtenerCartasJugador(String nickname) throws RemoteException;

    String nicknameJugadorActual() throws RemoteException;

    int cantidadTotalFormasArmadas() throws RemoteException;

    void continuarRonda() throws RemoteException;

    int cantidadJugadores() throws RemoteException;

    Jugador obtenerUltimoJugadorAgregado() throws RemoteException;

    boolean nicknameExistente(String nickname) throws RemoteException;

    void eliminarJugador(String nickname) throws RemoteException;

    /*Getters*/
    int getNumeroRondaActual() throws RemoteException;
    ArrayList<Jugador> getJugadores() throws RemoteException;

    String getLider() throws RemoteException;

    boolean getRondaIniciada() throws RemoteException;

    boolean getJuegoIniciado() throws RemoteException;

    boolean getJuegoTerminado() throws RemoteException;

    HashMap<String, Integer> getMejoresJugadores() throws RemoteException;

    String getUltimoJugadorEliminado() throws RemoteException;

    /*Setters*/
    void setJugadorActualSeBajoRecien(boolean a) throws RemoteException;
}
