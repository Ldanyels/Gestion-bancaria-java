package main;

import service.TransaccionService;
import service.UsuarioService;
import util.Validador;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Clase principal de la aplicación.
 * Gestiona el menú y la interacción con el usuario por consola.
 * NO contiene lógica de negocio, solo coordinación y entrada/salida.
 */
public class App {
    
    private static Scanner scanner;
    private static UsuarioService usuarioService;
    private static TransaccionService transaccionService;
    
    public static void main(String[] args) {
        inicializarSistema();
        ejecutarMenuPrincipal();
    }
    
    /**
     * Inicializa los servicios y el scanner.
     */
    private static void inicializarSistema() {
        scanner = new Scanner(System.in);
        usuarioService = new UsuarioService();
        transaccionService = new TransaccionService(usuarioService);
        
        System.out.println("=".repeat(50));
        System.out.println("   SISTEMA DE GESTION BANCARIA - BIENVENIDO");
        System.out.println("=".repeat(50));
    }
    
    /**
     * Ejecuta el bucle principal del menú.
     */
    private static void ejecutarMenuPrincipal() {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);
        
        System.out.println("\n¡Gracias por usar el sistema! Hasta pronto.");
        scanner.close();
    }
    
    /**
     * Muestra el menú principal.
     */
    private static void mostrarMenu() {
        System.out.println("\n============= MENU PRINCIPAL =============");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Realizar depósito");
        System.out.println("4. Realizar retiro");
        System.out.println("5. Realizar transferencia");
        System.out.println("6. Consultar saldo");
        System.out.println("7. Ver historial de transacciones");
        System.out.println("8. Ver historial por usuario");
        System.out.println("0. Salir");
        System.out.println("=".repeat(42));
        System.out.print("Seleccione una opción: ");
    }
    
    /**
     * Lee la opción del usuario de forma segura.
     */
    private static int leerOpcion() {
        try {
            String entrada = scanner.nextLine();
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Procesa la opción seleccionada por el usuario.
     */
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarUsuario();
                break;
            case 2:
                usuarioService.mostrarUsuarios();
                break;
            case 3:
                realizarDeposito();
                break;
            case 4:
                realizarRetiro();
                break;
            case 5:
                realizarTransferencia();
                break;
            case 6:
                consultarSaldo();
                break;
            case 7:
                transaccionService.mostrarHistorial();
                break;
            case 8:
                verHistorialPorUsuario();
                break;
            case 0:
                // Salir - no hace nada
                break;
            default:
                Validador.mostrarError("Opción no válida. Intente nuevamente.");
        }
    }
    
    /**
     * Solicita datos y registra un nuevo usuario.
     */
    private static void registrarUsuario() {
        System.out.println("\n--- REGISTRO DE USUARIO ---");
        
        System.out.print("Ingrese DNI (8 dígitos): ");
        String dni = scanner.nextLine().trim();
        
        System.out.print("Ingrese nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Ingrese saldo inicial (S/): ");
        BigDecimal saldo = Validador.textoAMonto(scanner.nextLine());
        
        if (saldo == null) {
            Validador.mostrarError("El saldo ingresado no es válido.");
            return;
        }
        
        usuarioService.registrarUsuario(dni, nombre, saldo);
    }
    
    /**
     * Solicita datos y realiza un depósito.
     */
    private static void realizarDeposito() {
        System.out.println("\n--- DEPÓSITO ---");
        
        System.out.print("Ingrese DNI del usuario: ");
        String dni = scanner.nextLine().trim();
        
        System.out.print("Ingrese monto a depositar (S/): ");
        BigDecimal monto = Validador.textoAMonto(scanner.nextLine());
        
        if (monto == null) {
            Validador.mostrarError("El monto ingresado no es válido.");
            return;
        }
        
        transaccionService.realizarDeposito(dni, monto);
    }
    
    /**
     * Solicita datos y realiza un retiro.
     */
    private static void realizarRetiro() {
        System.out.println("\n--- RETIRO ---");
        
        System.out.print("Ingrese DNI del usuario: ");
        String dni = scanner.nextLine().trim();
        
        System.out.print("Ingrese monto a retirar (S/): ");
        BigDecimal monto = Validador.textoAMonto(scanner.nextLine());
        
        if (monto == null) {
            Validador.mostrarError("El monto ingresado no es válido.");
            return;
        }
        
        transaccionService.realizarRetiro(dni, monto);
    }
    
    /**
     * Solicita datos y realiza una transferencia.
     */
    private static void realizarTransferencia() {
        System.out.println("\n--- TRANSFERENCIA ---");
        
        System.out.print("Ingrese DNI del usuario origen: ");
        String dniOrigen = scanner.nextLine().trim();
        
        System.out.print("Ingrese DNI del usuario destino: ");
        String dniDestino = scanner.nextLine().trim();
        
        System.out.print("Ingrese monto a transferir (S/): ");
        BigDecimal monto = Validador.textoAMonto(scanner.nextLine());
        
        if (monto == null) {
            Validador.mostrarError("El monto ingresado no es válido.");
            return;
        }
        
        transaccionService.realizarTransferencia(dniOrigen, dniDestino, monto);
    }
    
    /**
     * Consulta el saldo de un usuario.
     */
    private static void consultarSaldo() {
        System.out.println("\n--- CONSULTA DE SALDO ---");
        
        System.out.print("Ingrese DNI del usuario: ");
        String dni = scanner.nextLine().trim();
        
        transaccionService.consultarSaldo(dni);
    }
    
    /**
     * Muestra el historial de transacciones de un usuario.
     */
    private static void verHistorialPorUsuario() {
        System.out.println("\n--- HISTORIAL POR USUARIO ---");
        
        System.out.print("Ingrese DNI del usuario: ");
        String dni = scanner.nextLine().trim();
        
        transaccionService.mostrarHistorialPorUsuario(dni);
    }
}
