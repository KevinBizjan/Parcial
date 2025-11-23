// ==========================================================
// Animalito.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

abstract class Animalito {
  protected String especie;
  protected int edad;
  protected String nombre;
  protected double peso;

  public Animalito(String especie, int edad, String nombre, double peso) {
    this.especie = especie.toUpperCase();
    this.edad = edad;
    this.nombre = nombre.toUpperCase();
    this.peso = peso;
  }

  // Getters
  public String getEspecie() {
    return especie;
  }

  public int getEdad() {
    return edad;
  }

  public String getNombre() {
    return nombre;
  }

  public double getPeso() {
    return peso;
  }

  // Setters (opcionales)
  public void setEspecie(String especie) {
    this.especie = especie.toUpperCase();
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre.toUpperCase();
  }

  public void setPeso(double peso) {
    this.peso = peso;
  }

  // Método abstracto para que cada hijo diga qué tipo de animal es
  public abstract String verTipoDeAnimal();

  // ====== Igualdad por NOMBRE (mismo patrón que en otras materias) ======
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Animalito animalito = (Animalito) obj;
    if (this.nombre == null || animalito.nombre == null) {
      return false;
    }
    return this.nombre.equalsIgnoreCase(animalito.nombre);
  }

  @Override
  public int hashCode() {
    return (nombre == null) ? 0 : nombre.toUpperCase().hashCode();
  }

  @Override
  public String toString() {
    return especie + "\t" + edad + " años\t" + nombre + "\t" + peso + "kg";
  }
}

// ==========================================================
// Cuidados.java (interface)
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

public interface Cuidados {

  /**
   * Calcula el costo de cuidado del animal, basado en un costo base
   * y la edad actual del animal.
   */
  double calcularCostoCuidado(double costoBase, int edadAnimal);
}

// ==========================================================
// PesoInsuficienteException.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

/**
 * Excepción verificada que se lanza cuando se intenta crear
 * un animal avícola con menos de 1kg de peso.
 */
public class PesoInsuficienteException extends Exception {

  /**
   * Constructor que recibe un mensaje personalizado.
   */
  public PesoInsuficienteException(String mensaje) {
    super(mensaje);
  }

  /**
   * Constructor por defecto con mensaje predeterminado.
   */
  public PesoInsuficienteException() {
    super("Error: un animal avícola debe tener al menos 1kg de peso.");
  }
}

// ==========================================================
// Avicolas.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

class Avicolas extends Animalito implements Cuidados {

  private String tipoPlumaje;

  public Avicolas(String especie, int edad, String nombre, double peso, String tipoPlumaje)
      throws PesoInsuficienteException {
    super(especie, edad, nombre, peso);

    // Validar que el animal avícola tenga al menos 1kg
    if (peso < 1.0) {
      throw new PesoInsuficienteException(
          "Error: un animal avícola debe tener al menos 1kg de peso. Peso recibido: " + peso + "kg");
    }

    this.tipoPlumaje = tipoPlumaje;
  }

  public String getTipoPlumaje() {
    return tipoPlumaje;
  }

  public void setTipoPlumaje(String tipoPlumaje) {
    this.tipoPlumaje = tipoPlumaje;
  }

  // Implementación de la interface Cuidados
  @Override
  public double calcularCostoCuidado(double costoBase, int edadAnimal) {
    // Incremento por edad (animales mayores requieren más cuidado)
    double incrementoEdad = this.edad * 0.05; // 5% por año

    // Porcentaje adicional según tipo de plumaje (ejemplo)
    double porcentajePlumaje;
    if (tipoPlumaje != null && tipoPlumaje.equalsIgnoreCase("FINO")) {
      porcentajePlumaje = 0.10; // 10% extra
    } else {
      porcentajePlumaje = 0.05; // 5% extra
    }

    double factorTotal = 1 + incrementoEdad + porcentajePlumaje;
    return costoBase * factorTotal;
  }

  // Implementación del método abstracto
  @Override
  public String verTipoDeAnimal() {
    return "🐦 AVÍCOLA";
  }

  @Override
  public String toString() {
    return verTipoDeAnimal() + "\t" + especie + "\t" + edad + " años\t"
        + nombre + "\t" + peso + "kg\tPlumaje: " + tipoPlumaje;
  }
}

// ==========================================================
// Caceras.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

class Caceras extends Animalito implements Cuidados {

  public Caceras(String especie, int edad, String nombre, double peso) {
    super(especie, edad, nombre, peso);
  }

  // Implementación de la interface Cuidados
  @Override
  public double calcularCostoCuidado(double costoBase, int edadAnimal) {
    // Incremento por edad (cazadores envejecen “más rápido”)
    double incrementoEdad = this.edad * 0.08; // 8% por año

    double costoConEdad = costoBase * (1 + incrementoEdad);

    // Descuento por ser animales más independientes (ej. 10%)
    double descuentoCazador = 0.10;

    return costoConEdad * (1 - descuentoCazador);
  }

