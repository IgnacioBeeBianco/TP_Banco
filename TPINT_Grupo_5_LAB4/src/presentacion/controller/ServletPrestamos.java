package presentacion.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cuenta;
import entidad.InfoUsuario;
import entidad.Prestamo;
import entidad.Usuario;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;

/**
 * Servlet implementation class ServletPrestamos
 */
@WebServlet("/ServletPrestamos")
public class ServletPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrestamoNegocio neg = new PrestamoNegocioImpl();
       
    public ServletPrestamos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
    	if (request.getParameter("solicitudes") != null) {
			request.setAttribute("solicitudes", neg.obtenerSolicitudes());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_SolicitudPrestamos.jsp");
			dispatcher.forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		
		if (request.getParameter("btnPedirPrestamo") != null) {
			String pagina = "/C_PedirPrestamo.jsp";
			HttpSession session = request.getSession();
			String usuario = (String) session.getAttribute("usuario");

			CuentaNegocio NegocioCuenta = new CuentaNegocioImpl(); 
			List<Cuenta> cuentas = NegocioCuenta.obtenerCuentasCliente(usuario);
			request.setAttribute("cuentasCliente", cuentas);

			Usuario user = new Usuario();
			user.setUsuario(usuario);

			InfoUsuario cliente = new InfoUsuario();
			cliente.setUsuario(user);
			
			Double interes = 1.3;

			Prestamo prestamo = new Prestamo();
			try {
				int cantCuotas = Integer.parseInt(request.getParameter("cantidad_cuotas"));
				prestamo.setCantidadCuotas(cantCuotas);
				prestamo.setPlazoMeses(cantCuotas);
				Double montoPedido = Double.parseDouble(request.getParameter("monto"));
				Double montoPagar = montoPedido * interes;
				Double montoCuota = montoPagar / cantCuotas;
				prestamo.setImportePedido(BigDecimal.valueOf(montoPedido));
				prestamo.setImportePagar(BigDecimal.valueOf(montoPagar));
				prestamo.setMontoCuota(BigDecimal.valueOf(montoCuota));
				prestamo.setCbuDeposito(request.getParameter("cuenta_Origen"));
				prestamo.setCliente(cliente);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("MensajeError", "Inputs inválidos.");
				RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
				dispatcher.forward(request, response);
				return;
			}
			
			if (neg.pedirPrestamo(prestamo)) {
				request.setAttribute("MensajeExito", "Prestamo en espera de ser aceptado.");
			} else {
				request.setAttribute("MensajeError", "No se pudo pedir el prestamo, intente más tarde.");
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		}
		
		if (request.getParameter("aceptar") != null) {
			String pagina = "/A_SolicitudPrestamos.jsp";
			
			int id;
			try {
				id = Integer.parseInt(request.getParameter("id"));
			}
			catch (Exception e) {
				request.setAttribute("MensajeError", "ID de prestamo inválida.");
				request.setAttribute("solicitudes", neg.obtenerSolicitudes());
				RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
				dispatcher.forward(request, response);
				return;
			}
			
			if (neg.aceptarPrestamo(id)) {
				request.setAttribute("MensajeExito", "Prestamo aceptado con éxito.");
			} else {
				request.setAttribute("MensajeError", "No se pudo aceptar el prestamo.");
			}

			request.setAttribute("solicitudes", neg.obtenerSolicitudes());
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		}
		
		if (request.getParameter("rechazar") != null) {
			String pagina = "/A_SolicitudPrestamos.jsp";

			int id;
			try {
				id = Integer.parseInt(request.getParameter("id"));
			}
			catch (Exception e) {
				request.setAttribute("MensajeError", "ID de prestamo inválida.");
				request.setAttribute("solicitudes", neg.obtenerSolicitudes());
				RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
				dispatcher.forward(request, response);
				return;
			}
			
			if (neg.rechazarPrestamo(id)) {
				request.setAttribute("MensajeExito", "Prestamo rechazado con éxito.");
			} else {
				request.setAttribute("MensajeError", "No se pudo aceptar el prestamo.");
			}

			request.setAttribute("solicitudes", neg.obtenerSolicitudes());
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		}
	}

}
