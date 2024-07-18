package negocioImpl;

import java.util.ArrayList;

import datos.TipoMovimientoDao;
import datosImpl.TipoMovimientoDaoImpl;
import entidad.TipoMovimiento;
import negocio.TipoMovimientoNegocio;

public class TipoMovimientoNegocioImpl implements TipoMovimientoNegocio{
	
	TipoMovimientoDao tpd = new TipoMovimientoDaoImpl();
	
	public ArrayList<TipoMovimiento> obtener_Todos_LosTipoDeMovimientos() {
		
		ArrayList<TipoMovimiento> listaM = new ArrayList<>();
		listaM = tpd.obtenerTipoMovimiento();
	    return listaM;
	}

}
