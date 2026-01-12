package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una transacción realizada en el sistema.
 * Almacena el tipo de operación, monto, fecha y usuarios involucrados.
 */
public class Transaccion {
    
    // Tipos de transacción disponibles
    public static final String TIPO_DEPOSITO = "DEPOSITO";
    public static final String TIPO_RETIRO = "RETIRO";
    public static final String TIPO_TRANSFERENCIA = "TRANSFERENCIA";
    
    private String tipo;
    private BigDecimal monto;
    private LocalDateTime fechaHora;
    private String dniOrigen;      // Usuario que realiza la operación
    private String dniDestino;     // Solo aplica para transferencias
    
    private static final DateTimeFormatter FORMATO_FECHA = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public Transaccion(String tipo, BigDecimal monto, String dniOrigen, String dniDestino) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
        this.dniOrigen = dniOrigen;
        this.dniDestino = dniDestino;
    }
    
    // Constructor para depósitos y retiros (sin destino)
    public Transaccion(String tipo, BigDecimal monto, String dniOrigen) {
        this(tipo, monto, dniOrigen, null);
    }
    
    // Getters
    public String getTipo() {
        return tipo;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public String getDniOrigen() {
        return dniOrigen;
    }
    
    public String getDniDestino() {
        return dniDestino;
    }
    
    public String getFechaFormateada() {
        return fechaHora.format(FORMATO_FECHA);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getFechaFormateada()).append("] ");
        sb.append(tipo).append(" - Monto: S/ ").append(monto);
        sb.append(" - Usuario: ").append(dniOrigen);
        
        if (dniDestino != null) {
            sb.append(" -> ").append(dniDestino);
        }
        
        return sb.toString();
    }
}
