package service;

import model.Transaccion;
import model.Usuario;
import util.Validador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones financieras del sistema.
 * Ejecuta depósitos, retiros y transferencias, actualizando saldos
 * y registrando el historial de transacciones.
 */
public class TransaccionService {
    
    private List<Transaccion> historialTransacciones;
    private UsuarioService usuarioService;
    
    public TransaccionService(UsuarioService usuarioService) {
        this.historialTransacciones = new ArrayList<>();
        this.usuarioService = usuarioService;
    }
    
    /**
     * Realiza un depósito a la cuenta de un usuario.
     */
    public boolean realizarDeposito(String dniUsuario, BigDecimal monto) {
        // Validar monto
        if (!Validador.esMontoValido(monto)) {
            Validador.mostrarError("El monto debe ser mayor a cero.");
            return false;
        }
        
        // Buscar usuario
        Usuario usuario = usuarioService.buscarPorDni(dniUsuario);
        if (usuario == null) {
            Validador.mostrarError("No existe un usuario con el DNI: " + dniUsuario);
            return false;
        }
        
        // Realizar depósito
        BigDecimal nuevoSaldo = usuario.getSaldo().add(monto);
        usuario.setSaldo(nuevoSaldo);
        
        // Registrar transacción
        Transaccion transaccion = new Transaccion(
                Transaccion.TIPO_DEPOSITO, monto, dniUsuario);
        historialTransacciones.add(transaccion);
        
        Validador.mostrarExito("Depósito realizado correctamente.");
        System.out.println("Nuevo saldo: S/ " + nuevoSaldo);
        return true;
    }
    
    /**
     * Realiza un retiro de la cuenta de un usuario.
     */
    public boolean realizarRetiro(String dniUsuario, BigDecimal monto) {
        // Validar monto
        if (!Validador.esMontoValido(monto)) {
            Validador.mostrarError("El monto debe ser mayor a cero.");
            return false;
        }
        
        // Buscar usuario
        Usuario usuario = usuarioService.buscarPorDni(dniUsuario);
        if (usuario == null) {
            Validador.mostrarError("No existe un usuario con el DNI: " + dniUsuario);
            return false;
        }
        
        // Validar saldo suficiente
        if (!Validador.tieneSaldoSuficiente(usuario.getSaldo(), monto)) {
            Validador.mostrarError("Saldo insuficiente. Saldo actual: S/ " + usuario.getSaldo());
            return false;
        }
        
        // Realizar retiro
        BigDecimal nuevoSaldo = usuario.getSaldo().subtract(monto);
        usuario.setSaldo(nuevoSaldo);
        
        // Registrar transacción
        Transaccion transaccion = new Transaccion(
                Transaccion.TIPO_RETIRO, monto, dniUsuario);
        historialTransacciones.add(transaccion);
        
        Validador.mostrarExito("Retiro realizado correctamente.");
        System.out.println("Nuevo saldo: S/ " + nuevoSaldo);
        return true;
    }
    
    /**
     * Realiza una transferencia entre dos usuarios.
     */
    public boolean realizarTransferencia(String dniOrigen, String dniDestino, BigDecimal monto) {
        // Validar monto
        if (!Validador.esMontoValido(monto)) {
            Validador.mostrarError("El monto debe ser mayor a cero.");
            return false;
        }
        
        // Validar que no sea la misma cuenta
        if (dniOrigen.equals(dniDestino)) {
            Validador.mostrarError("No puede transferir a la misma cuenta.");
            return false;
        }
        
        // Buscar usuario origen
        Usuario usuarioOrigen = usuarioService.buscarPorDni(dniOrigen);
        if (usuarioOrigen == null) {
            Validador.mostrarError("No existe el usuario origen con DNI: " + dniOrigen);
            return false;
        }
        
        // Buscar usuario destino
        Usuario usuarioDestino = usuarioService.buscarPorDni(dniDestino);
        if (usuarioDestino == null) {
            Validador.mostrarError("No existe el usuario destino con DNI: " + dniDestino);
            return false;
        }
        
        // Validar saldo suficiente
        if (!Validador.tieneSaldoSuficiente(usuarioOrigen.getSaldo(), monto)) {
            Validador.mostrarError("Saldo insuficiente. Saldo actual: S/ " + usuarioOrigen.getSaldo());
            return false;
        }
        
        // Realizar transferencia
        BigDecimal nuevoSaldoOrigen = usuarioOrigen.getSaldo().subtract(monto);
        BigDecimal nuevoSaldoDestino = usuarioDestino.getSaldo().add(monto);
        
        usuarioOrigen.setSaldo(nuevoSaldoOrigen);
        usuarioDestino.setSaldo(nuevoSaldoDestino);
        
        // Registrar transacción
        Transaccion transaccion = new Transaccion(
                Transaccion.TIPO_TRANSFERENCIA, monto, dniOrigen, dniDestino);
        historialTransacciones.add(transaccion);
        
        Validador.mostrarExito("Transferencia realizada correctamente.");
        System.out.println("De: " + usuarioOrigen.getNombreCompleto() + " -> A: " + usuarioDestino.getNombreCompleto());
        System.out.println("Monto transferido: S/ " + monto);
        System.out.println("Nuevo saldo de " + usuarioOrigen.getNombreCompleto() + ": S/ " + nuevoSaldoOrigen);
        return true;
    }
    
    /**
     * Consulta el saldo de un usuario.
     */
    public void consultarSaldo(String dniUsuario) {
        Usuario usuario = usuarioService.buscarPorDni(dniUsuario);
        if (usuario == null) {
            Validador.mostrarError("No existe un usuario con el DNI: " + dniUsuario);
            return;
        }
        
        System.out.println("\n========== CONSULTA DE SALDO ==========");
        System.out.println("Usuario: " + usuario.getNombreCompleto());
        System.out.println("DNI: " + usuario.getDni());
        System.out.println("Saldo disponible: S/ " + usuario.getSaldo());
        System.out.println("=".repeat(40));
    }
    
    /**
     * Muestra el historial de todas las transacciones.
     */
    public void mostrarHistorial() {
        if (historialTransacciones.isEmpty()) {
            Validador.mostrarInfo("No hay transacciones registradas.");
            return;
        }
        
        System.out.println("\n========== HISTORIAL DE TRANSACCIONES ==========");
        for (Transaccion transaccion : historialTransacciones) {
            System.out.println(transaccion);
        }
        System.out.println("=".repeat(50));
        System.out.println("Total de transacciones: " + historialTransacciones.size());
    }
    
    /**
     * Muestra el historial de transacciones de un usuario específico.
     */
    public void mostrarHistorialPorUsuario(String dni) {
        if (!usuarioService.existeUsuario(dni)) {
            Validador.mostrarError("No existe un usuario con el DNI: " + dni);
            return;
        }
        
        System.out.println("\n========== HISTORIAL DEL USUARIO: " + dni + " ==========");
        int contador = 0;
        
        for (Transaccion t : historialTransacciones) {
            if (t.getDniOrigen().equals(dni) || 
                (t.getDniDestino() != null && t.getDniDestino().equals(dni))) {
                System.out.println(t);
                contador++;
            }
        }
        
        if (contador == 0) {
            Validador.mostrarInfo("Este usuario no tiene transacciones registradas.");
        } else {
            System.out.println("Total de transacciones: " + contador);
        }
    }
}
