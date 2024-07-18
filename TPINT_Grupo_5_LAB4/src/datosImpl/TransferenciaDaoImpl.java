package datosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import datos.TransferenciaDao;
import entidad.Transferencia;

public class TransferenciaDaoImpl implements TransferenciaDao{
	private static final String INSERT = "INSERT INTO transferencias (CBU_Origen, CBU_Destino, FechaTransferencia, Importe) " +
            "VALUES (?, ?, NOW(), ?)";


public int AgregarTransferencia(Transferencia trans)
{

	PreparedStatement pst = null;
	Connection conexion = Conexion.getConexion().getSQLConexion();
	int filasInsertadas = 0;

	try {
		//
		pst = conexion.prepareStatement(INSERT);
        pst.setString(1,trans.getCbuOrigen().getCBU());
        pst.setString(2,trans.getCbuDestino().getCBU());
        pst.setBigDecimal(3,trans.getImporte());

        filasInsertadas = pst.executeUpdate();
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

	return filasInsertadas;
	
}
}
