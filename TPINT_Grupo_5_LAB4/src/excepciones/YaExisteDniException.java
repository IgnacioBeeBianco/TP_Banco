package excepciones;

public class YaExisteDniException  extends Exception {
	
	public YaExisteDniException () {
		
	}

	@Override
	public String getMessage() {

		return  "Ese DNI ya existe";
	}
	
	

}
