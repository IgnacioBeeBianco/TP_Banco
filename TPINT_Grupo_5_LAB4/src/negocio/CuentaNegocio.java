package negocio;

import java.util.ArrayList;
import java.util.List;

import entidad.Cuenta;
import excepciones.ClienteNoExiste;
import excepciones.CuentaNoExiste;


public interface CuentaNegocio {
	public Cuenta obtenerCuenta(String cbu) throws CuentaNoExiste;

	public int AsignarCuenta(Cuenta Cuenta) ;
	public boolean eliminarCuenta(String cbu);
	public boolean modificarCuenta(Cuenta cuenta) throws ClienteNoExiste;
	public ArrayList<Cuenta> obtenet_Todos_LasCuentas();

	boolean existeCuenta(String cbu);
	public int obtenerSiguienteNumeroDeCuenta();
    public boolean TieneMasDe_3Cuentas(int DNI);

	public List<Cuenta> obtenerCuentasCliente(String usuario);
}
