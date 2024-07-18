package datos;

import java.util.ArrayList;

import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;

public interface UbicacionesDao {
	public ArrayList<Pais> getPaises();
	public ArrayList<Provincia> getProvincias();
	public ArrayList<Localidad> getLocalidadesPorProvincia(Provincia provincia);
}
