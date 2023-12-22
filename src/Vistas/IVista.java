package Vistas;

import controlador.Controlador;
import modelo.Bajada;
import modelo.Jugador;

public interface IVista {
	void iniciar();
	void setControlador(Controlador controlador);
	void mostrarMensaje(String mensaje);
	void mostrarRondaActual();
	void mostrarFormasArmadasDeCadaJugador();
	void mostrarTurno(Jugador jugador);
	void mostrarCartasBajadas(Jugador jugador);

	void mostrarForma(Bajada bajada);
	void mostrarCartasJugador();
	void mostrarPuntosJugadores();

	void mostrarTopePozo();
	void mostrarMazo();

	void mostrarJugadorTomoCartaPozo(Jugador jugador);


	void mostrarJugadorTomoCartaMazo(Jugador jugador);

	void mostrarJugadorSeBajo(Jugador jugador);

	void mostrarJugadorNoPudoBajarse(Jugador jugador);

	void mostrarJugadorDejoCarta(Jugador jugador);

	void mostrarJugadorBajoCarta(Jugador jugador);

	void mostrarJugadorNoPudoBajarCarta(Jugador jugador);

	void mostrarTerminoRonda();

	void mostrarFinJuego();

	void mostrarInicioJuego();

	void mostrarNuevoJugadorAgregado(Jugador jugador);

	void anunciarLider(String lider);

	void anunciarJugadorSeFue(String nickameEliminado);
}
