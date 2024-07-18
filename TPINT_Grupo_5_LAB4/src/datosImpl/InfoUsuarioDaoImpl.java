package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import datos.InfoUsuarioDao;
import datos.UsuarioDao;
import entidad.InfoUsuario;
import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;
import entidad.Usuario;

public class InfoUsuarioDaoImpl  implements InfoUsuarioDao {
	private static final String ObtenerInfo = "SELECT " + 
            "iu.DNI, " + 
            "iu.UsuarioCliente, " + 
            "u.Contraseña, " + 
            "u.EsAdmin, " + 
            "iu.CUIL, " + 
            "iu.Nombre, " + 
            "iu.Apellido, " + 
            "iu.Sexo, " + 
            "p.ID_Nacionalidad, " + 
            "p.Pais, " + 
            "iu.FechaNacimiento, " + 
            "prov.ID_Provincia, " + 
            "prov.Provincia, " + 
            "l.ID_Localidad, " + 
            "l.Localidad, " + 
            "iu.Direccion, " + 
            "iu.Mail, " +
            "iu.Telefono1, " +
            "iu.Telefono2, " +
            "iu.Estado " + 
            "FROM " + 
            "InfoUsuarios iu " + 
            "INNER JOIN usuarios u ON iu.UsuarioCliente = u.Usuario " + 
            "INNER JOIN paises p ON iu.ID_Nacionalidad = p.ID_Nacionalidad " + 
            "INNER JOIN provincias prov ON iu.ID_Provincia = prov.ID_Provincia " + 
            "INNER JOIN localidades l ON iu.ID_Provincia = l.ID_Provincia AND iu.ID_Localidad = l.ID_Localidad " + 
            "WHERE " + 
            "iu.UsuarioCliente = ? AND iu.Estado = 1";
	
	private static final String ObtenerTodosClientes = 
	        "SELECT " + 
	        "iu.DNI, " + 
	        "iu.UsuarioCliente, " + 
	        "u.Contraseña, " + 
	        "u.EsAdmin, " + 
	        "iu.CUIL, " + 
	        "iu.Nombre, " + 
	        "iu.Apellido, " + 
	        "iu.Sexo, " + 
	        "p.ID_Nacionalidad, " + 
	        "p.Pais, " + 
	        "iu.FechaNacimiento, " + 
	        "prov.ID_Provincia, " + 
	        "prov.Provincia, " + 
	        "l.ID_Localidad, " + 
	        "l.Localidad, " + 
	        "iu.Direccion, " + 
	        "iu.Mail, " +
	        "iu.Telefono1, " +
            "iu.Telefono2, " +
	        "iu.Estado " + 
	        "FROM " + 
	        "InfoUsuarios iu " + 
	        "INNER JOIN usuarios u ON iu.UsuarioCliente = u.Usuario " + 
	        "INNER JOIN paises p ON iu.ID_Nacionalidad = p.ID_Nacionalidad " + 
	        "INNER JOIN provincias prov ON iu.ID_Provincia = prov.ID_Provincia " + 
	        "INNER JOIN localidades l ON iu.ID_Provincia = l.ID_Provincia AND iu.ID_Localidad = l.ID_Localidad " + 
	        "WHERE u.EsAdmin = 0  AND iu.Estado = 1";

	private static final String ObtenerTodosAdmins = 
	        "SELECT " + 
	        "iu.DNI, " + 
	        "iu.UsuarioCliente, " + 
	        "u.Contraseña, " + 
	        "u.EsAdmin, " + 
	        "iu.CUIL, " + 
	        "iu.Nombre, " + 
	        "iu.Apellido, " + 
	        "iu.Sexo, " + 
	        "p.ID_Nacionalidad, " + 
	        "p.Pais, " + 
	        "iu.FechaNacimiento, " + 
	        "prov.ID_Provincia, " + 
	        "prov.Provincia, " + 
	        "l.ID_Localidad, " + 
	        "l.Localidad, " + 
	        "iu.Direccion, " + 
	        "iu.Mail, " +
	        "iu.Telefono1, " +
            "iu.Telefono2, " +
	        "iu.Estado " + 
	        "FROM " + 
	        "InfoUsuarios iu " + 
	        "INNER JOIN usuarios u ON iu.UsuarioCliente = u.Usuario " + 
	        "INNER JOIN paises p ON iu.ID_Nacionalidad = p.ID_Nacionalidad " + 
	        "INNER JOIN provincias prov ON iu.ID_Provincia = prov.ID_Provincia " + 
	        "INNER JOIN localidades l ON iu.ID_Provincia = l.ID_Provincia AND iu.ID_Localidad = l.ID_Localidad " + 
	        "WHERE u.EsAdmin = 1  AND iu.Estado = 1";

