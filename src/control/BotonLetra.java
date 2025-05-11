package control;

public class BotonLetra {

	// PROPIEDADES
	private String cara;
	private String cruz;
	private boolean clicado;
	private String botonAsignado;
	private static int countBotones = 1; // afecta a la clase, no al objeto particular
	private boolean clicadoUnaVez;
	
	// COSNTRUCTOR
	public BotonLetra() {
		cara = "?";
		cruz = "";
		clicado = false;
		clicadoUnaVez = false;
		// para saber a cual de los 16 botones está asignado ese objeto, se le asigna una variable numérica
		botonAsignado = "b" + countBotones++; 
	}
	
	// METODOS
	public boolean getEstado() {
		return clicado;
	}
	
	/** Si la propiedad 'clicado' es true, va a imprimir la cruz. De normal, como esta en false, va a imprimir la cara
	 * @return
	 */
	public String imprimirSegunEstado() {
		return (clicado == true) ?  cruz : cara;
	}
	
	public void clicar() {
		// Ya clicado, no va a darse la vuelta otra vez
		if(clicado == true) {
			return;
		}
		
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
	
	/** Sea cual sea el valor, dejará esa letra como falsa
	 * 
	 */
	public void asiganarComoFalso() {
		clicado = false;
	}
	
	public boolean seHaClicado() {
		return clicadoUnaVez;
	}
	
	public void setClicado() {
		clicadoUnaVez = true;
	}
	
	public void setUnclicado() {
		clicadoUnaVez = false;
	}
	
}
