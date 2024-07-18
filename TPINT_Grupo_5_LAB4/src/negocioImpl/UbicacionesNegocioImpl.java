package negocioImpl;

import java.util.ArrayList;

import datos.UbicacionesDao;
import datosImpl.UbicacionesDaoImpl;
import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;
import negocio.UbicacionesNegocio;

public class UbicacionesNegocioImpl implements UbicacionesNegocio {
	UbicacionesDao dao = new UbicacionesDaoImpl();

	@Override
	public ArrayList<Pais> getPaises() {
		return dao.getPaises();
	}

	@Override
	public ArrayList<Provincia> getProvincias() {
		return dao.getProvincias();
	}

	@Override
	public ArrayList<Localidad> getLocalidadesPorProvincia(Provincia provincia) {
		return dao.getLocalidadesPorProvincia(provincia);
	}

}
