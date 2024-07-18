package negocioImpl;

import java.util.ArrayList;

import datos.PagoCuotaDao;
import datosImpl.PagoCuotaDaoImpl;
import entidad.PagoCuota;
import negocio.PagoCuotaNegocio;

public class PagoCuotaNegocioImpl implements PagoCuotaNegocio {

	PagoCuotaDao DaoCuotas = new PagoCuotaDaoImpl();
	
	@Override
	public ArrayList<PagoCuota> Obtener_Las_Cuotas_Por_IDprestamo(int ID_prestamo) {
		
		ArrayList<PagoCuota> Lista = new ArrayList<>();
		Lista = DaoCuotas.Obtener_Las_Cuotas_Por_IDprestamo(ID_prestamo);
	    return Lista;

	}

	@Override
	public boolean Realizar_PagoCuota(PagoCuota Cuota) {
		
		return DaoCuotas.Realizar_PagoCuota(Cuota);
		
	}

}
