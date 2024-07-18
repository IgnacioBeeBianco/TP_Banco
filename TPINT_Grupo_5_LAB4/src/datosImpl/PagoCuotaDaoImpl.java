package datosImpl;

import java.util.ArrayList;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import datos.PagoCuotaDao;
import entidad.PagoCuota;

public class PagoCuotaDaoImpl implements PagoCuotaDao {

	private static final String Obtener_Cuotas_Por_IDPrestamo = "SELECT " +
		    "ID_Prestamo AS PrestamoID, " +
		    "NroCuota AS NumeroCuota, " +
		    "FechaVencimiento AS FechaDeVencimiento, " +
		    "FechaPago AS FechaDePago, " +
		    "CBU_Origen AS CuentaBancariaOrigen, " +
		    "ImportePago AS ImporteDelPago, " +
		    "EstadoCuota AS EstadoDeLaCuota " +
		    "FROM pagoscuotas " +
		    "WHERE ID_Prestamo = ?";
	
	private static final String Pagar_Cuota = 
		    "UPDATE pagoscuotas " +
		    "SET FechaPago = NOW(), " +      //Fecha Actual
		    "    CBU_Origen = ?, " +
		    "    ImportePago = ?, " +
		    "    EstadoCuota = 1 " +        // 1 = Aceptado
		    "WHERE ID_Prestamo = ? AND NroCuota = ?";


	@Override
	public ArrayList<PagoCuota> Obtener_Las_Cuotas_Por_IDprestamo(int ID_prestamo) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conexion = Conexion.getConexion().getSQLConexion();
	    ArrayList<PagoCuota> lista = new ArrayList<PagoCuota>();
	    PreparedStatement pst;

	    try {
	        pst = conexion.prepareStatement(Obtener_Cuotas_Por_IDPrestamo);
	        pst.setInt(1, ID_prestamo);
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            PagoCuota cuota = new PagoCuota();

	            cuota.setIdPrestamo(rs.getInt("PrestamoID"));
	            cuota.setNroCuota(rs.getInt("NumeroCuota"));
	            
	            cuota.setFechaVencimiento(rs.getDate("FechaDeVencimiento"));

	            // Verifica si FechaPago es nulo antes de asignarla
	            if (rs.getDate("FechaDePago") != null) {
	                cuota.setFechaPago(rs.getDate("FechaDePago"));
	            } else {
	                cuota.setFechaPago(null);
	            }            

	            // Verifica si CBU_Origen es nulo antes de asignarla
	            if (rs.getString("CuentaBancariaOrigen") != null) {
	                cuota.setCbuOrigen(rs.getString("CuentaBancariaOrigen"));
	            } else {
	                cuota.setCbuOrigen(""); 
	            }

	            cuota.setImportePago(rs.getBigDecimal("ImporteDelPago"));
	            cuota.setEstadoCuota(rs.getInt("EstadoDeLaCuota"));

	            lista.add(cuota);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    return lista;
	}


    @Override
    public boolean Realizar_PagoCuota(PagoCuota cuota) {
        
    	boolean resultado = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return resultado;
        }

        Connection conexion = Conexion.getConexion().getSQLConexion();
        PreparedStatement pst = null;

        try {
            pst = conexion.prepareStatement(Pagar_Cuota);
            pst.setString(1, cuota.getCbuOrigen());
            pst.setBigDecimal(2, cuota.getImportePago());
            pst.setInt(3, cuota.getIdPrestamo());
            pst.setInt(4, cuota.getNroCuota());
            
            int filasActualizadas = pst.executeUpdate();
            
            if (filasActualizadas > 0) {
            	conexion.commit();
            	resultado = true;
            } 
        } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (conexion != null) {
	                conexion.rollback();
	            }
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    } 
        return resultado;
    
    }
    
    
    
    
    
    
    
}




