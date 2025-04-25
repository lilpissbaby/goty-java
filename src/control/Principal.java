package control;

import java.awt.event.ActionEvent;
import java.util.Random;
//import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class Principal {
	
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

	@FXML private Button[] botonesJuego = new Button[16];
    BotonLetra[] objetosBoton = new BotonLetra[16];
	
	// En el metodo initialize() permite acceder a todo lo instanciado en el construcotr de Pricipal
	@FXML public void initialize() {
		botonesJuego = new Button[]{b1, b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16};
		
		// A continuación tendremos dos arrays, uno solamente con letras, ya mezcladas,
		// y uno con obejtos BotonLetra que poseen un "?" y una de las respectivas letras dle array anterior
	    String[] letras = repartirLetras();
	    
		for (int i = 0; i < botonesJuego.length; i++) {
			
			objetosBoton[i] = new BotonLetra(); // en ese array de objetos vacio, le creamos un objeto en la casilla
			objetosBoton[i].asignarLetra(letras[i]); // al botón le asignamos una letra en la propiedad 'cruz'
			botonesJuego[i].setText(String.valueOf(objetosBoton[i].imprimirSegunEstado())); // y le asignamos esa letra, desde la porpiedad de cara obtejo BotonLetra
		}
	}
	
	/////////////////////////////////////
	/**    FUNCIONES DE MAQUETACIÓN    */
	/////////////////////////////////////
	public void revelarBoton(ActionEvent event) {
		// Del boton que clicamos, sacamos su id
	    Button botonClicado = (Button) event.getSource();	    
	    String idBotonClicado = botonClicado.getId();
		
	    // buscamos un objeto BotonLetra que su propiedad botonAsignado coincida con ese id
		BotonLetra objetoBotonPorRevelar = null;
		
		for(int i = 0; i < objetosBoton.length; i++) {
			if(objetoBotonPorRevelar == null &&
			   objetosBoton[i].getBotonAsignado().equals(idBotonClicado)) {
			
				objetoBotonPorRevelar = objetosBoton[i];
			}
		}
		// le damos la vuelta (invocar funcion clicar() que cambiará el estado de la porpiedad 'clicar')
		objetoBotonPorRevelar.clicar();
		
		// segun la propiedad 'clicar', revelar la cara o la cruz del boton
		botonClicado.setText(objetoBotonPorRevelar.imprimirSegunEstado());
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