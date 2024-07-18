<%
    String Nombre = "";

    // Verificar si la sesión está creada
    if(session.getAttribute("NombreCliente") != null) {
        Nombre = (String) session.getAttribute("NombreCliente");
    } else {
        // Si la sesión no está creada, redirigir a la página de inicio de sesión
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Inicio_Login.jsp");
        dispatcher.forward(request, response);
        return;
    }
%>

<div class="parteIzqCliente">
    <div class="menuCliente">
        <ul>
            <li>
                <a href="Inicio_Cliente.jsp">
                    <span class="fa fa-home"></span> Menu
                </a>
                <ul>
                    <li><a href="ServletCuentas?cuentasCliente=1"><span class="fa fa-credit-card"></span> Cuentas</a></li>
                    <li><a href="ServletTransferir?transferirCliente=1"><span class="fa fa-exchange-alt"></span> Transferir</a></li>
                    <li><a href="ServletCuentas?cuentasPrestamo=1"><span class="fa fa-money-bill-alt"></span> Pedir Préstamo</a></li>
                    <li><a href="ServletPagosCuotas?ListarPrestamos=1"><span class="fa fa-money-check-alt"></span> Préstamos</a></li>
                    <li><a href="ServletUbicaciones?crearUsuario=1"><span class="fa fa-money-check-alt"></span> Crear usuario</a></li>
                </ul>
            </li>
            <li><a href="ServletInfoUsuarios?infoUsuario=1"><span class="fa fa-user"></span> <%= Nombre %></a></li>
            <li><a href="Inicio_Login.jsp?cerrarSesion=true"><span class="fa fa-sign-out-alt"></span> Salir</a></li> 
        </ul>
    </div>
</div>

<% 
 // Lógica para cerrar sesión
    if (request.getParameter("cerrarSesion") != null) {
        // Invalidar la sesión actual
        session.invalidate();
        response.sendRedirect("Inicio_Login.jsp");
        return;
    }
%>


