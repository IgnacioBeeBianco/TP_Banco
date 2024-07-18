package negocio;

import java.util.ArrayList;

import entidad.PagoCuota;

public interface PagoCuotaNegocio {
	
	public ArrayList<PagoCuota> Obtener_Las_Cuotas_Por_IDprestamo(int ID_prestamo);
	public boolean Realizar_PagoCuota(PagoCuota Cuota);

}
