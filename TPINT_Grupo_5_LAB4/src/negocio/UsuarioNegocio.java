package negocio;

import entidad.Usuario;


public interface UsuarioNegocio {

	public boolean BuscarUsuario(Usuario Usuario);
	public boolean Existe_Ese_NombreUsuario(String usuario);
	public boolean VerificarClave(Usuario Usuario);
	public boolean actualizarPassword(String clientUser, String newPassword);
	public boolean updateRolInfoUsuario(String usuario, boolean admin);
}
