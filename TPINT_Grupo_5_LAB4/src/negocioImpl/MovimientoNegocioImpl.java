package negocioImpl;

import java.util.ArrayList;

import datos.MovimientoDao;
import datosImpl.MovimientoDaoImpl;
import entidad.Movimiento;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio{
	MovimientoDao mDao = new MovimientoDaoImpl();

	public ArrayList<Movimiento> obtener_Todos_LosMovimientos() {
		return mDao.obtener_Todos_LosMovimientos();
	}
	
	
	public ArrayList<Movimiento> generarReporteMovimientos(String fechaInicio, String fechaFin, String tipoMovimiento, float saldoMin, float saldoMax) {
		return mDao.generarReporteMovimientos(fechaInicio, fechaFin, tipoMovimiento, saldoMin, saldoMax);
	}
	
	public ArrayList<Movimiento> obtenerMovimientosNumCuenta(int nroCuenta) {
		return mDao.obtenerMovimientosNumCuenta(nroCuenta);
	}
}
