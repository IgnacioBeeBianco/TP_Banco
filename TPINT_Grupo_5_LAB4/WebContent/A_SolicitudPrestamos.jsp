<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Prestamo"%>
<%@ page import="entidad.InfoUsuario"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html><html lang="es"><head><meta charset="ISO-8859-1">

	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<link rel="stylesheet" type="text/css" href="css/StyleSheet2.css">

	<link rel="stylesheet" type="text/css"
		href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#table_id').DataTable();
		});
		
		function aprobarPrestamo() {
	        return confirm('¿Estás seguro de que quieres aceptar este prestamo?');
	    }
	    
	    function rechazarPrestamo() {
	        return confirm('¿Estás seguro de que quieres rechazar este prestamo?');
	    }
	</script>

	<title>Solicitud de Prestamos</title>

</head>
<body>
    <div class="container">
        <jsp:include page="sidebarAdmin.jsp"></jsp:include>
        <div class="main">
            <h2>Administración Solicitudes De Prestamos</h2>
            <% if (request.getAttribute("MensajeExito") != null) { %>
                <span class="message-exito"><%= request.getAttribute("MensajeExito") %></span>
            <% } else if (request.getAttribute("MensajeError") != null) { %>
                <span class="message-error"><%= request.getAttribute("MensajeError") %></span>
            <% } %>
            <%
            List<Prestamo> solicitudes = (List<Prestamo>) request.getAttribute("solicitudes");
            if (solicitudes == null || solicitudes.isEmpty()) {
            %>
                <p>No hay prestamos a revisar</p>
            <% } else { %>
                <table id="table_id" class="display">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>DNI Cliente</th>
                            <th>Importe pedido</th>
                            <th>Importe a pagar</th>
                            <th>Plazo meses</th>
                            <th>Cantidad Cuotas</th>
                            <th>Monto Cuota</th>
                            <th>Fecha Solicitud</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (Prestamo s : solicitudes) { %>
                        <tr>
                            <td><%= s.getId() %></td>
                            <td><%= s.getCliente().getDni() %></td>
                            <td><%= s.getImportePedido() %></td>
                            <td><%= s.getImportePagar() %></td>
                            <td><%= s.getPlazoMeses() %></td>
                            <td><%= s.getCantidadCuotas() %></td>
                            <td><%= s.getMontoCuota() %></td>
                            <td><%= s.getFechaSolicitud() %></td>
                            <td>
                                <form action="ServletPrestamos" method="post">
                                    <input type="hidden" name="id" value="<%= s.getId() %>">
                                    <button type="submit" name="aceptar" value="true" onclick="return aprobarPrestamo();" class="btn btn-primary">Aprobar</button>
                                    <button type="submit" name="rechazar" value="true" onclick="return rechazarPrestamo();" class="btn btn-secondary">Rechazar</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
    </div>
</body>
</html>