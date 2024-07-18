package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import datos.CuentaDao;
import datosImpl.CuentaDaoImpl;
import entidad.Cuenta;
import excepciones.ClienteNoExiste;
import excepciones.CuentaNoExiste;
import negocio.CuentaNegocio;
import negocio.InfoUsuarioNegocio;

public class CuentaNegocioImpl implements CuentaNegocio {
	
	CuentaDao Cdao = new CuentaDaoImpl();

	@Override
	public ArrayList<Cuenta> obtenet_Todos_LasCuentas() {
		
		ArrayList<Cuenta> listaCuentas = new ArrayList<>();
		listaCuentas = Cdao.obtenet_Todas_LasCuentas();
	    return listaCuentas;
	}

	@Override
	public int AsignarCuenta(Cuenta Cuenta) {
		int filas = Cdao.AgregarCuenta(Cuenta);
		return filas;
	}

	public Cuenta obtenerCuenta(String cbu) throws CuentaNoExiste {
		if (existeCuenta(cbu)) {
			return Cdao.obtenerCuenta(cbu);
		}
		throw new CuentaNoExiste();
	}
	

	public boolean eliminarCuenta(String cbu) {
		return Cdao.eliminarCuenta(cbu);
	}
	
	
	@Override
	public boolean modificarCuenta(Cuenta cuenta) throws ClienteNoExiste {
		InfoUsuarioNegocio iun = new InfoUsuarioNegocioImpl();
		if (!iun.existe_ese_DNI(cuenta.getDNI())) {
			throw new ClienteNoExiste();
		}

		return Cdao.modificarCuenta(cuenta);
	}

	@Override
	public int obtenerSiguienteNumeroDeCuenta() {
		return Cdao.obtenerSiguienteNumeroDeCuenta();
	}

	@Override
	public boolean TieneMasDe_3Cuentas(int DNI) {
		boolean estado = Cdao.TieneMasDe_3Cuentas(DNI);
		return estado;
	}

	@Override
	public boolean existeCuenta(String cbu) {
		return Cdao.existeCuenta(cbu);
	}

	@Override
	public List<Cuenta> obtenerCuentasCliente(String usuario) {
		return Cdao.obtenerCuentasCliente(usuario);
	}
}
