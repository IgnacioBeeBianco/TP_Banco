
<%
    String Nombre = "";

    // Verificar si la sesión está creada
    if(session.getAttribute("NombreAdmin") != null) {
        Nombre = (String) session.getAttribute("NombreAdmin");
    } else {
        // Si la sesión no está creada, redirigir a la página de inicio de sesión
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Login.jsp");
        dispatcher.forward(request, response);
    }
%>

<div class="sidebar">
	<h1>Banco Grupo5</h1>
	<p class="highlight"><a href="ServletInfoUsuarios?infoUsuarioAdmin=1">Hola  <%= Nombre %></a></p>
	<p><a href="Inicio_Admin.jsp">Inicio</a></p>
	<p><a href="ServletInfoUsuarios?Param=ListarClientes">Administrar Clientes</a></p>
	<p><a href="ServletCuentas?Param=ListarCuentas">Administrar Cuentas</a></p>
	<p><a href="ServletPrestamos?solicitudes=1">Solicitudes de Prestamos</a></p>
	<p><a href="ServletReportes?Param=ListarStats">Reportes/Estadísticas</a></p>
    
    <form action="ServletUsuarios" method="post" >
        <p><input type="submit" name="btnSalir" value="Salir" class="btn-salir"></p>
    </form>
    
</div>


