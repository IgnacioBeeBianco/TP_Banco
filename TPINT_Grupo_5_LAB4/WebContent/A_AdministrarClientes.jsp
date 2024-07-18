	<%@page import="java.util.ArrayList"%>
	<%@page import="entidad.InfoUsuario"%>
	<%@ page import="entidad.Pais" %>
	<%@ page import="entidad.Provincia" %>
	<%@ page import="entidad.Localidad" %>
	
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
		<script src="JavaScript/jsAdminClientes.js"></script>
			    
	</head>
	
	<body>
	    <div class="container">
	        <%@include file="sidebarAdmin.jsp" %>
	        
	        <div class="main">
	            <h2>Administración de Clientes</h2>
	            <button type="button" class="btn btn-edit" onclick="window.location.href='ServletUbicaciones?agregarCliente=1'">Agregar Cliente</button>
	            
	            <% 
	                ArrayList<InfoUsuario> listaClientes = null;
	                if(request.getAttribute("listaCli") != null) {
	                    listaClientes = (ArrayList<InfoUsuario>) request.getAttribute("listaCli");
	                }
	            %>
	            
	            <% 
	                ArrayList<InfoUsuario> listaAdmins = null;
	                if(request.getAttribute("listaAdmins") != null) {
	                    listaAdmins = (ArrayList<InfoUsuario>) request.getAttribute("listaAdmins");
	                }
	            %>
