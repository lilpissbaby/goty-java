package control;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Principal {
	
	// Booleano que permitirá saber si el jeugo ha empezado o no
	boolean juegoIniciado = false;
	
	// Para agilizar la función de asignar letras a los botones, se instancia cada uno de ellos
	// y se almacena en un array.
	@FXML private Button b1;
	@FXML private Button b2;
	@FXML private Button b3;
	@FXML private Button b4;
	@FXML private Button b5;
	@FXML private Button b6;
	@FXML private Button b7;
	@FXML private Button b8;
	@FXML private Button b9;
	@FXML private Button b10;
	@FXML private Button b11;
	@FXML private Button b12;
	@FXML private Button b13;
	@FXML private Button b14;
	@FXML private Button b15;
	@FXML private Button b16;

	// Esto no se puede inyectar desde FXML, lo inicializamos manualmente
	private Button[] botonesJuego = new Button[16];
    BotonLetra[] objetosBoton = new BotonLetra[16];
	
	// En el metodo initialize() permite acceder a todo lo instanciado en el construcotr de Pricipal
	@FXML public void initialize() throws IOException {		
		botonesJuego = new Button[]{b1, b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16};
		
		escribirPuntuacionGlobal();
		actualizarPuntuacionGlobal();
		
		// A continuación tendremos dos arrays, uno solamente con letras, ya mezcladas,
		// y uno con obejtos BotonLetra que poseen un "?" y una de las respectivas letras dle array anterior
	    String[] letras = repartirLetras();
	    
		for (int i = 0; i < botonesJuego.length; i++) {
			
			objetosBoton[i] = new BotonLetra(); // en ese array de objetos vacio, le creamos un objeto en la casilla
			objetosBoton[i].asignarLetra(letras[i]); // al botón le asignamos una letra en la propiedad 'cruz'
			botonesJuego[i].setText(String.valueOf(objetosBoton[i].imprimirSegunEstado())); // y le asignamos esa letra, desde la porpiedad de cara obtejo BotonLetra
		}
		
		// Nada más empezar la partida, pondrá el texto de comenzar y no el de reiniciar
		cambiarBotonInicioEmpezar();
	}
	
	/////////////////////////////////////
	/**    FUNCIONES DE MAQUETACIÓN    */
	/////////////////////////////////////
	
	/** Permite cambiar o revelar el valor de cada botón. 
	 * 
	 * @param event
	 * @throws IOException 
	 */
	@FXML
	public void revelarBotonPorId(ActionEvent event) throws IOException {
		
		if(juegoIniciado == false) {
			return;
		}
		
		// del botón clicado, scamos su id (id del objeto/elemento fxml)
	    Button clickedButton = (Button) event.getSource();
	    String buttonId = clickedButton.getId();
	    
	    for (int i = 0; i < objetosBoton.length; i++) {
	        if (objetosBoton[i].getBotonAsignado().equals(buttonId)) {
	            objetosBoton[i].clicar();
	            botonesJuego[i].setText(objetosBoton[i].imprimirSegunEstado());
	            
	            // Clicado el botón y revelada la cruz (que puede ser X,O,W) se va a valorar el resultado
	            valorarCasilla(objetosBoton[i].getCruz(), objetosBoton[i].seHaClicado());
	            objetosBoton[i].setClicado();
	            return;
	        }
	    }
	}
	
	/** Hasta que no se pase por esta función, el juego no comienza
	 * 
	 */
	@FXML
	public void funcionesBotonReset() {
		// Si el juego no ha empezado, serivá para emepzarlo
		if(juegoIniciado == false) {
			juegoIniciado = true;
			cambiarBotonInicioReiniciar();
		}
		// Si ha empezado ya, servirá para reiniciar
		else {
			// Mezclará el array de botones y letras
			String[] nuevasLetras = repartirLetras();
			
			// y va a "girarlas" dejando el ? boca arriba
		    reiniciarPartida(objetosBoton, botonesJuego, nuevasLetras);
		}
	}
	
	
	/** Función que reinicia la partida, util en caso de perder o voluntariamente reiniciarla
	 * 
	 * @param objetosBoton
	 * @param elementosBoton
	 * @param nuevasLetras
	 */
	public void reiniciarPartida(BotonLetra[] objetosBoton, Button[] elementosBoton, String[] nuevasLetras) {
		for (int i = 0; i < objetosBoton.length; i++) {
			objetosBoton[i].setUnclicado();
			objetosBoton[i].asignarLetra(nuevasLetras[i]); // creado otro array de letras nuevas, las insertamos en los botones de antes
	    	objetosBoton[i].asiganarComoFalso(); // y estén clicados o no, los marcamos como falsos cada uno para que den el ?
	    	botonesJuego[i].setText(String.valueOf(objetosBoton[i].imprimirSegunEstado()));
	    }
	}
	
	/** Hace que al empezar la partida, el botón de inicio tenga el texto 'Començar'
	 * 
	 */
	@FXML private Button resetButton;
	
	@FXML
	public void cambiarBotonInicioEmpezar() {
		resetButton.setText("Començar");
	}
	
	@FXML
	public void cambiarBotonInicioReiniciar() {
		resetButton.setText("Reiniciar pantalla");
	}
	
	@FXML
	private void cerrarVentana(ActionEvent event) throws IOException {
		// actualizar la puntuación
		actualizarPuntuacionGlobal();
		
	    // Obtener el botón que disparó el evento
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Has fet " + puntuacionActual + " punts");
        alert.showAndWait();
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}

	
	////////////////////////////////////////////////
	/**			FUNCI0NES DE PUNTUACIÓN			  */
	////////////////////////////////////////////////
	
	private int puntuacionActual = 0;
	private int puntuacionTotal;
	@FXML private Label scoreLabel;
	
	@FXML
	public void sumar1() {
		puntuacionActual++;
		scoreLabel.setText(puntuacionActual + "");
	}
	
	@FXML
	public void por2() {
		puntuacionActual *= 2;
		scoreLabel.setText(puntuacionActual + "");
	}
	
	@FXML
	public void perdrePartida() {
		puntuacionActual = 0;
		scoreLabel.setText(puntuacionActual + "");
		
		// Además de poner la puntuación local en 0, va a reiniciar el tablero
		String[] nuevasLetras = repartirLetras();
		reiniciarPartida(objetosBoton, botonesJuego, nuevasLetras);
	}
	
	public void valorarCasilla(String valorCasilla, boolean seHaClicado) throws IOException {
		// Segun qué ponga en la casilla, va a escribir una cosa u otra en la puntiación
		switch(valorCasilla) {
		case "X":
			perdrePartida();
			actualizarPuntuacionGlobal();
			break;
		case "O":
			if(seHaClicado == false) {
				sumar1();
			}
			break;
		case "W":
			if(seHaClicado == false) {
				por2();
			}
			break;
		}
	}
	
	@FXML Label recordLabel;
	
	public void actualizarPuntuacionGlobal() throws IOException {
		// Ver si la puntuación local supera la global
		File ficheroRecord = new File("src/puntuacionGlobal.txt");
		Scanner leerFichero = new Scanner(ficheroRecord);
		int recordActual = leerFichero.nextInt();
		
		// Escribir la puntuación local si supera la global
		recordLabel.setText("Rècord: " + Math.max(recordActual, puntuacionActual));
		
		if(puntuacionActual > recordActual) {
			FileWriter escribirFichero = new FileWriter(ficheroRecord);
			escribirFichero.write(String.valueOf(puntuacionActual));
			escribirFichero.close();
		}
	}

	/** Escribirá en el fichero la puntuación máxima
	 * 
	 * @throws IOException
	 */
	@FXML
	public void escribirPuntuacionGlobal() throws IOException {
		File ficheroRecord = new File("src/puntuacionGlobal.txt");

		// Si el archivo no existe o está vacío, escribir 0
		if (!ficheroRecord.exists() || ficheroRecord.length() == 0) {
			FileWriter escribirFichero = new FileWriter(ficheroRecord);
			escribirFichero.write("0");
			escribirFichero.close();
		} else {
			Scanner leerFichero = new Scanner(ficheroRecord);
			if (leerFichero.hasNextInt()) {
				int valor = leerFichero.nextInt();
				leerFichero.close();
				FileWriter escribirFichero = new FileWriter(ficheroRecord);
				escribirFichero.write(String.valueOf(valor));
				escribirFichero.close();
			} else {
				leerFichero.close();
				// Si no hay un entero, escribimos 0 para reiniciar el contenido corrupto
				FileWriter escribirFichero = new FileWriter(ficheroRecord);
				escribirFichero.write("0");
				escribirFichero.close();
			}
		}
	}

	
	////////////////////////////////////////////////////
	/**    FUNCIONES QUE NO AFECTAN LA MAQUETACIÓN    */
	////////////////////////////////////////////////////
	
	/** Devuelve el array de letras mezclado
	 * @return
	 */
	public String[] repartirLetras() {
		String[] tablero = new String[16];
		
		Random random = new Random();
        int probabilidadW = random.nextInt(2) + 1;
		
		// Imprimirá dos X
		tablero[0] = "X";
		tablero[1] = "X";
		
		// Según la probabilidad de W que haya, va a ir generándolas
		int ultimaCasillaLibre = 2;
		
		for(int i = 0; i <= probabilidadW; i++) {
			// Pasadas las casillas con las X...
			tablero[ultimaCasillaLibre] = "W";
			ultimaCasillaLibre++;
		}
		
		// Imprimir en todo lo restante las O
		for(int i = 0; i < tablero.length; i++) {
			// Si la casilla esta vacia...
			if(tablero[i] == null) {
				tablero[i] = "O";
			}
		}
		
		// Barajar el array
		return shuffleArray(tablero);
	}
		
	/** Mezcla las posiciones de un array y devuelve ese array mezclado
	 * @param array
	 * @return
	 */
	public static String[] shuffleArray(String[] array) {
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--) {
	        int j = random.nextInt(i + 1);
	        String temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	    }
	    return array; 
	}
}