	private static final String INSERT_CLIENTE = "INSERT INTO InfoUsuarios"
			+ "(DNI, "
			+ "UsuarioCliente, "
			+ "CUIL, "
			+ "Nombre, "
			+ "Apellido, "
			+ "Sexo, "
			+ "ID_Nacionalidad, "
			+ "FechaNacimiento, "
			+ "ID_Provincia, "
			+ "ID_Localidad, "
			+ "Direccion, "
			+ "Mail, " 
			+ "Telefono1, " 
            + "Telefono2, " 
			+ "Estado) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
	
	private static final String EXISTE_DNI = "SELECT DNI FROM InfoUsuarios WHERE DNI = ? AND Estado = 1";
	private static final String EXISTE_CUIL = "SELECT CUIL FROM InfoUsuarios WHERE CUIL = ?";	
	private static final String UPDATE_CLIENTE_ESTADO = "UPDATE infousuarios SET Estado = 0 WHERE dni = ?";
	private static final String UPDATE_CLIENTE = "UPDATE infousuarios SET CUIL = ?, Nombre = ?, Apellido = ?, Sexo = ?, ID_Nacionalidad = ?, FechaNacimiento = ?, ID_Provincia = ?, ID_Localidad = ?, Direccion = ?, Mail = ?, Telefono1 = ?, Telefono2 = ?, Estado = 1 WHERE DNI = ?";
	private static final String REPORTES=
			"SELECT" + 
					"    (SELECT COUNT(*) " + 
					"     FROM InfoUsuarios iu " + 
					"     JOIN usuarios u ON iu.UsuarioCliente = u.Usuario " + 
					"     WHERE iu.Estado = 1 AND u.EsAdmin = 0) AS Clientes_Activos," + 
					"    (SELECT COUNT(*) " + 
					"     FROM InfoUsuarios iu \r\n" + 
					"     JOIN usuarios u ON iu.UsuarioCliente = u.Usuario " + 
					"     WHERE iu.Estado = 0 AND u.EsAdmin = 0) AS Clientes_Inactivos," + 
					"    (SELECT COUNT(*) " + 
					"     FROM cuentas \r\n" + 
					"     WHERE idTipoCuenta = 2 AND Estado = 1) AS Cantidad_Caja_Ahorros," + 
					"    (SELECT COUNT(*) " + 
					"     FROM cuentas \r\n" + 
					"     WHERE idTipoCuenta = 1 AND Estado = 1) AS Cantidad_Cuentas_Corrientes," + 
					"    (SELECT COUNT(*) " + 
					"     FROM prestamos " + 
					"     WHERE EstadoSolicitud = 1) AS Prestamos_Aprobados," + 
					"    (SELECT SUM(ImportePedido) " + 
					"     FROM prestamos WHERE EstadoSolicitud = 1) AS Monto_Total_Prestado_Aprobado," +
					"    (SELECT SUM(Saldo) " + 
					"     FROM cuentas) AS Saldo_Total_Clientes," + 
					"    (SELECT COUNT(*) " + 
					"     FROM cuentas " + 
					"     WHERE FechaCreacion >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)) AS Nuevas_Cuentas_Abiertas," + 
					"    (SELECT COUNT(*) " + 
					"     FROM prestamos " + 
					"     WHERE EstadoSolicitud = 2) AS Prestamos_Pendientes_Aprobacion," + 
					"    (SELECT COUNT(*) " + 
					"     FROM transferencias) AS Transferencias_Realizadas;";
		
