<%@ page import="entidad.Pais" %>
<%@ page import="entidad.Provincia" %>
<%@ page import="entidad.Localidad" %>
<%@ page import="entidad.InfoUsuario" %>
<%@ page import="entidad.Usuario" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Cliente</title>
    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">-->
    <link rel="stylesheet" type="text/css" href="css/StyleSheet.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="JavaScript/jsAdminClientes.js"></script>
	
	<script>
	function confirmarAgregar() {
	    return confirm('¿Estas seguro de que quieres agregar este cliente?');
	}
	</script>
	
</head>

<body class="inicio-Cliente">
	<div class="encabezadoCliente">Banco Grupo5</div>
    <div class="main">
  	<jsp:include page="sidebarCliente.jsp"></jsp:include>
    
    <div class="parteDerCliente">
    
<%
InfoUsuario cliente = null;
if (request.getAttribute("cliente") != null) {
	cliente = new InfoUsuario();
	cliente = (InfoUsuario)request.getAttribute("cliente");
}
%>
    
<form id="formAgregar" method="post" action="ServletInfoUsuarios" class="formulario-cliente" onsubmit="return confirmarAgregar()">
    <fieldset>
        <legend>Agregar Cliente</legend>
        
		<div class="form-group">
		    <label for="dni">DNI:</label>
		    <input type="text" id="dni" placeholder="Ingrese el DNI" required name="dni" maxlength="8"
		           value="<%= cliente != null ? (request.getAttribute("ErrorDNI") != null ? "" : cliente.getDni()) : "" %>">
		    <% if (request.getAttribute("ErrorDNI") != null) { %>
		        <span style="color: red;"><%= request.getAttribute("ErrorDNI") %></span>
		    <% } %>
		</div>
        
        <div class="form-group">
            <label for="usuario">Usuario:</label>
            <input type="text" id="usuario" placeholder="Ingrese el usuario" required name="usuario"
            value="<%= cliente != null ? cliente.getUsuario().getUsuario() : "" %>">
            <% if (request.getAttribute("ErrorUsuario") != null) { %>
		        <span style="color: red;"><%= request.getAttribute("ErrorUsuario") %></span>
		    <% } %>
        </div>

        <div class="form-group">
            <label for="password">Constraseña:</label>
            <input type="password" id="password" placeholder="Ingrese la contraseña" required name="password">
        </div>

        <div class="form-group">
            <label for="confirm-password">Repita Constraseña:</label>
            <input type="password" id="confirm-password" placeholder="Ingrese la contraseña otra vez" required name="confirm-password">
        </div>

		<div class="form-group">
		    <label for="cuil">CUIL:</label>
		    <input type="text" id="cuil" placeholder="Ingrese el CUIL" required name="cuil" maxlength="11"
            value="<%= cliente != null ? (request.getAttribute("ErrorCUIL") != null ? "" : cliente.getCuil()) : "" %>">
		    
		    <% if (request.getAttribute("ErrorCUIL") != null) { %>
		        <span style="color: red;"><%= request.getAttribute("ErrorCUIL") %></span>
		    <% } %>
		    <% if (request.getAttribute("ErrorCUIL2") != null) { %>
		     <span style="color: red;"><%= request.getAttribute("ErrorCUIL2") %></span>
		    <% } %>
		    
		</div>
        
        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" placeholder="Ingrese sus nombres" required name="nombre"
            value="<%= cliente != null ? cliente.getNombre() : "" %>">
        </div>
        
        <div class="form-group">
            <label for="apellido">Apellidos:</label>
            <input type="text" id="apellido" placeholder="Ingrese sus apellidos" required name="apellido"
            value="<%= cliente != null ? cliente.getApellido() : "" %>">
        </div>
        
        <div class="form-group">
            <label for="sexo">Sexo:</label>
            <select id="sexo" name="sexo" required>
                <option value="Masculino" 
                <%= (cliente != null && cliente.getSexo() == "Masculino") ? "selected" : "" %>
                >Masculino</option>
                <option value="Femenino"
                <%= (cliente != null && cliente.getSexo() == "Femenino") ? "selected" : "" %>
                >Femenino</option>
                <option value="Otro"
                <%= (cliente != null && cliente.getSexo() == "Otro") ? "selected" : "" %>
                >Otro</option>
            </select>
        </div>
        
        <div class="form-group">
            <label for="nacionalidad">Nacionalidad:</label>
            <select id="nacionalidad" required name="nacionalidad">
     		<%
     			ArrayList<Pais> paises = new ArrayList<Pais>();
     			if (request.getAttribute("paises") != null) {
					paises = (ArrayList<Pais>)request.getAttribute("paises");
					
					for (Pais p : paises) {
					%>
					<option value="<%= p.getID_Nacionalidad() %>"
					<%= ((cliente != null) && (cliente.getNacionalidad().getID_Nacionalidad() == p.getID_Nacionalidad())) ? "selected" : "" %>
					><%= p.getPais() %></option>
					<% 
					}
     			}
     		%>
            </select>
        </div>
        
        <div class="form-group">
            <label for="fecha_nacimiento">Fecha de Nacimiento:</label>
            <input type="date" id="fecha_nacimiento" required name="fecha_nacimiento"
            value="<%= cliente != null ? (cliente.getFechaNacimiento() != null) ? cliente.getFechaNacimiento() : "" : "" %>">
        </div>
        
        <div class="form-group">
            <label for="provincia">Provincia:</label>
            <select id="provincia" required name="provincia">
     		<%
     			ArrayList<Provincia> provincias = new ArrayList<Provincia>();
     			if (request.getAttribute("provincias") != null) {
					provincias = (ArrayList<Provincia>)request.getAttribute("provincias");
					
					for (Provincia p : provincias) {
					%>
					<option value="<%= p.getID_Provincia() %>" 
					<%= ((cliente != null) && (cliente.getProvincia().getID_Provincia() == p.getID_Provincia())) ? "selected" : "" %>
					><%= p.getProvincia() %></option>
					<% 
					}
     			}
     		%>
     		</select>
        </div>
        
        <div class="form-group">
            <label for="localidad">Localidad:</label>
            <select id="localidad" required name="localidad">
     		<%
     			ArrayList<Localidad> localidades = new ArrayList<Localidad>();
     			if (request.getAttribute("localidades") != null) {
					localidades = (ArrayList<Localidad>)request.getAttribute("localidades");
					for (Localidad l : localidades) {
					%>
					<option value="<%= l.getID_Localidad() %>"
					<%= ((cliente != null) && (cliente.getLocalidad().getID_Localidad() == l.getID_Localidad())) ? "selected" : "" %>
					><%= l.getLocalidad() %></option>
					<% 
					}
     			}
     		%>
     		</select>
        </div>
        
        <div class="form-group">
            <label for="direccion">Dirección:</label>
            <input type="text" id="direccion" placeholder="Ingrese la dirección" required name="direccion"
            value="<%= cliente != null ? cliente.getDireccion() : "" %>">
        </div>

        <div class="form-group">
            <label for="correo">Correo Electrónico:</label>
            <input type="email" id="correo" placeholder="Ingrese correo electrónico" required name="correo"
            value="<%= cliente != null ? cliente.getMail() : "" %>">
        </div>
        
        <div class="form-group">
            <label for="telefono">Telefono pricipal:</label>
            <input type="text" id="telefono1" placeholder="Ingrese el telefono" required name="telefono1" maxlength="10"
            value="<%= cliente != null ? cliente.getTelefono1() : "" %>">
        </div>
        
        <div class="form-group">
            <label for="telefono">Telefono secundario:</label>
            <input type="text" id="telefono2" placeholder="Ingrese el telefono" name="telefono2" maxlength="10"
            value="<%= cliente != null ? cliente.getTelefono2() : "" %>">
        </div>
        
        <div class="form-group">
        <button type="submit" name="btnAgregarCliente" value="Agregar Cliente" class="btn btn-primary">Agregar Cliente</button>
    	<button type="button" name="btnCancelarCliente" id="btnCancelarCliente" class="btn btn-secondary">Cancelar</button>
        </div>

    </fieldset>

</form>
</div>
</div>

</body>
</html>