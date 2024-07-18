package datosImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datos.MovimientoDao;
import entidad.Cuenta;
import entidad.Movimiento;
import entidad.TipoMovimiento;

public class MovimientoDaoImpl implements MovimientoDao{
	private static final String ObtenerTodosLosMovimientos = 
			"SELECT m.NroMovimiento as 'Nro Movimiento', c.CBU, m.FechaMovimiento as 'Fecha Movimiento', m.Importe, m.Detalle_Concepto as 'Detalle Concepto',m.ID_TipoMovimiento, "
			+ "tm.NombreTipoMovimiento as 'Nombre Tipo Movimiento' From movimientos m " 
			+"JOIN cuentas c ON c.CBU = m.CBU_Cliente " 
			+"JOIN tipodemovimientos tm ON m.ID_TipoMovimiento = tm.ID_TipoMovimiento";
	
	
	private static final String FILTRADO = "SELECT m.NroMovimiento as 'Nro Movimiento', c.CBU, m.FechaMovimiento as 'Fecha Movimiento', m.Importe, m.Detalle_Concepto as 'Detalle Concepto', m.ID_TipoMovimiento," + 
			"tm.NombreTipoMovimiento as 'Nombre Tipo Movimiento'" + 
			"FROM movimientos m " + 
			"JOIN cuentas c ON c.CBU = m.CBU_Cliente " + 
			"JOIN tipodemovimientos tm ON m.ID_TipoMovimiento = tm.ID_TipoMovimiento " + 
			"WHERE m.Importe BETWEEN ? AND ?";
	
	       private static final String ObtenerMovimientosPorNumCuenta = 
		    "SELECT m.NroMovimiento AS 'Nro Movimiento', c.CBU, c.NumeroCuenta, " +
		    "m.FechaMovimiento AS 'Fecha Movimiento', m.Importe, m.SaldoPosterior AS 'Saldo Total', " +
		    "m.Detalle_Concepto AS 'Detalle Concepto', m.ID_TipoMovimiento, " +
		    "tm.NombreTipoMovimiento AS 'Nombre Tipo Movimiento' " +
		    "FROM movimientos m " +
		    "JOIN cuentas c ON c.CBU = m.CBU_Cliente " +
		    "JOIN tipodemovimientos tm ON m.ID_TipoMovimiento = tm.ID_TipoMovimiento " +
		    "WHERE c.NumeroCuenta = ?";


	
	@Override
	public ArrayList<Movimiento> obtener_Todos_LosMovimientos() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet rs = null;
		ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

