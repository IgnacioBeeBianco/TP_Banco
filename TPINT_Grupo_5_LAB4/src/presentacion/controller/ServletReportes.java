package presentacion.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.MovimientoNegocio;
import negocio.TipoMovimientoNegocio;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.TipoMovimientoNegocioImpl;
import negocio.InfoUsuarioNegocio;
import negocioImpl.InfoUsuarioNegocioImpl;

/**
 * Servlet implementation class ServletReportes
 */
@WebServlet("/ServletReportes")
public class ServletReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	InfoUsuarioNegocio IUneg = new InfoUsuarioNegocioImpl();
	MovimientoNegocio Mneg = new MovimientoNegocioImpl();
	TipoMovimientoNegocio tmn = new TipoMovimientoNegocioImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletReportes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("Param") != null) {
			String opcion = request.getParameter("Param").toString();

			switch (opcion) {
			case "ListarStats": {
				request.setAttribute("listarStat", IUneg.obtener_Todas_LasStats());
				request.setAttribute("listarMovs", Mneg.obtener_Todos_LosMovimientos());
				request.setAttribute("listaTipoMovimientos", tmn.obtener_Todos_LosTipoDeMovimientos());
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/A_Reportes.jsp");
				dispatcher.forward(request, response);
				break;
			}
			default:
				break;
			}

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		
		
		
	}

}
