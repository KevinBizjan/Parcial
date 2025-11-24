// ==========================================================
// Vehiculo.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

abstract class Vehiculo {
  protected String marca;
  protected int modelo;
  protected String patente;
  protected int kilometraje;

  public Vehiculo(String marca, int modelo, String patente, int kilometraje) {
    this.marca = marca.toUpperCase();
    this.modelo = modelo;
    this.patente = patente.toUpperCase();
    this.kilometraje = kilometraje;
  }

  // Getters
  public String getMarca() {
    return marca;
  }

  public int getModelo() {
    return modelo;
  }

  public String getPatente() {
    return patente;
  }

  public int getKilometraje() {
    return kilometraje;
  }

  // Setters (opcionales, según lo que pidan)
  public void setMarca(String marca) {
    this.marca = marca.toUpperCase();
  }

  public void setModelo(int modelo) {
    this.modelo = modelo;
  }

  public void setPatente(String patente) {
    this.patente = patente.toUpperCase();
  }

  public void setKilometraje(int kilometraje) {
    this.kilometraje = kilometraje;
  }

  // ====== EJERCICIO TIPO "Analizar igualdad" ======
  // Dos vehículos se consideran iguales SOLO si tienen la misma patente
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Vehiculo otro = (Vehiculo) obj;
    if (this.patente == null || otro.patente == null) {
      return false;
    }

    return this.patente.equalsIgnoreCase(otro.patente);
  }

  @Override
  public int hashCode() {
    return (patente == null) ? 0 : patente.toUpperCase().hashCode();
  }
  //termina ejercicio fijarse si el metodo abstraco de abajo se necesita.
  // Método abstracto para que cada vehículo devuelva su "icono" o tipo
  public abstract String verTipoDeVehiculo();
}

// ==========================================================
// Ventas.java (Interface)
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

public interface Ventas {

  /**
   * Calcula el precio de venta del vehículo basado en el precio base y el año
   * actual.
   *
   * Se usa en ejercicios tipo:
   * - "Implemente en la clase X el método requerido por la interface..."
   */
  double calcularPrecioVenta(double precioBase, int anioActual);
}


// ==========================================================
// PuertasInsuficientesException.java
// (Ejercicio tipo Excepciones)
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

/**
 * Excepción personalizada que se lanza cuando se intenta crear un Auto
 * con menos de 3 puertas.
 *
 * Es una excepción verificada (checked exception), por lo que el código
 * que crea un Auto debe manejarla con try/catch o declararla con throws.
 */
public class PuertasInsuficientesException extends Exception {

  // Constructor con mensaje personalizado
  public PuertasInsuficientesException(String mensaje) {
    super(mensaje);
  }

  // Constructor por defecto
  public PuertasInsuficientesException() {
    super("Error: un auto debe tener al menos 3 puertas.");
  }
}
//fin ejercicio excepcion en puertasinsuficientesexception
// ==========================================================
// Auto.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

class Auto extends Vehiculo implements Ventas {

  private int cantPuertas;

  public Auto(String marca, int modelo, String patente, int kilometraje, int cantPuertas)
      throws PuertasInsuficientesException {

    super(marca, modelo, patente, kilometraje);

    // ====== EJERCICIO TIPO "Excepciones" ======
    // Validar que el auto tenga al menos 3 puertas
    if (cantPuertas < 3) {
      throw new PuertasInsuficientesException(
          "Error: un auto debe tener al menos 3 puertas. Puertas recibidas: " + cantPuertas);
    }
  //termina aca ejercicio excepcion oooo abajo
    this.cantPuertas = cantPuertas;
  }

  public int getCantPuertas() {
    return cantPuertas;
  }

