package presentacion.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;
import negocio.UbicacionesNegocio;
import negocioImpl.UbicacionesNegocioImpl;

/**
 * Servlet implementation class ServletAgregarCliente
 */
@WebServlet("/ServletUbicaciones")
public class ServletUbicaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletUbicaciones() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("agregarCliente") != null) {
			
			UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
			request.setAttribute("paises", ubn.getPaises());
			request.setAttribute("provincias", ubn.getProvincias());
			Provincia p = new Provincia();
			p.setID_Provincia(1);
			request.setAttribute("localidades", ubn.getLocalidadesPorProvincia(p));

			RequestDispatcher dispatcher = request.getRequestDispatcher("/A_AgregarCliente.jsp");
			dispatcher.forward(request, response);
		}
		
		if (request.getParameter("crearUsuario") != null) {
			
			UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
			request.setAttribute("paises", ubn.getPaises());
			request.setAttribute("provincias", ubn.getProvincias());
			Provincia p = new Provincia();
			p.setID_Provincia(1);
			request.setAttribute("localidades", ubn.getLocalidadesPorProvincia(p));

			RequestDispatcher dispatcher = request.getRequestDispatcher("/C_AgregarCliente.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (request.getParameter("localidades") != null) {
			Provincia p = new Provincia();
			p.setID_Provincia(1);
			ArrayList<Localidad> list = new ArrayList<Localidad>();
			try {
				int id = Integer.parseInt(request.getParameter("localidades"));
				p.setID_Provincia(id);
				UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
				list = ubn.getLocalidadesPorProvincia(p);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// Armar options con localidades:
			String options = "";
			for (Localidad l : list) {
				options += "<option value=" + l.getID_Localidad() + ">" + l.getLocalidad() + "</option>";
			}
			response.setContentType("text/plain"); 
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(options);
		}
		if (request.getParameter("paises") != null) {
			ArrayList<Pais> list = new ArrayList<Pais>();
			try {
				UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
				list = ubn.getPaises();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// Armar options con localidades:
			String options = "";
			for (Pais l : list) {
				options += "<option value=" + l.getID_Nacionalidad() + ">" + l.getPais() + "</option>";
			}
			response.setContentType("text/plain"); 
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(options);
		}
		if (request.getParameter("provincias") != null) {
			ArrayList<Provincia> list = new ArrayList<Provincia>();
			try {
				UbicacionesNegocio ubn = new UbicacionesNegocioImpl();
				list = ubn.getProvincias();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// Armar options con localidades:
			String options = "";
			for (Provincia l : list) {
				options += "<option value=" + l.getID_Provincia() + ">" + l.getProvincia() + "</option>";
			}
			response.setContentType("text/plain"); 
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(options);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
