package Vistas.interfazGrafica;

import Enumerados.Forma;
import controlador.Controlador;
import modelo.Bajada;
import modelo.Carta;
import modelo.Jugador;
import Vistas.IVista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VistaJugador implements IVista {
    private final JFrame frame;
    private JPanel contentPane;

    private JTabbedPane tabbedPane1;
    private JPanel ingresar;
    private JPanel jugar;
    private JLabel label;
    private JTextField ingresoNickname;
    private JButton enterBtn;
    private JLabel labelBienvenida;

    private JButton pozoBtn;
    private JButton bajarseBtn;
    private JButton dejarBtn;
    private JTextArea cartas;
    private JTextArea notificaciones;
    private JButton bajarBtn;
    private JButton mazoBtn;
    private JSpinner spinner1;
    private JTextArea datosRonda;
    private JButton iniciarRondaBtn;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JPanel mj;
    private JTextArea textAreaMJ;
    private JLabel label5;
    private JLabel label6;
    private JLabel label4;
    private JScrollPane JScrollPane1;
    private JScrollPane JScrollPane2;
    private JButton salirBtn;

    private String nickname;
    private Controlador controlador;

    //Variables utiles para controlar que acciones hizo y puede hacer el jugador en SU TURNO
    private int tomoCartaMazo;
    private int tomoCartaPozo;
    private int dejoCarta;
    private int seBajo;
    private int  bajoCarta;
    private boolean participa;

    public VistaJugador() {
        participa= true;
        //paneles
        contentPane.setBackground(new Color(255, 255, 224));
        ingresar.setBackground(new Color(255, 255, 224));
        jugar.setBackground(new Color(255, 255, 224));
        mj.setBackground(new Color(255, 255, 224));
        Border bordeCompuesto = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)),
                new LineBorder(new Color(70, 130, 180), 5, true)
        );
        ingresar.setBorder(bordeCompuesto);
        jugar.setBorder(bordeCompuesto);
        mj.setBorder(bordeCompuesto);

        //frame
        frame = new JFrame("JUGADOR");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        //textAreas
        cartas.setBackground(new Color(255, 225, 224));
        cartas.setForeground(new Color(100, 0,0));
        datosRonda.setBackground(new Color(255, 225, 224));
        datosRonda.setForeground(new Color(100, 0,0));
        notificaciones.setBackground(new Color(255, 228, 225));
        notificaciones.setForeground(new Color(100, 0,0));
        textAreaMJ.setFont(new Font("Arial", Font.PLAIN, 16));
        textAreaMJ.setBackground(new Color(255, 255, 224));
        textAreaMJ.setForeground(new Color(70, 130, 180));

        //LABELS
        Color colorJLabel = new Color(100, 0, 0);
        // Borde compuesto con colores ajustados
        Border bordeCompuesto1 = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)),
                new LineBorder(colorJLabel, 5, true)
        );
        label6.setBorder(bordeCompuesto1);

        label5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI("https://www.comolohago.cl/como-jugar-carioca/"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //botones
        enterBtn.setBackground(new Color(70, 130, 180));
        enterBtn.setForeground(Color.WHITE);
        enterBtn.setFont(new Font("Arial", Font.BOLD, 16));

        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarIngresoNickname();
            }
        });


        iniciarRondaBtn.setBackground(new Color(70, 130, 180));
        iniciarRondaBtn.setForeground(Color.WHITE);
        iniciarRondaBtn.setFont(new Font("Arial", Font.BOLD, 16));

        iniciarRondaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname!= null){
                    if (controlador.getJuegoTerminado()==false){
                        if (participa==true){
                            if (controlador.getRondaIniciada()==false){
                                String lider= controlador.getLider();
                                if (nickname.equals(lider)){
                                    if (controlador.cantidadJugadores()>=2 && controlador.cantidadJugadores()<=4){
                                        controlador.iniciarJuego();
                                    }
                                    else{
                                        mostrarMensaje2("-AVISO: LA CANTIDAD DE JUGADORES NO ES VALIDA PARA INICIAR EL JUEGO.");
                                    }
                                }
                                else{
                                    mostrarMensaje2("-AVISO: EL LIDER DEBE INICIAR LA RONDA.");
                                }
                            }
                            else{
                                mostrarMensaje2("-AVISO: LA RONDA YA ESTA INICIADA.");
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: EL JUEGO TERMINO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                }
            }
        });


        //
        mazoBtn.setBackground(new Color(70, 130, 180));
        mazoBtn.setForeground(Color.WHITE);
        mazoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        mazoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                } else if (controlador.getJuegoTerminado()==false) {
                    if (participa==true){
                        if (controlador.getRondaIniciada()){
                            if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                                if (tomoCartaMazo==0 && tomoCartaPozo==0 && seBajo==0 && dejoCarta==0 && bajoCarta==0){
                                    controlador.jugadorActualTomarCartaMazo();
                                    tomoCartaMazo=1;
                                }
                                else{
                                    mostrarMensaje2("-AVISO: NO PUEDES TOMAR UNA CARTA");
                                }
                            }
                            else{
                                mostrarMensaje2("-AVISO: NO ES TU TURNO.");
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: DEBES ESPERAR A QUE LA RONDA INICIE.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: EL JUEGO TERMINO.");
                }
            }
        });

        pozoBtn.setBackground(new Color(70, 130, 180));
        pozoBtn.setForeground(Color.WHITE);
        pozoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        pozoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                } else if (controlador.getJuegoTerminado()==false) {
                    if (participa==true){
                        if (controlador.getRondaIniciada()){
                            if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                                if (tomoCartaMazo==0 && tomoCartaPozo==0 && seBajo==0 && dejoCarta==0 && bajoCarta==0){
                                    controlador.jugadorActualTomarCartaPozo();
                                    tomoCartaPozo=1;
                                }
                                else{
                                    mostrarMensaje2("-AVISO: NO PUEDES TOMAR UNA CARTA.");
                                }
                            }
                            else{
                                mostrarMensaje2("-AVISO: NO ES TU TURNO.");
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: DEBES ESPERAR A QUE LA RONDA INICE.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: EL JUEGO TERMINO.");
                }
            }
        });

        bajarseBtn.setBackground(new Color(70, 130, 180));
        bajarseBtn.setForeground(Color.WHITE);
        bajarseBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bajarseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                } else if (controlador.getJuegoTerminado()==false) {
                    if (participa==true){
                        if (controlador.getRondaIniciada()){
                            if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                                if (controlador.jugadorActualSeBajo()==false){
                                    if (tomoCartaMazo == 1 || tomoCartaPozo == 1) {
                                        mostrarMensaje2("-AVISO: NO PUEDES BAJARTE, PRIMERO DEBES DEJAR UNA CARTA.");
                                    } else {
                                        boolean puedeBajarse = controlador.jugadorActualPuedeBajarse();
                                        if (puedeBajarse == true) {
                                            if (controlador.obtenerNumeroRondaActual() != 9 && controlador.obtenerNumeroRondaActual() != 10) {
                                                controlador.setJugadorActualSeBajoRecien(true);
                                                seBajo = 1;
                                            } else {
                                                controlador.continuarRonda();
                                            }
                                        }
                                    }
                                }
                                else{
                                    mostrarMensaje2("-AVISO: YA TE HAS BAJADO ANTERIORMENTE.");
                                }
                            }
                            else{
                                mostrarMensaje2("-AVISO: NO ES TU TURNO.");
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: DEBES ESPERAR A QUE LA RONDA INICIE.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: EL JUEGO TERMINO.");
                }
            }
        });

        dejarBtn.setBackground(new Color(70, 130, 180));
        dejarBtn.setForeground(Color.WHITE);
        dejarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        dejarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname == null) {
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                } else if (controlador.getJuegoTerminado()==false) {
                    if (participa==true){
                        if (controlador.getRondaIniciada()){
                            if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                                if (tomoCartaMazo == 1 || tomoCartaPozo == 1 || seBajo == 1) {
                                    Integer indiceCarta = (Integer) spinner1.getValue();
                                    if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                                        controlador.jugadorActualDejarCarta(indiceCarta);
                                        dejoCarta = 1;
                                        if (controlador.jugadorActualSeBajoRecien()) {
                                            controlador.setJugadorActualSeBajoRecien(false);
                                        }
                                        controlador.continuarRonda();
                                    } else {
                                        mostrarMensaje2("-AVISO: DEBE INGRESAR UN NUMERO VALIDO.");
                                    }
                                } else {
                                    mostrarMensaje2("-AVISO: DEBE TOMAR UNA CARTA PRIMERO.");
                                }
                            }
                            else{
                                mostrarMensaje2("-AVISO: NO ES TU TURNO.");
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: DEBES ESPERAR A QUE LA RONDA INICIE.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: EL JUEGO TERMINO.");
                }
            }
        });

        bajarBtn.setBackground(new Color(70, 130, 180));
        bajarBtn.setForeground(Color.WHITE);
        bajarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bajarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                } else if (controlador.getJuegoTerminado()==false) {
                    if (participa==true){
                        if (controlador.getRondaIniciada()){
                            if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                                if (controlador.jugadorActualSeBajo()){
                                    if (tomoCartaPozo==0 && tomoCartaMazo==0 && dejoCarta==0){
                                        if (!controlador.jugadorActualSeBajoRecien()){
                                            Integer indiceCarta = (Integer) spinner1.getValue();
                                            if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                                                boolean pudoBajarCarta = controlador.jugadorActualPudoBajarCarta(indiceCarta);
                                                if (pudoBajarCarta == true) {
                                                    if (controlador.jugadorActualSinCartas()) {
                                                        controlador.continuarRonda();
                                                    } else {
                                                        mostrarCartasJugador();
                                                    }
                                                }
                                            } else {
                                                mostrarMensaje2("-AVISO: DEBES INGRESAR UN NUMERO VALIDO.");
                                            }
                                        }
                                        else{
                                            mostrarMensaje2("-AVISO: TE BAJASTE RECIEN, EN TU PROXIMO TURNO PODRAS BAJAR CARTAS.");
                                        }
                                    }
                                    else{
                                        mostrarMensaje2("-AVISO: TOMASTE UNA CARTA, DEBES DEJAR UNA.");
                                    }
                                } else{
                                    mostrarMensaje2("-AVISO: PRIMERO DEBES BAJARTE.");
                                }
                            }
                            else{
                                mostrarMensaje2("-AVISO: NO ES TU TURNO.");
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: DEBES ESPERAR A QUE LA RONDA INICIE.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: EL JUEGO TERMINO.");
                }
            }
        });

        salirBtn.setBackground(new Color(70, 130, 180));
        salirBtn.setForeground(Color.WHITE);
        salirBtn.setFont(new Font("Arial", Font.BOLD, 16));
        salirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    mostrarMensaje2("-AVISO: PRIMERO DEBES INGRESAR TU NICKNAME.");
                } else if (controlador.getJuegoTerminado()==false) {
                    if (participa==true){
                        if (controlador.getRondaIniciada()){
                            if (tomoCartaMazo== 1 || tomoCartaPozo==1 || dejoCarta==1 || seBajo==1 || bajoCarta==1){
                                mostrarMensaje2("-AVISO: YA REALIZASTE UN MOVIMIENTO, DEBES TERMINAR TU TURNO. PODRAS SALIR LUEGO.");
                            }
                            else{
                                controlador.eliminarJugador(nickname);
                            }
                        }
                        else{
                            mostrarMensaje2("-AVISO: DEBES ESPERAR A QUE LA RONDA INICIE PARA SALIR.");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: NO ESTAS PARTICIPANDO EN EL JUEGO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: EL JUEGO TERMINO");
                }
            }
        });

        //tabbed pane
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int indice= tabbedPane1.getSelectedIndex();
                if (indice==2){
                    mostrarMejoresJugadores();
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlador.eliminarJugador(nickname);
                controlador.eliminarObservador();
            }
        });
    }

    /**
     * Procesa el nickname del jugador, siempre y cuando no lo haya ingresado previamente, el juego no este iniciado
     * y el juego no este lleno.
     * Si el nickname ya lo posee otro jugador, se emite un aviso. Sino, se agrega al juego.
     * @return void
     **/
    public void procesarIngresoNickname(){
        if (this.nickname==null){
            if (controlador.getJuegoIniciado()==false){
                if (controlador.cantidadJugadores()<4){
                    String nickname1= ingresoNickname.getText();
                    if (controlador.nicknameExistente(nickname1)==false){
                        this.nickname= nickname1;
                        controlador.agregarJugadores(nickname);
                        if (!controlador.getRondaIniciada()){
                            mostrarMensaje2("- ESPERANDO A QUE LA RONDA INICIE...");
                        }
                    }
                    else{
                        mostrarMensaje2("-AVISO: YA HAY UN JUGADOR CON ESE NICKNAME, INGRESA OTRO.");
                    }
                }
                else{
                    mostrarMensaje2("-AVISO: NO PUEDES UNIRTE, EL JUEGO YA ESTA LLENO.");
                }
            }
            else{
                mostrarMensaje2("-AVISO: NO PUEDES UNIRTE, EL JUEGO YA INICIO.");
            }
        }
        else{
            mostrarMensaje2("-AVISO: YA INGRESASTE TU NICKNAME ANTES.");
        }
    }

    @Override
    public void setControlador(Controlador controlador){
        this.controlador= controlador;
    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarMensaje(String texto) {
        cartas.append(texto + "\n");
    }

    public void mostrarMensaje2(String texto){
        notificaciones.append(texto + "\n");
    }

    public void mostrarMensaje3(String texto){
        datosRonda.append(texto + "\n");
    }

    /**
     * Muestra el top de los 10 mejores jugadores.
     * @return void
     **/
    private void mostrarMejoresJugadores(){
        textAreaMJ.setText("");
        HashMap<String, Integer> map= controlador.obtenerMejoresJugadores();
        Set<String> claves = map.keySet();
        int i = 1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (i <= 10) {
                String mensaje = String.format("%d\t%s\t      %d\n", i, entry.getKey(), entry.getValue());
                textAreaMJ.append(mensaje);
                i++;
            }
        }
    }

    /**
     * Muestra la ronda actual y las formas que deben formarse.
     * @return void
     **/
    @Override
    public void mostrarRondaActual() {
        notificaciones.setText("");
        this.mostrarMensaje3("----------------------------------------------------");
        this.mostrarMensaje3("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje3("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje3("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje3("----------------------------------------------------");
    }

    public void mostrarRonda() {
        this.mostrarMensaje3("----------------------------------------------------");
        this.mostrarMensaje3("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje3("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje3("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje3("----------------------------------------------------");
    }

    /**
     * Muestra las formas armadas de cada jugador
     * @return void
     **/
    @Override
    public void mostrarFormasArmadasDeCadaJugador() {
        int totalFormasArmadas= controlador.cantidadTotalFormasArmadas();
        if (totalFormasArmadas>0){
            ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
            for (int i=0; i<controlador.cantidadJugadores(); i++){
                if (controlador.formasArmadasJugador(i).size()!=0){
                    mostrarCartasBajadas(jugadores.get(i));
                }
            }
            this.mostrarMensaje3("----------------------------------------------------");
        }
    }

    /**
     * Coloca todas las variables de turno en 0, muestra el turno, la ronda y las cartas del jugador.
     * @param jugador : jugador que posee el turno
     * @return void
     **/
    @Override
    public void mostrarTurno(Jugador jugador) {
        tomoCartaPozo=0;
        tomoCartaMazo=0;
        seBajo=0;
        dejoCarta=0;
        bajoCarta=0;
        datosRonda.setText("");
        mostrarRonda();
        this.mostrarMensaje3("ES EL TURNO DE: " + controlador.obtenerNickname(jugador));
        this.mostrarMensaje3("----------------------------------------------------");
        mostrarFormasArmadasDeCadaJugador();
        if (this.nickname!=null && participa==true){
            mostrarCartasJugador();
        }
        else{
            cartas.setText("");
            this.mostrarMensaje("NO ESTAS PARTICIPANDO DEL JUEGO, SOLO PODRAS OBSERVAR.");
        }
        mostrarMazo();
        mostrarTopePozo();
    }

    /**
     * Muestra las cartas bajadas de un jugador.
     * @param jugador : jugador el cual se mostraran sus cartas bajadas
     * @return void
     **/
    @Override
    public void mostrarCartasBajadas(Jugador jugador) {
        this.mostrarMensaje3("CARTAS BAJADAS DE: " + controlador.obtenerNickname(jugador));
        for (int i=0; i<controlador.formasArmadasJugador(jugador).size(); i++){
            mostrarForma(controlador.formasArmadasJugador(jugador).get(i));
        }
    }

    /**
     * Muestra una forma
     * @param bajada : bajada la cual se mostrara la forma.
     * @return void
     **/
    @Override
    public void mostrarForma(Bajada bajada) {
        if (controlador.obtenerNombreForma(bajada).equals(Forma.TRIO)){
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getValor().equals("$")){
                if (controlador.cartasQueFormanLaBajada(bajada).get(1).getValor().equals("$")){
                    this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(2).getValor());
                }
                else{
                    this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(1).getValor());
                }
            }
            else{
                this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor());
            }
        } else if (controlador.obtenerNombreForma(bajada).equals(Forma.ESCALA)) {
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getColor().equals(Enumerados.Color.JOKER)){
                this.mostrarMensaje3("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(1).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
            }
            else{
                this.mostrarMensaje3("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(0).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
            }
        }
    }

    /**
     * Muestra las cartas del jugador
     * @return void
     **/
    @Override
    public void mostrarCartasJugador() {
        cartas.setText("");
        ArrayList<Carta> cartas= controlador.obtenerCartasJugador(this.nickname);
        for (int i=0; i<cartas.size(); i++){
            this.mostrarMensaje((i+1) + " - " + cartas.get(i).toString());
        }
    }

    /**
     * Muestra el tope del pozo
     * @return void
     **/
    @Override
    public void mostrarTopePozo() {
        String a= controlador.obtenerTopePozo();
        this.mostrarMensaje3("TOPE POZO:");
        this.mostrarMensaje3(a);
        this.mostrarMensaje3("----------------------------------------------------");
    }

    @Override
    public void mostrarMazo() {
        this.mostrarMensaje3("MAZO...");
    }

    /**
     * Muestra el mensaje correspondiente cuando un jugador toma una carta del pozo.
     * @param jugador : jugador que tomo la carta del pozo
     * @return void
     **/
    @Override
    public void mostrarJugadorTomoCartaPozo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            mostrarMensaje2("- TOMASTE UNA CARTA DEL POZO CON EXITO!");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL POZO CON EXITO!");
        }
    }

    /**
     * Muestra el mensaje correspondiente cuando un jugador toma una carta del mazo.
     * @param jugador : jugador que tomo la carta del mazo
     * @return void
     **/
    @Override
    public void mostrarJugadorTomoCartaMazo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            mostrarMensaje2("- TOMASTE UNA CARTA DEL MAZO CON EXITO!");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL MAZO CON EXITO!");
        }
    }

    @Override
    public void mostrarJugadorSeBajo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            mostrarMensaje2("- TE BAJASTE CON EXITO, DEBES DEJAR UNA CARTA!");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " +  controlador.obtenerNickname(jugador) + " SE HA BAJADO CON EXITO!");
        }
    }

    @Override
    public void mostrarJugadorNoPudoBajarse(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje2("- NO PUEDES BAJARTE ");
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " INTENTO BAJARSE Y NO PUDO");
        }
    }

    @Override
    public void mostrarJugadorDejoCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            mostrarMensaje2("- DEJASTE LA CARTA CON EXITO!");
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " HA DEJADO LA CARTA CON EXITO!");
        }
    }

    @Override
    public void mostrarJugadorBajoCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            mostrarMensaje2("- HAS BAJADO LA CARTA CON EXITO!");
        }
        else{
            this.mostrarMensaje2( "- " + controlador.obtenerNickname(jugador) + " HA BAJADO UNA CARTA CON EXITO!");
        }
    }

    @Override
    public void mostrarJugadorNoPudoBajarCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            mostrarMensaje2("- INTENTASTE BAJAR UNA CARTA Y NO PUDISTE");
        }
        else{
            this.mostrarMensaje2( "- " + controlador.obtenerNickname(jugador) + " INTENTO BAJAR UNA CARTA Y NO PUDO.");
        }
    }

    @Override
    public void mostrarTerminoRonda() {
        cartas.setText("");
        datosRonda.setText("");
        notificaciones.setText("");
        Jugador j= controlador.ganadorRonda();
        this.mostrarMensaje2("EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
        this.mostrarMensaje2("------------------------------------------------------------");
        mostrarPuntosJugadores();
        this.mostrarMensaje2("ESPERANDO QUE EL LIDER INICIE LA PROXIMA RONDA...");
    }

    @Override
    public void mostrarPuntosJugadores() {
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        mostrarMensaje2("PUNTOS DE LOS JUGADORES: ");
        for (int i=0; i<jugadores.size(); i++){
            this.mostrarMensaje2(controlador.obtenerNickname(jugadores.get(i)) + ", PUNTOS: " + controlador.obtenerPuntosJugador(jugadores.get(i)));
        }
        this.mostrarMensaje2("------------------------------------------------------------");
    }

    @Override
    public void mostrarFinJuego() {
        cartas.setText("");
        datosRonda.setText("");
        notificaciones.setText("");
        this.mostrarMensaje2("EL JUEGO HA TERMINADO");
        this.mostrarMensaje2("------------------------------------------------------------");
        Jugador j= controlador.ganadorJuego();
        this.mostrarMensaje2("GANADOR: " + controlador.obtenerNickname(j));
        this.mostrarMensaje2("------------------------------------------------------------");
        mostrarPuntosJugadores();
    }

    @Override
    public void mostrarInicioJuego() {
        mostrarMensaje2("- LA RONDA FUE INICIADA, A JUGAR!");
        if (nickname==null){
            participa=false;
        }
        else{
            participa=true;
        }
    }

    @Override
    public void mostrarNuevoJugadorAgregado(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            mostrarMensaje2("- INGRESASTE AL JUEGO");
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " HA INGRESADO AL JUEGO");
        }
    }

    @Override
    public void anunciarLider(String lider) {
        mostrarMensaje2("- " + lider + " ES EL LIDER DE LA PARTIDA");
    }

    @Override
    public void anunciarJugadorSeFue(String nickameEliminado) {
        if (nickameEliminado.equals(this.nickname)){
            participa=false;
            tomoCartaMazo= 0;
            tomoCartaPozo= 0;
            dejoCarta= 0;
            seBajo= 0;
            bajoCarta= 0;
            cartas.setText("");
            mostrarMensaje("ABANDONASTE EL JUEGO, AHORA SOLO PODRAS OBSERVAR");
        }
        else{
            mostrarMensaje2("-" + nickameEliminado + " ABANDONO EL JUEGO.");
        }
    }
}
