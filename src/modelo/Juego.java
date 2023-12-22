package modelo;

import Enumerados.Forma;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import controlador.Controlador;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Juego extends ObservableRemoto implements IJuego {
	private ArrayList<Jugador> jugadores;
	private ArrayList<Ronda> rondas;
	private Ronda rondaActual;
	private AdministradorMejoresJugadores mejoresJugadores;
	private int numeroRondaActual;
	private Mazo mazo;
	private int turno;
	private String lider;
	private boolean juegoInicado;
	private boolean rondaInicada;
	private boolean juegoTerminado;
	private String ultimoJugadorEliminado;

	public Juego(){
		rondas= new ArrayList<>();
		cargarRondas();
		this.rondaActual= rondas.get(0);
		this.numeroRondaActual= 0;
		jugadores= new ArrayList<>();
		rondaInicada= false;
		juegoInicado= false;
		juegoTerminado= false;
		mejoresJugadores= new AdministradorMejoresJugadores();
	}

	/**
	 * Crea un nuevo mazo, lo mezcla y otorga 12 cartas a cada jugador.
	 * @return void
	 **/
	@Override
	public void repartir() throws RemoteException{
		mazo= new Mazo();
		mazo.mezclar();
		for (int i=0; i<jugadores.size(); i++){
			jugadores.get(i).setCartas(mazo.darCartas());
		}
	}

	/**
	 * Agrega un jugador al juego, siempre y cuando no este lleno.
	 * Si es el primer jugador en ser agregado, es asignado como lider, sino, simplemente se agrega.
	 * Notifica a los observadores(controladores) que se agrego un nuevo jugador, y que se asigno el lider.
	 * @param nickname: nickname del jugador a agregar
	 * @return void
	 **/
	@Override
	public void agregarJugador(String nickname) throws RemoteException{
		if (this.jugadores.size()<4){
			Jugador j= new Jugador(nickname);
			if (this.jugadores.size()==0){
				lider= nickname;
				this.jugadores.add(j);
				notificarObservadores(Evento.NUEVO_JUGADOR_AGREGADO);
				notificarObservadores(Evento.ANUNCIAR_LIDER);
			}
			else{
				this.jugadores.add(j);
				notificarObservadores(Evento.NUEVO_JUGADOR_AGREGADO);
			}
		}
	}

	/**
	 * Este metodo inicia el juego/ronda.
	 * Si la ronda actual es 0, es decir, todavia no comenzo, se inicia el juego y se notifica a los observadores.
	 * Si el juego ya esta empezado, inicia una nueva ronda.
	 * @param
	 * @return void
	 **/
	@Override
	public void iniciarJuego() throws RemoteException {
		if (numeroRondaActual==0){
			juegoInicado= true;
			notificarObservadores(Evento.INICIAR_JUEGO);
		}
		nuevaRonda();
		rondaInicada= true;
	}

	/**
	 * Asigna un turno, genera un numero aleatorio entre la cantidad de jugadores.
	 * @return void
	 **/
	@Override
	public void asignarTurno() throws RemoteException{
		Random random= new Random();
		int indiceTurnoAleatorio;
		indiceTurnoAleatorio= random.nextInt(jugadores.size());
		this.turno= indiceTurnoAleatorio;
		notificarObservadores(Evento.NUEVO_TURNO);
	}

	/**
	 * Cambia el turno al siguiente jugador.
	 * Si no se elimino un jugador, es decir, ultimoJugadorEliminado==null, cambia el turno al siguiente.
	 * Si se elimino un jugador, este fue borrado del array por lo que las posiciones del ArrayList fueron
	 * reasignadas, por lo tanto, no debe cambiarse el turno al siguiente. Solo si el jugador eliminado era el ultimo,
	 * el turno se pasa al primero.
	 * @return void
	 **/
	@Override
	public void cambiarTurno() throws RemoteException{
		if (ultimoJugadorEliminado==null){
			if (this.turno==jugadores.size()-1){
				this.turno= 0;
			}
			else{
				this.turno+=1;
			}
		}
		else{
			if (this.turno==jugadores.size()){
				this.turno=0;
			}
		}
		notificarObservadores(Evento.NUEVO_TURNO);
	}

	/**
	 * Retorna el jugador actual, es decir, el jugador que posee el turno actual.
	 * @return void
	 **/
	@Override
	public Jugador jugadorActual() throws RemoteException{
		return jugadores.get(turno);
	}

	/**
	 * Retorna la cantidad de cartas del jugador actual.
	 * @return int: cantidad de cartas
	 **/
	@Override
	public int cantidadCartasJugadorActual() throws RemoteException{
		Jugador j= jugadorActual();
		return j.getCantidadCartas();
	}

	/**
	 * Inicializa y carga todas las rondas para comenzar con el juego.
	 * @return void
	 **/
	private void cargarRondas() {
		//Inicializo todas las rondas y las cargo en el juego
		Ronda ronda;
		for (int i = 0; i < 10; i++) {
			switch (i) {
				case 0:
					ronda = new Ronda(1);
					ronda.añadirForma(Forma.TRIO, 2);
					rondas.add(ronda);
					break;
				case 1:
					ronda = new Ronda(2);
					ronda.añadirForma(Forma.ESCALA, 1);
					ronda.añadirForma(Forma.TRIO, 1);
					rondas.add(ronda);
					break;
				case 2:
					ronda = new Ronda(3);
					ronda.añadirForma(Forma.ESCALA, 2);
					rondas.add(ronda);
					break;
				case 3:
					ronda = new Ronda(4);
					ronda.añadirForma(Forma.TRIO, 3);
					rondas.add(ronda);
					break;
				case 4:
					ronda = new Ronda(5);
					ronda.añadirForma(Forma.TRIO, 2);
					ronda.añadirForma(Forma.ESCALA, 1);
					rondas.add(ronda);
					break;
				case 5:
					ronda = new Ronda(6);
					ronda.añadirForma(Forma.ESCALA, 2);
					ronda.añadirForma(Forma.TRIO, 1);
					rondas.add(ronda);
					break;
				case 6:
					ronda = new Ronda(7);
					ronda.añadirForma(Forma.ESCALA, 3);
					rondas.add(ronda);
					break;
				case 7:
					ronda = new Ronda(8);
					ronda.añadirForma(Forma.TRIO, 4);
					rondas.add(ronda);
					break;
				case 8:
					ronda = new Ronda(9);
					ronda.añadirForma(Forma.ESCALERASUCIA, 1);
					rondas.add(ronda);
					break;
				case 9:
					ronda = new Ronda(10);
					ronda.añadirForma(Forma.ESCALERAREAL, 1);
					rondas.add(ronda);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Indica si finalizo la ronda, es decir, si el jugador actual no posee cartas.
	 * Si finalizo la ronda, carga los puntos y notifica a los observadores.
	 * @param actual: jugador actual
	 * @return void
	 **/
	@Override
	public boolean finRonda(Jugador actual) throws RemoteException{
		if (actual.sinCartas()){
			cargarPuntos();
			rondaInicada= false;
			notificarObservadores(Evento.TERMINO_RONDA);
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Recorre los jugadores, calcula los puntos de cada uno y los carga.
	 * @return void
	 **/
	private void cargarPuntos() {
		for (int i = 0; i < jugadores.size(); i++) {
			int puntos = jugadores.get(i).calcularPuntosRonda();
			jugadores.get(i).incrementarPuntos(puntos);
		}
	}

	/**
	 * Inicializa una nueva ronda siempre y cuando el juego no haya terminado,
	 * limpia las cartas bajadas de cada jugador y vuelve a repartir.
	 * Asigna el primer turno.
	 * @return void
	 **/
	@Override
	public void nuevaRonda() throws RemoteException{
		this.numeroRondaActual+=1;
		if (numeroRondaActual<=10){
			this.rondaActual= rondas.get(this.numeroRondaActual-1);
			for (int i=0; i<jugadores.size(); i++){
				jugadores.get(i).limpiarCartasBajadas();
				jugadores.get(i).setSeBajo(false);
			}
			repartir();
			notificarObservadores(Evento.NUEVA_RONDA);
			asignarTurno();
		}
		else{
			finJuego();
		}
	}

	/**
	 * Controla si el juego termino, si es asi, determina el ganador del juego.
	 * Actualiza los mejores jugadores, enviandole el ganador. Notifica a los observadores que el juego termino.
	 * @return boolean: indica si el juego finalizo
	 **/
	@Override
	public boolean finJuego() throws RemoteException{
		if (this.numeroRondaActual+1 > 10){
			if (juegoTerminado==false){
				mejoresJugadores.actualizar(ganadorJuego().getNickname());
				notificarObservadores(Evento.TERMINO_JUEGO);
				juegoTerminado= true;
				return true;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}

	/**
	 * Verifica si algun jugador se quedo sin cartas y lo retorna.
	 * @return Jugador: ganador de la ronda
	 **/
	@Override
	public Jugador ganadorRonda() throws RemoteException{
		Jugador j= null;
		for (int i=0; i<jugadores.size(); i++){
			if (jugadores.get(i).sinCartas()){
				j= jugadores.get(i);
			}
		}
		return j;
	}

	/**
	 * Verifica e indica quien fue el ganador del juego, controlando quien es el que posee
	 * la menor cantidad de puntos.
	 * @return Jugador: ganador del juego
	 **/
	@Override
	public Jugador ganadorJuego() throws RemoteException{
		int minimoPuntos=0;
		Jugador ganador= null;
		int flag= 0;
		for (int i=0; i<jugadores.size(); i++){
			if (flag==0){
				ganador= jugadores.get(i);
				minimoPuntos= jugadores.get(i).getPuntos();
				flag= 1;
			}
			else{
				if (jugadores.get(i).getPuntos() < minimoPuntos){
					ganador= jugadores.get(i);
					minimoPuntos= jugadores.get(i).getPuntos();
				}
				//else if (jugadores.get(i).getPuntos() == minimoPuntos) {
				//	return null;
				//}
			}
		}
		return ganador;
	}

	/**
	 * Verifica si el jugador actual se bajo, es decir, formo las cartas indicadas
	 * en la ronda.
	 * @return boolean: indica si el jugador se bajo
	 **/
	@Override
	public boolean jugadorActualSeBajo() throws RemoteException{
		return jugadorActual().getSeBajo();
	}

	/**
	 * Hace que el jugador actual recoja una carta del mazo.
	 * @return void
	 **/
	@Override
	public void jugadorActualTomarCartaMazo() throws RemoteException{
		jugadorActual().recogerCarta(this.mazo, true);
		notificarObservadores(Evento.JUGADOR_TOMO_CARTA_MAZO);
	}

	/**
	 * Hace que el jugador actual recoja una carta del pozo.
	 * @return void
	 **/
	@Override
	public void jugadorActualTomarCartaPozo() throws RemoteException{
		jugadorActual().recogerCarta(this.mazo, false);
		notificarObservadores(Evento.JUGADOR_TOMO_CARTA_POZO);
	}

	/**
	 * Hace que el jugador actual deje una carta en el pozo.
	 * @param indice: indica la posicion de carta a dejar
	 * @return void
	 **/
	@Override
	public void jugadorActualDejarCarta(int indice) throws RemoteException{
		if (indice>=1 && indice<=cantidadCartasJugadorActual()){
			jugadorActual().dejarCarta(this.mazo, indice);
			notificarObservadores(Evento.JUGADOR_DEJO_CARTA);
		}
	}

	/**
	 * Verifica si el jugador actual puede bajarse, es decir, formar las formas que
	 * indica la ronda.
	 * @return boolean: determina si el jugador pudo bajarse
	 **/
	@Override
	public boolean jugadorActualPuedeBajarse() throws RemoteException{
		boolean puedeBajarse= jugadorActual().bajarse(this.rondaActual);
		if (puedeBajarse==true){
			notificarObservadores(Evento.JUGADOR_SE_BAJO);
		}
		else{
			notificarObservadores(Evento.JUGADOR_NO_PUDO_BAJARSE);
		}
		return puedeBajarse;
	}

	/**
	 * Verifica si el jugador actual pudo bajar la carta indicada.
	 * @param indice: indica la posicion de la carta a dejar
	 * @return boolean: indica si la carta pudo ser bajada
	 **/
	@Override
	public boolean jugadorActualPudoBajarCarta(int indice) throws RemoteException{
		if (indice>=1 && indice<=cantidadCartasJugadorActual()){
			boolean pudoBajarCarta= jugadorActual().bajarCarta(indice, this.jugadores);
			if (pudoBajarCarta==true){
				notificarObservadores(Evento.JUGADOR_BAJO_CARTA);
			}
			else{
				notificarObservadores(Evento.JUGADOR_NO_PUDO_BAJAR_CARTA);
			}
			return pudoBajarCarta;
		}
		else{
			notificarObservadores(Evento.JUGADOR_NO_PUDO_BAJAR_CARTA);
			return false;
		}
	}

	/**
	 * Verifica si el jugador actual no posee cartas.
	 * @return boolean: indica si el jugador actual no posee cartas
	 **/
	@Override
	public boolean jugadorActualSinCartas() throws RemoteException{
		return jugadorActual().sinCartas();
	}

	/**
	 * Obtiene el tope del pozo.
	 * @return String: tostring de la carta que se encuentra en el tope del pozo
	 **/
	@Override
	public String obtenerTopePozo() throws RemoteException{
		return this.mazo.obtenerTopePozo();
	}

	/**
	 * Obtiene las formas que hay que formar en la ronda actual.
	 * @return HashMap<Forma, Integer>
	 **/
	@Override
	public HashMap<Forma, Integer> obtenerFormasRondaActual() throws RemoteException{
		return rondaActual.getFormas();
	}

	/**
	 * Retorna las formas armadas de un jugador, señalizado mediante un indice
	 * @param indice: indice del jugador
	 * @return ArrayList<Bajada>
	 **/
	@Override
	public ArrayList<Bajada> formasArmadas(int indice) throws RemoteException{
		return jugadores.get(indice).getFormasArmadas();
	}

	/**
	 * Obtiene las formas armadas de un jugador, mediante el jugador.
	 * @param jugador: jugador el cual se desean obtener las formas bajadas
	 * @return
	 **/
	@Override
	public ArrayList<Bajada> formasArmadas(Jugador jugador) throws RemoteException{
		return jugador.getFormasArmadas();
	}

	/**
	 * Obtiene el nickname de un jugador.
	 * @param jugador: jugador el cual se desea obtener el nickname
	 * @return String: nicknma del jugador
	 **/
	@Override
	public String obtenerNickname(Jugador jugador) throws RemoteException{
		return jugador.getNickname();
	}

	/**
	 * Obtiene el nombre de la bajada.
	 * @param bajada: bajada de cartas
	 * @return Forma: Forma que arma la bajada
	 **/
	@Override
	public Forma obtenerNombreForma(Bajada bajada) throws RemoteException{
		return bajada.getNombreForma();
	}

	/**
	 * Obtiene las cartas que forman una bajada
	 * @param bajada: bajada de cartas
	 * @return ArrayList<Carta>
	 **/
	@Override
	public ArrayList<Carta> cartasQueFormanLaBajada(Bajada bajada) throws RemoteException{
		return bajada.getCartasQueLaForman();
	}

	/**
	 * Obtiene los puntos de un jugador.
	 * @param jugador: Jugador el cual se desean obtener sus puntos
	 * @return int: puntos del jugador
	 **/
	@Override
	public int obtenerPuntosJugador(Jugador jugador) throws RemoteException{
		return jugador.getPuntos();
	}

	/**
	 * Obtiene las cartas de un jugador
	 * @param nickname: nickname del jugador
	 * @return ArrayList<Carta>: cartas del jugador
	 **/
	@Override
	public ArrayList<Carta> obtenerCartasJugador(String nickname) throws RemoteException{
		for (int i=0; i<jugadores.size(); i++){
			if (jugadores.get(i).getNickname().equals(nickname)){
				return jugadores.get(i).getCartas();
			}
		}
		return null;
	}

	/**
	 * Obtiene el nickname del jugador actual
	 * @return String
	 **/
	@Override
	public String nicknameJugadorActual() throws RemoteException{
		return jugadorActual().getNickname();
	}

	/**
	 * Obtiene la cantidad de formas armadas en la ronda
	 * @return int
	 **/
	@Override
	public int cantidadTotalFormasArmadas() throws RemoteException{
		int total=0;
		for (int i=0; i<jugadores.size(); i++){
			total+= jugadores.get(i).getFormasArmadas().size();
		}
		return total;
	}

	/**
	 * Metodo para continuar con la ronda, toma el jugador actual y verifica si la ronda termino.
	 * Si no termino, cambia el turno.
	 * @return
	 **/
	@Override
	public void continuarRonda() throws RemoteException{
		Jugador actual = jugadorActual();
		boolean terminoRonda = finRonda(actual);
		if (terminoRonda == true) {
			//nuevaRonda();
			return;
		}
		cambiarTurno();
	}

	/**
	 * Obtiene la cantidad de jugadores que estan participando en el juego
	 * @return int
	 **/
	@Override
	public int cantidadJugadores() throws RemoteException{
		return this.jugadores.size();
	}

	/**
	 * Obtiene el ultimo jugador que fue agregado
	 * @return Jugador
	 **/
	@Override
	public Jugador obtenerUltimoJugadorAgregado() throws RemoteException {
		return jugadores.get(jugadores.size()-1);
	}

	/**
	 * Verifica si un nickname ya se encuentra en el juego.
	 * @param nickname : nickname a buscar
	 * @return boolean: indica si el nickname se encuentra o no
	 **/
	public boolean nicknameExistente(String nickname) throws RemoteException{
		for (int i=0; i<jugadores.size(); i++){
			if (jugadores.get(i).getNickname().equals(nickname)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Elimina un jugador de los que estan participando en el juego.
	 * Primero obtiene el jugador actual. Elimina al jugador recibido por parametro del array de jugadores.
	 * Notifica a los jugadores de que un jugador fue eliminado.
	 * Si quedo 1 solo jugador, termina el juego ya que no es  posible jugar con uno solo, notifica a los observadores.
	 * Si la persona que se fue era el jugador actual, cambia el turno al siguiente.
	 * Si el jugador eliminado era el lider, se asigna un lider de manera aleatoria.
	 * Establece el ultimo jugador eliminado como nulo para el metodo de cambiar turno.
	 * @param nickname : nickname del jugador a eliminar
	 **/
	@Override
	public void eliminarJugador(String nickname) throws RemoteException{
		String nicknameJActual= jugadorActual().getNickname();
		for (int i=0; i<jugadores.size(); i++){
			if (jugadores.get(i).getNickname().equals(nickname)){
				ultimoJugadorEliminado= nickname;
				jugadores.remove(i);
				notificarObservadores(Evento.JUGADOR_SE_FUE);
			}
		}
		if (cantidadJugadores()<2){
			if (juegoTerminado==false){
				mejoresJugadores.actualizar(ganadorJuego().getNickname());
				juegoTerminado= true;
				notificarObservadores(Evento.TERMINO_JUEGO);
			}
		}
		else{
			if (nicknameJActual.equals(nickname)){
				cambiarTurno();
			}
			else{
				mantenerTurno(nicknameJActual);
			}
			if (lider.equals(nickname)){
				Random random= new Random();
				int indiceLiderAleatorio= random.nextInt(jugadores.size());
				lider= jugadores.get(indiceLiderAleatorio).getNickname();
				notificarObservadores(Evento.ANUNCIAR_LIDER);
			}
		}
		ultimoJugadorEliminado= null;
	}

	/**
	 * Este metodo se activa cuando un jugador abandona y no poseia el turno actual,
	 * se encarga de reasignar el turno al jugador que lo tenia.
	 * EJEMPLO:  J1  J2  J3, TURNO=1, es decir, lo posee J2
	 * J1 abandona el juego, queda asi: J2 J3, TURNO=1, el abandono de J1 provoca que el turno no quede
	 * acorde a los jugadores, entonces recorro los jugadores y asigno el numero de turno correspondiente.
	 * RESULTADO: J2 J3, TURNO=0.
	 * @param nickname : nickname del jugador a que tenia el turno antes de que un jugador abandone
	 **/
	private void mantenerTurno(String nickname){
		for (int i=0; i<jugadores.size(); i++){
			if (jugadores.get(i).getNickname().equals(nickname)){
				turno= i;
			}
		}
	}

	/*Getters*/
	@Override
	public ArrayList<Jugador> getJugadores() throws RemoteException{
		return jugadores;
	}

	@Override
	public HashMap<String, Integer> getMejoresJugadores() throws RemoteException{
		return mejoresJugadores.obtenerLosMejoresJugadores();
	}

	@Override
	public int getNumeroRondaActual() throws RemoteException{
		return numeroRondaActual;
	}

	@Override
	public String getLider() throws RemoteException{
		return this.lider;
	}

	@Override
	public boolean getJuegoIniciado() throws RemoteException{
		return juegoInicado;
	}

	@Override
	public boolean getRondaIniciada() throws RemoteException{
		return this.rondaInicada;
	}

	@Override
	public String getUltimoJugadorEliminado() throws RemoteException{
		return this.ultimoJugadorEliminado;
	}

	@Override
	public boolean getJuegoTerminado() throws RemoteException{
		return juegoTerminado;
	}

	//Setters
	@Override
	public void setJugadorActualSeBajoRecien(boolean a) throws RemoteException{
		jugadorActual().setSeBajoRecien(a);
	}

	/*private void guardarPartida(){
		try {
			FileOutputStream fos = new FileOutputStream("PartidaSerializada.bin");
			var oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void recuperarPartida(){
		try {
			FileInputStream fos = new FileInputStream("PartidaSerializada.bin");
			var oos = new ObjectInputStream(fos);
			var obj = (Juego) oos.readObject();
			fos.close();
			return;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}*/

}
