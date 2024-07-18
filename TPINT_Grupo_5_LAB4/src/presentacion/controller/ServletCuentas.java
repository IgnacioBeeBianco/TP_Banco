package presentacion.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
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
import entidad.TipoCuenta;
import excepciones.ClienteNoExiste;
import excepciones.CuentaNoExiste;
import negocio.CuentaNegocio;
import negocio.InfoUsuarioNegocio;
import negocio.TipoCuentaNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.InfoUsuarioNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;

@WebServlet("/ServletCuentas")
public class ServletCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	InfoUsuarioNegocio NegIF = new InfoUsuarioNegocioImpl();
	TipoCuentaNegocio NegocioTipoCuenta = new TipoCuentaNegocioImpl();
	CuentaNegocio NegocioCuenta = new CuentaNegocioImpl();

	public ServletCuentas() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("Param") != null) {
			String opcion = request.getParameter("Param").toString();

			switch (opcion) {
			case "ListarCuentas": {
				request.setAttribute("listaCue", NegocioCuenta.obtenet_Todos_LasCuentas());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
				dispatcher.forward(request, response);
				break;
			}
			default:
				break;
			}
		}

		if (request.getParameter("cuentasCliente") != null) {
			HttpSession session = request.getSession();
			String usuario = (String) session.getAttribute("usuario");
			
			List<Cuenta> cuentas = NegocioCuenta.obtenerCuentasCliente(usuario);
			
			request.setAttribute("cuentasCliente", cuentas);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Cliente.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (request.getParameter("cuentasPrestamo") !=null) {
			HttpSession session = request.getSession();
			String usuario = (String) session.getAttribute("usuario");
			
			List<Cuenta> cuentas = NegocioCuenta.obtenerCuentasCliente(usuario);
			
			request.setAttribute("cuentasCliente", cuentas);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/C_PedirPrestamo.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("btnDelete") != null) {
			System.out.println("Procesando eliminación de cuenta...");
			CuentaNegocio neg = new CuentaNegocioImpl();
			try {
				String cbu = request.getParameter("cbu");

				boolean cuentaEliminada = neg.eliminarCuenta(cbu);

				if (cuentaEliminada) {

					 request.setAttribute("listaCue", neg.obtenet_Todos_LasCuentas());
					 RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
					 dispatcher.forward(request, response);
					 return;
					
				} else {

					request.setAttribute("listaCue", neg.obtenet_Todos_LasCuentas());
					RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
					dispatcher.forward(request, response);
					return;
				}

			} catch (NumberFormatException e) {
				request.setAttribute("MensajeError", "DNI inválido.");
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
			dispatcher.forward(request, response);
		}

		if (request.getParameter("btnCuenta") != null) {
			String cbu = request.getParameter("NumeroCuenta");
			request.setAttribute("Numseleccionado", cbu);
			RequestDispatcher miDispacher = request.getRequestDispatcher("/C_Movimientos.jsp"); // Es el archivo JSP al
																								// que le vamos a enviar
																								// la informacion
			miDispacher.forward(request, response);
		}

		// ASIGNAR CUENTA

		if (request.getParameter("btnAsignarCuenta") != null) {
			String dniStr = request.getParameter("txtDni").trim();
			String idTipoCuentaStr = request.getParameter("tipocuenta").trim();

			// Validar que el DNI no tenga más de 8 caracteres y que sean dígitos
			if (dniStr.length() <= 8 && dniStr.matches("\\d+")) {

				int dni = Integer.parseInt(dniStr);
				int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);

				Cuenta cuenta = new Cuenta();
				cuenta.setDNI(dni);
				cuenta.setTipoCuenta(new TipoCuenta(idTipoCuenta, ""));

				InfoUsuario UsuarioCuenta = new InfoUsuario();
				UsuarioCuenta.setDni(dni);

				RequestDispatcher dispatcher;

				// Verificar si el cliente existe
				if (!NegIF.existe_ese_DNI(UsuarioCuenta.getDni())) {
					request.setAttribute("errorDNI", "El Dni Ingresado no corresponde a un cliente.");

					int siguienteNumeroCuenta = NegocioCuenta.obtenerSiguienteNumeroDeCuenta();
					request.setAttribute("Siguiente_Num_Cuenta", siguienteNumeroCuenta);
					request.setAttribute("listaTipoCuentas", NegocioTipoCuenta.obtenet_Todos_LosTipoDeCuentas());
					dispatcher = request.getRequestDispatcher("/A_AsignarCuenta.jsp");
					dispatcher.forward(request, response);
					return;
				}

				// Verificar si el cliente ya tiene 3 o mas cuentas cuentas
				if (NegocioCuenta.TieneMasDe_3Cuentas(dni)) {
					request.setAttribute("errorDNI", "El cliente ya tiene el máximo de 3 cuentas asignadas.");

					int siguienteNumeroCuenta = NegocioCuenta.obtenerSiguienteNumeroDeCuenta();
					request.setAttribute("Siguiente_Num_Cuenta", siguienteNumeroCuenta);
					request.setAttribute("listaTipoCuentas", NegocioTipoCuenta.obtenet_Todos_LosTipoDeCuentas());
					dispatcher = request.getRequestDispatcher("/A_AsignarCuenta.jsp");
					dispatcher.forward(request, response);
					return;
				}

				// Asigna La cuenta
				int filas = NegocioCuenta.AsignarCuenta(cuenta);

				request.setAttribute("AgregadoExito", filas);

				int siguienteNumeroCuenta = NegocioCuenta.obtenerSiguienteNumeroDeCuenta();
				request.setAttribute("Siguiente_Num_Cuenta", siguienteNumeroCuenta);
				request.setAttribute("listaTipoCuentas", NegocioTipoCuenta.obtenet_Todos_LosTipoDeCuentas());
				dispatcher = request.getRequestDispatcher("/A_AsignarCuenta.jsp");
				dispatcher.forward(request, response);

			} else {
				request.setAttribute("errorDNI", "El Dni debe ser un número de máximo 8 dígitos válidos.");

				int siguienteNumeroCuenta = NegocioCuenta.obtenerSiguienteNumeroDeCuenta();
				request.setAttribute("Siguiente_Num_Cuenta", siguienteNumeroCuenta);
				request.setAttribute("listaTipoCuentas", NegocioTipoCuenta.obtenet_Todos_LosTipoDeCuentas());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AsignarCuenta.jsp");
				dispatcher.forward(request, response);
			}
		}

		//

		if (request.getParameter("modificar") != null) {
			String cbu = request.getParameter("cbu");

			Cuenta cuenta = new Cuenta();
			try {
				cuenta = NegocioCuenta.obtenerCuenta(cbu);
				request.setAttribute("cuentaModificar", cuenta);
			} catch (CuentaNoExiste e) {
				request.setAttribute("MensajeError", e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("listaCue", NegocioCuenta.obtenet_Todos_LasCuentas());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if (request.getParameter("aceptarModificarCuenta") != null) {
			Cuenta cuenta = new Cuenta();
			try {
				cuenta.setCBU(request.getParameter("CBU"));
				cuenta.setDNI(Integer.parseInt(request.getParameter("dni")));
				cuenta.setNumeroCuenta(Long.parseLong(request.getParameter("numeroCuenta")));

				TipoCuenta tipo = new TipoCuenta();
				tipo.setIdTipoCuenta(Integer.parseInt(request.getParameter("tipoCuenta")));
				cuenta.setTipoCuenta(tipo);

				cuenta.setFechaCreacion(Date.valueOf(request.getParameter("fechaCreacion")));
				cuenta.setSaldo(BigDecimal.valueOf(Double.parseDouble(request.getParameter("saldo"))));
				cuenta.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("MensajeError",
						"Valores inválidos, cuenta: " + cuenta.getNumeroCuenta() + " no modificada.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// Hacer update
			try {
				NegocioCuenta.modificarCuenta(cuenta);
				request.setAttribute("MensajeExito", "Cuenta modificada correctamente.");
			} catch (ClienteNoExiste e) {
				request.setAttribute("MensajeError", e.getMessage());
			} catch (Exception e) {
				request.setAttribute("MensajeError", "Error al modificar la cuenta: " + cuenta.getNumeroCuenta() + ".");
			}

			// Obtener cuentas actualizadas
			request.setAttribute("listaCue", NegocioCuenta.obtenet_Todos_LasCuentas());

			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarCuentas.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
	}
}
