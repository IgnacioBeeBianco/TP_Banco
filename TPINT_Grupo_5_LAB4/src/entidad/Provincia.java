package entidad;

public class Provincia {
	
	private int ID_Provincia;
	private String Provincia;
	
	public Provincia  () {
		
	}
	
	  public Provincia (int ID_Provincia, String Provincia) {
	        this.ID_Provincia = ID_Provincia;
	        this.Provincia = Provincia;
	    }

	public int getID_Provincia() {
		return ID_Provincia;
	}

	public void setID_Provincia(int iD_Provincia) {
		ID_Provincia = iD_Provincia;
	}

	public String getProvincia() {
		return Provincia;
	}

	public void setProvincia(String provincia) {
		Provincia = provincia;
	}

	@Override
	public String toString() {
		return "Provincia [ID_Provincia=" + ID_Provincia + ", Provincia=" + Provincia + "]";
	}
}
