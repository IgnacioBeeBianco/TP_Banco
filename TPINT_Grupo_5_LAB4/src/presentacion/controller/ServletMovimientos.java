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

import entidad.Movimiento;
import negocio.InfoUsuarioNegocio;
import negocio.MovimientoNegocio;
import negocio.TipoMovimientoNegocio;
import negocioImpl.InfoUsuarioNegocioImpl;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.TipoMovimientoNegocioImpl;

/**
 * Servlet implementation class ServletMovimientos
 */
@WebServlet("/ServletMovimientos")
public class ServletMovimientos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    MovimientoNegocio mNeg= new MovimientoNegocioImpl();
    TipoMovimientoNegocio tmn = new TipoMovimientoNegocioImpl();
    public ServletMovimientos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("DoPost");
	    
	    if (request.getParameter("NumeroCuenta") != null) {
	        String numeroCuentaStr = request.getParameter("NumeroCuenta");

	        if (numeroCuentaStr != null && !numeroCuentaStr.isEmpty()) {
	            try {
	                int numeroCuenta = Integer.parseInt(numeroCuentaStr);
	                ArrayList<Movimiento> movimientos = mNeg.obtenerMovimientosNumCuenta(numeroCuenta);
	                request.setAttribute("movimientos", movimientos);
	                request.setAttribute("Numseleccionado", numeroCuentaStr);

	                // Obtener el saldo de la cuenta
	                String saldoCuentaStr = request.getParameter("SaldoCuenta");
	                if (saldoCuentaStr != null && !saldoCuentaStr.isEmpty()) {
	                    BigDecimal saldoCuenta = new BigDecimal(saldoCuentaStr);
	                    request.setAttribute("SaldoCuenta", saldoCuenta);
	                } else {
	                    request.setAttribute("error", "Saldo de cuenta no proporcionado.");
	                }

	            } catch (NumberFormatException e) {
	                request.setAttribute("error", "Número de cuenta inválido.");
	            }
	        } else {
	            request.setAttribute("error", "Número de cuenta no proporcionado.");
	        }

	        RequestDispatcher dispatcher = request.getRequestDispatcher("/C_Movimientos.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }

		
		if(request.getParameter("btnGenerarReporte")!=null) {
			String fechaInicio = request.getParameter("fechaInicio");
		    String fechaFin = request.getParameter("fechaFin");
		    String tipoMovimiento = request.getParameter("tipoMovimiento");
		    String saldoMinStr = request.getParameter("saldoMin");
		    String saldoMaxStr = request.getParameter("saldoMax");
            
		    float saldoMin = saldoMinStr != null && !saldoMinStr.isEmpty() ? Float.parseFloat(saldoMinStr) : 0.0f;
		    float saldoMax = saldoMaxStr != null && !saldoMaxStr.isEmpty() ? Float.parseFloat(saldoMaxStr) : Float.MAX_VALUE;
	    	
		    ArrayList<Movimiento> reporteNegocio = null;
		    reporteNegocio = mNeg.generarReporteMovimientos(fechaInicio, fechaFin, tipoMovimiento, saldoMin, saldoMax);
    
		    InfoUsuarioNegocio IUneg = new InfoUsuarioNegocioImpl();
		    request.setAttribute("listarStat", IUneg.obtener_Todas_LasStats());
		    request.setAttribute("listaTipoMovimientos", tmn.obtener_Todos_LosTipoDeMovimientos());
		    request.setAttribute("listarMovs", reporteNegocio);

		    
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/A_Reportes.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
		
		

	}
}



