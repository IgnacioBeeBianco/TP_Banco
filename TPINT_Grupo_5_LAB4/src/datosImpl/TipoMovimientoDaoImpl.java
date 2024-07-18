package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datos.TipoMovimientoDao;
import entidad.TipoMovimiento;

public class TipoMovimientoDaoImpl implements TipoMovimientoDao{
	
	private static final String Obtener_Tipo_DeMovimientos = "SELECT ID_TipoMovimiento, NombreTipoMovimiento FROM tipodemovimientos";
	
	public ArrayList<TipoMovimiento> obtenerTipoMovimiento() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet rs = null;
		ArrayList<TipoMovimiento> listaTipoMovimientos = new ArrayList<>();

		try {
			pst = conexion.prepareStatement(Obtener_Tipo_DeMovimientos);
			rs = pst.executeQuery();

			while (rs.next()) {
				TipoMovimiento movimiento = new TipoMovimiento();
				
				movimiento.setId(rs.getInt("ID_TipoMovimiento"));
				movimiento.setNombre(rs.getString("NombreTipoMovimiento"));

	
				listaTipoMovimientos.add(movimiento);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaTipoMovimientos;
		
	}
}
