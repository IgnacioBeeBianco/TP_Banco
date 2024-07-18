package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import datos.InfoUsuarioDao;
import datos.PrestamoDao;
import datosImpl.InfoUsuarioDaoImpl;
import datosImpl.PrestamoDaoImpl;
import entidad.InfoUsuario;
import entidad.Prestamo;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio {
	PrestamoDao dao = new PrestamoDaoImpl();

	public List<Prestamo> obtenerSolicitudes() {
		return dao.obtenerSolicitudes();
	}

	public boolean pedirPrestamo(Prestamo prestamo) {
		InfoUsuarioDao daoCliente = new InfoUsuarioDaoImpl();
		prestamo.setCliente(daoCliente.obtener_Un_InfoUsuario(prestamo.getCliente().getUsuario().getUsuario()));
		return dao.pedirPrestamo(prestamo);
	}

	public boolean aceptarPrestamo(int id) {
		return dao.aceptarPrestamo(id);
	}

	public boolean rechazarPrestamo(int id) {
		return dao.rechazarPrestamo(id);
	}

	@Override
	public ArrayList<Prestamo> Obtener_Prestamos_PorDni(int dni) {
		ArrayList<Prestamo> Lista = new ArrayList<>();
		Lista = dao.Obtener_Prestamos_PorDni(dni);
	    return Lista;
	}
}