	public ArrayList<String> obtener_Todas_LasStats() {
        ArrayList<String> datos = new ArrayList<>();
        
        try {
 
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = Conexion.getConexion().getSQLConexion();

            String consultaSQL = REPORTES;
            

            PreparedStatement pst = conexion.prepareStatement(consultaSQL);
            

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                datos.add(rs.getString("Clientes_Activos"));
                datos.add(rs.getString("Clientes_Inactivos"));
                datos.add(rs.getString("Cantidad_Caja_Ahorros"));
                datos.add(rs.getString("Cantidad_Cuentas_Corrientes"));
                datos.add(rs.getString("Prestamos_Aprobados"));
                datos.add(rs.getString("Monto_Total_Prestado_Aprobado"));
                datos.add(rs.getString("Saldo_Total_Clientes"));
                datos.add(rs.getString("Nuevas_Cuentas_Abiertas"));
                datos.add(rs.getString("Prestamos_Pendientes_Aprobacion"));
                datos.add(rs.getString("Transferencias_Realizadas"));
            }
            
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        return datos;
    }
    
    
	@Override
	public InfoUsuario obtener_Un_InfoUsuario(String Usuario) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		InfoUsuario infoUsuario = new InfoUsuario();

		try
		{
			
		    pst = conexion.prepareStatement(ObtenerInfo);	
		    pst.setString(1, Usuario);	
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
	            
				infoUsuario.setDni(rs.getInt("DNI"));
	            
	            Usuario usu = new Usuario();
	            usu.setUsuario(rs.getString("UsuarioCliente"));
	            usu.setContraseña(rs.getString("Contraseña"));
	            usu.setEsAdmin(rs.getBoolean("EsAdmin"));
	            
	            infoUsuario.setUsuario(usu);
	            infoUsuario.setCuil(rs.getString("CUIL"));
	            infoUsuario.setNombre(rs.getString("Nombre"));
	            infoUsuario.setApellido(rs.getString("Apellido"));
	            infoUsuario.setSexo(rs.getString("Sexo"));
	            
	            Pais pais = new Pais();
	            pais.setID_Nacionalidad(rs.getInt("ID_Nacionalidad"));
	            pais.setPais(rs.getString("Pais"));
	            
	            infoUsuario.setNacionalidad(pais);
	            infoUsuario.setFechaNacimiento(rs.getDate("FechaNacimiento"));
	            
	            Provincia provincia = new Provincia();
	            provincia.setID_Provincia(rs.getInt("ID_Provincia"));
	            provincia.setProvincia(rs.getString("Provincia"));
	            
	            infoUsuario.setProvincia(provincia);
	            
	            Localidad localidad = new Localidad();
	            localidad.setID_Localidad(rs.getInt("ID_Localidad"));
	            localidad.setLocalidad(rs.getString("Localidad"));
                localidad.setProvincia(provincia);
                
	            infoUsuario.setLocalidad(localidad);
	            infoUsuario.setDireccion(rs.getString("Direccion"));
	            infoUsuario.setMail(rs.getString("Mail"));
	            infoUsuario.setTelefono1(rs.getString("Telefono1"));
	            
	            String telefono2 = rs.getString("Telefono2");
	            if (rs.wasNull()) {
	                infoUsuario.setTelefono2(null);
	            } else {
	                infoUsuario.setTelefono2(telefono2);
	            }
	            
	            infoUsuario.setEstado(rs.getBoolean("Estado"));
	        }			
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return infoUsuario;
	}

	
	@Override
	public ArrayList<InfoUsuario> obtenet_Todos_InfoUsuarios() {
		 
		    try {
		        Class.forName("com.mysql.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		    
			ArrayList<InfoUsuario> listaInfoUsuarios = new ArrayList<>();
			PreparedStatement pst;
			Connection conexion = Conexion.getConexion().getSQLConexion();
		    ResultSet rs = null;
		    
		    try {
		        pst = conexion.prepareStatement(ObtenerTodosClientes);
		        rs = pst.executeQuery();
		        
		        while (rs.next()) {
		            InfoUsuario infoUsuario = new InfoUsuario();
		            
		            infoUsuario.setDni(rs.getInt("DNI"));
		            
		            Usuario usu = new Usuario();
		            usu.setUsuario(rs.getString("UsuarioCliente"));
		            usu.setContraseña(rs.getString("Contraseña"));
		            usu.setEsAdmin(rs.getBoolean("EsAdmin"));
		            
		            infoUsuario.setUsuario(usu);
		            infoUsuario.setCuil(rs.getString("CUIL"));
		            infoUsuario.setNombre(rs.getString("Nombre"));
		            infoUsuario.setApellido(rs.getString("Apellido"));
		            infoUsuario.setSexo(rs.getString("Sexo"));
		            
		            Pais pais = new Pais();
		            pais.setID_Nacionalidad(rs.getInt("ID_Nacionalidad"));
		            pais.setPais(rs.getString("Pais"));
		            
		            infoUsuario.setNacionalidad(pais);
		            infoUsuario.setFechaNacimiento(rs.getDate("FechaNacimiento"));
		            
		            Provincia provincia = new Provincia();
		            provincia.setID_Provincia(rs.getInt("ID_Provincia"));
		            provincia.setProvincia(rs.getString("Provincia"));
		            
		            infoUsuario.setProvincia(provincia);
		            
		            Localidad localidad = new Localidad();
		            localidad.setID_Localidad(rs.getInt("ID_Localidad"));
		            localidad.setLocalidad(rs.getString("Localidad"));
		            localidad.setProvincia(provincia);
		            
		            infoUsuario.setLocalidad(localidad);
		            infoUsuario.setDireccion(rs.getString("Direccion"));
		            infoUsuario.setMail(rs.getString("Mail"));
		            infoUsuario.setTelefono1(rs.getString("Telefono1"));
		            
		            String telefono2 = rs.getString("Telefono2");
		            if (rs.wasNull()) {
		                infoUsuario.setTelefono2(null);
		            } else {
		                infoUsuario.setTelefono2(telefono2);
		            }
		            
		            infoUsuario.setEstado(rs.getBoolean("Estado"));
		            
		            listaInfoUsuarios.add(infoUsuario);
		        }
		    } 
		    catch (SQLException e) 
			{
				e.printStackTrace();
			}
		    
		    return listaInfoUsuarios;
	}

	public ArrayList<InfoUsuario> obtener_Todos_InfoUsuariosAdmins() {
		 
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
		ArrayList<InfoUsuario> listaInfoUsuarios = new ArrayList<>();
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
	    ResultSet rs = null;
	    
	    try {
	        pst = conexion.prepareStatement(ObtenerTodosAdmins);
	        rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            InfoUsuario infoUsuario = new InfoUsuario();
	            
	            infoUsuario.setDni(rs.getInt("DNI"));
	            
	            Usuario usu = new Usuario();
	            usu.setUsuario(rs.getString("UsuarioCliente"));
	            usu.setContraseña(rs.getString("Contraseña"));
	            usu.setEsAdmin(rs.getBoolean("EsAdmin"));
	            
	            infoUsuario.setUsuario(usu);
	            infoUsuario.setCuil(rs.getString("CUIL"));
	            infoUsuario.setNombre(rs.getString("Nombre"));
	            infoUsuario.setApellido(rs.getString("Apellido"));
	            infoUsuario.setSexo(rs.getString("Sexo"));
	            
	            Pais pais = new Pais();
	            pais.setID_Nacionalidad(rs.getInt("ID_Nacionalidad"));
	            pais.setPais(rs.getString("Pais"));
	            
	            infoUsuario.setNacionalidad(pais);
	            infoUsuario.setFechaNacimiento(rs.getDate("FechaNacimiento"));
	            
	            Provincia provincia = new Provincia();
	            provincia.setID_Provincia(rs.getInt("ID_Provincia"));
	            provincia.setProvincia(rs.getString("Provincia"));
	            
	            infoUsuario.setProvincia(provincia);
	            
	            Localidad localidad = new Localidad();
	            localidad.setID_Localidad(rs.getInt("ID_Localidad"));
	            localidad.setLocalidad(rs.getString("Localidad"));
	            localidad.setProvincia(provincia);
	            
	            infoUsuario.setLocalidad(localidad);
	            infoUsuario.setDireccion(rs.getString("Direccion"));
	            infoUsuario.setMail(rs.getString("Mail"));
	            infoUsuario.setTelefono1(rs.getString("Telefono1"));
	            
	            String telefono2 = rs.getString("Telefono2");
	            if (rs.wasNull()) {
	                infoUsuario.setTelefono2(null);
	            } else {
	                infoUsuario.setTelefono2(telefono2);
	            }
	            
	            infoUsuario.setEstado(rs.getBoolean("Estado"));
	            
	            listaInfoUsuarios.add(infoUsuario);
	        }
	    } 
	    catch (SQLException e) 
		{
			e.printStackTrace();
		}
	    
	    return listaInfoUsuarios;
}
	
	@Override
	public boolean agregarInfoUsuario(InfoUsuario infoUsuario) {
		UsuarioDao daoUsuario = new UsuarioDaoImpl();
		Connection conexion = null;
		PreparedStatement pst = null;
		boolean resultado = false;
		
		if (!daoUsuario.AgregarUsuario(infoUsuario.getUsuario())) {
			return resultado;
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = Conexion.getConexion().getSQLConexion();
			pst = conexion.prepareStatement(INSERT_CLIENTE);
			pst.setInt(1, infoUsuario.getDni());
			pst.setString(2, infoUsuario.getUsuario().getUsuario());
			pst.setString(3, infoUsuario.getCuil());
			pst.setString(4, infoUsuario.getNombre());
			pst.setString(5, infoUsuario.getApellido());
			pst.setString(6, infoUsuario.getSexo());
			pst.setInt(7, infoUsuario.getNacionalidad().getID_Nacionalidad());
			pst.setDate(8, infoUsuario.getFechaNacimiento());
			pst.setInt(9, infoUsuario.getProvincia().getID_Provincia());
			pst.setInt(10, infoUsuario.getLocalidad().getID_Localidad());
			pst.setString(11, infoUsuario.getDireccion());
			pst.setString(12, infoUsuario.getMail());
			pst.setString(13, infoUsuario.getTelefono1());
			if (infoUsuario.getTelefono2() != null) {
				pst.setString(14, infoUsuario.getTelefono2());
			} else {
				pst.setNull(14, Types.VARCHAR);
			}
			

			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas > 0) {
				conexion.commit();
				resultado = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
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
	public boolean existeDNI(int dni) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;

		try
		{
			pst = conexion.prepareStatement(EXISTE_DNI);	
			pst.setInt(1, dni);	
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
    public boolean eliminarInfoUsuario(int dni) {
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
            pst = conexion.prepareStatement(UPDATE_CLIENTE_ESTADO);
            pst.setInt(1, dni);

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
    public boolean modificarInfoUsuario(InfoUsuario infoUsuario) {
	    	 try {
	             Class.forName("com.mysql.jdbc.Driver");
	         } catch (ClassNotFoundException e) {
	             e.printStackTrace();
	             return false;
	         }
    	
    	    boolean modificado = false;
    	    Connection conexion = Conexion.getConexion().getSQLConexion();
    	    PreparedStatement pst = null;
    	    
    	    try {
    	        pst = conexion.prepareStatement(UPDATE_CLIENTE);

    	        pst.setString(1, infoUsuario.getCuil());
    	        pst.setString(2, infoUsuario.getNombre());
    	        pst.setString(3, infoUsuario.getApellido());
    	        pst.setString(4, infoUsuario.getSexo());
    	        pst.setInt(5, infoUsuario.getNacionalidad().getID_Nacionalidad());
    	        pst.setDate(6, infoUsuario.getFechaNacimiento());
    	        pst.setInt(7, infoUsuario.getProvincia().getID_Provincia());
    	        pst.setInt(8, infoUsuario.getLocalidad().getID_Localidad());
    	        pst.setString(9, infoUsuario.getDireccion());
    	        pst.setString(10, infoUsuario.getMail());
    	        pst.setString(11, infoUsuario.getTelefono1());
    	        if (infoUsuario.getTelefono2() != null) {
    	            pst.setString(12, infoUsuario.getTelefono2());
    	        } else {
    	            pst.setNull(12, Types.VARCHAR);
    	        }
    	        pst.setInt(13, infoUsuario.getDni());

    	        System.out.println("Consulta SQL: " + pst.toString());

    	        int filasActualizadas = pst.executeUpdate();

    	        if (filasActualizadas > 0) {
    	            conexion.commit();
    	            modificado = true;
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
    	    return modificado;
    }


	@Override
	public boolean existeCUIL(String cuil) {
			
		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		    boolean existe = false;
		    Connection conexion = Conexion.getConexion().getSQLConexion();
		    PreparedStatement pst = null;
		    ResultSet rs = null;

		    try {
		        pst = conexion.prepareStatement(EXISTE_CUIL);
		        pst.setString(1, cuil);
		        rs = pst.executeQuery();

		        if (rs.next()) {
		            existe = true;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } 

		    return existe;
	}

}
