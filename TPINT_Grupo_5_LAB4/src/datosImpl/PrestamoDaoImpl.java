package datosImpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datos.PrestamoDao;
import entidad.InfoUsuario;
import entidad.Prestamo;

public class PrestamoDaoImpl implements PrestamoDao {
	
	private static final String ObtenerSolicitudes = "SELECT " +
            "ID_Prestamo AS id, " +
            "DNI_Cliente AS dni, " +
            "FechaSolicitud AS fecha, " +
            "ImportePedido AS pedido, " +
            "PlazoMeses AS meses, " +
            "CantidadCuotas AS cuotas, " +
            "ImportePagar AS importe, " +
            "MontoCuota AS monto_cuota, " +
            "EstadoSolicitud, " +
            "EstadoPrestamo, " +
            "CBU_Deposito AS cbu_depositar " +
            "FROM prestamos " +
            "WHERE EstadoSolicitud = 2 " +
            "AND EstadoPrestamo = 0";
	
	private static final String PedirPrestamo = 
			"INSERT INTO prestamos("
			+ "DNI_Cliente, "
			+ "FechaSolicitud, "
			+ "ImportePedido, "
			+ "PlazoMeses, "
			+ "CantidadCuotas, "
			+ "ImportePagar, "
			+ "MontoCuota, "
			+ "EstadoSolicitud, "
			+ "EstadoPrestamo, "
			+ "CBU_Deposito) "
			+ "VALUES (?, NOW(), ?, ?, ?, ?, ?, 2, 0, ?)"; 
	
	private static final String RechazarPrestamo =
			"UPDATE prestamos SET EstadoSolicitud = 0 WHERE ID_Prestamo = ?";

	private static final String AceptarPrestamo =
			"UPDATE prestamos SET EstadoSolicitud = 1 WHERE ID_Prestamo = ?";
	
	private static final String ObtenerPrestamosPorDNI = "SELECT " +
		    "ID_Prestamo AS id, " +
		    "DNI_Cliente AS dni, " +
		    "FechaSolicitud AS fecha, " +
		    "ImportePedido AS pedido, " +
		    "PlazoMeses AS meses, " +
		    "CantidadCuotas AS cuotas, " +
		    "ImportePagar AS importe, " +
		    "MontoCuota AS monto_cuota, " +
		    "EstadoSolicitud, " +
		    "EstadoPrestamo, " +
		    "CBU_Deposito AS cbu_depositar " +
		    "FROM prestamos " +
		    "WHERE DNI_Cliente = ?";


	@Override
	public List<Prestamo> obtenerSolicitudes() {
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ArrayList<Prestamo> lista = new ArrayList<Prestamo>();
		PreparedStatement pst;

	    try {
	        pst = conexion.prepareStatement(ObtenerSolicitudes);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	Prestamo solicitud = new Prestamo();
	        	
	        	InfoUsuario cliente = new InfoUsuario();
	        	cliente.setDni(rs.getInt("dni"));
	        	solicitud.setCliente(cliente);
	        	
	        	Date fecha = new Date(rs.getDate("fecha").getTime());
	        	solicitud.setFechaSolicitud(fecha);

	        	solicitud.setId(rs.getInt("id"));
	        	solicitud.setImportePedido(rs.getBigDecimal("pedido"));
	        	solicitud.setImportePagar(rs.getBigDecimal("importe"));
	        	solicitud.setCantidadCuotas(rs.getInt("cuotas"));
	        	solicitud.setCbuDeposito(rs.getString("cbu_depositar"));
	        	solicitud.setPlazoMeses(rs.getInt("meses"));
	        	solicitud.setMontoCuota(rs.getBigDecimal("monto_cuota"));

	        	lista.add(solicitud);
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return lista;
	}

	@Override
	public boolean pedirPrestamo(Prestamo prestamo) {
		boolean resultado = false;
		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement pst = conexion.prepareStatement(PedirPrestamo);

			pst.setInt(1, prestamo.getCliente().getDni());
			pst.setBigDecimal(2, prestamo.getImportePedido());
			pst.setInt(3, prestamo.getPlazoMeses());
			pst.setInt(4, prestamo.getCantidadCuotas());
			pst.setBigDecimal(5, prestamo.getImportePagar());
			pst.setBigDecimal(6, prestamo.getMontoCuota());
			pst.setString(7, prestamo.getCbuDeposito());

			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas > 0) {
				conexion.commit();
				resultado = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultado;
	}

	public boolean aceptarPrestamo(int id) {
		boolean resultado = false;
		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement pst = conexion.prepareStatement(AceptarPrestamo);

			pst.setInt(1, id);

			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas > 0) {
				conexion.commit();
				resultado = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultado;
	}

	public boolean rechazarPrestamo(int id) {
		boolean resultado = false;
		try {
			Connection conexion = Conexion.getConexion().getSQLConexion();
			PreparedStatement pst = conexion.prepareStatement(RechazarPrestamo);

			pst.setInt(1, id);

			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas > 0) {
				conexion.commit();
				resultado = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultado;
	}

	@Override
	public ArrayList<Prestamo> Obtener_Prestamos_PorDni(int dni) {
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Connection conexion = Conexion.getConexion().getSQLConexion();
	    ArrayList<Prestamo> lista = new ArrayList<>();
	    PreparedStatement pst;

	    try {
	        pst = conexion.prepareStatement(ObtenerPrestamosPorDNI);
	        pst.setInt(1, dni);
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            Prestamo prestamo = new Prestamo();

	            InfoUsuario cliente = new InfoUsuario();
	            cliente.setDni(rs.getInt("dni"));
	            prestamo.setCliente(cliente);

	            prestamo.setFechaSolicitud(rs.getDate("fecha"));
	            
	            prestamo.setId(rs.getInt("id"));
	            prestamo.setImportePedido(rs.getBigDecimal("pedido"));
	            prestamo.setPlazoMeses(rs.getInt("meses"));
	            prestamo.setCantidadCuotas(rs.getInt("cuotas"));
	            prestamo.setImportePagar(rs.getBigDecimal("importe"));
	            prestamo.setMontoCuota(rs.getBigDecimal("monto_cuota"));
	            prestamo.setEstadoSolicitud(rs.getInt("EstadoSolicitud"));
	            prestamo.setEstadoPrestamo(rs.getInt("EstadoPrestamo"));
	            prestamo.setCbuDeposito(rs.getString("cbu_depositar"));

	            lista.add(prestamo);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	    return lista;
	}





}
