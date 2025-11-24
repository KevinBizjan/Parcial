// ==========================================================
// Computadora.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

abstract class Computadora {
  protected String marca;
  protected int modelo;
  protected String numeroSerie;
  protected int horasUso;

  public Computadora(String marca, int modelo, String numeroSerie, int horasUso) {
    this.marca = marca.toUpperCase();
    this.modelo = modelo;
    this.numeroSerie = numeroSerie.toUpperCase();
    this.horasUso = horasUso;
  }

  // Getters
  public String getMarca() {
    return marca;
  }

  public int getModelo() {
    return modelo;
  }

  public String getNumeroSerie() {
    return numeroSerie;
  }

  public int getHorasUso() {
    return horasUso;
  }

  // Setters (opcionales)
  public void setMarca(String marca) {
    this.marca = marca.toUpperCase();
  }

  public void setModelo(int modelo) {
    this.modelo = modelo;
  }

  public void setNumeroSerie(String numeroSerie) {
    this.numeroSerie = numeroSerie.toUpperCase();
  }

  public void setHorasUso(int horasUso) {
    this.horasUso = horasUso;
  }

  // ====== EJERCICIO TIPO "Analizar igualdad" ======
  // Dos computadoras son iguales SOLO si tienen el mismo número de serie
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Computadora otra = (Computadora) obj;

    if (this.numeroSerie == null || otra.numeroSerie == null) {
      return false;
    }

    return this.numeroSerie.equalsIgnoreCase(otra.numeroSerie);
  }

  @Override
  public int hashCode() {
    return (numeroSerie == null) ? 0 : numeroSerie.toUpperCase().hashCode();
  }

  public abstract String verTipoDeComputadora();

  // toString genérico
  @Override
  public String toString() {
    return verTipoDeComputadora() + "\t" + marca + "\t" + modelo + "\t"
        + numeroSerie + "\t" + horasUso + "hs";
  }
}

//Termina ejercicio comparacion igualdad
// ==========================================================
// Ventas.java (interface)
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

public interface Ventas {

  /**
   * Calcula el precio de venta de la computadora a partir de un
   * precio base y el año actual.
   */
  double calcularPrecioVenta(double precioBase, int anioActual);
}


// ==========================================================
// PuertosInsuficientesException.java
// (Ejercicio tipo Excepciones, para Escritorio)
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

/**
 * Excepción verificada que se lanza cuando se intenta crear
 * un Escritorio con menos de 5 puertos disponibles.
 */
public class PuertosInsuficientesException extends Exception {

  // Constructor con mensaje personalizado
  public PuertosInsuficientesException(String mensaje) {
    super(mensaje);
  }

  // Constructor por defecto
  public PuertosInsuficientesException() {
    super("Error: una computadora de escritorio debe tener al menos 5 puertos disponibles.");
  }
}
//termina ejercicio parte 1
// ==========================================================
// Escritorio.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

class Escritorio extends Computadora implements Ventas {

  private int cantidadPuertos;

  public Escritorio(String marca, int modelo, String numeroSerie,
                    int horasUso, int cantidadPuertos)
      throws PuertosInsuficientesException {

    super(marca, modelo, numeroSerie, horasUso);

    // ====== EJERCICIO TIPO "Excepciones" ======
    if (cantidadPuertos < 5) {
      throw new PuertosInsuficientesException(
          "Error: un Escritorio debe tener al menos 5 puertos. Valor recibido: " + cantidadPuertos);
    }
   //termina ejercicio escritorio parte 2
    this.cantidadPuertos = cantidadPuertos;
  }

  public int getCantidadPuertos() {
    return cantidadPuertos;
  }

  public void setCantidadPuertos(int cantidadPuertos) {
    this.cantidadPuertos = cantidadPuertos;
  }

  @Override
  public String verTipoDeComputadora() {
    return "🖥️ ESCRITORIO";
  }