  public void setCantPuertas(int cantPuertas) {
    this.cantPuertas = cantPuertas;
  }
  //aca 
  // ====== EJERCICIO TIPO "Implementar la interface" ======
  @Override
  public double calcularPrecioVenta(double precioBase, int anioActual) {
    // Calcular años de uso
    int aniosDeUso = anioActual - this.modelo;
    if (aniosDeUso < 0) {
      aniosDeUso = 0; // Por si el modelo es del futuro, no restar valor
    }

    // Ejemplo de depreciación: 10% por año para autos
    double depreciacion = aniosDeUso * 0.10;
    // Limitar depreciación máxima (ej: 80%)
    if (depreciacion > 0.80) {
      depreciacion = 0.80;
    }

    double precioConDepreciacion = precioBase * (1 - depreciacion);

    // Descuento extra fijo por "desgaste" (podés cambiar el porcentaje según consigna)
    double descuentoAdicional = 0.05; // 5% adicional
    double precioFinal = precioConDepreciacion * (1 - descuentoAdicional);

    return precioFinal;
  }
  //termina aca ejercicio de ventas auto o abajo
  @Override
  public String verTipoDeVehiculo() {
    return "🚗 AUTO";
  }

  @Override
  public String toString() {
    return verTipoDeVehiculo() + "\t" + marca + "\t" + modelo + "\t" + patente + "\t"
        + kilometraje + "Km\t" + cantPuertas + " puertas";
  }
}
//o aca corroborar.

// ==========================================================
// Moto.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

class Moto extends Vehiculo implements Ventas {

  public Moto(String marca, int modelo, String patente, int kilometraje) {
    super(marca, modelo, patente, kilometraje);
  }

  // Implementación de la interface Ventas
  @Override
  public double calcularPrecioVenta(double precioBase, int anioActual) {
    int aniosDeUso = anioActual - this.modelo;
    if (aniosDeUso < 0) {
      aniosDeUso = 0;
    }

    // Ejemplo: 8% de depreciación anual para motos
    double depreciacion = aniosDeUso * 0.08;
    if (depreciacion > 0.85) {
      depreciacion = 0.85;
    }

    double precioConDepreciacion = precioBase * (1 - depreciacion);

    // Descuento extra por "desgaste típico" de moto
    double descuentoMoto = 0.07; // 7% adicional
    double precioFinal = precioConDepreciacion * (1 - descuentoMoto);

    return precioFinal;
  }
  //termina ejercicio motos ventas 
  @Override
  public String verTipoDeVehiculo() {
    return "🏍️ MOTO";
  }

  @Override
  public String toString() {
    return verTipoDeVehiculo() + "\t" + marca + "\t" + modelo + "\t" + patente + "\t"
        + kilometraje + "Km";
  }
}
//o aca mirar bien

// ==========================================================
// Inventario.java
// (Ejercicio tipo "Buscar elemento" + CRUD básico)
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

import java.util.ArrayList;

public class Inventario {

  private ArrayList<Auto> autos;
  private ArrayList<Moto> motos;

  public Inventario() {
    this.autos = new ArrayList<>();
    this.motos = new ArrayList<>();
  }

  // ================== ALTAS ==================

  public void agregarAuto(Auto auto) {
    autos.add(auto);
  }

  public void agregarMoto(Moto moto) {
    motos.add(moto);
  }

  // ================== LISTAR ==================

  public void listarVehiculos() {
    System.out.println("\nAUTOS (" + autos.size() + "):");
    autos.forEach(a -> System.out.println(a.toString()));

    System.out.println("\nMOTOS (" + motos.size() + "):");
    motos.forEach(m -> System.out.println(m.toString()));

    System.out.println("\nTotal de vehículos: " + getCantidadDeVehiculos());
  }

