<%@page import="entidad.TipoMovimiento"%>
<%@page import="entidad.Movimiento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reportes</title>
    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">-->
    <link rel="stylesheet" type="text/css" href="css/StyleSheet2.css">
</head>

<!-- Paginación tabla -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script>
$(document).ready( function () {
    $('#table_id').DataTable();
} );
</script>

<body>
<%@ page import="java.util.ArrayList" %>
<% 
	ArrayList<String> listaStats = new ArrayList<>();
	if(request.getAttribute("listarStat") != null) {
		listaStats = (ArrayList<String>) request.getAttribute("listarStat");
	}
	ArrayList<Movimiento> listaMovs = new ArrayList<>();
	if(request.getAttribute("listarMovs")!= null){
		listaMovs = (ArrayList<Movimiento>)request.getAttribute("listarMovs");
	}
	   
    ArrayList<TipoMovimiento> listaTipos_DeMovimientos= null;
	if(request.getAttribute("listaTipoMovimientos")!=null)
	{
		listaTipos_DeMovimientos = (ArrayList<TipoMovimiento>) request.getAttribute("listaTipoMovimientos");
	}
%>
 <div class="container">
  <jsp:include page="sidebarAdmin.jsp"></jsp:include>

        <div class="main">
                
        <h2>Estado de clientes</h2>
        <table class="tabla_clientes_activos">
            <thead>
                <tr>
                    <th>Total de Clientes Activos</th>
                    <th>Total de Clientes Dados de Baja</th>
                    <th>Porcentaje de Clientes Activos</th>
                    <th>Porcentaje de Clientes Dados de Baja</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= listaStats.get(0) %></td>
                    <td><%= listaStats.get(1) %></td>
                    
                    <%
                    float clientesActivos = Float.parseFloat(listaStats.get(0));
                    float clientesInactivos = Float.parseFloat(listaStats.get(1));
                    
                    float totalClientes = clientesActivos + clientesInactivos;
                    float porcentajeActivos = (clientesActivos / totalClientes) * 100;
                    float porcentajeInactivos = (clientesInactivos / totalClientes) * 100;
                    %>
                    
                    
                    <td><%= porcentajeActivos%>%</td>
                    <td><%= porcentajeInactivos%>%</td>
                </tr>
            </tbody>
        </table>
        
         <h2>Cantidad de cuentas</h2>
        <table class="tabla_tipos">
            <thead>
                <tr>
                    <th>Tipo de Cuenta</th>
                    <th>Número de Cuentas</th>
                    <th>Porcentaje de Cuentas</th>
                </tr>
            </thead>
            <tbody>
                <tr>                  
                <%
                    float cuentasCDA = Float.parseFloat(listaStats.get(2));
                    float cuentasCC = Float.parseFloat(listaStats.get(3));
                    
                    float totalCuentas = cuentasCDA + cuentasCC;
                    float porcentajeCDA = (cuentasCDA / totalCuentas) * 100;
                    float porcentajeCC = (cuentasCC / totalCuentas) * 100;
                    %>
                    <td>Caja de Ahorro</td>
                    <td><%= listaStats.get(2) %></td>
                    <td><%= porcentajeCDA%>%</td>
    
                </tr>
                <tr>
                    <td>Cuenta Corriente</td>
                    <td><%= listaStats.get(3) %></td>
                    <td><%= porcentajeCC%>%</td>
                </tr>
            </tbody>
        </table>
        
        <h2>Estadísticas del Banco</h2>
        <table class="tabla_estadisticas_generales">
            <thead>
                <tr>
                    <th>Estadística</th>
                    <th>Descripción</th>
                    <th>Ejemplo</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Préstamos Aprobados</td>
                    <td>Cantidad de préstamos aprobados en el último mes</td>
                    <td><%= listaStats.get(4) %></td>
                </tr>
                <tr>
                    <td>Monto Total Prestado</td>
                    <td>Suma del total de dinero prestado en préstamos activos</td>
                    <td>$<%= listaStats.get(5) %></td>
                </tr>
                <tr>
                    <td>Promedio de Saldo por Cliente</td>
                    <td>Promedio del saldo de todas las cuentas por cliente</td>
                    
                    <%
                    float saldoTotal=Float.parseFloat(listaStats.get(6));
                    %>
                    <td>$<%=saldoTotal/clientesActivos%> </td>
                </tr>
                <tr>
                    <td>Nuevas Cuentas Abiertas</td>
                    <td>Número de nuevas cuentas abiertas en los últimos 30 días</td>
                    <td><%= listaStats.get(7) %></td>
                </tr>
                <tr>
                    <td>Préstamos Pendientes de Aprobación</td>
                    <td>Número de solicitudes de préstamos pendientes de aprobación</td>
                    <td><%= listaStats.get(8) %></td>
                </tr>
                <tr>
                    <td>Transferencias Realizadas</td>
                    <td>Número de transferencias internacionales realizadas este mes</td>
                    <td><%= listaStats.get(9) %></td>
                </tr>

            </tbody>
        </table>
        
<h2>Movimientos</h2>
<div id="reporte_movimientos">
    <form id="reportForm" method="post" action="ServletMovimientos">
        <label for="fechaInicio">Fecha de Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" placeholder="aaaa-mm-dd" pattern="\d{4}-\d{2}-\d{2}">
        <label for="fechaFin">Fecha de Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" placeholder="aaaa-mm-dd" pattern="\d{4}-\d{2}-\d{2}">
        <label for="tipoMovimiento">Tipo de Movimiento:</label>
        <select id="tipoMovimiento" name="tipoMovimiento">
			<option value="0">---seleccionar tipo de movimiento---</option>
			<%  
				if (listaTipos_DeMovimientos != null) {
						for (TipoMovimiento Tm : listaTipos_DeMovimientos) { 
			%>       
			<option value="<%= Tm.getId() %>"><%= Tm.getNombre() %></option>
			<%      
					}
				}
			%>
        </select>
        <label for="saldoMin">Saldo Mínimo:</label>
        <input type="number" id="saldoMin" name="saldoMin" placeholder="0.00">
        <label for="saldoMax">Saldo Máximo:</label>
        <input type="number" id="saldoMax" name="saldoMax" placeholder="0.00">
        <button type="submit" name="btnGenerarReporte" value="Filtrar">Generar Reporte</button>
    </form>
    
    <div class="resultado_reporte">
        <% 
        if (listaMovs != null && !listaMovs.isEmpty()) { 
        %>
            <table id="table_id" class="display">
                <thead>
                    <tr>
                        <th>Nro Movimiento</th>
                        <th>CBU</th>
                        <th>Fecha Movimiento</th>
                        <th>Importe</th>
                        <th>Detalle Concepto</th>
                        <th>Tipo Movimiento</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Movimiento movimiento : listaMovs) { %>
                        <tr>
                            <td><%= movimiento.getNroMovimiento() %></td>
                            <td><%= movimiento.getCbuCliente().getCBU() %></td>
                            <td><%= movimiento.getFechaMovimiento() %></td>
                            <td><%= movimiento.getImporte() %></td>
                            <td><%= movimiento.getDetalle() %></td>
                            <td><%= movimiento.getTipoMovimiento().getNombre() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            No se encontraron movimientos para los criterios proporcionados.
        <% } %>
    </div>
</div>

        
         
        </div>
    </div>
</body>
</html>