		try {
			pst = conexion.prepareStatement(ObtenerTodosLosMovimientos);
			rs = pst.executeQuery();

			while (rs.next()) {
				
				Movimiento movimiento = new Movimiento();
				
				movimiento.setNroMovimiento(rs.getInt("Nro Movimiento"));
				
				Cuenta cuenta = new Cuenta();
				cuenta.setCBU(rs.getString("CBU"));
				movimiento.setCbuCliente(cuenta);
				
				movimiento.setFechaMovimiento(rs.getDate("Fecha Movimiento"));
				movimiento.setImporte(rs.getBigDecimal("Importe"));
				movimiento.setDetalle(rs.getString("Detalle Concepto"));
				
				TipoMovimiento tipoM = new TipoMovimiento();
				tipoM.setId(rs.getInt("ID_TipoMovimiento"));
				tipoM.setNombre(rs.getString("Nombre Tipo Movimiento"));
				
				
				movimiento.setTipoMovimiento(tipoM);
				
				listaMovimientos.add(movimiento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaMovimientos;
	}
	

	@Override
	public ArrayList<Movimiento> generarReporteMovimientos(String fechaInicio, String fechaFin, String tipoMovimiento, float saldoMin, float saldoMax) {
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    PreparedStatement pst = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    ResultSet rs = null;
	    ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

	    try {
	    	String consultaTipoM = "";
	    	if(!tipoMovimiento.equals("0"))
	    	{
	    	consultaTipoM = " AND m.ID_TipoMovimiento = "+tipoMovimiento;
	    	}

	         
	         String consultaFechas = "";
	         
	    if(!fechaInicio.equals("")&&!fechaFin.equals(""))
	    	{
	    		consultaFechas = " AND m.FechaMovimiento BETWEEN '"+fechaInicio+" 00:00:00' AND '"+fechaFin+" 00:00:00'";	
	    	
	    	}else if(!fechaInicio.equals("")){
	    		
	    		consultaFechas = " AND m.FechaMovimiento >= '"+fechaInicio+" 00:00:00'";
	    	}else if(!fechaFin.equals("")) {
	    		
	    		consultaFechas = " AND m.FechaMovimiento <= '"+fechaFin+" 00:00:00'";	    		
	    	}
	    	System.out.println(FILTRADO+consultaTipoM+consultaFechas);
	    	
	    	
	        
	        pst = conexion.prepareStatement(FILTRADO+consultaTipoM+consultaFechas);
	        
	        BigDecimal BDsaldoMin = BigDecimal.valueOf(saldoMin);
	        pst.setBigDecimal(1, BDsaldoMin);
	        BigDecimal BDsaldoMax = BigDecimal.valueOf(saldoMax);
	        pst.setBigDecimal(2, BDsaldoMax);
	        
	    	System.out.println(BDsaldoMin);
	    	System.out.println(BDsaldoMax);


	        rs = pst.executeQuery();

	        while (rs.next()) {
	            Movimiento movimiento = new Movimiento();

				movimiento.setNroMovimiento(rs.getInt("Nro Movimiento"));
				
				Cuenta cuenta = new Cuenta();
				cuenta.setCBU(rs.getString("CBU"));
				movimiento.setCbuCliente(cuenta);
				movimiento.setFechaMovimiento(rs.getDate("Fecha Movimiento"));
				movimiento.setImporte(rs.getBigDecimal("Importe"));
				movimiento.setDetalle(rs.getString("Detalle Concepto"));
				
				TipoMovimiento tipoM = new TipoMovimiento();
				tipoM.setId(rs.getInt("ID_TipoMovimiento"));
				tipoM.setNombre(rs.getString("Nombre Tipo Movimiento"));
				movimiento.setTipoMovimiento(tipoM);
				
	            listaMovimientos.add(movimiento);
	            
	        }
	    } catch (SQLException e) {
	    	System.out.println("HOLA 2 ");
	        e.printStackTrace();
	    }
	    return listaMovimientos;
	}
	
	public ArrayList<Movimiento> obtenerMovimientosNumCuenta(int nroCuenta) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
		try {

			pst = conexion.prepareStatement(ObtenerMovimientosPorNumCuenta);
			pst.setInt(1, nroCuenta);
			ResultSet rs = pst.executeQuery();
			

			while (rs.next()) {

				Movimiento movimiento = new Movimiento();

				movimiento.setNroMovimiento(rs.getInt("Nro Movimiento"));

				Cuenta cuenta = new Cuenta();
				cuenta.setCBU(rs.getString("CBU"));
				movimiento.setCbuCliente(cuenta);
				
				movimiento.setFechaMovimiento(rs.getDate("Fecha Movimiento"));
				movimiento.setImporte(rs.getBigDecimal("Importe"));
	            movimiento.setImportePostMovimiento(rs.getBigDecimal("Saldo Total")); //Saldo Post Movimiento
				movimiento.setDetalle(rs.getString("Detalle Concepto"));

				TipoMovimiento tipoM = new TipoMovimiento();
				tipoM.setId(rs.getInt("ID_TipoMovimiento"));
				tipoM.setNombre(rs.getString("Nombre Tipo Movimiento"));

				movimiento.setTipoMovimiento(tipoM);

				listaMovimientos.add(movimiento);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaMovimientos;
	} 
	
}
