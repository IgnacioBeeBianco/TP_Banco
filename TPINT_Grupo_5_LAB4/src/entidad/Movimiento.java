package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class Movimiento {
    private int nroMovimiento;
    private Cuenta cbuCliente;
    private Date fechaMovimiento;
    private BigDecimal importe;
    private BigDecimal importePostMovimiento; // Nuevo 
    private String detalle;
    private TipoMovimiento tipoMovimiento;
    
    public Movimiento() {}

    public Movimiento(int nroMovimiento, Cuenta cbuCliente, Date fechaMovimiento, BigDecimal importe,
            BigDecimal importePostMovimiento, String detalle, TipoMovimiento tipoMovimiento) {
        super();
        this.nroMovimiento = nroMovimiento;
        this.cbuCliente = cbuCliente;
        this.fechaMovimiento = fechaMovimiento;
        this.importe = importe;
        this.importePostMovimiento = importePostMovimiento;
        this.detalle = detalle;
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getNroMovimiento() {
        return nroMovimiento;
    }

    public void setNroMovimiento(int nroMovimiento) {
        this.nroMovimiento = nroMovimiento;
    }

    public Cuenta getCbuCliente() {
        return cbuCliente;
    }

    public void setCbuCliente(Cuenta cbuCliente) {
        this.cbuCliente = cbuCliente;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImportePostMovimiento() {
        return importePostMovimiento;
    }

    public void setImportePostMovimiento(BigDecimal importePostMovimiento) {
        this.importePostMovimiento = importePostMovimiento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    @Override
    public String toString() {
        return "Movimiento [nroMovimiento=" + nroMovimiento + ", cbuCliente=" + cbuCliente + ", fechaMovimiento="
                + fechaMovimiento + ", importe=" + importe + ", importePostMovimiento=" + importePostMovimiento
                + ", detalle=" + detalle + ", tipoMovimiento=" + tipoMovimiento + "]";
    }
}
