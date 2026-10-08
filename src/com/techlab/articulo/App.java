// Preentrega Gestion de Articulos - Santino Toso

package com.techlab.articulo;
import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.Categoria;
import java.util.Scanner;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Articulo> articulos = new ArrayList<>();

        ArrayList<Categoria> categorias = new ArrayList<>();

        precargarCategorias(categorias);

        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA DE ARTÍCULOS");
            System.out.println("========================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Listar categorías");
            System.out.println("0 - Salir");
            System.out.println("========================================");

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarArticulo(scanner, articulos, categorias);
                    break;

                case 2:
                    listarArticulos(articulos);
                    break;

                case 3:
                    consultarArticulo(scanner, articulos);
                    break;

                case 4:
                    modificarArticulo(scanner, articulos, categorias);
                    break;

                case 5:
                    eliminarArticulo(scanner, articulos);
                    break;

                case 6:
                    listarCategorias(categorias);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción incorrecta. Intente nuevamente.");
            }

        } while (opcion != 0);

        scanner.close();

    }

    public static void precargarCategorias(ArrayList<Categoria> categorias) {

        categorias.add(new Categoria(1, "Electrónica", "Productos tecnológicos y electrónicos"));

        categorias.add(new Categoria(2, "Periféricos", "Accesorios para computadora"));

        categorias.add(new Categoria(3, "Alimentos", "Productos alimenticios"));

        categorias.add(new Categoria(4, "Limpieza", "Artículos de limpieza del hogar"));

    }

    //------ Ingresar articulo

    public static void ingresarArticulo(Scanner scanner,
                                        ArrayList<Articulo> articulos,
                                        ArrayList<Categoria> categorias) {

        System.out.println("\n--- INGRESAR ARTÍCULO ---");
        System.out.println("1 - Artículo electrónico");
        System.out.println("2 - Artículo alimenticio");

        int tipo;

        do {
            tipo = leerEntero(scanner, "Seleccione el tipo de artículo: ");

            if (tipo != 1 && tipo != 2) {
                System.out.println("Tipo inválido. Elegí 1 o 2.");
            }

        } while (tipo != 1 && tipo != 2);

        int codigo = leerEntero(scanner, "Código del artículo: ");

        if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Ya existe un artículo con ese código. Se canceló el ingreso.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Nombre del artículo: ");
        double precio = leerDoubleNoNegativo(scanner, "Precio del artículo: ");

        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : categorias) {
            System.out.println(categoria.getCodigo() + " - " + categoria.getNombre());
        }

        Categoria categoria = pedirCategoriaExistente(scanner, categorias);

        Articulo articulo;

        if (tipo == 1) {
            int garantia = leerEnteroNoNegativo(scanner, "Garantía en meses: ");

            articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantia);

        } else {
            int dias = leerEnteroNoNegativo(scanner, "Días para vencimiento: ");

            articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, dias);
        }

        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println(articulo);
    }


    //------ Listar articulos

    public static void listarArticulos(ArrayList<Articulo> articulos) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : articulos) {
            System.out.println(articulo);
            System.out.println();
        }
    }


    //------ Consultar articulo

    public static void consultarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Código del artículo a consultar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        System.out.println(articulo);
        System.out.println("Detalle específico: " + articulo.getDetalleEspecifico());
    }


    //------ Modificar articulo

    public static void modificarArticulo(Scanner scanner,
                                         ArrayList<Articulo> articulos,
                                         ArrayList<Categoria> categorias) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Código del artículo a modificar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        String nombre = leerTextoNoVacio(scanner, "Nuevo nombre: ");
        double precio = leerDoubleNoNegativo(scanner, "Nuevo precio: ");

        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : categorias) {
            System.out.println(categoria.getCodigo() + " - " + categoria.getNombre());
        }

        Categoria nuevaCategoria = pedirCategoriaExistente(scanner, categorias);

        articulo.setNombre(nombre);
        articulo.setPrecio(precio);
        articulo.setCategoria(nuevaCategoria);

        if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;

            int garantia = leerEnteroNoNegativo(scanner, "Nueva garantía en meses: ");
            electronico.setGarantiaMeses(garantia);

        } else if (articulo instanceof ArticuloAlimenticio) {
            ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;

            int dias = leerEnteroNoNegativo(scanner, "Nuevos días para vencimiento: ");
            alimenticio.setDiasParaVencimiento(dias);
        }

        System.out.println("Artículo modificado correctamente.");
    }


    //------ Eliminar articulo

    public static void eliminarArticulo(Scanner scanner, ArrayList<Articulo> articulos) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(scanner, "Código del artículo a eliminar: ");

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        articulos.remove(articulo);

        System.out.println("Artículo eliminado correctamente.");
    }


    //------ Listar categorias

    public static void listarCategorias(ArrayList<Categoria> categorias) {

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
            System.out.println();
        }
    }

    public static int leerEntero(Scanner scanner, String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero válido.");
            }
        }
    }

    public static Articulo buscarArticuloPorCodigo(ArrayList<Articulo> articulos, int codigo) {

        for (Articulo articulo : articulos) {
            if (articulo.getCodigo() == codigo) {
                return articulo;
            }
        }

        return null;
    }

    public static Categoria buscarCategoriaPorCodigo(ArrayList<Categoria> categorias, int codigo) {

        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }

        return null;
    }

    public static Categoria pedirCategoriaExistente(Scanner scanner, ArrayList<Categoria> categorias) {

        while (true) {
            int codigo = leerEntero(scanner, "Código de la categoría: ");

            Categoria categoria = buscarCategoriaPorCodigo(categorias, codigo);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("La categoría no existe. Intentá nuevamente.");
        }
    }

    public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {

        while (true) {
            int numero = leerEntero(scanner, mensaje);

            if (numero >= 0) {
                return numero;
            }

            System.out.println("Error: no se permiten valores negativos.");
        }
    }

    public static double leerDoubleNoNegativo(Scanner scanner, String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                double numero = Double.parseDouble(scanner.nextLine().trim());

                if (Double.isFinite(numero) && numero >= 0) {
                    return numero;
                }

                System.out.println("Error: ingresá un precio no negativo y finito.");

            } catch (NumberFormatException e) {
                System.out.println("Error: ingresá un número decimal válido.");
            }
        }
    }

    public static String leerTextoNoVacio(Scanner scanner, String mensaje) {

        while (true) {
            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Error: el texto no puede estar vacío.");
        }
    }

}
