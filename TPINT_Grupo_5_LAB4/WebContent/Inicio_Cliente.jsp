<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
	<link rel="stylesheet" media="screen" href="css/StyleSheet.css">

<title>Inicio</title>

</head>
<body class="inicio-Cliente">

    <div class="encabezadoCliente">Banco Grupo5</div>
    <div class="main">
        <jsp:include page="sidebarCliente.jsp"></jsp:include>
        <div class="parteDerCliente">
  
       <% if (request.getAttribute("MensajeExito") != null) { %>
      <div class="mensaje-exito"><%= request.getAttribute("MensajeExito") %></div>
      <% } %>
      <% if (request.getAttribute("MensajeError") != null) { %>
     <div class="mensaje-error"><%= request.getAttribute("MensajeError") %></div>
      <% } %>
   
        <form action="ServletMovimientos" method="post">
        
    	</form>
            <% 
            if (request.getAttribute("cuentasCliente") != null) {
                List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentasCliente");
                for (Cuenta c : cuentas) {
            %>
                <form method="post" action="ServletMovimientos">
                    <input type="hidden" name="NumeroCuenta" value="<%= c.getNumeroCuenta() %>">
                    <input type="hidden" name="SaldoCuenta" value="<%= c.getSaldo() %>">
                    <input type="submit" value="<%= c.getTipoCuenta().getDescripcion() %> - N°<%= c.getNumeroCuenta() %> - <%= c.getSaldo() %> $" name="btnCuenta" class="botonCuenta"><br>
                </form>
            <% 
                }
            }
            %>
        </div>
    </div>
</body>
</html>