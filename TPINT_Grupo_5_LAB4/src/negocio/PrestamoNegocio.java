package negocio;

import java.util.ArrayList;
import java.util.List;

import entidad.Prestamo;

public interface PrestamoNegocio {
	public List<Prestamo> obtenerSolicitudes();
	public boolean pedirPrestamo(Prestamo prestamo);
	public boolean aceptarPrestamo(int id);
	public boolean rechazarPrestamo(int id);
	public ArrayList<Prestamo> Obtener_Prestamos_PorDni(int dni);

}
