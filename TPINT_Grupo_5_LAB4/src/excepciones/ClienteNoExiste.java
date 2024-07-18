package excepciones;

public class ClienteNoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "El DNI del cliente ingresado no existe";
	}
}
