package negocioImpl;
import entidad.Transferencia;
import datos.TransferenciaDao;
import datosImpl.TransferenciaDaoImpl;
import negocio.TransferenciaNegocio;

public class TransferenciaNegocioImpl implements TransferenciaNegocio{
	TransferenciaDao tDao = new TransferenciaDaoImpl();
	public int AgregarTransferencia(Transferencia trans)
	{
		int filas = tDao.AgregarTransferencia(trans);
		return filas;
	};
}
