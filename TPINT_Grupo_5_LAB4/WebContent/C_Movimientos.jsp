<%@page import="entidad.Movimiento"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html><html lang="es"><head><meta charset="ISO-8859-1">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movimientos</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
	<link rel="stylesheet" media="screen" href="css/StyleSheet.css">

<!-- Paginación tabla -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script>
$(document).ready( function () {
    $('#tabla_Movimientos').DataTable();
} );
</script>


</head>

<body class="inicio-Cliente">

    <div class="encabezadoCliente">Banco Grupo5</div>
    
    <div class="main"> 
    <jsp:include page="sidebarCliente.jsp"></jsp:include>

	<div class="parteDerCliente">
				   <% 
					String numeroCuenta = "";
					BigDecimal saldoCuenta = null;
					
					if (request.getAttribute("Numseleccionado") != null) {
					    numeroCuenta = (String) request.getAttribute("Numseleccionado");
					}
					
					if (request.getAttribute("SaldoCuenta") != null) {
					    saldoCuenta = (BigDecimal) request.getAttribute("SaldoCuenta");
					}
					%>
					
					<div class="numero-cuenta">
					    <p><strong>Número de Cuenta:</strong> <%= numeroCuenta %></p>
					    <% if (saldoCuenta != null) { %>
					        <p><strong>Saldo:</strong> <%= saldoCuenta %> $</p>
					    <% } else { %>
					        <p><strong>Saldo:</strong> No disponible</p>
					    <% } %>
					</div>

   	<h2>Movimientos Bancarios</h2>

<table id="tabla_Movimientos">    
    <thead>
        <tr>
            <th>Nro Movimiento</th>
            <th>CBU</th>
            <th>Fecha</th>
            <th>Importe</th>
            <th>Saldo Total</th> <!-- Nuevo encabezado -->
            <th>Detalle Concepto</th>
            <th>Tipo de Movimiento</th>
        </tr>
    </thead>
    <tbody>
        <% if (request.getAttribute("movimientos") != null) {
            ArrayList<Movimiento> movimientos = (ArrayList<Movimiento>)(request.getAttribute("movimientos"));
            for (Movimiento m : movimientos) { %>
                <tr>
                    <td><%= m.getNroMovimiento() %></td>
                    <td><%= m.getCbuCliente().getCBU()%></td>
                    <td><%= m.getFechaMovimiento()%></td>
                    <td><%= m.getImporte()%></td>
                    <td><%= m.getImportePostMovimiento()%></td>
                    <td><%= m.getDetalle()%></td>
                    <td><%= m.getTipoMovimiento().getNombre()%></td>
                </tr>
            <% }
            } %>
    </tbody>
</table>

<!--  parteDerCliente   --></div>
<!-- main --></div>
</body>
</html>