package presentacion.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.InfoUsuario;
import entidad.Usuario;
import negocioImpl.InfoUsuarioNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ServletUsuarios")
public class ServletUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletUsuarios() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("btnAceptarLogin") != null) {
			String usuario = request.getParameter("txtusuario");
			String clave = request.getParameter("txtclave");

			UsuarioNegocioImpl Uneg = new UsuarioNegocioImpl();
			Usuario Usuario = new Usuario();
			Usuario.setUsuario(usuario);
			Usuario.setContraseña(clave);

			if (!Uneg.BuscarUsuario(Usuario)) {
				request.setAttribute("errorUsuario", "El usuario ingresado no existe.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Login.jsp");
				dispatcher.forward(request, response);
				return;
			}
			if (!Uneg.VerificarClave(Usuario)) {
				request.setAttribute("errorClave", "La contraseña ingresada es incorrecta.");
				request.setAttribute("usuario", usuario); // Mantener el valor del usuario ingresado
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			InfoUsuarioNegocioImpl IUneg = new InfoUsuarioNegocioImpl();
			InfoUsuario infoUsuario = new InfoUsuario();

			infoUsuario = IUneg.obtener_La_InfoUsuario(Usuario.getUsuario());
			String Nombre = infoUsuario.getNombre();
			String Apellido = infoUsuario.getApellido();
			String NombreCompleto = Nombre + " " + Apellido;
			int DNI = infoUsuario.getDni();

			if (infoUsuario.getUsuario().isEsAdmin()) {
				HttpSession session = request.getSession();
				session.setAttribute("NombreAdmin", NombreCompleto);
				session.setAttribute("usuario", usuario);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Admin.jsp");
				dispatcher.forward(request, response);
				return;
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("NombreCliente", NombreCompleto);
				session.setAttribute("usuario", usuario);
				session.setAttribute("Dni_Cliente", DNI);

				response.sendRedirect("ServletCuentas?cuentasCliente=1");
//				RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Cliente.jsp");
//				dispatcher.forward(request, response);
			}
		}

		if (request.getParameter("btnSalir") != null) {
			HttpSession session = request.getSession();
			session.invalidate();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (request.getParameter("newPassword") != null && request.getParameter("clientUser") != null) {
            String clientUser = request.getParameter("clientUser");
            String newPassword = request.getParameter("newPassword");

            UsuarioNegocioImpl UNI = new UsuarioNegocioImpl();
            boolean passwordActualizada = UNI.actualizarPassword(clientUser, newPassword);

            if (passwordActualizada) {
                response.getWriter().write("Success");
            } else {
                response.getWriter().write("Failure");
            }
        }
		
	}

}
