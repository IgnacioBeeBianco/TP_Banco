package datos;

import java.util.ArrayList;

import entidad.InfoUsuario;

public interface InfoUsuarioDao {
	
	public InfoUsuario obtener_Un_InfoUsuario(String Usuario);
	public ArrayList<InfoUsuario> obtenet_Todos_InfoUsuarios();
	public ArrayList<InfoUsuario> obtener_Todos_InfoUsuariosAdmins();
	public boolean agregarInfoUsuario(InfoUsuario infoUsuario);
	public boolean existeDNI(int dni);
	public boolean existeCUIL(String cuil);
	public boolean eliminarInfoUsuario(int dni);
	public boolean modificarInfoUsuario(InfoUsuario infoUsuario);
	public ArrayList<String>obtener_Todas_LasStats();
}