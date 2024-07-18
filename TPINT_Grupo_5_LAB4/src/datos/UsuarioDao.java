package datos;

import entidad.Usuario;

public interface UsuarioDao {
	public boolean ExisteUsuario(Usuario Usuario);
	public boolean ExisteNombreUsuario(String Usuario);
	public boolean ClaveCorrecta(Usuario Usuario);
	public boolean AgregarUsuario(Usuario Usuario);
	public boolean actualizarPassword(String usuario, String newPassword);
	public boolean updateRolInfoUsuario(String usuario, boolean admin);
}