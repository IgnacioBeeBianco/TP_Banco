package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datos.CuentaDao;
import entidad.Cuenta;
import entidad.TipoCuenta;

public class CuentaDaoImpl implements CuentaDao {

	private static final String ObtenerTodasLasCuentas = 
			"SELECT c.CBU, c.NumeroCuenta, c.DNI, c.idTipoCuenta, c.FechaCreacion, c.Saldo, c.Estado, "
			+ "t.Descripcion, "
			+ "CONCAT(i.Nombre, ' ', i.Apellido) AS NombreApellido FROM cuentas c "
			+ "JOIN InfoUsuarios i ON c.DNI = i.DNI "
			+ "JOIN tipodecuentas t ON c.idTipoCuenta = t.idTipoCuenta";

	private static final String InsertarCuenta = "INSERT INTO cuentas (DNI, idTipoCuenta) VALUES (?, ?)";
	
	private static final String ObtenerCuenta =
	        "SELECT c.CBU, c.NumeroCuenta, c.DNI, c.idTipoCuenta, c.FechaCreacion, c.Saldo, c.Estado, " +
	        "t.Descripcion, " +
	        "CONCAT(i.Nombre, ' ', i.Apellido) AS NombreApellido " +
	        "FROM cuentas c " +
	        "JOIN InfoUsuarios i ON c.DNI = i.DNI " +
			"JOIN tipodecuentas t ON c.idTipoCuenta = t.idTipoCuenta " +
	        "WHERE c.CBU = ?";
	
	private static final String ExisteCuenta = "SELECT CBU FROM cuentas WHERE CBU = ?";
	
	private static final String ModificarCuenta =
			"UPDATE cuentas SET "
			+ "idTipoCuenta = ?, "
			+ "FechaCreacion = ?, "
			+ "Saldo = ?, "
			+ "Estado = ? "
			+ "WHERE CBU = ? "
			+ "AND NumeroCuenta = ?";

	private static final String UPDATE_CUENTA_ESTADO = "UPDATE cuentas SET Estado = 0 WHERE CBU = ?";
	
	private static final String ObtenerSig_NumCuenta = "SELECT (MAX(NumeroCuenta) + 1) AS SiguienteNumeroCuenta FROM cuentas";
    private static final String Obtener_Cantidad_DeCuentas_porDNI = "SELECT COUNT(*) AS CantidadCuentas FROM cuentas WHERE DNI = ?";

    private static final String ObtenerCuentasCliente = "SELECT "
    		+ "c.CBU, c.NumeroCuenta,c.Saldo, t.Descripcion FROM cuentas AS c JOIN infousuarios AS iu ON c.DNI = iu.DNI "
    		+ "JOIN tipodecuentas t ON c.idTipoCuenta = t.idTipoCuenta "
    		+ "WHERE UsuarioCliente = ?";
	
	@Override
	public ArrayList<Cuenta> obtenet_Todas_LasCuentas() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet rs = null;
		ArrayList<Cuenta> listaCuentas = new ArrayList<>();

