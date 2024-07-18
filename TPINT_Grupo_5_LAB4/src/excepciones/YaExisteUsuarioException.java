package excepciones;

public class YaExisteUsuarioException extends Exception {

	public YaExisteUsuarioException () {
		
	}

	@Override
	public String getMessage() {
		
		return  "Ya Existe Ese Nombre de Usuario";

	}
	
	
	
}
