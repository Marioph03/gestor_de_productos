package org.example;

import java.util.Scanner;

public class GestorDeProductos {
    public static void main(String[] args) {
        String nombre, rutaAlXML, rutaCSV;
        int opcion;
        boolean control=false;
        Producto p = new Producto();
        OperacionesSobreGestor op = new OperacionesSobreGestor();
        Scanner sc = new Scanner(System.in);


        while(!control) {

            System.out.println("=======================================");
            System.out.println("1. Añadir Productos");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Modificar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Exportar Producto a CSV");
            System.out.println("6. Salir");
            System.out.println("=======================================");
            System.out.println("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case OptionInterface.AÑADIR:
                    op.escribeProducto(p);
                break;

                case OptionInterface.BUSCAR:
                    System.out.println("Introduce el nombre del producto que quieres buscar: ");
                    nombre = sc.nextLine();
                    op.buscarProducto(nombre);
                break;

                case OptionInterface.MODIFICAR:
                    System.out.println("Introduce el nombre del producto que quieres modificar: ");
                    nombre = sc.nextLine();
                    op.modificarProducto(nombre);
                break;

                case OptionInterface.ELIMINAR:
                    System.out.println("Introduce el nombre del producto que quieres eliminar: ");
                    nombre = sc.nextLine();
                    op.eliminarProducto(nombre);
                break;

                case OptionInterface.EXPORTAR:
                    System.out.println("Introduce la ruta del archivo que quieres exportar: ");
                    rutaAlXML = sc.nextLine();
                    System.out.println("Introduce el nombre del archivo al que quieres exportar: ");
                    rutaCSV = sc.nextLine();
                    op.exportarXMLaCSV(rutaAlXML, rutaCSV);
                break;

                case OptionInterface.SALIR:
                    System.out.println("Has salido del programa");
                    control=true;
                break;
            }
        }
    }
}
