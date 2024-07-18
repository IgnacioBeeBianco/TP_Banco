<%@page import="entidad.Cuenta"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrar Cuentas</title>
    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">-->
    <link rel="stylesheet" type="text/css" href="css/StyleSheet2.css">
</head>

<!-- Paginación tabla -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script>
$(document).ready( function () {
    $('#tabla').DataTable();
} );

function confirmarEliminacion() {
    return confirm('¿Estás seguro de que quieres eliminar esta cuenta?');
}

function confirmarModificacion(){
	return confirm('¿Estás seguro de que quieres modificar esta cuenta?');
}
</script>

<body>

<div class="container">
  <jsp:include page="sidebarAdmin.jsp"></jsp:include>
     
     <div class="main">

    <button type="button" class="btn btn-edit" onclick="window.location.href='ServletTipoCuenta?Param=DesplegableTipoCuentaYSigNumCuenta'">Agregar Cuenta</button><br>
     
     
<% 
	ArrayList<Cuenta> listaCuentas = null;
	if(request.getAttribute("listaCue") != null) {
		listaCuentas = (ArrayList<Cuenta>) request.getAttribute("listaCue");
	}
%>

                        <div>
						<% if (request.getAttribute("MensajeExito") != null) {%> 
						<span style="color: green;"><%= request.getAttribute("MensajeExito") %></span>
						<%} else if (request.getAttribute("MensajeError") != null) {%> 
						<span style="color: red;"><%= request.getAttribute("MensajeError") %></span>
						<%} %>
						</div>			

<table id="tabla">
    <thead>
        <tr>
            <th>DNI Cliente</th>
            <th>Nombre y Apellido</th>
            <th>CBU</th>
            <th>Número de Cuenta</th>
            <th>Tipo de Cuenta</th>
            <th>Fecha de Creación</th>
            <th>Saldo</th>
            <th>Estado</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <% if(listaCuentas != null) {
            for(entidad.Cuenta cuenta : listaCuentas) { %>
                <tr>
                    <td><%= cuenta.getDNI() %></td>
                    <td><%= cuenta.getNombreyApellido() %></td>
                    <td><%= cuenta.getCBU() %></td>
                    <td><%= cuenta.getNumeroCuenta() %></td>
                    <td><%= cuenta.getTipoCuenta().getDescripcion() %></td>
                    <td><%= cuenta.getFechaCreacion() %></td>
                    <td>$<%= cuenta.getSaldo() %></td>
                    <td><%= cuenta.isEstado() ? "Activa" : "Inactiva" %></td>
                    <td>
                    <form method="post" action="ServletCuentas" id="FormModificarCuenta">
                        <button type="submit" name="modificar" class="btn btn-edit">Modificar</button>
                        <input type="hidden" name="cbu" value="<%= cuenta.getCBU() %>">
                    </form>
                    <form method="post" action="ServletCuentas" id="FormularioEliminarCuenta" onsubmit="return confirmarEliminacion();">
				        <button type="submit" name="btnDelete" value="Eliminar" class="btn btn-delete">Eliminar</button>
                        <input type="hidden" name="cbu" value="<%= cuenta.getCBU() %>">
                    </form>
                    </td>
                </tr>
        <%    }
        } %>
    </tbody>
</table>

		              

</div>
                       
</div>
<% 
Cuenta cuenta = new Cuenta();
if (request.getAttribute("cuentaModificar") != null) {
	cuenta = (Cuenta)request.getAttribute("cuentaModificar");
%>
<div class="modalBackground-cuentas">
  <form id="modificarCuenta" method="post" action="ServletCuentas" class="modal-cuentas formulario-cliente">
    <div>
      <h2>Modificar Cuenta</h2>
      <fieldset>
        <div class="form-group"><label>
          Número de Cuenta<br>
          <input type="text" name="nroCuentaDisabled" value="<%= cuenta.getNumeroCuenta() %>" disabled><br>
        </label></div>
        <div class="form-group"><label>
          CBU<br>
          <input type="text" name="CBUDisabled" value="<%= cuenta.getCBU() %>" disabled><br>
        </label></div>
        <div class="form-group"><label>
          DNI Cliente Asignado<br>
        <input type="text" name="dni" id="edit_dni" value="<%= cuenta.getDNI() %>" readonly required><br>
        </label></div>
        <div class="form-group"><label>
          Tipo de Cuenta<br>
          <select name="tipoCuenta" required>
          	<option value="1" 
          	<%= (cuenta.getTipoCuenta().getIdTipoCuenta() == 1) ? "selected" : "" %>
          	>Cuenta Corriente</option>
          	<option value="2" 
          	<%= (cuenta.getTipoCuenta().getIdTipoCuenta() == 2) ? "selected" : "" %>
          	>Caja de Ahorro</option>
          </select><br>
        </label></div>
        <div class="form-group"><label>
          Fecha de Creación<br>
          <input type="text" name="fechaDisabled" value="<%= cuenta.getFechaCreacion().toString() %>" disabled><br>
        </label></div>
        <div class="form-group"><label>
          Saldo<br>
          <input type="text" name="saldo" value="<%= cuenta.getSaldo().toString() %>" required><br>
        </label></div>
        <div class="form-group"><label>
          Estado<br>
          <select name="estado" required>
          	<option value="True" 
          	<%= (cuenta.isEstado()) ? "selected" : "" %>
          	>Activo</option>
          	<option value="False" 
          	<%= (!cuenta.isEstado()) ? "selected" : "" %>
          	>Inactivo</option>
          </select>
        </label></div>
        <input type="hidden" name="CBU" value="<%= cuenta.getCBU() %>">
        <input type="hidden" name="fechaCreacion" value="<%= cuenta.getFechaCreacion().toString() %>">
        <input type="hidden" name="numeroCuenta" value="<%= cuenta.getNumeroCuenta() %>">
      </fieldset>
      <footer>
        <div class="form-group">
        <button type="submit" name="aceptarModificarCuenta" class="btn btn-primary" onclick="return confirmarModificacion()">Aceptar</button>
		<button type="button" class="btn btn-secondary" onclick="window.location.href='ServletCuentas?Param=ListarCuentas'">Cancelar</button>
        </div>
      </footer>
    </div>
  </form>
</div>
<%}%>
</body>
</html>