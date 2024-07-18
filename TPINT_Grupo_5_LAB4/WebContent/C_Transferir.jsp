<%@page import="entidad.Cuenta"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel="stylesheet" media="screen" href="css/StyleSheet.css">

    <title>Transferir</title>
</head>
<script>

function confirmarTransferir(){
	return confirm('¿Estás seguro de que quieres hacer esta trasferencia?');
}

function validarMonto() {
    const montoInput = document.getElementById('monto');
   	const monto = parseFloat(montoInput.value);
    if (monto < 0) {
        montoInput.value = '';
        alert("El monto no puede ser negativo.");
      }
}

</script>
<body class="inicio-Cliente">
    <div class="encabezadoCliente">Banco Grupo5</div>
    
    <div class="main">
    <jsp:include page="sidebarCliente.jsp"></jsp:include>
    
    <div class="parteDerCliente">
            <h2>Hacer Transferencia</h2>
            <form action="ServletTransferir" method="post" class="form-container" onsubmit="return confirmarTransferir()">
                <div class="form-group">
                    <label for="cuentaOrigen">Cuenta origen:</label>
                    <select id="cuentaOrigen" name="cuentaOrigen" required>
                    <%
                        ArrayList<Cuenta> listaCuentas = (ArrayList<Cuenta>) request.getAttribute("cuentas");
                        if (listaCuentas == null) {
                            listaCuentas = new ArrayList<>();  // Inicializar como una lista vacía
                        }
                    %>
                    
                    <%
                        if (!listaCuentas.isEmpty()) {
                            for(Cuenta cue : listaCuentas) {
                    %>
                                <option value="<%= cue.getCBU() %>"><%= cue.getCBU() %></option>
                    <%
                            }
                        } else {
                    %>
                            <option>No hay cuentas disponibles</option>
                    <%
                        }
                    %>
                
                    </select>
                </div>
                <div class="form-group">
                    <label for="cbu">CBU Destino:</label>
                    <input type="text" id="cbu" name="cbu" required>
              			<% if (request.getAttribute("errorCbu") != null) { %>
			                <span style="color: red;"><%= request.getAttribute("errorCbu") %></span>
			            <% } %>
                </div>
                <div class="form-group">
                    <label for="monto">Monto:</label>
                    <input type="text" id="monto" name="monto" required oninput="validarMonto()">
               			<% if (request.getAttribute("errorMonto") != null) { %>
			                <span style="color: red;"><%= request.getAttribute("errorMonto") %></span>
			            <% } %>
                </div>
                <div class="form-group">
                <p><input type="submit" name="btnTransferir" value="Transferir" class="btn btn-primary"></p>
                               			<% if (request.getAttribute("exito") != null) { %>
			                <span style="color: green;"><%= request.getAttribute("exito") %></span>
			            <% } %>
			    </div>
            </form>
        </div>
    </div>
</body>
</html>