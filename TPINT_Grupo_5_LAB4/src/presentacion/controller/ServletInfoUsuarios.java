package presentacion.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datos.InfoUsuarioDao;
import datosImpl.InfoUsuarioDaoImpl;
import entidad.InfoUsuario;
import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;
import entidad.Usuario;
import excepciones.YaExisteCuilException;
import excepciones.YaExisteDniException;
import excepciones.YaExisteUsuarioException;
import negocio.InfoUsuarioNegocio;
import negocio.UbicacionesNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.InfoUsuarioNegocioImpl;
import negocioImpl.UbicacionesNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ServletInfoUsuarios")
public class ServletInfoUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;

	InfoUsuarioNegocio IUneg = new InfoUsuarioNegocioImpl();
    UsuarioNegocio NegocioUsuario = new UsuarioNegocioImpl();
	
	public ServletInfoUsuarios() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
	if (request.getParameter("Param") != null) {
			String opcion = request.getParameter("Param").toString();

			switch (opcion) {
			case "ListarClientes": {
				setUbicaciones(request);
				request.setAttribute("listaCli", IUneg.obtenet_Todos_LosClientes());
				
				request.setAttribute("listaAdmins", IUneg.obtener_Todos_LosAdmins());
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarClientes.jsp");
				dispatcher.forward(request, response);
				break;
			}
			default:
				break;
			}
		}
		
	if (request.getParameter("infoUsuario") != null) {
			HttpSession session = request.getSession();
			String usuario = (String) session.getAttribute("usuario");
		
			InfoUsuarioDao dao = new InfoUsuarioDaoImpl();
			InfoUsuario Infousuario = dao.obtener_Un_InfoUsuario(usuario);
			request.setAttribute("cliente", Infousuario);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("C_InfoCliente.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
	if (request.getParameter("infoUsuarioAdmin") != null) {
			HttpSession session = request.getSession();
			String usuario = (String) session.getAttribute("usuario");
		
			InfoUsuarioDao dao = new InfoUsuarioDaoImpl();
			InfoUsuario Infousuario = dao.obtener_Un_InfoUsuario(usuario);
			request.setAttribute("admin", Infousuario);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("A_InfoAdmin.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		setUbicaciones(request);
		//System.out.println("Solicitud POST recibida");

		
	if (request.getParameter("btnAgregar") != null) {
			//System.out.println("Procesando creación de cliente...");

			InfoUsuario nuevoCliente = getInfoUsuarioFromRequest(request);
			
			
			if (nuevoCliente.getDni() < 1) {
				request.setAttribute("ErrorDNI", "Ingrese un DNI válido de 8 Digitos.");
			}		
			else if (IUneg.existe_ese_DNI(nuevoCliente.getDni())) {
				request.setAttribute("ErrorDNI", "Ese DNI ya existe");
			}
			else if (NegocioUsuario.BuscarUsuario(nuevoCliente.getUsuario())) {
				request.setAttribute("ErrorUsuario", "Ya Existe Ese Nombre de Usuario");
			}
			else if (nuevoCliente.getCuil() == "CUIL Inválido") {
				request.setAttribute("ErrorCUIL", "Ingrese un CUIL válido de 11 Digitos");
			}
			else if (IUneg.existe_ese_CUIL(nuevoCliente.getCuil())) {
				request.setAttribute("ErrorCUIL", "Ese CUIL ya existe");
			}			
			else if (nuevoCliente.getFechaNacimiento() == null) {
				request.setAttribute("MensajeError", "Formato fecha: AAAA-mm-dd");
			}
			else {
				if (IUneg.agregarInfoUsuario(nuevoCliente)) {
					request.setAttribute("MensajeExito", "Usuario creado exitosamente.");
					response.sendRedirect("ServletInfoUsuarios?Param=ListarClientes");
					return;
				}
				request.setAttribute("MensajeError", "Error al crear usuario.");
			}

			request.setAttribute("cliente", nuevoCliente);
			setLocalidades(request, nuevoCliente.getProvincia().getID_Provincia());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AgregarCliente.jsp");
			dispatcher.forward(request, response);
		}
		
		

	if (request.getParameter("btnDelete") != null) {
			//System.out.println("Procesando eliminación de cliente...");
			try {
				int dni = Integer.parseInt(request.getParameter("dni"));

				boolean clienteEliminado = IUneg.eliminarInfoUsuario(dni);

				if (clienteEliminado) {
					request.setAttribute("MensajeExito", "Dado de baja correctamente.");
				} else {
					request.setAttribute("MensajeError", "No se pudo dar de baja.");
				}

			} catch (NumberFormatException e) {
				request.setAttribute("MensajeError", "DNI inválido.");
			}

			request.setAttribute("listaCli", IUneg.obtenet_Todos_LosClientes());
			request.setAttribute("listaAdmins", IUneg.obtener_Todos_LosAdmins());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarClientes.jsp");
			dispatcher.forward(request, response);
		}
		
		//EDITAR CLIENTE	
	if (request.getParameter("btnEditar") != null) {
		    //System.out.println("Modificar cliente");

		    InfoUsuario clienteActualizado = getInfoUsuarioFromRequest(request);
		    //System.out.println(clienteActualizado);
		    
		    // cliente antes de actualizar para comparar el CUIL, si cambio o no, Para verificar que no exista el cuil modificado en la BD
		    InfoUsuario clienteAntesDeActualizar = IUneg.obtener_La_InfoUsuario(clienteActualizado.getUsuario().getUsuario());

		    if (clienteActualizado.getFechaNacimiento() == null) {
		        request.setAttribute("MensajeError", "No se Pudo Actulizar Ingrese una fecha válida.");
		    } else if (clienteActualizado.getCuil() == "CUIL Inválido") {
		        request.setAttribute("ErrorCUIL", "No se pudo Actualizar Ingrese un CUIL válido de 11 dígitos");
		    } else {
		        // Verifica si el CUIL ha cambiado
		        if (!clienteActualizado.getCuil().equals(clienteAntesDeActualizar.getCuil())) {
		            // Si el CUIL ha cambiado, verificar si ya existe en la base de datos
		            if (IUneg.existe_ese_CUIL(clienteActualizado.getCuil())) {
		                request.setAttribute("ErrorCUIL", "No se Pudo Actualizar Ese CUIL ya existe");
		            } else {
		                // Si el CUIL no existe, proceder con la modificación
		                if (IUneg.modificarInfoUsuario(clienteActualizado)) {
		                    request.setAttribute("MensajeExito", "Datos editados exitosamente.");
		                } else {
		                    request.setAttribute("MensajeError", "Error al editar los datos.");
		                }
		            }
		        } else {
		            // Si el CUIL no ha cambiado, proceder directamente con la modificación
		            if (IUneg.modificarInfoUsuario(clienteActualizado)) {
		                request.setAttribute("MensajeExito", "Datos editados exitosamente.");
		            } else {
		                request.setAttribute("MensajeError", "Error al editar los datos.");
		            }
		        }
		    }

		    request.setAttribute("listaCli", IUneg.obtenet_Todos_LosClientes());
		    request.setAttribute("listaAdmins", IUneg.obtener_Todos_LosAdmins());
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarClientes.jsp");
		    dispatcher.forward(request, response);
		}


	if (request.getParameter("btnCancelar") != null) {
			request.setAttribute("listaCli", IUneg.obtenet_Todos_LosClientes());
			request.setAttribute("listaAdmins", IUneg.obtener_Todos_LosAdmins());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AdministrarClientes.jsp");
			dispatcher.forward(request, response);
		}
		
	
	if (request.getParameter("btnCancelarCliente") != null) {

			RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Cliente.jsp");
			dispatcher.forward(request, response);
		}
		
 //	AGREGAR CLIENTE POR PARTE DEL CLIENTE // USANDO EXCEPCIONES CREADAS
	
	if (request.getParameter("btnAgregarCliente") != null) {
	    InfoUsuario nuevoCliente = getInfoUsuarioFromRequest(request);
	    boolean datosValidos = true;

	    // Validación de DNI
	    if (nuevoCliente.getDni() < 1) {
	        request.setAttribute("ErrorDNI", "Ingrese un DNI válido de 8 dígitos.");
	        datosValidos = false;
	    }

	    // Validación de Fecha de Nacimiento
	    else if (nuevoCliente.getFechaNacimiento() == null) {
	        request.setAttribute("MensajeError", "Formato fecha: AAAA-mm-dd");
	        datosValidos = false;
	    }

	    // Validación de CUIL 
	    if (nuevoCliente.getCuil() == "CUIL Inválido") {
	        request.setAttribute("ErrorCUIL", "Ingrese un CUIL válido de 11 dígitos.");
	        datosValidos = false;
	    }

	    // Si todos los datos son válidos, intenta agregar el usuario
	    if (datosValidos) {
	        try {
	            if (IUneg.Agregar_InfoUsuario_UsandoExceptions(nuevoCliente)) {
	                request.setAttribute("MensajeExito", "Cliente creado exitosamente.");
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Cliente.jsp");
	                dispatcher.forward(request, response);
	                return;
	            }
	            request.setAttribute("MensajeError", "Error al crear el cliente.");
	        } catch (YaExisteDniException e) {
	            request.setAttribute("ErrorDNI", e.getMessage());
	        } catch (YaExisteCuilException e) {
	            request.setAttribute("ErrorCUIL", e.getMessage());
	        } catch (YaExisteUsuarioException e) {
	            request.setAttribute("ErrorUsuario", e.getMessage());
	        }
	    }

	    request.setAttribute("cliente", nuevoCliente);
	    setLocalidades(request, nuevoCliente.getProvincia().getID_Provincia());
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/C_AgregarCliente.jsp");
	    dispatcher.forward(request, response);
	}




    
    
		HttpSession session = request.getSession();
		String usuario = (String) session.getAttribute("usuario");
		
		
	if (request.getParameter("clientUser") != null && request.getParameter("adminRol") != null) {
            //System.out.println("test");
			String clientUser = request.getParameter("clientUser");
            boolean isAdmin = Boolean.parseBoolean(request.getParameter("adminRol"));
            //System.out.println(clientUser);
            //System.out.println(isAdmin);
            if (clientUser.equals(usuario) && !isAdmin) {
                response.getWriter().write("No puedes quitarte a ti mismo el rol de administrador.");
            }
            else {
            	UsuarioNegocioImpl UNI = new UsuarioNegocioImpl();
                boolean rolActualizado = UNI.updateRolInfoUsuario(clientUser, isAdmin);
                System.out.println(rolActualizado);
                if (rolActualizado) {
                    response.getWriter().write("success");
                } else {
                    response.getWriter().write("Failure");
                }
            }
            
        }
	}

	
   public void setUbicaciones(HttpServletRequest request) {
		UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
		request.setAttribute("paises", ubn.getPaises());
		request.setAttribute("provincias", ubn.getProvincias());
		Provincia p = new Provincia(1, "");
		request.setAttribute("localidades", ubn.getLocalidadesPorProvincia(p));
	}
	
	
   public void setLocalidades(HttpServletRequest request, int idProvincia) {
		UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
		Provincia p = new Provincia(idProvincia, "");
		request.setAttribute("localidades", ubn.getLocalidadesPorProvincia(p));
	}

	
   public InfoUsuario getInfoUsuarioFromRequest(HttpServletRequest request) {
		int dni = 0;
		try {
		    String dniParam = request.getParameter("dni");
		    if (dniParam != null && dniParam.matches("\\d{8}")) {
		        dni = Integer.parseInt(dniParam);
		    }
		}  catch (Exception e) {
			e.printStackTrace();
		}

		
		String cuil = "CUIL Inválido";

		try {
		    String cuilParam = request.getParameter("cuil");
		    if (cuilParam != null && cuilParam.matches("\\d{11}")) {
		        cuil = cuilParam;
		    } 
		} catch (Exception e) {
		    e.printStackTrace(); 
		}
				
		String usuarioParam = request.getParameter("usuario");
		String passwordParam = request.getParameter("password");

		if (usuarioParam != null && !usuarioParam.isEmpty()) {
		    usuarioParam = usuarioParam.trim(); 
		}

		if (passwordParam != null && !passwordParam.isEmpty()) {
		    passwordParam = passwordParam.trim(); 
		}

		Usuario usuario = new Usuario(usuarioParam, passwordParam, false);

		String nombre = request.getParameter("nombre").trim();
		String apellido = request.getParameter("apellido").trim();
		String sexo = request.getParameter("sexo");
		String fechaNacimientoStr = request.getParameter("fecha_nacimiento").replace('/', '-');

		Pais nacionalidad = new Pais();
		nacionalidad.setID_Nacionalidad(Integer.parseInt(request.getParameter("nacionalidad")));

		Provincia provincia = new Provincia();
		provincia.setID_Provincia(Integer.parseInt(request.getParameter("provincia")));

		Localidad localidad = new Localidad();
		localidad.setProvincia(provincia);
		localidad.setID_Localidad(Integer.parseInt(request.getParameter("localidad")));

		String direccion = request.getParameter("direccion").trim();
		String correo = request.getParameter("correo");

		Date fechaNacimiento = null;
		try {
			if (fechaNacimientoStr.charAt(2) == '-') {
				String[] fecha = fechaNacimientoStr.split("-");
				fechaNacimientoStr = String.join("-", fecha[2], fecha[1], fecha[0]);
			}
			fechaNacimiento = Date.valueOf(fechaNacimientoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String telefono1 = request.getParameter("telefono1").trim();
		String telefono2 = request.getParameter("telefono2").trim();


		// Verificar y asignar null si telefono2 esta vacio
		if (telefono2 != null && telefono2.trim().isEmpty()) {
			telefono2 = null;
		}

		InfoUsuario nuevoCliente = new InfoUsuario(dni, usuario, cuil, nombre, apellido, sexo, nacionalidad,
				fechaNacimiento, provincia, localidad, direccion, correo, telefono1, telefono2, true);
		return nuevoCliente;
	}
}
