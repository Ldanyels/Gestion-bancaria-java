package util;

import java.math.BigDecimal;

/**
 * Clase utilitaria que centraliza las validaciones comunes del sistema.
 * Evita duplicar lógica de validación en diferentes partes del código.
 */
public class Validador {
    
    /**
     * Valida que el DNI tenga el formato correcto (8 dígitos numéricos).
     */
    public static boolean esDniValido(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        
        // DNI peruano: 8 dígitos numéricos
        return dni.matches("\\d{8}");
    }
    
    /**
     * Valida que el nombre no esté vacío y tenga un formato aceptable.
     */
    public static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        // Al menos 2 caracteres, solo letras y espacios (incluye acentos)
        return nombre.trim().length() >= 2 && 
               nombre.matches("[a-zA-Z\\u00C0-\\u00FF\\s]+");
    }
    
    /**
     * Valida que el monto sea mayor a cero.
     */
    public static boolean esMontoValido(BigDecimal monto) {
        if (monto == null) {
            return false;
        }
        return monto.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Valida que el saldo sea suficiente para realizar una operación.
     */
    public static boolean tieneSaldoSuficiente(BigDecimal saldoActual, BigDecimal montoOperacion) {
        if (saldoActual == null || montoOperacion == null) {
            return false;
        }
        return saldoActual.compareTo(montoOperacion) >= 0;
    }
    
    /**
     * Convierte un texto a BigDecimal de forma segura.
     * Retorna null si el texto no es un número válido.
     */
    public static BigDecimal textoAMonto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        
        try {
            BigDecimal monto = new BigDecimal(texto.trim());
            return monto;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Muestra un mensaje de error formateado.
     */
    public static void mostrarError(String mensaje) {
        System.out.println("\n[ERROR] " + mensaje);
    }
    
    /**
     * Muestra un mensaje de éxito formateado.
     */
    public static void mostrarExito(String mensaje) {
        System.out.println("\n[ÉXITO] " + mensaje);
    }
    
    /**
     * Muestra un mensaje informativo formateado.
     */
    public static void mostrarInfo(String mensaje) {
        System.out.println("\n[INFO] " + mensaje);
    }
}
