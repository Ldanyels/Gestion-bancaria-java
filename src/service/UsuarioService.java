package service;

import model.Usuario;
import util.Validador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona la lista de usuarios del sistema.
 * Responsable de registrar, buscar y listar usuarios.
 */
public class UsuarioService {
    
    private List<Usuario> listaUsuarios;
    
    public UsuarioService() {
        this.listaUsuarios = new ArrayList<>();
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que el DNI no esté duplicado.
     */
    public boolean registrarUsuario(String dni, String nombreCompleto, BigDecimal saldoInicial) {
        // Validar formato de DNI
        if (!Validador.esDniValido(dni)) {
            Validador.mostrarError("El DNI ingresado no es válido. Debe tener 8 dígitos.");
            return false;
        }
        
        // Validar nombre
        if (!Validador.esNombreValido(nombreCompleto)) {
            Validador.mostrarError("El nombre ingresado no es válido.");
            return false;
        }
        
        // Validar saldo inicial
        if (saldoInicial == null || saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            Validador.mostrarError("El saldo inicial debe ser un número no negativo.");
            return false;
        }
        
        // Validar DNI no duplicado
        if (existeUsuario(dni)) {
            Validador.mostrarError("Ya existe un usuario registrado con el DNI: " + dni);
            return false;
        }
        
        // Crear y registrar usuario
        Usuario nuevoUsuario = new Usuario(dni, nombreCompleto.trim(), saldoInicial);
        listaUsuarios.add(nuevoUsuario);
        
        Validador.mostrarExito("Usuario registrado correctamente: " + nombreCompleto);
        return true;
    }
    
    /**
     * Busca un usuario por su DNI.
     * Retorna null si no existe.
     */
    public Usuario buscarPorDni(String dni) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getDni().equals(dni)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Verifica si existe un usuario con el DNI dado.
     */
    public boolean existeUsuario(String dni) {
        return buscarPorDni(dni) != null;
    }
    
    /**
     * Retorna la lista de todos los usuarios registrados.
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
    
    /**
     * Retorna la cantidad de usuarios registrados.
     */
    public int contarUsuarios() {
        return listaUsuarios.size();
    }
    
    /**
     * Muestra la lista de todos los usuarios en consola.
     */
    public void mostrarUsuarios() {
        if (listaUsuarios.isEmpty()) {
            Validador.mostrarInfo("No hay usuarios registrados en el sistema.");
            return;
        }
        
        System.out.println("\n========== LISTA DE USUARIOS ==========");
        System.out.printf("%-12s %-30s %15s%n", "DNI", "NOMBRE", "SALDO");
        System.out.println("-".repeat(60));
        
        for (Usuario usuario : listaUsuarios) {
            System.out.printf("%-12s %-30s S/ %12.2f%n", 
                    usuario.getDni(), 
                    usuario.getNombreCompleto(),
                    usuario.getSaldo());
        }
        System.out.println("=".repeat(60));
        System.out.println("Total de usuarios: " + listaUsuarios.size());
    }
}