  // ================== EJERCICIO TIPO "Buscar elemento" ==================
  /**
   * Busca un vehículo por patente. Primero recorre la lista de AUTOS y,
   * si no encuentra, recorre la lista de MOTOS.
   *
   * Si no se encuentra en ninguna lista, retorna null.
   */
  public Vehiculo buscarVehiculo(String patente) {
    if (patente == null) {
      return null;
    }

    String patenteBuscada = patente.toUpperCase();

    // Buscar primero en autos
    for (Auto a : autos) {
      if (a.getPatente().equalsIgnoreCase(patenteBuscada)) {
        return a;
      }
    }

    // Si no se encontró, buscar en motos
    for (Moto m : motos) {
      if (m.getPatente().equalsIgnoreCase(patenteBuscada)) {
        return m;
      }
    }

    // Si no está en ninguna, retornar null
    return null;
  }
  //termina ejercicio
  // ================== ELIMINAR ==================

  public boolean eliminarVehiculo(String patente) {
    Vehiculo encontrado = buscarVehiculo(patente);
    if (encontrado == null) {
      return false;
    }

    if (encontrado instanceof Auto) {
      return autos.remove(encontrado);
    } else if (encontrado instanceof Moto) {
      return motos.remove(encontrado);
    }

    return false;
  }

  // ================== ACTUALIZAR ==================

  public boolean actualizarAuto(String patenteOriginal, Auto autoNuevo) {
    if (patenteOriginal == null) {
      return false;
    }

    String pat = patenteOriginal.toUpperCase();
    for (int i = 0; i < autos.size(); i++) {
      if (autos.get(i).getPatente().equalsIgnoreCase(pat)) {
        autos.set(i, autoNuevo);
        return true;
      }
    }
    return false;
  }

  public boolean actualizarMoto(String patenteOriginal, Moto motoNueva) {
    if (patenteOriginal == null) {
      return false;
    }

    String pat = patenteOriginal.toUpperCase();
    for (int i = 0; i < motos.size(); i++) {
      if (motos.get(i).getPatente().equalsIgnoreCase(pat)) {
        motos.set(i, motoNueva);
        return true;
      }
    }
    return false;
  }

  // ================== CONSULTAS VARIAS ==================

  public int getCantidadDeVehiculos() {
    return autos.size() + motos.size();
  }

  public boolean existeVehiculo(String patente) {
    return buscarVehiculo(patente) != null;
  }
}

// ==========================================================
// Menu.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

public class Menu {

  public void mostrarMenuPrincipal() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("🚘 SISTEMA DE INVENTARIO - CONCESIONARIO 🚘");
    System.out.println("=".repeat(60));
    System.out.println("┌───────────────────────────────────────────────┐");
    System.out.println("│  1 │  Agregar Auto                            │");
    System.out.println("│  2 │  Agregar Moto                            │");
    System.out.println("│  3 │  Listar todos los vehículos              │");
    System.out.println("│  4 │  Buscar vehículo por patente             │");
    System.out.println("│  5 │  Eliminar vehículo por patente           │");
    System.out.println("│  6 │  Ver cantidad total de vehículos         │");
    System.out.println("│  7 │  Actualizar Auto                         │");
    System.out.println("│  8 │  Actualizar Moto                         │");
    System.out.println("└───────────────────────────────────────────────┘");
    System.out.print("➤ Ingrese opción (cualquier letra para salir): ");
  }
}


