<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">-->

    <link rel="stylesheet" type="text/css" href="css/StyleSheet.css">

	<title>Banco Grupo5 - Login</title>
</head>

<body class="centro">
<div class="encabezado">Banco Grupo5</div>


<div class="parteCentro">
			    <form method="post" action="ServletUsuarios">
			    <fieldset>
			        <legend>¡Hola! Te damos la bienvenida a Banca Online</legend>
			        
			        <!-- Mensaje de error para usuario incorrecto -->
			        <p>
			            <label for="usuario">Usuario</label>
			          <input id="usuario" type="text" placeholder="Ingrese su Usuario" required name="txtusuario" 
					    <% if (request.getAttribute("usuario") != null) { %>
					        value="<%= request.getAttribute("usuario") %>"
					    <% } %>
					  >
						<% if (request.getAttribute("errorUsuario") != null) { %>
						    <span style="color: red;"><%= request.getAttribute("errorUsuario") %></span>
						<% } %>
			        </p>
			        
			        <!-- Mensaje de error para clave incorrecta -->
			        <p>
			            <label for="clave">Clave Digital</label>
			            <input id="clave" type="password" placeholder="Ingrese su clave Digital" required name="txtclave">
			            <% if (request.getAttribute("errorClave") != null) { %>
			                <span style="color: red;"><%= request.getAttribute("errorClave") %></span>
			            <% } %>
			        </p>
			        
			        <p>
			            <input id="btnAceptar" type="submit" value="Aceptar" required name="btnAceptarLogin">
			        </p>
			    </fieldset>
			 </form>
</div>


</body>
</html>