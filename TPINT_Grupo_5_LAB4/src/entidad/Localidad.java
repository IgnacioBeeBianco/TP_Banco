package entidad;

public class Localidad {
	
	private int ID_Localidad;
	private Provincia Provincia;
	private String Localidad;
 
	public Localidad() {
		
	}

	public Localidad(int ID_Localidad, Provincia Provincia, String Localidad) {
		super();
		this.ID_Localidad = ID_Localidad;
		this.Provincia = Provincia;
		this.Localidad = Localidad;
	}
	
	
	public int getID_Localidad() {
		return ID_Localidad;
	}

	public void setID_Localidad(int iD_Localidad) {
		ID_Localidad = iD_Localidad;
	}

	public Provincia getProvincia() {
		return Provincia;
	}

	public void setProvincia(Provincia provincia) {
		Provincia = provincia;
	}

	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}
	
	
}
