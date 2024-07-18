package excepciones;

public class YaExisteCuilException extends Exception {

	public YaExisteCuilException () {
			
		}

	@Override
	public String getMessage() {
		
		return  "Ese CUIL ya EXISTE";

	}
	
	

}
