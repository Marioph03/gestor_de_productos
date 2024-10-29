package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OperacionesSobreGestor {
    Scanner sc = new Scanner(System.in);

    public Producto creaProducto(){
        Producto p = new Producto();
        System.out.println("Ingrese un Nombre para el producto: ");
        p.setNombre(sc.nextLine());
        System.out.println("Ingrese un Precio para el producto: ");
        p.setPrecio(sc.nextDouble());
        System.out.println("Ingrese un Cantidad para el producto: ");
        p.setCantidad(sc.nextInt());
        sc.nextLine();
        return p;
    }

    public boolean escribeProducto(Producto p){
        p = creaProducto();
        ArrayList<Producto> productos = new ArrayList<>();
        JAXBContext jaxbContext = null;
        File f = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");
        try {
            jaxbContext = JAXBContext.newInstance(Productos.class);

            // Si el archivo existe, lo leemos
            if (f.exists()) {
                Unmarshaller um = jaxbContext.createUnmarshaller();
                Productos productosWrapper = (Productos) um.unmarshal(f);

                // Si la lista deserializada es nula, la inicializamos
                if (productosWrapper.getProductos() != null) {
                    productos.addAll(productosWrapper.getProductos());
                }
            }
            // Agregamos el nuevo producto
            productos.add(p);

            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           // m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "producto.xsd");
            m.marshal(new Productos(productos), f); // Serializar y guardar
            System.out.println("El producto se ha guardado correctamente");

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /** public Producto agregarProducto(Producto p) {


     try {
     List<Producto> productos = new ArrayList<>();

     // Si el archivo existe, lo leemos
     if (f.exists()) {
     Unmarshaller um = jaxbContext.createUnmarshaller();
     Productos productosWrapper = (Productos) um.unmarshal(f);

     // Si la lista deserializada es nula, la inicializamos
     if (productosWrapper.getProductos() != null) {
     productos.addAll(productosWrapper.getProductos());
     }
     }
     // Agregamos el nuevo producto
     productos.add(p);

     // Guardamos la lista actualizada de productos en el archivo XML

     m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Dar formato legible al XML

     } catch (JAXBException e) {
     throw new RuntimeException(e);
     }
     return p;
     }**/

    public Producto buscarProducto(String nombre) {
        Producto p = new Producto();
        try {
            File archivo = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);

            doc.getDocumentElement().normalize();

            NodeList listaProductos = doc.getElementsByTagName("productos");

            boolean productoEncontrado = false;
            for (int i = 0; i < listaProductos.getLength(); i++) {
                Element producto = (Element) listaProductos.item(i);

                // Obtenemos el valor del nombre del producto
                String nombreProducto = producto.getElementsByTagName("nombre").item(0).getTextContent();

                // Si el nombre coincide, se muestra la información
                if (nombreProducto.equalsIgnoreCase(nombre)) {
                    productoEncontrado = true;
                    p = new Producto();
                    p.setNombre(nombreProducto);
                    p.setCantidad(Integer.parseInt(producto.getElementsByTagName("cantidad").item(0).getTextContent()));
                    p.setPrecio(Double.parseDouble(producto.getElementsByTagName("precio").item(0).getTextContent()));

                    System.out.println("Producto encontrado:");
                    System.out.println("Nombre: " + p.getNombre());
                    System.out.println("Cantidad: " + p.getCantidad());
                    System.out.println("Precio: $" + p.getPrecio());
                    break;
                }
            }
            if (!productoEncontrado) {
                System.out.println("El producto no existe.");
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return p;
    }

    public Producto modificarProducto(String nombre) {
        String nuevoNombre;
        int nuevaCantidad = 0;
        double nuevoPrecio = 0;
        Producto p = new Producto();
        Scanner sc = new Scanner(System.in);
        try {
            File archivo = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);

            doc.getDocumentElement().normalize();

            NodeList listaProductos = doc.getElementsByTagName("productos");

            boolean productoEncontrado = false;
            for (int i = 0; i < listaProductos.getLength(); i++) {
                Element producto = (Element) listaProductos.item(i);

                // Obtenemos el valor del nombre del producto
                String nombreProducto = producto.getElementsByTagName("nombre").item(0).getTextContent();

                // Si el nombre coincide, se muestra la información
                if (nombreProducto.equalsIgnoreCase(nombre)) {
                    productoEncontrado = true;
                    p = new Producto();
                    System.out.println("introduce el nuevo nombre del producto: ");
                    nuevoNombre = sc.nextLine();
                    producto.getElementsByTagName("nombre").item(0).setTextContent(nuevoNombre);
                    System.out.println("Introduce el nuevo precio del producto: ");
                    nuevoPrecio = sc.nextDouble();
                    producto.getElementsByTagName("cantidad").item(0).setTextContent(String.valueOf(nuevoPrecio));
                    System.out.println("Introduce la nuevo cantidad del producto: ");
                    nuevaCantidad = sc.nextInt();
                    producto.getElementsByTagName("cantidad").item(0).setTextContent(String.valueOf(nuevaCantidad));

                    p.setNombre(nuevoNombre);
                    p.setPrecio(nuevoPrecio);
                    p.setCantidad(nuevaCantidad);

                    System.out.println("Producto modificado:");
                    break;
                }
            }
            if (!productoEncontrado) {
                System.out.println("El producto no existe.");
            } else {
                // Guardamos los cambios en el archivo XML
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(archivo);
                transformer.transform(source, result);
                System.out.println("Los cambios se han guardado en el archivo XML.");
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public boolean eliminarProducto(String nombre) {
        Producto p = new Producto();
        boolean productoEliminado = false;

        try {
            File archivo = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);

            doc.getDocumentElement().normalize();

            NodeList listaProductos = doc.getElementsByTagName("productos");

            for (int i = 0; i < listaProductos.getLength(); i++) {
                Element producto = (Element) listaProductos.item(i);

                // Obtenemos el valor del nombre del producto
                String nombreProducto = producto.getElementsByTagName("nombre").item(0).getTextContent();

                // Si el nombre coincide, eliminamos el nodo del producto
                if (nombreProducto.equalsIgnoreCase(nombre)) {
                    producto.getParentNode().removeChild(producto);
                    productoEliminado = true;
                    System.out.println("Producto eliminado: " + nombre);
                    break;
                }
            }

            if (productoEliminado) {
                // Guardamos los cambios en el archivo XML
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(archivo);
                transformer.transform(source, result);
                System.out.println("Los cambios se han guardado en el archivo XML.");
            } else {
                System.out.println("El producto no existe.");
            }

        } catch (ParserConfigurationException | IOException | SAXException | javax.xml.transform.TransformerException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());;
        }

        return productoEliminado;
    }

    public ArrayList<Producto> leerProductosDesdeXML(String archivoXML) {
        ArrayList<Producto> productos = new ArrayList<>();

        try {
            File file = new File(archivoXML);
            JAXBContext jaxbContext = JAXBContext.newInstance(Productos.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Productos productosWrapper = (Productos) unmarshaller.unmarshal(file);

            // Obtener la lista de productos
            if (productosWrapper.getProductos() != null) {
                productos = productosWrapper.getProductos();
            }

        } catch (JAXBException e) {
            System.err.println("Error al leer productos desde XML: " + e.getMessage());
        }
        return productos;
    }

    public void exportarProductosACSV(ArrayList<Producto> productos, String archivoCSV) {
        try (FileWriter writer = new FileWriter(archivoCSV)) {
            // Escribir la cabecera del archivo CSV
            writer.write("Nombre,Precio,Cantidad\n");

            // Escribir cada producto en el archivo
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + "," + producto.getPrecio() + "," + producto.getCantidad() + "\n");
            }

            System.out.println("Los productos se han exportado correctamente a " + archivoCSV);

        } catch (IOException e) {
            System.err.println("Error al exportar los productos a CSV: " + e.getMessage());
        }
    }

    public boolean exportarXMLaCSV(String archivoXML, String archivoCSV) {
        // Leer los productos desde el archivo XML
        ArrayList<Producto> productos = leerProductosDesdeXML(archivoXML);

        // Exportar la lista de productos a CSV
        exportarProductosACSV(productos, archivoCSV);
        return true;
    }
}