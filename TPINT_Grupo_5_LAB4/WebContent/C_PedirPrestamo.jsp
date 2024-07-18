<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">

	<link rel="stylesheet" media="screen" href="css/StyleSheet.css">
    <title>Pedir Prestamos</title>
    <script type="text/javascript">
        function actualizarCampos() {
            const interes = 1.3;
            const monto = parseFloat(document.getElementById('monto').value);
            const cantCuotas = parseInt(document.getElementById('cantidad_cuotas').value);

            if (!isNaN(monto) && !isNaN(cantCuotas)) {
                const montoPagar = monto * interes;
                const montoCuota = montoPagar / cantCuotas;

                document.getElementById('plazo_meses').value = cantCuotas;
                document.getElementById('monto_pagar').value = montoPagar.toFixed(2);
                document.getElementById('monto_cuota').value = montoCuota.toFixed(2);
            } else {
                document.getElementById('plazo_meses').value = '';
                document.getElementById('monto_pagar').value = '';
                document.getElementById('monto_cuota').value = '';
            }
        }
        
        function validarMonto() {
            const montoInput = document.getElementById('monto');
            const monto = parseFloat(montoInput.value);
            if (monto < 0) {
                montoInput.value = '';
                alert("El monto no puede ser negativo.");
            }
            actualizarCampos();
        }
        
        function confirmarPrestamo(){
        	return confirm('¿Estás seguro de que quieres pedir este prestamo?');
        }
    </script> 
</head>

<body class="inicio-Cliente">
    <div class="encabezadoCliente">Banco Grupo5</div>
    <div class="main">
    <jsp:include page="sidebarCliente.jsp"></jsp:include>

    
    <div class="parteDerCliente">
    <% if (request.getAttribute("MensajeExito") != null) {%> 
			<span class="message-exito"><%= request.getAttribute("MensajeExito") %></span>
	<%} else if (request.getAttribute("MensajeError") != null) {%> 
			<span class="message-error"><%= request.getAttribute("MensajeError") %></span>
	<%} %>
	<h2>Pedir Préstamo</h2>
<form action="ServletPrestamos" method="post" class="form-container" onsubmit="return confirmarPrestamo()">
    <div class="form-group">
        <label for="cuenta_Origen">Cuenta:</label>
        <select id="cuenta_Origen" name="cuenta_Origen" required>
            <% 
            if (request.getAttribute("cuentasCliente") != null) {
                List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentasCliente");
                for (Cuenta c : cuentas) {
            %>
                    <option value="<%= c.getCBU() %>"><%= c.getTipoCuenta().getDescripcion() + " - " + c.getCBU() %></option>
            <% 
                }
            }
            %>
        </select>
    </div>
    <div class="form-group">
        <label for="monto">Monto:</label>
        <input type="text" id="monto" name="monto" required oninput="validarMonto()">
    </div>
    <div class="form-group">
        <label for="plazo_meses">Plazo en meses:</label>
        <input type="text" id="plazo_meses" name="plazo_meses" readonly>
    </div>
    <div class="form-group">
        <label for="cantidad_cuotas">Cantidad de Cuotas:</label>
        <select id="cantidad_cuotas" name="cantidad_cuotas" required onchange="actualizarCampos()">
            <option value="6">6</option>
            <option value="12">12</option>
            <option value="18">18</option>
            <option value="24">24</option>
            <option value="30">30</option>
        </select>
    </div>
    <div class="form-group">
        <label for="monto_pagar">Monto a Pagar (Interés 30%):</label>
        <input type="text" id="monto_pagar" name="monto_pagar" readonly>
    </div>
    <div class="form-group">
        <label for="monto_cuota">Monto de la Cuota:</label>
        <input type="text" id="monto_cuota" name="monto_cuota" readonly>
    </div>
    <div class="form-group">
		<p><input type="submit" name="btnPedirPrestamo" value="Pedir Prestamo" class="btn btn-primary"></p>
	</div>
</form>
   </div>
<!-- main        --></div>
</body>
    
</html>