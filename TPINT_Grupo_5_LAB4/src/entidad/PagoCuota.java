package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class PagoCuota {
    private int idPrestamo;
    private int nroCuota;
    private Date fechaVencimiento;
    private Date fechaPago;
    private String cbuOrigen;
    private BigDecimal importePago;
    private int estadoCuota;

    public PagoCuota() {}

    public PagoCuota(int idPrestamo, int nroCuota, Date fechaVencimiento, Date fechaPago, String cbuOrigen,
                     BigDecimal importePago, int estadoCuota) {
        this.idPrestamo = idPrestamo;
        this.nroCuota = nroCuota;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago;
        this.cbuOrigen = cbuOrigen;
        this.importePago = importePago;
        this.estadoCuota = estadoCuota;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(int nroCuota) {
        this.nroCuota = nroCuota;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCbuOrigen() {
        return cbuOrigen;
    }

    public void setCbuOrigen(String cbuOrigen) {
        this.cbuOrigen = cbuOrigen;
    }

    public BigDecimal getImportePago() {
        return importePago;
    }

    public void setImportePago(BigDecimal importePago) {
        this.importePago = importePago;
    }

    public int getEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(int estadoCuota) {
        this.estadoCuota = estadoCuota;
    }

    @Override
    public String toString() {
        return "PagoCuota [idPrestamo=" + idPrestamo + ", nroCuota=" + nroCuota + ", fechaVencimiento=" + fechaVencimiento
                + ", fechaPago=" + fechaPago + ", cbuOrigen=" + cbuOrigen + ", importePago=" + importePago
                + ", estadoCuota=" + estadoCuota + "]";
    }
}
