package negocioImpl;


import datos.UsuarioDao;
import datosImpl.UsuarioDaoImpl;
import entidad.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio{
      
	UsuarioDao Udao = new UsuarioDaoImpl();

	
	@Override
	public boolean BuscarUsuario(Usuario Usuario) {
		
		boolean Existe = false;
		
        if(Udao.ExisteUsuario(Usuario)) {
        	Existe = true;
        }
		return Existe;
	}


	@Override
	public boolean VerificarClave(Usuario Usuario) {
       boolean Existe = false;
		
        if(Udao.ClaveCorrecta(Usuario)) {
        	Existe = true;
        }
		return Existe;
	}


	public boolean actualizarPassword(String usuario, String newPassword) {
	    UsuarioDao usuarioDao = new UsuarioDaoImpl();
	    return usuarioDao.actualizarPassword(usuario, newPassword);
	}


	@Override
	public boolean Existe_Ese_NombreUsuario(String Usuario) {
		return Udao.ExisteNombreUsuario(Usuario);
	}
	
	public boolean updateRolInfoUsuario(String usuario, boolean admin) {
		return Udao.updateRolInfoUsuario(usuario, admin);
	}

}
