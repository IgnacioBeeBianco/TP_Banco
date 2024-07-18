package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import datos.UsuarioDao;
import entidad.Usuario;

public class UsuarioDaoImpl implements UsuarioDao{

	private static final String ExistUsuario = "SELECT * FROM usuarios WHERE Usuario = ?";
	private static final String ExisteClave = "SELECT * FROM usuarios WHERE Usuario = ? and Contraseña = ?";
	private static final String INSERT_USUARIO = "INSERT INTO usuarios(Usuario, Contraseña, EsAdmin) VALUES(?, ?, 0)";
	private static final String Update_PassWord = "UPDATE usuarios SET Contraseña = ? WHERE Usuario = ?";
	private static final String UPDATE_ROL_USUARIO="UPDATE usuarios SET EsAdmin = ? WHERE usuario = ?";

	
	@Override
	public boolean ExisteUsuario(Usuario Usuario) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean Existe = false;

		try
		{
			
		    pst = conexion.prepareStatement(ExistUsuario);	
		    pst.setString(1, Usuario.getUsuario());	
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				Existe = true;
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return Existe;
	}
	
	@Override
	public boolean ExisteNombreUsuario(String nombreUsuario) {
		 try {
		        Class.forName("com.mysql.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }

		    PreparedStatement pst;
		    Connection conexion = Conexion.getConexion().getSQLConexion();
		    boolean existe = false;

		    try {
		        pst = conexion.prepareStatement(ExistUsuario);
		        pst.setString(1, nombreUsuario);
		        ResultSet rs = pst.executeQuery();
		        if (rs.next()) {
		            existe = true;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return existe;
	}


	@Override
	public boolean ClaveCorrecta(Usuario Usuario) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pst;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean Existe = false;

		try
		{
			
		    pst = conexion.prepareStatement(ExisteClave);	
		    pst.setString(1, Usuario.getUsuario());	
		    pst.setString(2, Usuario.getContraseña());	
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				Existe = true;
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return Existe;
	}


	@Override
	public boolean AgregarUsuario(Usuario usuario) {
		Connection conexion = null;
		PreparedStatement pst = null;
		boolean resultado = false;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = Conexion.getConexion().getSQLConexion();
			pst = conexion.prepareStatement(INSERT_USUARIO);
			pst.setString(1, usuario.getUsuario());
			pst.setString(2, usuario.getContraseña());

			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas > 0) {
				resultado = true;
			}
			conexion.commit();
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
	
	public boolean actualizarPassword(String usuario, String newPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PreparedStatement pst = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        boolean resultado = false;

        try {
            pst = conexion.prepareStatement(Update_PassWord);
            pst.setString(1, newPassword);
            pst.setString(2, usuario);
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

	public boolean updateRolInfoUsuario(String usuario, boolean admin) {
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
            pst = conexion.prepareStatement(UPDATE_ROL_USUARIO);
            pst.setBoolean(1, admin);
            pst.setString(2, usuario);
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
}
