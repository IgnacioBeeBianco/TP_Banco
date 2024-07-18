<%@page import="entidad.InfoUsuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html lang="es">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Administrar Clientes</title>
	    <link rel="stylesheet" type="text/css" href="css/StyleSheet2.css">
	    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
</head>

<div class="container">
  	<jsp:include page="sidebarAdmin.jsp"></jsp:include>
    <div class="main">

	<% 
     InfoUsuario admin = (InfoUsuario) request.getAttribute("admin");
     %>
            

      
         <h2>Información del Administrador</h2>
         <div class="info-admin">
            <label>Usuario:</label>
            <span><%= admin.getUsuario().getUsuario() %></span>
        </div>
        <div class="info-admin">
            <label>Nombre:</label>
            <span><%= admin.getNombre() %></span>
        </div>
        <div class="info-admin">
            <label>Apellido:</label>
            <span><%= admin.getApellido() %></span>
        </div>
        <div class="info-admin">
            <label>DNI:</label>
            <span><%= admin.getDni() %></span>
        </div>
        <div class="info-admin">
            <label>CUIL:</label>
            <span><%= admin.getCuil() %></span>
        </div>
        <div class="info-admin">
            <label>Sexo:</label>
            <span><%= admin.getSexo() %></span>
        </div>
        <div class="info-admin">
            <label>Fecha de Nacimiento:</label>
            <span><%= admin.getFechaNacimiento() %></span>
        </div>
        <div class="info-admin">
            <label>Nacionalidad:</label>
            <span><%= admin.getNacionalidad().getPais() %></span>
        </div>
        <div class="info-admin">
            <label>Provincia:</label>
            <span><%= admin.getProvincia().getProvincia() %></span>
        </div>
        <div class="info-admin">
            <label>Localidad:</label>
            <span><%= admin.getLocalidad().getLocalidad() %></span>
        </div>
        <div class="info-admin">
            <label>Dirección:</label>
            <span><%= admin.getDireccion() %></span>
        </div>
        <div class="info-admin">
            <label>Mail:</label>
            <span><%= admin.getMail() %></span>
        </div>
        <div class="info-admin">
            <label>Teléfono principal:</label>
            <span><%= admin.getTelefono1() %></span>
        </div>
        <div class="info-admin">
            <label>Teléfono secundario:</label>
            <span><%= (admin.getTelefono2() == null) ? "No posee" : admin.getTelefono2() %></span>
        </div>
		</div>
	</div>

</body>
</html>