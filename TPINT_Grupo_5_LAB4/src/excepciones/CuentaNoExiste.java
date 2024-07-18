package excepciones;

public class CuentaNoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "La cuenta buscada no existe";
	}
}
