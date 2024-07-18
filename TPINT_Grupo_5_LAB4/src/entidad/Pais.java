package entidad;

public class Pais {
	private int ID_Nacionalidad;
	private String Pais;
	
    public Pais () {
    	
    }
    
    public Pais(int ID_Nacionalidad, String Pais) {
        this.ID_Nacionalidad = ID_Nacionalidad;
        this.Pais = Pais;
    }

	public int getID_Nacionalidad() {
		return ID_Nacionalidad;
	}

	public void setID_Nacionalidad(int iD_Nacionalidad) {
		ID_Nacionalidad = iD_Nacionalidad;
	}

	public String getPais() {
		return Pais;
	}

	public void setPais(String pais) {
		Pais = pais;
	}

	@Override
	public String toString() {
		return "Pais [ID_Nacionalidad=" + ID_Nacionalidad + ", Pais=" + Pais + "]";
	}
	
}
