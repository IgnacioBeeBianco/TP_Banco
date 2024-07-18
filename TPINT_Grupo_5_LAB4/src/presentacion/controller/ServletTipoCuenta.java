package presentacion.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;


@WebServlet("/ServletTipoCuenta")
public class ServletTipoCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    TipoCuentaNegocio NegocioTipoCuenta = new TipoCuentaNegocioImpl();
    CuentaNegocio NegocioCuenta = new CuentaNegocioImpl();
    
    public ServletTipoCuenta() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("Param") != null) {
			String opcion = request.getParameter("Param").toString();

			switch (opcion) {
			case "DesplegableTipoCuentaYSigNumCuenta": {
				
				int siguienteNumeroCuenta = NegocioCuenta.obtenerSiguienteNumeroDeCuenta();
				request.setAttribute("Siguiente_Num_Cuenta", siguienteNumeroCuenta);
				
				request.setAttribute("listaTipoCuentas", NegocioTipoCuenta.obtenet_Todos_LosTipoDeCuentas());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AsignarCuenta.jsp");
				dispatcher.forward(request, response);
				break;
			}
			default:
				break;
			}
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
