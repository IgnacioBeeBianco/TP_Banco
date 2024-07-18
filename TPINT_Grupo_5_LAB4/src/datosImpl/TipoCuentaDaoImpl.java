package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datos.TipoCuentaDao;
import entidad.TipoCuenta;

public class TipoCuentaDaoImpl implements TipoCuentaDao {

	private static final String Obtener_Tipo_DeCuentas = "SELECT idTipoCuenta,Descripcion FROM tipodecuentas";
	
	
	@Override
	public ArrayList<TipoCuenta> obtenerTipoCuenta() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet rs = null;
		ArrayList<TipoCuenta> listaTipoCuentas = new ArrayList<>();

		try {
			pst = conexion.prepareStatement(Obtener_Tipo_DeCuentas);
			rs = pst.executeQuery();

			while (rs.next()) {
				TipoCuenta cuenta = new TipoCuenta();

				cuenta.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
				cuenta.setDescripcion(rs.getString("Descripcion"));


	
				listaTipoCuentas.add(cuenta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaTipoCuentas;
		
	}

}