  // Implementación EJEMPLO de la interface (puede variar según consigna)
  @Override
  public double calcularPrecioVenta(double precioBase, int anioActual) {
    int aniosDeUso = anioActual - this.modelo;
    if (aniosDeUso < 0) {
      aniosDeUso = 0;
    }

    // Ejemplo: depreciación del 8% por año para escritorio
    double depreciacion = aniosDeUso * 0.08;
    if (depreciacion > 0.90) {
      depreciacion = 0.90;
    }

    double precioDepreciado = precioBase * (1 - depreciacion);

    // Un pequeño descuento adicional, ej: 5%
    double descuentoAdicional = 0.05;
    return precioDepreciado * (1 - descuentoAdicional);
  }

  @Override
  public String toString() {
    return super.toString() + "\tPuertos: " + cantidadPuertos;
  }
}

// ==========================================================
// Laptop.java
// (Ejercicio tipo "Implementar la interface")
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

class Laptop extends Computadora implements Ventas {

  public Laptop(String marca, int modelo, String numeroSerie, int horasUso) {
    super(marca, modelo, numeroSerie, horasUso);
  }

  @Override
  public String verTipoDeComputadora() {
    return "💻 LAPTOP";
  }

  // ====== EJERCICIO TIPO "Implementar la interface" ======
  /**
   * El cálculo del precio final debe considerar:
   * - 12% de depreciación por cada año de uso.
   * - 15% de descuento adicional por desgaste de portabilidad.
   *
   * Pasos:
   * - Calcular años de uso.
   * - Aplicar depreciación anual.
   * - Aplicar descuento por portabilidad.
   */
  @Override
  public double calcularPrecioVenta(double precioBase, int anioActual) {
    // Calcular años de uso
    int aniosDeUso = anioActual - this.modelo;
    if (aniosDeUso < 0) {
      aniosDeUso = 0;
    }

    // 12% de depreciación por cada año de uso
    double depreciacion = aniosDeUso * 0.12;

    // Opcional: tope máximo de depreciación (ej: 90%)
    if (depreciacion > 0.90) {
      depreciacion = 0.90;
    }

    double precioDepreciado = precioBase * (1 - depreciacion);

    // Descuento adicional del 15% por portabilidad
    double descuentoPortabilidad = 0.15;

    double precioFinal = precioDepreciado * (1 - descuentoPortabilidad);

    return precioFinal;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
//termina ejercicio
// ==========================================================
// InventarioComputadoras.java
// (Ejercicio tipo "Buscar elemento" + CRUD básico)
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

import java.util.ArrayList;

public class InventarioComputadoras {

  private ArrayList<Escritorio> escritorios;
  private ArrayList<Laptop> laptops;

  public InventarioComputadoras() {
    this.escritorios = new ArrayList<>();
    this.laptops = new ArrayList<>();
  }

  // ================== ALTAS ==================

  public void agregarEscritorio(Escritorio e) {
    escritorios.add(e);
  }

  public void agregarLaptop(Laptop l) {
    laptops.add(l);
  }

  // ================== LISTAR ==================

  public void listarComputadoras() {
    System.out.println("\nESCRITORIOS (" + escritorios.size() + "):");
    escritorios.forEach(e -> System.out.println(e.toString()));

    System.out.println("\nLAPTOPS (" + laptops.size() + "):");
    laptops.forEach(l -> System.out.println(l.toString()));

    System.out.println("\nTotal de computadoras: " + getCantidadComputadoras());
  }

  // ================== EJERCICIO TIPO "Buscar elemento" ==================
  /**
   * Busca una computadora por número de serie.
   * - Primero en la lista de ESCRITORIOS.
   * - Si no la encuentra, busca en la lista de LAPTOPS.
   * - Si no está en ninguna, retorna null.
   */
  public Computadora buscarComputadora(String numeroSerie) {
    if (numeroSerie == null) {
      return null;
    }

    String serieBuscada = numeroSerie.toUpperCase();

    // Buscar primero en escritorios
    for (Escritorio e : escritorios) {
      if (e.getNumeroSerie().equalsIgnoreCase(serieBuscada)) {
        return e;
      }
    }

    // Si no se encontró, buscar en laptops
    for (Laptop l : laptops) {
      if (l.getNumeroSerie().equalsIgnoreCase(serieBuscada)) {
        return l;
      }
    }

    // Si no está en ninguna lista, retornar null
    return null;
  }
  //termina ejercicio
  // ================== ELIMINAR ==================

  public boolean eliminarComputadora(String numeroSerie) {
    Computadora encontrada = buscarComputadora(numeroSerie);
    if (encontrada == null) {
      return false;
    }

    if (encontrada instanceof Escritorio) {
      return escritorios.remove(encontrada);
    } else if (encontrada instanceof Laptop) {
      return laptops.remove(encontrada);
    }
    return false;
  }

  // ================== ACTUALIZAR ==================

  public boolean actualizarEscritorio(String numeroSerieOriginal, Escritorio nuevo) {
    if (numeroSerieOriginal == null) {
      return false;
    }

    String serieOriginal = numeroSerieOriginal.toUpperCase();

    for (int i = 0; i < escritorios.size(); i++) {
      if (escritorios.get(i).getNumeroSerie().equalsIgnoreCase(serieOriginal)) {
        escritorios.set(i, nuevo);
        return true;
      }
    }
    return false;
  }

  public boolean actualizarLaptop(String numeroSerieOriginal, Laptop nueva) {
    if (numeroSerieOriginal == null) {
      return false;
    }

    String serieOriginal = numeroSerieOriginal.toUpperCase();

    for (int i = 0; i < laptops.size(); i++) {
      if (laptops.get(i).getNumeroSerie().equalsIgnoreCase(serieOriginal)) {
        laptops.set(i, nueva);
        return true;
      }
    }
    return false;
  }

  // ================== CONSULTAS VARIAS ==================

  public int getCantidadComputadoras() {
    return escritorios.size() + laptops.size();
  }

  public boolean existeComputadora(String numeroSerie) {
    return buscarComputadora(numeroSerie) != null;
  }
}


// ==========================================================
// Menu.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

public class Menu {

  public void mostrarMenuPrincipal() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("🖥️💻 SISTEMA DE INVENTARIO - TECNOLOGÍA 💻🖥️");
    System.out.println("=".repeat(60));
    System.out.println("┌───────────────────────────────────────────────┐");
    System.out.println("│  1 │  Agregar Escritorio                      │");
    System.out.println("│  2 │  Agregar Laptop                          │");
    System.out.println("│  3 │  Listar todas las computadoras           │");
    System.out.println("│  4 │  Buscar computadora por número de serie  │");
    System.out.println("│  5 │  Eliminar computadora por número de serie│");
    System.out.println("│  6 │  Ver cantidad total de computadoras      │");
    System.out.println("│  7 │  Actualizar Escritorio                   │");
    System.out.println("│  8 │  Actualizar Laptop                       │");
    System.out.println("└───────────────────────────────────────────────┘");
    System.out.print("➤ Ingrese opción (cualquier letra para salir): ");
  }
}


// ==========================================================
// Main.java
// ==========================================================
package Parciales.Parcial2025.Segundo.Tecnologia;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    InventarioComputadoras inventario = new InventarioComputadoras();
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
          registrarEscritorio(inventario, scanner);
          break;
        case 2:
          registrarLaptop(inventario, scanner);
          break;
        case 3:
          inventario.listarComputadoras();
          break;
        case 4:
          buscarComputadora(inventario, scanner);
          break;
        case 5:
          eliminarComputadora(inventario, scanner);
          break;
        case 6:
          System.out.println("Cantidad total de computadoras: "
              + inventario.getCantidadComputadoras());
          break;
        case 7:
          actualizarEscritorio(inventario, scanner);
          break;
        case 8:
          actualizarLaptop(inventario, scanner);
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

