package entidad;

import java.sql.Date;

public class InfoUsuario {

	private int dni;
	private Usuario usuario;
	private String cuil;
	private String nombre;
	private String apellido;
	private String sexo;
	private Pais Nacionalidad;
	private Date fechaNacimiento;
	private Provincia Provincia;
	private Localidad Localidad;
	private String direccion;
	private String mail;
	private String telefono1;
	private String telefono2;
	private boolean estado;

	public InfoUsuario() {

	}

	public InfoUsuario(int dni, Usuario usuario, String cuil, String nombre, String apellido, String sexo,
			Pais nacionalidad, Date fechaNacimiento, Provincia provincia, Localidad localidad, String direccion,
			String mail, String tel1, String tel2, boolean estado) {
		super();
		this.dni = dni;
		this.usuario = usuario;
		this.cuil = cuil;
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.Nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.Provincia = provincia;
		this.Localidad = localidad;
		this.direccion = direccion;
		this.mail = mail;
		this.telefono1 = tel1;
		this.telefono2 = tel2;
		this.estado = estado;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Pais getNacionalidad() {
		return Nacionalidad;
	}

	public void setNacionalidad(Pais nacionalidad) {
		Nacionalidad = nacionalidad;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Provincia getProvincia() {
		return Provincia;
	}

	public void setProvincia(Provincia provincia) {
		Provincia = provincia;
	}

	public Localidad getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(Localidad localidad) {
		Localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
		
	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String  getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String  telefono2) {
		this.telefono2 = telefono2;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "InfoUsuario [dni=" + dni + ", usuario=" + usuario + ", cuil=" + cuil + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", sexo=" + sexo + ", Nacionalidad=" + Nacionalidad + ", fechaNacimiento="
				+ fechaNacimiento + ", Provincia=" + Provincia + ", Localidad=" + Localidad + ", direccion=" + direccion
				+ ", mail=" + mail + ", telefono 1=" + telefono1 + ", telefono 2=" + telefono2 + ", estado=" + estado + "]";
	}

}
