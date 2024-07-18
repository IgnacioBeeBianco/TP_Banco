<%@page import="entidad.InfoUsuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Informacion de Cliente</title>
   
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
	<link rel="stylesheet" media="screen" href="css/StyleSheet.css">
</head>

<body class="inicio-Cliente">
    <div class="encabezadoCliente">Banco Grupo5</div>
    <div class="main">
    <jsp:include page="sidebarCliente.jsp"></jsp:include>

	<% 
     InfoUsuario cliente = (InfoUsuario) request.getAttribute("cliente");
     %>
            
	
    <div class="parteDerCliente">
      
         <h2>Información del Cliente</h2>
         <div class="info-cliente">
            <label>Usuario:</label>
            <span><%= cliente.getUsuario().getUsuario() %></span>
        </div>
        <div class="info-cliente">
            <label>Nombre:</label>
            <span><%= cliente.getNombre() %></span>
        </div>
        <div class="info-cliente">
            <label>Apellido:</label>
            <span><%= cliente.getApellido() %></span>
        </div>
        <div class="info-cliente">
            <label>DNI:</label>
            <span><%= cliente.getDni() %></span>
        </div>
        <div class="info-cliente">
            <label>CUIL:</label>
            <span><%= cliente.getCuil() %></span>
        </div>
        <div class="info-cliente">
            <label>Sexo:</label>
            <span><%= cliente.getSexo() %></span>
        </div>
        <div class="info-cliente">
            <label>Fecha de Nacimiento:</label>
            <span><%= cliente.getFechaNacimiento() %></span>
        </div>
        <div class="info-cliente">
            <label>Nacionalidad:</label>
            <span><%= cliente.getNacionalidad().getPais() %></span>
        </div>
        <div class="info-cliente">
            <label>Provincia:</label>
            <span><%= cliente.getProvincia().getProvincia() %></span>
        </div>
        <div class="info-cliente">
            <label>Localidad:</label>
            <span><%= cliente.getLocalidad().getLocalidad() %></span>
        </div>
        <div class="info-cliente">
            <label>Dirección:</label>
            <span><%= cliente.getDireccion() %></span>
        </div>
        <div class="info-cliente">
            <label>Mail:</label>
            <span><%= cliente.getMail() %></span>
        </div>
        <div class="info-cliente">
            <label>Teléfono principal:</label>
            <span><%= cliente.getTelefono1() %></span>
        </div>
        <div class="info-cliente">
            <label>Teléfono secundario:</label>
            <span><%= (cliente.getTelefono2() == null) ? "No posee" : cliente.getTelefono2() %></span>
        </div>
		</div>
	</div>

    
</body>
</html>