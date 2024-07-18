package entidad;

public class Usuario {
	
	private String Usuario;
	private String Contrase�a;
    private boolean esAdmin;

    public  Usuario() {
    	
    }
    
    public Usuario (String usuario, String contrase�a, boolean esAdmin) {
        this.Usuario = usuario;
        this.Contrase�a = contrase�a;
        this.esAdmin = esAdmin;
    }
    
	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getContrase�a() {
		return Contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		Contrase�a = contrase�a;
	}

	public boolean isEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	@Override
	public String toString() {
		return "Usuario [Usuario=" + Usuario + ", Contrase�a=" + Contrase�a + ", esAdmin=" + esAdmin + "]";
	}
}
