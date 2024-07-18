package negocioImpl;

import java.util.ArrayList;

import datos.TipoCuentaDao;
import datosImpl.TipoCuentaDaoImpl;
import entidad.TipoCuenta;
import negocio.TipoCuentaNegocio;

public class TipoCuentaNegocioImpl implements TipoCuentaNegocio{

	TipoCuentaDao TCdao = new TipoCuentaDaoImpl();
	
	@Override
	public ArrayList<TipoCuenta> obtenet_Todos_LosTipoDeCuentas() {
		
		ArrayList<TipoCuenta> lista = new ArrayList<>();
		lista = TCdao.obtenerTipoCuenta();
	    return lista;
	}

}
