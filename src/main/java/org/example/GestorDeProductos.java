package org.example;

public class GestorDeProductos {
    public static void main(String[] args) {
        Producto p = new Producto();
        OperacionesSobreGestor op = new OperacionesSobreGestor();
        System.out.println(op.agregarProducto(p));
    }
}
