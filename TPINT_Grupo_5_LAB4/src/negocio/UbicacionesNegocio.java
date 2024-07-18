package negocio;

import java.util.ArrayList;

import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;

public interface UbicacionesNegocio {
	public ArrayList<Pais> getPaises();
	public ArrayList<Provincia> getProvincias();
	public ArrayList<Localidad> getLocalidadesPorProvincia(Provincia provincia);
}
