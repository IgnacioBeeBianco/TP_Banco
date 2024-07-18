package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class Prestamo {
    private int id;
    private InfoUsuario cliente;
    private Date fechaSolicitud;
    private BigDecimal importePedido;
    private int plazoMeses;
    private int cantidadCuotas;
    private BigDecimal importePagar;
    private BigDecimal montoCuota;
    private int estadoSolicitud; 
    private int estadoPrestamo; 
    private String cbuDeposito;

    public Prestamo() {}

    public Prestamo(int id, InfoUsuario cliente, Date fechaSolicitud, BigDecimal importePedido, int plazoMeses,
            int cantidadCuotas, BigDecimal importePagar, BigDecimal montoCuota, int estadoSolicitud,
            int estadoPrestamo, String cbuDeposito) {
        super();
        this.id = id;
        this.cliente = cliente;
        this.fechaSolicitud = fechaSolicitud;
        this.importePedido = importePedido;
        this.plazoMeses = plazoMeses;
        this.cantidadCuotas = cantidadCuotas;
        this.importePagar = importePagar;
        this.montoCuota = montoCuota;
        this.estadoSolicitud = estadoSolicitud;
        this.estadoPrestamo = estadoPrestamo;
        this.cbuDeposito = cbuDeposito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InfoUsuario getCliente() {
        return cliente;
    }

    public void setCliente(InfoUsuario cliente) {
        this.cliente = cliente;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public BigDecimal getImportePedido() {
        return importePedido;
    }

    public void setImportePedido(BigDecimal importePedido) {
        this.importePedido = importePedido;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public BigDecimal getImportePagar() {
        return importePagar;
    }

    public void setImportePagar(BigDecimal importePagar) {
        this.importePagar = importePagar;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public int getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(int estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public int getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(int estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public String getCbuDeposito() {
        return cbuDeposito;
    }

    public void setCbuDeposito(String cbuDeposito) {
        this.cbuDeposito = cbuDeposito;
    }

    @Override
    public String toString() {
        return "Prestamo [id=" + id + ", cliente=" + cliente.getDni() + ", fechaSolicitud=" + fechaSolicitud
                + ", importePedido=" + importePedido + ", plazoMeses=" + plazoMeses + ", cantidadCuotas="
                + cantidadCuotas + ", importePagar=" + importePagar + ", montoCuota=" + montoCuota
                + ", estadoSolicitud=" + estadoSolicitud + ", estadoPrestamo=" + estadoPrestamo + ", cbuDeposito="
                + cbuDeposito + "]";
    }
}
