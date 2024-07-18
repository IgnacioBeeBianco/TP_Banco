<%@page import="java.util.ArrayList"%>
<%@page import="entidad.Prestamo"%>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

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
        $('#tabla_prestamos').DataTable();
    });
    </script>
    

    <title>Pago de Préstamos</title>

</head>
<body class="inicio-Cliente">
    <div class="encabezadoCliente">Banco Grupo5</div>
    <div class="main">
    <jsp:include page="sidebarCliente.jsp"></jsp:include>
    <div class="parteDerCliente">
        <h2>Prestamos Del Cliente</h2>
       
       
       	      <% 
	            ArrayList<Prestamo> lista = null;
	            if(request.getAttribute("Lista_De_Prestamos") != null) {
	            	lista = (ArrayList<Prestamo>) request.getAttribute("Lista_De_Prestamos");
	            }
	          %>
			<% if (lista != null && !lista.isEmpty()) { %>
            <table id="tabla_prestamos" class="display">
                <thead>
                    <tr>
                        <th>ID Prestamo</th>
                        <th>DNI Cliente</th>
                        <th>Fecha Solicitud</th>
                        <th>Importe Pedido</th>
                        <th>Plazo Meses</th>
                        <th>Cantidad Cuotas</th>
                        <th>Importe Pagar</th>
                        <th>Monto Cuota</th>
                        <th>Estado Solicitud</th>
                        <th>Estado Prestamo</th>
                        <th>CBU Depósito</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        for (Prestamo prestamo : lista) {
                    %>
                    <tr>
                        <td><%= prestamo.getId() %></td>
                        <td><%= prestamo.getCliente().getDni() %></td>
                        <td><%= prestamo.getFechaSolicitud() %></td>          
                        <td><%= prestamo.getImportePedido() %></td>
                        <td><%= prestamo.getPlazoMeses() %></td>
                        <td><%= prestamo.getCantidadCuotas() %></td>
                        <td><%= prestamo.getImportePagar() %></td>
                        <td><%= prestamo.getMontoCuota() %></td>
                        <td><%= prestamo.getEstadoSolicitud() == 1 ? "Aceptado" : prestamo.getEstadoSolicitud() == 2 ? "En espera" : "Rechazado" %></td>
                        <td><%= prestamo.getEstadoPrestamo() == 1 ? "Pagado" : "No pagado" %></td>
                        <td><%= prestamo.getCbuDeposito() %></td>
                        <td>
                            <% if (prestamo.getEstadoSolicitud() == 1) { %>
                                <!-- Botón para dirigirse a las cuotas del préstamo -->
                                <form action="ServletPagosCuotas" method="post">
                                    <input type="hidden" name="idPrestamo" value="<%= prestamo.getId() %>">
                                    <input type="submit" value="Ver Cuotas" name="btnVerCuotas" class="btn btn-vercuotas"><br>
                                </form>
                            <% } else { %>
                                <!-- Mensaje Cuando El Prestamo esta en Espera o Rechazado-->
                                No disponible
                            <% } %>
                        </td>
                    </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>
            <% } else { %>
                <p>No se encontraron préstamos para este cliente.</p>
            <% } %>
        </div>
    </div>
</body>
</html>
