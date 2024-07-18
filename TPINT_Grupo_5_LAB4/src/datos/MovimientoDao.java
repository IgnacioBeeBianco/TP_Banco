package datos;

import java.util.ArrayList;

import entidad.Movimiento;

public interface MovimientoDao {
	public ArrayList<Movimiento> obtener_Todos_LosMovimientos();
	public ArrayList<Movimiento> generarReporteMovimientos(String fechaInicio, String fechaFin, String tipoMovimiento, float saldoMin, float saldoMax);
	public ArrayList<Movimiento> obtenerMovimientosNumCuenta(int nroCuenta);
}