  @Override
  public String verTipoDeAnimal() {
    return "🐱 CAZADOR";
  }

  @Override
  public String toString() {
    return verTipoDeAnimal() + "\t" + especie + "\t" + edad + " años\t"
        + nombre + "\t" + peso + "kg";
  }
}

// ==========================================================
// Inventario.java
// (Dos listas: Avícolas y Caceras + buscar por nombre, etc.)
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

import java.util.ArrayList;

public class Inventario {

  private ArrayList<Avicolas> avicolas;
  private ArrayList<Caceras> caceras;

  public Inventario() {
    this.avicolas = new ArrayList<>();
    this.caceras = new ArrayList<>();
  }

  // ================== ALTAS ==================

  public void agregarAvicola(Avicolas a) {
    avicolas.add(a);
  }

  public void agregarCacera(Caceras c) {
    caceras.add(c);
  }

  // ================== LISTAR ==================

  public void listarAnimales() {
    System.out.println("\nAVÍCOLAS (" + avicolas.size() + "):");
    avicolas.forEach(a -> System.out.println(a.toString()));

    System.out.println("\nCACERAS (" + caceras.size() + "):");
    caceras.forEach(c -> System.out.println(c.toString()));

    System.out.println("\nTotal de animales: " + getCantidadDeAnimales());
  }

  // ================== BUSCAR (tipo buscarComputadora / buscarVehiculo) ==================
  /**
   * Busca un animal por nombre:
   * - Primero en la lista de avícolas
   * - Luego en la lista de cazadores
   * - Si no lo encuentra, retorna null
   */
  public Animalito buscarAnimal(String nombre) {
    if (nombre == null) {
      return null;
    }
    String buscado = nombre.toUpperCase();

    for (Avicolas a : avicolas) {
      if (a.getNombre().equalsIgnoreCase(buscado)) {
        return a;
      }
    }

    for (Caceras c : caceras) {
      if (c.getNombre().equalsIgnoreCase(buscado)) {
        return c;
      }
    }

    return null;
  }

  // ================== ELIMINAR ==================

  public boolean eliminarAnimal(String nombre) {
    Animalito encontrado = buscarAnimal(nombre);
    if (encontrado == null) {
      return false;
    }

    if (encontrado instanceof Avicolas) {
      return avicolas.remove(encontrado);
    } else if (encontrado instanceof Caceras) {
      return caceras.remove(encontrado);
    }
    return false;
  }

  // ================== ACTUALIZAR ==================

  public boolean actualizarAvicola(String nombreOriginal, Avicolas nuevo) {
    if (nombreOriginal == null) {
      return false;
    }

    String buscado = nombreOriginal.toUpperCase();

    for (int i = 0; i < avicolas.size(); i++) {
      if (avicolas.get(i).getNombre().equalsIgnoreCase(buscado)) {
        avicolas.set(i, nuevo);
        return true;
      }
    }
    return false;
  }

  public boolean actualizarCacera(String nombreOriginal, Caceras nuevo) {
    if (nombreOriginal == null) {
      return false;
    }

    String buscado = nombreOriginal.toUpperCase();

    for (int i = 0; i < caceras.size(); i++) {
      if (caceras.get(i).getNombre().equalsIgnoreCase(buscado)) {
        caceras.set(i, nuevo);
        return true;
      }
    }
    return false;
  }

  // ================== CONSULTAS VARIAS ==================

  public int getCantidadDeAnimales() {
    return avicolas.size() + caceras.size();
  }

  public boolean existeAnimal(String nombre) {
    return buscarAnimal(nombre) != null;
  }
}

// ==========================================================
// Menu.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

public class Menu {

  public void mostrarMenuPrincipal() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("🐾 SISTEMA DE INVENTARIO - VETERINARIA 🐾");
    System.out.println("=".repeat(60));
    System.out.println("┌───────────────────────────────────────────────┐");
    System.out.println("│  1 │  Agregar Avícola                         │");
    System.out.println("│  2 │  Agregar Cazador                         │");
    System.out.println("│  3 │  Listar todos los animales               │");
    System.out.println("│  4 │  Buscar animal por nombre                │");
    System.out.println("│  5 │  Eliminar animal por nombre              │");
    System.out.println("│  6 │  Ver cantidad total de animales          │");
    System.out.println("│  7 │  Actualizar Avícola                      │");
    System.out.println("│  8 │  Actualizar Cazador                      │");
    System.out.println("└───────────────────────────────────────────────┘");
    System.out.print("➤ Ingrese opción (cualquier letra para salir): ");
  }
}