		try {
			pst = conexion.prepareStatement(ObtenerTodasLasCuentas);
			rs = pst.executeQuery();

			while (rs.next()) {
				Cuenta cuenta = new Cuenta();

				cuenta.setCBU(rs.getString("CBU"));
				cuenta.setNumeroCuenta(rs.getLong("NumeroCuenta"));
				cuenta.setDNI(rs.getInt("DNI"));

				TipoCuenta tipo = new TipoCuenta();
				tipo.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
				tipo.setDescripcion(rs.getString("Descripcion"));
				cuenta.setTipoCuenta(tipo);

				cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
				cuenta.setSaldo(rs.getBigDecimal("Saldo"));
				cuenta.setEstado(rs.getBoolean("Estado"));
				cuenta.setNombreyApellido(rs.getString("NombreApellido"));

				listaCuentas.add(cuenta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaCuentas;

	}

	@Override
	public int AgregarCuenta(Cuenta Cuenta) {

		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		;
		int filasInsertadas = 0;

		try {
			pst = conexion.prepareStatement(InsertarCuenta);

			pst.setInt(1, Cuenta.getDNI());
			pst.setInt(2, Cuenta.getTipoCuenta().getIdTipoCuenta());

			filasInsertadas = pst.executeUpdate();

		  } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                if (conexion != null) {
	                    conexion.rollback();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

		return filasInsertadas;

	}
	
	
	public Cuenta obtenerCuenta(String cbu) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		Cuenta cuenta = new Cuenta();

		try
		{
			pst = conexion.prepareStatement(ObtenerCuenta);	
			pst.setString(1, cbu);	
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				cuenta.setDNI(rs.getInt("DNI"));
				cuenta.setNombreyApellido(rs.getString("NombreApellido"));
				cuenta.setCBU(rs.getString("CBU"));
				cuenta.setNumeroCuenta(rs.getLong("NumeroCuenta"));

				TipoCuenta tipo = new TipoCuenta();
				tipo.setIdTipoCuenta(rs.getInt("idTipoCuenta"));
				tipo.setDescripcion(rs.getString("Descripcion"));
				cuenta.setTipoCuenta(tipo);
				cuenta.setFechaCreacion(rs.getDate("FechaCreacion"));
				cuenta.setSaldo(rs.getBigDecimal("Saldo"));
				cuenta.setEstado(rs.getBoolean("Estado"));
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return cuenta;
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		boolean modificado = false;
		
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try
		{
			pst = conexion.prepareStatement(ModificarCuenta);	
			pst.setInt(1, cuenta.getTipoCuenta().getIdTipoCuenta());	
			pst.setDate(2, cuenta.getFechaCreacion());	
			pst.setBigDecimal(3, cuenta.getSaldo());	
			pst.setBoolean(4, cuenta.isEstado());	
			pst.setString(5, cuenta.getCBU());	
			pst.setLong(6, cuenta.getNumeroCuenta());	
			if (pst.executeUpdate() == 1) {
				conexion.commit();
				modificado = true;
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return modificado;
	}
	
	
    public boolean eliminarCuenta(String cbu) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        Connection conexion = null;
        PreparedStatement pst = null;
        boolean resultado = false;
        
        try {
            conexion = Conexion.getConexion().getSQLConexion();
            pst = conexion.prepareStatement(UPDATE_CUENTA_ESTADO);
            pst.setString(1, cbu);

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                resultado = true;
            }
            

            conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return resultado;
    }

	@Override
	public int obtenerSiguienteNumeroDeCuenta() {
		int siguienteNumeroCuenta = 0;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();

	    try {
	        pst = conexion.prepareStatement(ObtenerSig_NumCuenta);
	        rs = pst.executeQuery();

	        if (rs.next()) {
	            siguienteNumeroCuenta = rs.getInt("SiguienteNumeroCuenta");
	        }
	    }catch (SQLException e) 
		{
			e.printStackTrace();
		}

	    return siguienteNumeroCuenta;	
	}

    @Override
    public boolean TieneMasDe_3Cuentas(int DNI) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        boolean tieneMasDe3Cuentas = false;

        try {
            pst = conexion.prepareStatement(Obtener_Cantidad_DeCuentas_porDNI);
            pst.setInt(1, DNI);
            rs = pst.executeQuery();

            if (rs.next()) {
                int cantidadCuentas = rs.getInt("CantidadCuentas");
                if (cantidadCuentas >= 3) {
                    tieneMasDe3Cuentas = true;
                }
            }
        }catch (SQLException e) 
		{
			e.printStackTrace();
		}

        return tieneMasDe3Cuentas;
    }

	@Override
	public boolean existeCuenta(String cbu) {
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		try
		{
			pst = conexion.prepareStatement(ExisteCuenta);	
			pst.setString(1, cbu);	
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				existe = true;
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return existe;
	}

	@Override
	public List<Cuenta> obtenerCuentasCliente(String usuario) {
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ArrayList<Cuenta> lista = new ArrayList<Cuenta>();
		PreparedStatement pst;
		try
		{
			pst = conexion.prepareStatement(ObtenerCuentasCliente);	
			pst.setString(1, usuario);	
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				Cuenta cuenta = new Cuenta();
				cuenta.setCBU(rs.getString("CBU"));
				cuenta.setNumeroCuenta(rs.getLong("NumeroCuenta"));
				cuenta.setSaldo(rs.getBigDecimal("Saldo"));
				TipoCuenta tipo = new TipoCuenta();
				tipo.setDescripcion(rs.getString("Descripcion"));
				cuenta.setTipoCuenta(tipo);
				
				lista.add(cuenta);
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return lista;
	}
}