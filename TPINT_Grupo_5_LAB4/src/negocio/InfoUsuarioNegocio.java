package negocio;

import java.util.ArrayList;

import entidad.InfoUsuario;
import excepciones.YaExisteCuilException;
import excepciones.YaExisteDniException;
import excepciones.YaExisteUsuarioException;

public interface InfoUsuarioNegocio {
	public InfoUsuario obtener_La_InfoUsuario(String Usuario);
	public boolean agregarInfoUsuario(InfoUsuario infoUsuario);
	
	public boolean Agregar_InfoUsuario_UsandoExceptions(InfoUsuario infoUsuario) throws YaExisteDniException,YaExisteUsuarioException,YaExisteCuilException ;

	public ArrayList<InfoUsuario> obtenet_Todos_LosClientes();
	public ArrayList<InfoUsuario> obtener_Todos_LosAdmins();
	public boolean eliminarInfoUsuario(int dni);
	public boolean existe_ese_DNI(int dni) ;
	public boolean existe_ese_CUIL(String cuil) ;
	public boolean modificarInfoUsuario(InfoUsuario infoUsuario);
	public ArrayList<String> obtener_Todas_LasStats();
}
