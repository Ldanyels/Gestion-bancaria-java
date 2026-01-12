package model;

import java.math.BigDecimal;

/**
 * Representa un usuario del sistema bancario.
 * Almacena datos personales y saldo de cuenta.
 */
public class Usuario {
    
    private String dni;
    private String nombreCompleto;
    private BigDecimal saldo;
    
    public Usuario(String dni, String nombreCompleto, BigDecimal saldoInicial) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.saldo = saldoInicial;
    }
    
    // Getters
    public String getDni() {
        return dni;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public BigDecimal getSaldo() {
        return saldo;
    }
    
    // Setters
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "DNI='" + dni + '\'' +
                ", nombre='" + nombreCompleto + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
