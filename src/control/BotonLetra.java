package control;

public class BotonLetra {

	// PROPIEDADES
	private String cara;
	private String cruz;
	private boolean clicado;
	private String botonAsignado;
	private static int countBotones = 1;
	
	// COSNTRUCTOR
	public BotonLetra() {
		cara = "?";
		cruz = "";
		clicado = false;
		// para saber a cual de los 16 botones está asignado ese objeto, se le asigna una variable numérica
		botonAsignado = "b" + countBotones++; 
	}
	
	// METODOS
	public boolean getEstado() {
		return clicado;
	}
	
	/** Si la propiedad 'clicado' es true, va a imprimir la cruz, de normal, como esta en false, va a imprimir la cara
	 * @return
	 */
	public String imprimirSegunEstado() {
		return (clicado == true) ?  cruz : cara;
	}
	
	/** Si el botón está clicado, lo va a desclicar y viceversa
	 * 
	 */
	public void clicar() {
		clicado = !clicado;
	}
	
	public void asignarLetra(String nuevaLetraCruz) {
		cruz = nuevaLetraCruz;
	}
	
	public String getCara() {
		return cara;
	}
	
	public String getCruz() {
		return cruz;
	}
	
	public String getBotonAsignado() {
		return botonAsignado;
	}
	
}