// ==========================================================
// Main.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Concesionario;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Inventario inventario = new Inventario();
    Menu menu = new Menu();
    Scanner scanner = new Scanner(System.in);

    String input;
    int opcion = 0;

    do {
      menu.mostrarMenuPrincipal();
      input = scanner.nextLine();

      try {
        opcion = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Saliendo del sistema...");
        break;
      }

      switch (opcion) {
        case 1:
          registrarAuto(inventario, scanner);
          break;
        case 2:
          registrarMoto(inventario, scanner);
          break;
        case 3:
          inventario.listarVehiculos();
          break;
        case 4:
          buscarVehiculo(inventario, scanner);
          break;
        case 5:
          eliminarVehiculo(inventario, scanner);
          break;
        case 6:
          System.out.println("Cantidad total de vehículos: " + inventario.getCantidadDeVehiculos());
          break;
        case 7:
          actualizarAuto(inventario, scanner);
          break;
        case 8:
          actualizarMoto(inventario, scanner);
          break;
        default:
          System.out.println("Opción inválida");
      }

      System.out.println("\nPresione Enter para continuar...");
      scanner.nextLine();

    } while (true);

    scanner.close();
  }

  // ================== MÉTODOS PRIVADOS ==================

  private static void registrarAuto(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Marca: ");
      String marca = scanner.nextLine();

      System.out.print("Modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Patente: ");
      String patente = scanner.nextLine();

      System.out.print("Kilometraje: ");
      int km = Integer.parseInt(scanner.nextLine());

      System.out.print("Cantidad de puertas: ");
      int puertas = Integer.parseInt(scanner.nextLine());

      Auto auto = new Auto(marca, modelo, patente, kilometraje, puertas);
      inventario.agregarAuto(auto);

      System.out.println("✅ Auto registrado correctamente.");

    } catch (PuertasInsuficientesException e) {
      System.out.println("❌ " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void registrarMoto(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Marca: ");
      String marca = scanner.nextLine();

      System.out.print("Modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Patente: ");
      String patente = scanner.nextLine();

      System.out.print("Kilometraje: ");
      int km = Integer.parseInt(scanner.nextLine());

      Moto moto = new Moto(marca, modelo, patente, km);
      inventario.agregarMoto(moto);

      System.out.println("✅ Moto registrada correctamente.");

    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void buscarVehiculo(Inventario inventario, Scanner scanner) {
    System.out.print("Ingrese la patente a buscar: ");
    String patente = scanner.nextLine();

    Vehiculo encontrado = inventario.buscarVehiculo(patente);
    if (encontrado != null) {
      System.out.println("✅ Vehículo encontrado:");
      System.out.println(encontrado);
    } else {
      System.out.println("❌ No se encontró ningún vehículo con esa patente.");
    }
  }

  private static void eliminarVehiculo(Inventario inventario, Scanner scanner) {
    System.out.print("Ingrese la patente a eliminar: ");
    String patente = scanner.nextLine();

    if (inventario.eliminarVehiculo(patente)) {
      System.out.println("✅ Vehículo eliminado correctamente.");
    } else {
      System.out.println("❌ No se encontró ningún vehículo con esa patente.");
    }
  }

  private static void actualizarAuto(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Patente del Auto a actualizar: ");
      String patenteOriginal = scanner.nextLine();

      System.out.print("Nueva marca: ");
      String marca = scanner.nextLine();

      System.out.print("Nuevo modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Nueva patente: ");
      String patenteNueva = scanner.nextLine();

      System.out.print("Nuevo kilometraje: ");
      int km = Integer.parseInt(scanner.nextLine());

      System.out.print("Nueva cantidad de puertas: ");
      int puertas = Integer.parseInt(scanner.nextLine());

      Auto autoNuevo = new Auto(marca, modelo, patenteNueva, km, puertas);

      if (inventario.actualizarAuto(patenteOriginal, autoNuevo)) {
        System.out.println("✅ Auto actualizado correctamente.");
      } else {
        System.out.println("❌ No se encontró un Auto con esa patente.");
      }

    } catch (PuertasInsuficientesException e) {
      System.out.println("❌ " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void actualizarMoto(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Patente de la Moto a actualizar: ");
      String patenteOriginal = scanner.nextLine();

      System.out.print("Nueva marca: ");
      String marca = scanner.nextLine();

      System.out.print("Nuevo modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Nueva patente: ");
      String patenteNueva = scanner.nextLine();

      System.out.print("Nuevo kilometraje: ");
      int km = Integer.parseInt(scanner.nextLine());

      Moto motoNueva = new Moto(marca, modelo, patenteNueva, km);

      if (inventario.actualizarMoto(patenteOriginal, motoNueva)) {
        System.out.println("✅ Moto actualizada correctamente.");
      } else {
        System.out.println("❌ No se encontró una Moto con esa patente.");
      }

    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }
}

