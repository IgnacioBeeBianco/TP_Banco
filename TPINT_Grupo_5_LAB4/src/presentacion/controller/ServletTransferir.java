package presentacion.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Transferencia;
import negocio.TransferenciaNegocio;
import negocioImpl.TransferenciaNegocioImpl;
import excepciones.CuentaNoExiste;
import negocio.MovimientoNegocio;
import negocioImpl.MovimientoNegocioImpl;
import entidad.Cuenta;
import negocio.CuentaNegocio;
import negocioImpl.CuentaNegocioImpl;

/**
 * Servlet implementation class ServletTransferir
 */
@WebServlet("/ServletTransferir")
public class ServletTransferir extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ///copiar
    CuentaNegocio cNeg= new CuentaNegocioImpl();
    MovimientoNegocio mNeg= new MovimientoNegocioImpl();
    ///copiar
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTransferir() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		if(request.getParameter("transferirCliente")!=null)
		{
			HttpSession session = request.getSession();
			String usuario = (String) session.getAttribute("usuario");
			ArrayList<Cuenta> cuentas = (ArrayList<Cuenta>) cNeg.obtenerCuentasCliente(usuario);
			request.setAttribute("cuentas", cuentas);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/C_Transferir.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("btnTransferir")!=null)
		{
		    String cbuOrigen = request.getParameter("cuentaOrigen");
		    String cbuDestino = request.getParameter("cbu");
		    String montoString = request.getParameter("monto");
		    BigDecimal monto = null;
		    boolean revision = true;
		    
		    // Reemplazar coma por punto en el montoString
		    montoString = montoString.replace(',', '.');
		    
		    // Validar que montoString sea un BigDecimal válido
		    try {
		        monto = new BigDecimal(montoString);
		    } catch (NumberFormatException e) {
		        request.setAttribute("errorMonto", "El monto ingresado no es válido.");
		        revision = false;
		    }

		    LocalDateTime fechaYHoraActual = LocalDateTime.now();
		    java.util.Date utilDate = java.util.Date.from(fechaYHoraActual.atZone(ZoneId.systemDefault()).toInstant());
		    Date fechaSQL = new Date(utilDate.getTime());
		    
		    HttpSession session = request.getSession();
		    String usuario = (String) session.getAttribute("usuario");
		    ArrayList<Cuenta> cuentas = (ArrayList<Cuenta>) cNeg.obtenerCuentasCliente(usuario);
		    BigDecimal SaldoOrigen = BigDecimal.ZERO;
		    
		    ///VERIFICAR MONTO NO EXCEDA SALDO EN CUENTA
		    for(Cuenta cue:cuentas)
		    {
		        if (cue.getCBU().equals(cbuOrigen))
		        {
		            SaldoOrigen = cue.getSaldo();
		            break;
		        }
		    }
		    
		    if (monto != null && monto.compareTo(SaldoOrigen) <= 0) {

		    } else {
		        request.setAttribute("errorMonto", "El monto a transferir excede el saldo disponible en la cuenta de origen o no es válido.");
		        revision = false;
		    }
		    
		    ///VERIFICAR CBU EXISTENTE
		    if (cNeg.existeCuenta(cbuDestino)) {

		    } else {
		        request.setAttribute("errorCbu", "El CBU proporcionado no se encuentra registrado en nuestro sistema.");
		        revision = false;
		    }
		    
		    if (cbuOrigen.equals(cbuDestino)) {
		        request.setAttribute("errorCbu", "Ha ingresado su propio CBU como destino.");
		        revision = false;
		    }
		    
		    if(revision)
		    {
		        Transferencia transferir;
		        try {
		            transferir = new Transferencia(1,cNeg.obtenerCuenta(cbuOrigen),cNeg.obtenerCuenta(cbuDestino),fechaSQL,monto);
		            TransferenciaNegocio Ntra = new TransferenciaNegocioImpl();
		            int filas = Ntra.AgregarTransferencia(transferir);
		            if(filas!=0)
		            {
		                request.setAttribute("exito", "Se ha transferido exitosamente");
		            } else {
		                System.out.println("NO se ha registrado en BD");
		            }
		        } catch (CuentaNoExiste e) {
		            // Manejo de excepción si la cuenta no existe
		            e.printStackTrace();
		        }
		    }
		    
		    request.setAttribute("cuentas", cuentas);
		    
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/C_Transferir.jsp");
		    dispatcher.forward(request, response);    
		}


	}


}
