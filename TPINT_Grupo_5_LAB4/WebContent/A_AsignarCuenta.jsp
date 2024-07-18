<%@page import="entidad.TipoCuenta"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asignar Cuenta</title>
    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">-->
    <link rel="stylesheet" type="text/css" href="css/StyleSheet2.css">
</head>
<script>

function confirmarAsignar(){
	return confirm('¿Estás seguro de que quieres asignar esta cuenta?');
}

</script>
<body>

<div class="container">
  <jsp:include page="sidebarAdmin.jsp"></jsp:include>

        <div class="main">
        <button type="button" class="btn btn-edit" onclick="window.location.href='ServletCuentas?Param=ListarCuentas'">Administrar Cuentas</button><br>
     <%    
     ArrayList<TipoCuenta> listaTipos_DeCuentas= null;
	if(request.getAttribute("listaTipoCuentas")!=null)
	{
		listaTipos_DeCuentas = (ArrayList<TipoCuenta>) request.getAttribute("listaTipoCuentas");
	}
	%>
        
<form method="post" action="ServletCuentas" class="formulario-cliente" onsubmit="return confirmarAsignar()">
			
			<fieldset>
			    <legend>Asignar Cuenta</legend>
			    
			    <div class="form-group">
		        <label for="nrocuenta">Número de Cuenta</label>
				<%
				    Integer siguienteNumeroCuenta = (Integer) request.getAttribute("Siguiente_Num_Cuenta");
				    if (siguienteNumeroCuenta != null) {
				%>
				    <input id="nrocuenta" type="text" readonly value="<%= siguienteNumeroCuenta %>" />
				<%
				    } %>  		       
		       </div>
			    
			    <div class="form-group">
			        <label for="dni">DNI del Cliente</label>
			        <input id="dni" type="text" placeholder="Ingrese DNI" required name="txtDni">
			        <% if (request.getAttribute("errorDNI") != null) { %>
						    <span style="color: red;"><%= request.getAttribute("errorDNI") %></span>
					<% } %>
			    </div>
			    
			    <div class="form-group">
			        <label for="tipocuenta">Tipo de Cuenta</label>
			        
				 <select id="tipocuenta" name="tipocuenta" required>
				        <option value="">---seleccionar tipo de cuenta---</option>
				        <%  
				            if (listaTipos_DeCuentas != null) {
				                for (TipoCuenta Tc : listaTipos_DeCuentas) { 
				        %>       
				            <option value="<%= Tc.getIdTipoCuenta() %>"><%= Tc.getDescripcion() %></option>
				        <%      
				                }
				            }
				        %>
				 </select>
			        
			    </div>
			    
			    <p>Saldo inicial: $10.000</p>
			    
			    <div class="form-group">
			        <input id="btnAsignar" type="submit" value="Asignar" required name="btnAsignarCuenta" class="btn btn-primary">
			    
				
				
							
				<%
				    int filas = 0;
				
				    if (request.getAttribute("AgregadoExito") != null) {
				        filas = (int) request.getAttribute("AgregadoExito");
				    }
				%>
				
				<% if (filas == 1) { %>
				    <span style="color: green;">Se asignó una cuenta al cliente.</span>
				<% } %>
							    
			    			    
			    
			    </div>
			</fieldset>
   
</form>

				
        
        </div>
    </div>


</body>

</html>
