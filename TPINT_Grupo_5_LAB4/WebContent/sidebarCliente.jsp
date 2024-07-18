<%
    String Nombre = "";

    // Verificar si la sesi�n est� creada
    if(session.getAttribute("NombreCliente") != null) {
        Nombre = (String) session.getAttribute("NombreCliente");
    } else {
        // Si la sesi�n no est� creada, redirigir a la p�gina de inicio de sesi�n
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
                    <li><a href="ServletCuentas?cuentasPrestamo=1"><span class="fa fa-money-bill-alt"></span> Pedir Pr�stamo</a></li>
                    <li><a href="ServletPagosCuotas?ListarPrestamos=1"><span class="fa fa-money-check-alt"></span> Pr�stamos</a></li>
                    <li><a href="ServletUbicaciones?crearUsuario=1"><span class="fa fa-money-check-alt"></span> Crear usuario</a></li>
                </ul>
            </li>
            <li><a href="ServletInfoUsuarios?infoUsuario=1"><span class="fa fa-user"></span> <%= Nombre %></a></li>
            <li><a href="Inicio_Login.jsp?cerrarSesion=true"><span class="fa fa-sign-out-alt"></span> Salir</a></li> 
        </ul>
    </div>
</div>

<% 
 // L�gica para cerrar sesi�n
    if (request.getParameter("cerrarSesion") != null) {
        // Invalidar la sesi�n actual
        session.invalidate();
        response.sendRedirect("Inicio_Login.jsp");
        return;
    }
%>


