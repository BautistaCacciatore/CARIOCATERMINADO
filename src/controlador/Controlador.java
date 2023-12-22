package controlador;

import Enumerados.Forma;
import modelo.*;
import Vistas.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controlador implements IControladorRemoto {
	private IJuego modelo;
	private IVista vista;

	public Controlador(IVista vista){
		this.vista= vista;
	}

	public void agregarJugadores(String nickname){
		try {
			modelo.agregarJugador(nickname);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}


	public Jugador jugadorActual(){
		try{
			return modelo.jugadorActual();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean jugadorActualSeBajo(){
		try {
			return modelo.jugadorActualSeBajo();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public void jugadorActualTomarCartaMazo(){
		try {
			modelo.jugadorActualTomarCartaMazo();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public void jugadorActualTomarCartaPozo(){
		try {
			modelo.jugadorActualTomarCartaPozo();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public void jugadorActualDejarCarta(int indice){
		try {
			modelo.jugadorActualDejarCarta(indice);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public boolean jugadorActualPuedeBajarse(){
		try {
			return modelo.jugadorActualPuedeBajarse();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean jugadorActualPudoBajarCarta(int indice){
		try {
			return modelo.jugadorActualPudoBajarCarta(indice);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean jugadorActualSinCartas(){
		try {
			return modelo.jugadorActualSinCartas();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public String getLider(){
		try {
			return modelo.getLider();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean getRondaIniciada(){
		try {
			return modelo.getRondaIniciada();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public void iniciarJuego(){
		try {
			modelo.iniciarJuego();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public int cantidadJugadores(){
		try {
			return modelo.cantidadJugadores();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return -1;
	}

	public int cantidadCartasJugadorActual(){
		try {
			return modelo.cantidadCartasJugadorActual();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return -1;
	}

	public Jugador ganadorRonda(){
		try {
			return modelo.ganadorRonda();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public Jugador ganadorJuego(){
		try {
			return modelo.ganadorJuego();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Jugador> obtenerJugadores(){
		try {
			return modelo.getJugadores();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public String obtenerTopePozo(){
		try {
			return modelo.obtenerTopePozo();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public int obtenerNumeroRondaActual(){
		try {
			return modelo.getNumeroRondaActual();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return -1;
	}

	public HashMap<Forma, Integer> obtenerFormasRondaActual(){
		try {
			return modelo.obtenerFormasRondaActual();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Bajada> formasArmadasJugador(int indice){
		try {
			return modelo.formasArmadas(indice);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Bajada> formasArmadasJugador(Jugador jugador){
		try {
			return modelo.formasArmadas(jugador);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public String obtenerNickname(Jugador jugador){
		try {
			return modelo.obtenerNickname(jugador);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public int obtenerPuntosJugador(Jugador jugador){
		try {
			return modelo.obtenerPuntosJugador(jugador);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return -1;
	}

	public Forma obtenerNombreForma(Bajada bajada){
		try {
			return modelo.obtenerNombreForma(bajada);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Carta> cartasQueFormanLaBajada(Bajada bajada){
		try {
			return modelo.cartasQueFormanLaBajada(bajada);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean jugadorActualSeBajoRecien(){
		try {
			return modelo.jugadorActual().getSeBajoRecien();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public void setJugadorActualSeBajoRecien(boolean a){
		try {
			modelo.setJugadorActualSeBajoRecien(a);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public ArrayList<Carta> obtenerCartasJugador(String nickname){
		try {
			return modelo.obtenerCartasJugador(nickname);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public String nicknameJugadorActual(){
		try {
			return modelo.nicknameJugadorActual();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public int cantidadTotalFormasArmadas(){
		try {
			return modelo.cantidadTotalFormasArmadas();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return -1;
	}

	public void continuarRonda(){
		try {
			modelo.continuarRonda();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public boolean getJuegoIniciado(){
		try {
			return modelo.getJuegoIniciado();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean nicknameExistente(String nickname){
		try {
			return modelo.nicknameExistente(nickname);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public HashMap<String, Integer> obtenerMejoresJugadores(){
		try{
			return modelo.getMejoresJugadores();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}

	public void eliminarJugador(String nickname){
		try {
			modelo.eliminarJugador(nickname);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public boolean getJuegoTerminado(){
		try {
			return modelo.getJuegoTerminado();
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}

	public void eliminarObservador(){
		try {
			modelo.removerObservador(this);
		}
		catch (RemoteException e){
			e.printStackTrace();
		}
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.modelo= (IJuego) modeloRemoto;
	}

	@Override
	public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
		if (cambio instanceof Evento) {
			switch ((Evento) cambio) {
				case NUEVA_RONDA:
					this.vista.mostrarRondaActual();
					break;
				case TERMINO_RONDA:
					this.vista.mostrarTerminoRonda();
					break;
				case TERMINO_JUEGO:
					this.vista.mostrarFinJuego();
					break;
				case INICIAR_JUEGO:
					this.vista.mostrarInicioJuego();
					break;
				case NUEVO_JUGADOR_AGREGADO:
					this.vista.mostrarNuevoJugadorAgregado(this.modelo.obtenerUltimoJugadorAgregado());
					break;
				case NUEVO_TURNO:
					this.vista.mostrarTurno(this.modelo.jugadorActual());
					break;
				case JUGADOR_TOMO_CARTA_POZO:
					this.vista.mostrarJugadorTomoCartaPozo(this.modelo.jugadorActual());
					break;
				case JUGADOR_TOMO_CARTA_MAZO:
					this.vista.mostrarJugadorTomoCartaMazo(this.modelo.jugadorActual());
					break;
				case JUGADOR_SE_BAJO:
					this.vista.mostrarJugadorSeBajo(this.modelo.jugadorActual());
					break;
				case JUGADOR_NO_PUDO_BAJARSE:
					this.vista.mostrarJugadorNoPudoBajarse(this.modelo.jugadorActual());
					break;
				case JUGADOR_DEJO_CARTA:
					this.vista.mostrarJugadorDejoCarta(this.modelo.jugadorActual());
					break;
				case JUGADOR_BAJO_CARTA:
					this.vista.mostrarJugadorBajoCarta(this.modelo.jugadorActual());
					break;
				case JUGADOR_NO_PUDO_BAJAR_CARTA:
					this.vista.mostrarJugadorNoPudoBajarCarta(this.modelo.jugadorActual());
					break;
				case ANUNCIAR_LIDER:
					this.vista.anunciarLider(this.modelo.getLider());
					break;
				case JUGADOR_SE_FUE:
					this.vista.anunciarJugadorSeFue(this.modelo.getUltimoJugadorEliminado());
					break;
				default:
					break;
			}
		}
	}
}