  private static void registrarEscritorio(InventarioComputadoras inventario, Scanner scanner) {
    try {
      System.out.print("Marca: ");
      String marca = scanner.nextLine();

      System.out.print("Modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Número de serie: ");
      String numeroSerie = scanner.nextLine();

      System.out.print("Horas de uso: ");
      int horasUso = Integer.parseInt(scanner.nextLine());

      System.out.print("Cantidad de puertos: ");
      int puertos = Integer.parseInt(scanner.nextLine());

      Escritorio esc = new Escritorio(marca, modelo, numeroSerie, horasUso, puertos);
      inventario.agregarEscritorio(esc);

      System.out.println("✅ Escritorio registrado correctamente.");

    } catch (PuertosInsuficientesException e) {
      System.out.println("❌ " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void registrarLaptop(InventarioComputadoras inventario, Scanner scanner) {
    try {
      System.out.print("Marca: ");
      String marca = scanner.nextLine();

      System.out.print("Modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Número de serie: ");
      String numeroSerie = scanner.nextLine();

      System.out.print("Horas de uso: ");
      int horasUso = Integer.parseInt(scanner.nextLine());

      Laptop lap = new Laptop(marca, modelo, numeroSerie, horasUso);
      inventario.agregarLaptop(lap);

      System.out.println("✅ Laptop registrada correctamente.");

    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void buscarComputadora(InventarioComputadoras inventario, Scanner scanner) {
    System.out.print("Ingrese el número de serie a buscar: ");
    String numeroSerie = scanner.nextLine();

    Computadora encontrada = inventario.buscarComputadora(numeroSerie);
    if (encontrada != null) {
      System.out.println("✅ Computadora encontrada:");
      System.out.println(encontrada);
    } else {
      System.out.println("❌ No se encontró ninguna computadora con ese número de serie.");
    }
  }

  private static void eliminarComputadora(InventarioComputadoras inventario, Scanner scanner) {
    System.out.print("Ingrese el número de serie a eliminar: ");
    String numeroSerie = scanner.nextLine();

    if (inventario.eliminarComputadora(numeroSerie)) {
      System.out.println("✅ Computadora eliminada correctamente.");
    } else {
      System.out.println("❌ No se encontró ninguna computadora con ese número de serie.");
    }
  }

  private static void actualizarEscritorio(InventarioComputadoras inventario, Scanner scanner) {
    try {
      System.out.print("Número de serie del Escritorio a actualizar: ");
      String numeroSerieOriginal = scanner.nextLine();

      System.out.print("Nueva marca: ");
      String marca = scanner.nextLine();

      System.out.print("Nuevo modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Nuevo número de serie: ");
      String numeroSerieNueva = scanner.nextLine();

      System.out.print("Nuevas horas de uso: ");
      int horasUsoNueva = Integer.parseInt(scanner.nextLine());

      System.out.print("Nueva cantidad de puertos: ");
      int puertosNuevos = Integer.parseInt(scanner.nextLine());

      Escritorio escNuevo = new Escritorio(marca, modelo, numeroSerieNueva,
                                           horasUsoNueva, puertosNuevos);

      if (inventario.actualizarEscritorio(numeroSerieOriginal, escNuevo)) {
        System.out.println("✅ Escritorio actualizado correctamente.");
      } else {
        System.out.println("❌ No se encontró un Escritorio con ese número de serie.");
      }

    } catch (PuertosInsuficientesException e) {
      System.out.println("❌ " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }

  private static void actualizarLaptop(InventarioComputadoras inventario, Scanner scanner) {
    try {
      System.out.print("Número de serie de la Laptop a actualizar: ");
      String numeroSerieOriginal = scanner.nextLine();

      System.out.print("Nueva marca: ");
      String marca = scanner.nextLine();

      System.out.print("Nuevo modelo (año): ");
      int modelo = Integer.parseInt(scanner.nextLine());

      System.out.print("Nuevo número de serie: ");
      String numeroSerieNueva = scanner.nextLine();

      System.out.print("Nuevas horas de uso: ");
      int horasUsoNueva = Integer.parseInt(scanner.nextLine());

      Laptop lapNueva = new Laptop(marca, modelo, numeroSerieNueva, horasUsoNueva);

      if (inventario.actualizarLaptop(numeroSerieOriginal, lapNueva)) {
        System.out.println("✅ Laptop actualizada correctamente.");
      } else {
        System.out.println("❌ No se encontró una Laptop con ese número de serie.");
      }

    } catch (NumberFormatException e) {
      System.out.println("❌ Error: formato numérico inválido.");
    }
  }
}

