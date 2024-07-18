package entidad;

import java.math.BigDecimal;
import java.sql.Date;

public class Transferencia {
int ID_Transferencia;
Cuenta CbuOrigen;
Cuenta CbuDestino;
Date FechaTransferencia;
BigDecimal Importe;






public Transferencia() {
	super();
	// TODO Auto-generated constructor stub
}
public Transferencia(int iD_Transferencia, Cuenta cbuOrigen, Cuenta cbuDestino, Date fechaTransferencia,
		BigDecimal importe) {
	super();
	ID_Transferencia = iD_Transferencia;
	CbuOrigen = cbuOrigen;
	CbuDestino = cbuDestino;
	FechaTransferencia = fechaTransferencia;
	Importe = importe;
}
public int getID_Transferencia() {
	return ID_Transferencia;
}
public void setID_Transferencia(int iD_Transferencia) {
	ID_Transferencia = iD_Transferencia;
}
public Cuenta getCbuOrigen() {
	return CbuOrigen;
}
public void setCbuOrigen(Cuenta cbuOrigen) {
	CbuOrigen = cbuOrigen;
}
public Cuenta getCbuDestino() {
	return CbuDestino;
}
public void setCbuDestino(Cuenta cbuDestino) {
	CbuDestino = cbuDestino;
}
public Date getFechaTransferencia() {
	return FechaTransferencia;
}
public void setFechaTransferencia(Date fechaTransferencia) {
	FechaTransferencia = fechaTransferencia;
}
public BigDecimal getImporte() {
	return Importe;
}
public void setImporte(BigDecimal importe) {
	Importe = importe;
}



}