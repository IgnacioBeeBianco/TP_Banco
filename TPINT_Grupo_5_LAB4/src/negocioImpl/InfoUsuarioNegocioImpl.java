package negocioImpl;


import java.util.ArrayList;
import datos.InfoUsuarioDao;
import datos.UsuarioDao;
import datosImpl.InfoUsuarioDaoImpl;
import datosImpl.UsuarioDaoImpl;
import entidad.InfoUsuario;
import excepciones.ClienteNoExiste;
import excepciones.YaExisteCuilException;
import excepciones.YaExisteDniException;
import excepciones.YaExisteUsuarioException;
import negocio.InfoUsuarioNegocio;

public class InfoUsuarioNegocioImpl implements InfoUsuarioNegocio {

	InfoUsuarioDao UFdao = new InfoUsuarioDaoImpl();
	UsuarioDao UsuarioDao = new UsuarioDaoImpl();

	@Override
	public InfoUsuario obtener_La_InfoUsuario(String Usuario) {

	    InfoUsuario IF = new InfoUsuario();
		IF = UFdao.obtener_Un_InfoUsuario(Usuario);
    
		return IF;
	}
	
	@Override
	public ArrayList<InfoUsuario> obtenet_Todos_LosClientes() {
		
		ArrayList<InfoUsuario> listaInfoUsuarios = new ArrayList<>();
		listaInfoUsuarios = UFdao.obtenet_Todos_InfoUsuarios();
	    return listaInfoUsuarios;
	}
	
	public ArrayList<InfoUsuario> obtener_Todos_LosAdmins() {
		
		ArrayList<InfoUsuario> listaInfoUsuarios = new ArrayList<>();
		listaInfoUsuarios = UFdao.obtener_Todos_InfoUsuariosAdmins();
	    return listaInfoUsuarios;
	}

	
	@Override
	public boolean agregarInfoUsuario(InfoUsuario infoUsuario) {
		return UFdao.agregarInfoUsuario(infoUsuario);
	}
	
	
    @Override
    public boolean eliminarInfoUsuario(int dni) {
        return UFdao.eliminarInfoUsuario(dni);
    }

	
    @Override
	public boolean existe_ese_DNI(int dni) {
    	boolean estado=UFdao.existeDNI(dni);
    	return estado;
	}
    
    
    @Override
	public boolean modificarInfoUsuario(InfoUsuario infoUsuario) {
    	return UFdao.modificarInfoUsuario(infoUsuario);
    	
	}

	@Override
	public boolean existe_ese_CUIL(String cuil) {
		boolean estado=UFdao.existeCUIL(cuil);
    	return estado;
	}

	public ArrayList<String> obtener_Todas_LasStats()
	{
		ArrayList<String> Lista_Stats = UFdao.obtener_Todas_LasStats();
		
		return Lista_Stats;
	}

	
	@Override
	public boolean Agregar_InfoUsuario_UsandoExceptions(InfoUsuario infoUsuario)
	        throws YaExisteDniException, YaExisteUsuarioException, YaExisteCuilException {

	    if (UFdao.existeDNI(infoUsuario.getDni())) {
	        throw new YaExisteDniException();
	    }
	    if (UFdao.existeCUIL(infoUsuario.getCuil())) {
	        throw new YaExisteCuilException();
	    }
	    if (UsuarioDao.ExisteNombreUsuario(infoUsuario.getUsuario().getUsuario())) {
	        throw new YaExisteUsuarioException();
	    }
	    
	    return UFdao.agregarInfoUsuario(infoUsuario);
	}
	
}
