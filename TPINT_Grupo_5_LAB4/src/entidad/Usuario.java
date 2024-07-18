package entidad;

public class Usuario {
	
	private String Usuario;
	private String Contraseña;
    private boolean esAdmin;

    public  Usuario() {
    	
    }
    
    public Usuario (String usuario, String contraseña, boolean esAdmin) {
        this.Usuario = usuario;
        this.Contraseña = contraseña;
        this.esAdmin = esAdmin;
    }
    
	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getContraseña() {
		return Contraseña;
	}

	public void setContraseña(String contraseña) {
		Contraseña = contraseña;
	}

	public boolean isEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	@Override
	public String toString() {
		return "Usuario [Usuario=" + Usuario + ", Contraseña=" + Contraseña + ", esAdmin=" + esAdmin + "]";
	}
}
