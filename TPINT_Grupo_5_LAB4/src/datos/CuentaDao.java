package datos;

import java.util.ArrayList;
import java.util.List;

import entidad.Cuenta;


public interface CuentaDao {
	public ArrayList<Cuenta> obtenet_Todas_LasCuentas();
    public int AgregarCuenta(Cuenta Cuenta);
	public Cuenta obtenerCuenta(String cbu);
	public boolean modificarCuenta(Cuenta cuenta);
	public boolean eliminarCuenta(String cbu);
	public int obtenerSiguienteNumeroDeCuenta();
	public boolean   TieneMasDe_3Cuentas (int DNI);
	boolean existeCuenta(String cbu);
	public List<Cuenta> obtenerCuentasCliente(String usuario);
}