<% if (request.getAttribute("MensajeExito") != null) {%> 
<span class="message-exito"><%= request.getAttribute("MensajeExito") %></span>
<%} else if (request.getAttribute("MensajeError") != null) {%> 
<span class="message-error"><%= request.getAttribute("MensajeError") %></span>
<%} %>
	    <!-- Mensaje de Aviso de Modificar Cliente -->
  <% if (request.getAttribute("ErrorCUIL") != null) { %>
  <span style="color: red;"><%= request.getAttribute("ErrorCUIL") %></span>
  <% } %>

				<h4>Clientes</h4>
	            <table id="table_id" class="display">
	                <thead>
	                    <tr>
	                        <th>DNI</th>
	                        <th>Usuario</th>
	                        <th>Contraseña</th>
	                        <th>CUIL</th>
	                        <th>Nombre</th>
	                        <th>Apellido</th>
	                        <th>Sexo</th>
	                        <th>Nacionalidad</th>
	                        <th>Fecha de Nacimiento</th>
	                        <th>Provincia</th>
	                        <th>Localidad</th>
	                        <th>Dirección</th>
	                        <th>Correo Electrónico</th>
	                        <th>Telefono principal</th>
	                        <th>Telefono secundario</th>
	                        <th>Acciones</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <% if(listaClientes != null) {
	                        for(InfoUsuario user : listaClientes) { %>
	                            <tr>
	                                <td><%= user.getDni() %></td>
	                                <td><%= user.getUsuario().getUsuario() %></td>
	                                <td>*********</td>
	                                <td><%= user.getCuil() %></td>
	                                <td><%= user.getNombre() %></td>
	                                <td><%= user.getApellido() %></td>
	                                <td><%= user.getSexo() %></td>
	                                <td><%= user.getNacionalidad().getPais() %></td>
	                                <td><%= user.getFechaNacimiento() %></td>
	                                <td><%= user.getProvincia().getProvincia() %></td>
	                                <td><%= user.getLocalidad().getLocalidad() %></td>
	                                <td><%= user.getDireccion() %></td>
	                                <td><%= user.getMail() %></td>
	                                <td><%= user.getTelefono1() %></td>
	                                <td><%= (user.getTelefono2() == null) ? "No posee" : user.getTelefono2() %></td>
	                                <td>
	                                    <!-- Formulario para eliminar usuario -->
	                                    <form method="post" action="ServletInfoUsuarios" onsubmit="return confirmarEliminacion();">
	                                        <input type="hidden" name="dni" value="<%= user.getDni() %>">
	                                        <input type="submit" class="btn btn-delete" name="btnDelete" value="Eliminar">
	                                    </form>
	                                    <!-- Botón para editar (modal) -->
	                                    <button type="button" class="btn btn-edit" onclick="showModal('<%= user.getNombre() %>', '<%= user.getUsuario().getUsuario() %>', '<%= user.getUsuario().getContraseña() %>')">Editar contraseña</button>
	                                	<button type="button" class="btn btn-edit" onclick="showClienteModal('<%= user.getDni() %>', '<%= user.getUsuario().getUsuario() %>', '<%= user.getCuil() %>', '<%= user.getNombre() %>', '<%= user.getApellido() %>', '<%= user.getSexo() %>', '<%= user.getNacionalidad().getID_Nacionalidad() %>', '<%= user.getFechaNacimiento() %>', '<%= user.getProvincia().getID_Provincia() %>', '<%= user.getLocalidad().getID_Localidad() %>', '<%= user.getDireccion() %>', '<%= user.getMail() %>', '<%= user.getTelefono1() %>', '<%= user.getTelefono2() %>')">Editar datos cliente</button>
	                                	<button type="button" class="btn btn-edit" onclick="showModalAdmin('<%= user.getNombre() %>', '<%= user.getUsuario().getUsuario() %>', <%= user.getUsuario().isEsAdmin() %>)">Dar rol Administrador</button>
	                                </td>
	                            </tr>
	                    <%    }
	                    } %>
	                </tbody>
	            </table>
	            
	            <button class="btn btn-edit" onclick="mostrarTablaAdmin()">Mostrar/Ocultar Tabla Administradores</button>
	            <h4 id="h4_admin">Administradores</h4>
	            <table id="table_idAdmin" class="display">
	                <thead>
	                    <tr>
	                        <th>DNI</th>
	                        <th>Usuario</th>
	                        <th>Contraseña</th>
	                        <th>CUIL</th>
	                        <th>Nombre</th>
	                        <th>Apellido</th>
	                        <th>Sexo</th>
	                        <th>Nacionalidad</th>
	                        <th>Fecha de Nacimiento</th>
	                        <th>Provincia</th>
	                        <th>Localidad</th>
	                        <th>Dirección</th>
	                        <th>Correo Electrónico</th>
	                        <th>Telefono principal</th>
	                        <th>Telefono secundario</th>
	                        <th>Acciones</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <% if(listaAdmins != null) {
	                        for(InfoUsuario user : listaAdmins) { %>
	                            <tr>
	                                <td><%= user.getDni() %></td>
	                                <td><%= user.getUsuario().getUsuario() %></td>
	                                <td>*********</td>
	                                <td><%= user.getCuil() %></td>
	                                <td><%= user.getNombre() %></td>
	                                <td><%= user.getApellido() %></td>
	                                <td><%= user.getSexo() %></td>
	                                <td><%= user.getNacionalidad().getPais() %></td>
	                                <td><%= user.getFechaNacimiento() %></td>
	                                <td><%= user.getProvincia().getProvincia() %></td>
	                                <td><%= user.getLocalidad().getLocalidad() %></td>
	                                <td><%= user.getDireccion() %></td>
	                                <td><%= user.getMail() %></td>
	                                <td><%= user.getTelefono1() %></td>
	                                <td><%= (user.getTelefono2() == null) ? "No posee" : user.getTelefono2() %></td>
	                                <td>
	                                    <!-- Formulario para eliminar usuario -->
	                                    <form method="post" action="ServletInfoUsuarios" onsubmit="return confirmarEliminacion();">
	                                        <input type="hidden" name="dni" value="<%= user.getDni() %>">
	                                        <input type="submit" class="btn btn-delete" name="btnDelete" value="Eliminar">
	                                    </form>
	                                    <!-- Botón para editar (modal) -->
	                                    <button type="button" class="btn btn-edit" onclick="showModal('<%= user.getNombre() %>', '<%= user.getUsuario().getUsuario() %>', '<%= user.getUsuario().getContraseña() %>')">Editar contraseña</button>
	                                	<button type="button" class="btn btn-edit" onclick="showClienteModal('<%= user.getDni() %>', '<%= user.getUsuario().getUsuario() %>', '<%= user.getCuil() %>', '<%= user.getNombre() %>', '<%= user.getApellido() %>', '<%= user.getSexo() %>', '<%= user.getNacionalidad().getID_Nacionalidad() %>', '<%= user.getFechaNacimiento() %>', '<%= user.getProvincia().getID_Provincia() %>', '<%= user.getLocalidad().getID_Localidad() %>', '<%= user.getDireccion() %>', '<%= user.getMail() %>', '<%= user.getTelefono1() %>', '<%= user.getTelefono2() %>')">Editar datos admin</button>
	                                	<button type="button" class="btn btn-edit" onclick="showModalAdmin('<%= user.getNombre() %>', '<%= user.getUsuario().getUsuario() %>', <%= user.getUsuario().isEsAdmin() %>)">Quitar rol Administrador</button>
	                                </td>
	                            </tr>
	                    <%    }
	                    } %>
	                </tbody>
	            </table>
	            
	        </div>
	    </div>
	
	    <!-- Modal para modificar contraseña -->
	    <div id="passwordModal" class="modal">
	    <div class="modal-content">
	        <span class="close" onclick="closeModal()">&times;</span>
	        <h2>Modificar Contraseña</h2>
	        <p>Cliente: <span id="modalClientName"></span></p>
	        <form id="passwordForm">
	            <input type="hidden" id="modalClientUser" name="clientUser">
	            <input type="hidden" id="currentPassword" name="currentPassword">
	            <div class="form-group">
	                <label for="newPassword">Nueva Contraseña:</label>
	                <input type="password" id="newPassword" name="newPassword" required>
	            </div>
	        </form>
	        <button class="btn btn-primary" onclick="return confirmarEdicionContrasenia();">Aceptar</button>
	        <button class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
	    </div>
	</div>
	
	<div id="confirmationMessage" class="confirmation-message">
	    Se modificó la contraseña correctamente
	</div>
	<div id="errorMessage" class="error-message">
	    Ocurrio un error al modificar la contraseña
	</div>
	
	
	
	<div id="clienteModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeClienteModal()">&times;</span>
        <h2>Editar datos</h2>
        <form id="editClienteForm" method="post" action="ServletInfoUsuarios" class="formulario-cliente" onsubmit="return confirmarEdicionDatos()">
            <fieldset>
                <div class="form-group">
                    <label for="dni">DNI:</label>
                    <input type="text" id="edit_dni" placeholder="Ingrese el DNI" name="dni" readonly>
                </div>          

                <div class="form-group">
                    <label for="usuario">Usuario:</label>
                    <input type="text" id="edit_usuario" placeholder="Ingrese el usuario" name="usuario" readonly>
                </div>         
                <div class="form-group">
                    <label for="cuil">CUIL:</label>
                    <input type="text" id="edit_cuil" placeholder="Ingrese el CUIL" name="cuil" readonly>
                </div>
                <div class="form-group">
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="edit_nombre" placeholder="Ingrese sus nombres" required name="nombre">
                </div>
                <div class="form-group">
                    <label for="apellido">Apellidos:</label>
                    <input type="text" id="edit_apellido" placeholder="Ingrese sus apellidos" required name="apellido">
                </div>
                <div class="form-group">
                    <label for="sexo">Sexo:</label>
                    <select id="edit_sexo" name="sexo" required>
                        <option value="Masculino">Masculino</option>
                        <option value="Femenino">Femenino</option>
                        <option value="Otro">Otro</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="nacionalidad">Nacionalidad:</label>
                    <select id="edit_nacionalidad" required name="nacionalidad">
                        <% ArrayList<Pais> paises = (ArrayList<Pais>)request.getAttribute("paises");
                        if (paises != null) {
                            for (Pais p : paises) { %>
                                <option value="<%= p.getID_Nacionalidad() %>"><%= p.getPais() %></option>
                            <% }
                        } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="fecha_nacimiento">Fecha de Nacimiento:</label>
                    <input type="date" id="edit_fecha_nacimiento" required name="fecha_nacimiento">
                </div>
                <div class="form-group">
                    <label for="provincia">Provincia:</label>
                    <select id="edit_provincia" required name="provincia">
                        <% ArrayList<Provincia> provincias = (ArrayList<Provincia>)request.getAttribute("provincias");
                        if (provincias != null) {
                            for (Provincia p : provincias) { %>
                                <option value="<%= p.getID_Provincia() %>"><%= p.getProvincia() %></option>
                            <% }
                        } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="localidad">Localidad:</label>
                    <select id="edit_localidad" required name="localidad">
                        <% ArrayList<Localidad> localidades = (ArrayList<Localidad>)request.getAttribute("localidades");
                        if (localidades != null) {
                            for (Localidad l : localidades) { %>
                                <option value="<%= l.getID_Localidad() %>"><%= l.getLocalidad() %></option>
                            <% }
                        } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="direccion">Dirección:</label>
                    <input type="text" id="edit_direccion" placeholder="Ingrese la dirección" required name="direccion">
                </div>
                <div class="form-group">
                    <label for="correo">Correo Electrónico:</label>
                    <input type="email" id="edit_correo" placeholder="Ingrese correo electrónico" required name="correo">
                </div>
                <div class="form-group">
                    <label for="telefono1">Teléfono principal:</label>
                    <input type="text" id="edit_telefono1" placeholder="Ingrese el teléfono" required name="telefono1" maxlength="10">
                </div>
                <div class="form-group">
                    <label for="telefono2">Teléfono secundario:</label>
                    <input type="text" id="edit_telefono2" placeholder="Ingrese el teléfono" name="telefono2" maxlength="10">
                </div>
                <div class="form-group">
                    <input type="submit" name="btnEditar" value="Editar datos" class="btn btn-primary">
                </div>
            </fieldset>
        </form>
    </div>
</div>
	<!-- Modal para modificar rol de administrador -->
	<div id="adminRolModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeAdminModal()">&times;</span>
        <h2>Modificar rol de Administrador</h2>
        <p>Usuario: <span id="modalAdminClientName"></span></p>
        <form id="adminRolForm" method="post" action="ServletInfoUsuarios">
            <input type="hidden" id="modalAdminClientUser" name="clientUser">
            <div class="form-group">
                <label for="adminRol">Rol de Administrador:</label>
                <select id="adminRol" name="adminRol" required>
                    <!-- Options will be dynamically added here -->
                </select>
            </div>
            <button type="button" class="btn btn-primary" onclick="return confirmarAsignar_QuitarRol()">Aceptar</button>
            <button type="button" class="btn btn-secondary" onclick="closeAdminModal()">Cancelar</button>
        </form>
    </div>
</div>

<div id="confirmationMessageAdmin" class="confirmation-message" style="display:none;">
    Se asignó/quitó el rol administrador correctamente.
</div>
<div id="errorMessageAdmin" class="error-message" style="display:none;">
    Ocurrió un error al asignar/quitar el rol de administrador.
</div>
	</body>
</html>
