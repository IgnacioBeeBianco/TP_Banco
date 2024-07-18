package entidad;

import java.math.BigDecimal;
import java.sql.Date;

public class Cuenta {
	private String CBU;
    private long NumeroCuenta;
    private int DNI;
    private TipoCuenta tipoCuenta;
    private Date FechaCreacion;
    private BigDecimal Saldo;
	private boolean estado;
    private String NombreyApellido;

    public Cuenta() {
    }
 // Constructor sin el parámetro NombreyApellido 
    public Cuenta(String CBU, long NumeroCuenta, int DNI, TipoCuenta tipoCuenta, Date FechaCreacion, BigDecimal Saldo, boolean estado) {
        this.CBU = CBU;
        this.NumeroCuenta = NumeroCuenta;
        this.DNI = DNI;
        this.tipoCuenta = tipoCuenta;
        this.FechaCreacion = FechaCreacion;
        this.Saldo = Saldo;
        this.estado = estado;
    }

    // Constructor con el parámetro NombreyApellido
    public Cuenta(String CBU, long NumeroCuenta, int DNI, TipoCuenta tipoCuenta, Date FechaCreacion, BigDecimal Saldo, boolean estado, String NombreyApellido) {
        this.CBU = CBU;
        this.NumeroCuenta = NumeroCuenta;
        this.DNI = DNI;
        this.tipoCuenta = tipoCuenta;
        this.FechaCreacion = FechaCreacion;
        this.Saldo = Saldo;
        this.estado = estado;
        this.NombreyApellido = NombreyApellido;
    }

	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getNombreyApellido() {
		return NombreyApellido;
	}
	public void setNombreyApellido(String nombreyApellido) {
		NombreyApellido = nombreyApellido;
	}
	public String getCBU() {
		return CBU;
	}

	public void setCBU(String cBU) {
		CBU = cBU;
	}

	public long getNumeroCuenta() {
		return NumeroCuenta;
	}

	public void setNumeroCuenta(long numeroCuenta) {
		NumeroCuenta = numeroCuenta;
	}

	public int getDNI() {
		return DNI;
	}

	public void setDNI(int dNI) {
		DNI = dNI;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Date getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

	public BigDecimal getSaldo() {
		return Saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		Saldo = saldo;
	}

	@Override
	public String toString() {
		return "Cuenta [CBU=" + CBU + ", NumeroCuenta=" + NumeroCuenta + ", DNI=" + DNI + ", tipoCuenta=" + tipoCuenta
				+ ", FechaCreacion=" + FechaCreacion + ", Saldo=" + Saldo + "]";
	}

    
	
}