// ==========================================================
// Main.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Veterinaria;

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
          registrarAvicola(inventario, scanner);
          break;
        case 2:
          registrarCacera(inventario, scanner);
          break;
        case 3:
          inventario.listarAnimales();
          break;
        case 4:
          buscarAnimal(inventario, scanner);
          break;
        case 5:
          eliminarAnimal(inventario, scanner);
          break;
        case 6:
          System.out.println("Cantidad total de animales: " + inventario.getCantidadDeAnimales());
          break;
        case 7:
          actualizarAvicola(inventario, scanner);
          break;
        case 8:
          actualizarCacera(inventario, scanner);
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

  private static void registrarAvicola(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Especie: ");
      String especie = scanner.nextLine();

      System.out.print("Edad (años): ");
      int edad = Integer.parseInt(scanner.nextLine());

      System.out.print("Nombre: ");
      String nombre = scanner.nextLine();

      System.out.print("Peso (kg): ");
      double peso = Double.parseDouble(scanner.nextLine());

      System.out.print("Tipo de plumaje: ");
      String plumaje = scanner.nextLine();

      Avicolas a = new Avicolas(especie, edad, nombre, peso, plumaje);
      inventario.agregarAvicola(a);

      System.out.println("✅ Animal avícola registrado correctamente.");

    } catch (PesoInsuficienteException e) {
      System.out.println("❌ " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void registrarCacera(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Especie: ");
      String especie = scanner.nextLine();

      System.out.print("Edad (años): ");
      int edad = Integer.parseInt(scanner.nextLine());

      System.out.print("Nombre: ");
      String nombre = scanner.nextLine();

      System.out.print("Peso (kg): ");
      double peso = Double.parseDouble(scanner.nextLine());

      Caceras c = new Caceras(especie, edad, nombre, peso);
      inventario.agregarCacera(c);

      System.out.println("✅ Animal cazador registrado correctamente.");

    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void buscarAnimal(Inventario inventario, Scanner scanner) {
    System.out.print("Ingrese el nombre del animal a buscar: ");
    String nombre = scanner.nextLine();

    Animalito encontrado = inventario.buscarAnimal(nombre);
    if (encontrado != null) {
      System.out.println("✅ Animal encontrado:");
      System.out.println(encontrado);
    } else {
      System.out.println("❌ No se encontró ningún animal con ese nombre.");
    }
  }

  private static void eliminarAnimal(Inventario inventario, Scanner scanner) {
    System.out.print("Ingrese el nombre del animal a eliminar: ");
    String nombre = scanner.nextLine();

    if (inventario.eliminarAnimal(nombre)) {
      System.out.println("✅ Animal eliminado correctamente.");
    } else {
      System.out.println("❌ No se encontró ningún animal con ese nombre.");
    }
  }

  private static void actualizarAvicola(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Nombre del Avícola a actualizar: ");
      String nombreOriginal = scanner.nextLine();

      System.out.print("Nueva especie: ");
      String especie = scanner.nextLine();

      System.out.print("Nueva edad (años): ");
      int edad = Integer.parseInt(scanner.nextLine());

      System.out.print("Nuevo nombre: ");
      String nombreNuevo = scanner.nextLine();

      System.out.print("Nuevo peso (kg): ");
      double pesoNuevo = Double.parseDouble(scanner.nextLine());

      System.out.print("Nuevo tipo de plumaje: ");
      String plumajeNuevo = scanner.nextLine();

      Avicolas nuevo = new Avicolas(especie, edad, nombreNuevo, pesoNuevo, plumajeNuevo);

      if (inventario.actualizarAvicola(nombreOriginal, nuevo)) {
        System.out.println("✅ Avícola actualizado correctamente.");
      } else {
        System.out.println("❌ No se encontró un Avícola con ese nombre.");
      }

    } catch (PesoInsuficienteException e) {
      System.out.println("❌ " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void actualizarCacera(Inventario inventario, Scanner scanner) {
    try {
      System.out.print("Nombre del Cazador a actualizar: ");
      String nombreOriginal = scanner.nextLine();

      System.out.print("Nueva especie: ");
      String especie = scanner.nextLine();

      System.out.print("Nueva edad (años): ");
      int edad = Integer.parseInt(scanner.nextLine());

      System.out.print("Nuevo nombre: ");
      String nombreNuevo = scanner.nextLine();

      System.out.print("Nuevo peso (kg): ");
      double pesoNuevo = Double.parseDouble(scanner.nextLine());

      Caceras nuevo = new Caceras(especie, edad, nombreNuevo, pesoNuevo);

      if (inventario.actualizarCacera(nombreOriginal, nuevo)) {
        System.out.println("✅ Cazador actualizado correctamente.");
      } else {
        System.out.println("❌ No se encontró un Cazador con ese nombre.");
      }

    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }
}
