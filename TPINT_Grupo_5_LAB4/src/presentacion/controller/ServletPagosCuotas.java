package presentacion.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cuenta;
import entidad.PagoCuota;
import entidad.Prestamo;
import negocio.CuentaNegocio;
import negocio.PagoCuotaNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.PagoCuotaNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;


@WebServlet("/ServletPagosCuotas")
public class ServletPagosCuotas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PrestamoNegocio NegocioPrestamo = new PrestamoNegocioImpl();
	PagoCuotaNegocio NegocioCuotas = new PagoCuotaNegocioImpl();
    CuentaNegocio NegocioCuenta= new CuentaNegocioImpl();

    
    public ServletPagosCuotas() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if (request.getParameter("ListarPrestamos") != null) {
		    
		    HttpSession session = request.getSession();
		    int Dni_Cliente = (int) session.getAttribute("Dni_Cliente");
			    		   
 
		    ArrayList<Prestamo> listaPrestamos = NegocioPrestamo.Obtener_Prestamos_PorDni(Dni_Cliente);
		    request.setAttribute("Lista_De_Prestamos", listaPrestamos);
		    
		    

		    RequestDispatcher dispatcher = request.getRequestDispatcher("/C_PrestamosDelCliente.jsp");
		    dispatcher.forward(request, response);
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("btnVerCuotas") != null) {
		        
			 int ID_prestamo = Integer.parseInt(request.getParameter("idPrestamo"));

			 //OBTENER LAS CUOTAS DEL PRESTAMO SELECCIONADO
			 ArrayList<PagoCuota> listaCuotas = NegocioCuotas.Obtener_Las_Cuotas_Por_IDprestamo(ID_prestamo);
			 request.setAttribute("Lista_De_Cuotas_Por_IdPrestamo", listaCuotas);
			 
			 //OBTENER LAS CUENTAS DEL CLIENTE PARA PODER PAGAR
			 HttpSession session = request.getSession();
			 String usuario = (String) session.getAttribute("usuario");
			 ArrayList<Cuenta> cuentas_Del_Cliente = (ArrayList<Cuenta>) NegocioCuenta.obtenerCuentasCliente(usuario);
			 request.setAttribute("cuentas", cuentas_Del_Cliente);
				
			
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/C_RealizarPagos.jsp");
		     dispatcher.forward(request, response);
	 }
		
		
// PAGAR CUOTA		
		if(request.getParameter("btnPagarCuota")!=null)
		{
		    int ID_prestamo =Integer.parseInt( request.getParameter("idPrestamo"));
		    int nroCuota = Integer.parseInt( request.getParameter("nroCuota"));
		    String Cbu_Seleccionado = request.getParameter("cuentaOrigen");
		   
		    String montoCuotaString = request.getParameter("MontoCuota");    
		    BigDecimal montoCuota = new BigDecimal(montoCuotaString);		    
		   
		    //PARA OBTENER EL SALDO DEL CBU SELECCIONADO
		    HttpSession session = request.getSession();
		    String usuario = (String) session.getAttribute("usuario");
		    ArrayList<Cuenta> cuentas_Del_Cliente = (ArrayList<Cuenta>) NegocioCuenta.obtenerCuentasCliente(usuario); 
		    BigDecimal Saldo_Cuenta_Seleccionada = BigDecimal.ZERO;
		    
		    for(Cuenta cue:cuentas_Del_Cliente)
		    {
		        if (cue.getCBU().equals(Cbu_Seleccionado))
		        {
		        	Saldo_Cuenta_Seleccionada = cue.getSaldo();
		            break;
		        }
		    }
		
		    ///VERIFICAR QUE LA CUENTA SELECCIONADA TENGA FONDOS SUFICIENTES	    
		    if (montoCuota.compareTo(Saldo_Cuenta_Seleccionada) <= 0) {
		        
		    	PagoCuota Cuota = new PagoCuota();
		        Cuota.setCbuOrigen(Cbu_Seleccionado);
		        Cuota.setImportePago(montoCuota);
		        Cuota.setIdPrestamo(ID_prestamo);
		        Cuota.setNroCuota(nroCuota);
		   
		        // REALIZAR EL PAGO   
		        if(NegocioCuotas.Realizar_PagoCuota(Cuota))
		        {
		        	//System.out.println("Cuota= "+ Cuota);
			    	request.setAttribute("okPago", "Pago Realizado Con Exito.");
			    	cuentas_Del_Cliente = (ArrayList<Cuenta>) NegocioCuenta.obtenerCuentasCliente(usuario);
		        }else {
			    	request.setAttribute("errorPago", "No se Pudo Realizar El pago");
		        }
		        
		    } else {
		        request.setAttribute("errorPago", "El monto de la cuota excede el saldo disponible en la cuenta seleccionada.");
		    }

		    
		     //MANDAR LAS LISTAS DE VUELTA
			 ArrayList<PagoCuota> listaCuotas = NegocioCuotas.Obtener_Las_Cuotas_Por_IDprestamo(ID_prestamo);
			 request.setAttribute("Lista_De_Cuotas_Por_IdPrestamo", listaCuotas);
		     request.setAttribute("cuentas", cuentas_Del_Cliente);
		    
  		    RequestDispatcher dispatcher = request.getRequestDispatcher("C_RealizarPagos.jsp");
		    dispatcher.forward(request, response);    
		}
	
	
	}

}
