package datosImpl;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import datos.UbicacionesDao;
import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;
import java.sql.Connection;

public class UbicacionesDaoImpl implements UbicacionesDao {

	private static final String SELECT_PAISES = "SELECT ID_Nacionalidad, Pais FROM paises";
	private static final String SELECT_PROVINCIAS = "SELECT ID_Provincia, Provincia FROM provincias";
	private static final String SELECT_LOCALIDADES = "SELECT ID_Localidad, Localidad FROM localidades WHERE ID_Provincia = ";

	@Override
	public ArrayList<Pais> getPaises() {
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ArrayList<Pais> list = new ArrayList<Pais>();
		try {
			Statement st = conexion.createStatement(); 
			ResultSet rs = st.executeQuery(SELECT_PAISES);
			while (rs.next()) {
				Pais pais = new Pais();
				pais.setID_Nacionalidad(rs.getInt("paises.ID_Nacionalidad"));
				pais.setPais(rs.getString("paises.Pais"));
				list.add(pais);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<Provincia> getProvincias() {
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ArrayList<Provincia> list = new ArrayList<Provincia>();
		try {
			Statement st = conexion.createStatement(); 
			ResultSet rs = st.executeQuery(SELECT_PROVINCIAS);
			while (rs.next()) {
				Provincia prov = new Provincia();
				prov.setID_Provincia(rs.getInt("provincias.ID_Provincia"));
				prov.setProvincia(rs.getString("provincias.Provincia"));
				list.add(prov);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<Localidad> getLocalidadesPorProvincia(Provincia provincia) {
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ArrayList<Localidad> list = new ArrayList<Localidad>();
		try {
			Statement st = conexion.createStatement(); 
			ResultSet rs = st.executeQuery(SELECT_LOCALIDADES + provincia.getID_Provincia());
			while (rs.next()) {
				Localidad loc = new Localidad();
				loc.setID_Localidad(rs.getInt("localidades.ID_Localidad"));
				loc.setLocalidad(rs.getString("localidades.Localidad"));
				list.add(loc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}