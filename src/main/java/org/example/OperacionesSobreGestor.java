package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OperacionesSobreGestor {
Scanner sc = new Scanner(System.in);
    public Producto agregarProducto(Producto p) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        System.out.println("Ingrese un Nombre para el producto: ");
        p.setNombre(sc.nextLine());
        System.out.println("Ingrese un Precio para el producto: ");
        p.setPrecio(sc.nextDouble());
        System.out.println("Ingrese un Cantidad para el producto: ");
        p.setCantidad(sc.nextInt());
        sc.nextLine();

        File f = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\productos.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Productos.class);

            // Si el archivo existe, lo leemos
            if (f.exists()) {
                Unmarshaller um = jaxbContext.createUnmarshaller();
                Productos productosWrapper = (Productos) um.unmarshal(f);

                // Si la lista deserializada es nula, la inicializamos
                if (productosWrapper.getProductos() != null) {
                    productos = productosWrapper.getProductos();
                }
            }

            // Agregamos el nuevo producto
            productos.add(p);

            // Guardamos la lista actualizada de productos en el archivo XML
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Dar formato al XML
            m.marshal(new Productos(productos), f); // Serializar y guardar

            System.out.println("El producto se ha guardado correctamente");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
