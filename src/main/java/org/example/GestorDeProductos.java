package org.example;

public class GestorDeProductos {
    public static void main(String[] args) {
        Producto p = new Producto();
        OperacionesSobreGestor op = new OperacionesSobreGestor();
        System.out.println(op.exportarXMLaCSV("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml",
               "productosCSV"));

        //System.out.println(op.escribeProducto(p));
    }
}
