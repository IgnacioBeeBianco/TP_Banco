<%@page import="java.util.ArrayList"%>
<%@page import="entidad.PagoCuota"%>
<%@page import="entidad.Cuenta"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="css/StyleSheet.css">
	
	<!-- Paginación tabla -->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

	<script>
	$(document).ready( function () {
		$('#tabla_cuotas').DataTable();
	} );
	
	function confirmarPago(){
		return confirm('¿Estás seguro de que quieres pagar esta cuota?');
	}
	</script>

    <title>Pago de Préstamos</title>
</head>
<body class="inicio-Cliente">
    <div class="encabezadoCliente">Banco Grupo5</div>
    
    <div class="main">
    
    <jsp:include page="sidebarCliente.jsp"></jsp:include>
    
    
    <div class="parteDerCliente">	                       <!-- Main -->
    
             
    <h2>Pago de Préstamos</h2>
    
	<!-- Lista de la Cuotas -->
    <%
	    ArrayList<PagoCuota> listaCuotas = null;
		 if (request.getAttribute("Lista_De_Cuotas_Por_IdPrestamo") != null) {
			 listaCuotas = (ArrayList<PagoCuota>) request.getAttribute("Lista_De_Cuotas_Por_IdPrestamo");
		   }  
    %>
    
    <!-- Lista de las cuentas del Cliente -->
     <%
	    ArrayList<Cuenta> listaCuentas = null;
		if (request.getAttribute("cuentas") != null) {
			 listaCuentas = (ArrayList<Cuenta>) request.getAttribute("cuentas");
		   }  
    %>
    
       <!-- Mensajes del Servlet -->  
	 <% if (request.getAttribute("errorPago") != null) { %>
	    <span style="color: red;"><%= request.getAttribute("errorPago") %></span>
	 <% } %>
	 <% if (request.getAttribute("okPago") != null) { %>
	    <span style="color: green;"><%= request.getAttribute("okPago") %></span>
	 <% } %>


<table id="tabla_cuotas">
    <thead>
        <tr>
            <th>ID Prestamo</th>
            <th>Cuota</th>
            <th>Monto</th>
            <th>Fecha de Vencimiento</th>
            <th>Fecha de Pago</th>
            <th>Cuenta</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
		        <!-- Inicio de la lista de cuotas -->
		        <% if (listaCuotas != null) {
		            for (PagoCuota cuota : listaCuotas) {
		        %>
		        <tr>
		            <td><%= cuota.getIdPrestamo() %></td>
		            <td><%= cuota.getNroCuota() %></td>
		            <td><%= cuota.getImportePago() %></td>
		            <td><%= cuota.getFechaVencimiento() %></td>
		            <td><%= cuota.getFechaPago() != null ? cuota.getFechaPago() : '-' %></td>
		            
 <!--Cuentas-->     <td>
		                <% if (cuota.getEstadoCuota() != 1) { %>
<!--Form-->		   <form action="ServletPagosCuotas" method="post" onsubmit="return confirmarPago()">
		                       
                        <select id="cuentaOrigen" name="cuentaOrigen" required>
                            
                            <option value="" disabled selected>     -- Seleccionar cuenta --     </option>
                            
                            <% if (listaCuentas != null) {
                                for (Cuenta cuenta : listaCuentas) {
                            %>
                            <option value="<%= cuenta.getCBU() %>"><%= cuenta.getCBU() %> - Saldo: <%= "$ " + cuenta.getSaldo() %></option>
                            <% } } %>
                            
                        </select>
		            <% } else { %>
				        <!--Si esta Pago, Mostrar CBU-->
				        <%= cuota.getCbuOrigen() %>
				        <% } %>
		            </td>
		            
<!--Acciones-->      <td>
		                <% if (cuota.getEstadoCuota() != 1) { %>
		                    <input type="hidden" name="idPrestamo" value="<%= cuota.getIdPrestamo() %>">
		                    <input type="hidden" name="nroCuota" value="<%= cuota.getNroCuota() %>">
		                    <input type="hidden" name="MontoCuota" value="<%= cuota.getImportePago() %>">
		                    
<!--Boton-->			   <input type="submit" id="btnPagarCuota" name="btnPagarCuota" value="Pagar" required>


<!--Fin Form-->	    </form>
		                <% } else { %>
		                    <!-- Si esta Pago,no Mostrar Boton -->
		                    Cuota pagada
		                <% } %>
		            </td>
		            
		        </tr>
		        <% } } else { %>
		        <tr>
		            <!-- Mensaje de ninguna cuota encontrada -->
		            <td colspan="7">No se encontraron cuotas para este préstamo.</td>
		        </tr>
		        <% } %>
		        <!-- Fin de la lista de cuotas -->
    </tbody>
</table>


<!--         parteDerCliente --></div>
<!--     main --></div>
</body></html>
