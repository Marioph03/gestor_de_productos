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
import java.util.Scanner;

/**
 * La clase OperacionesSobreGestor contiene métodos para realizar
 * operaciones CRUD sobre productos almacenados en un archivo XML.
 */
public class OperacionesSobreGestor {
    Scanner sc = new Scanner(System.in);

    /**
     * Crea un nuevo producto solicitando sus atributos al usuario.
     *
     * @return El producto creado.
     */
    public Producto creaProducto() {
        // Crear un nuevo objeto Producto
        Producto p = new Producto();
        // Solicitar el nombre del producto al usuario
        System.out.println("Ingrese un Nombre para el producto: ");
        p.setNombre(sc.nextLine());
        // Solicitar el precio del producto al usuario
        System.out.println("Ingrese un Precio para el producto: ");
        p.setPrecio(sc.nextDouble());
        // Solicitar la cantidad del producto al usuario
        System.out.println("Ingrese un Cantidad para el producto: ");
        p.setCantidad(sc.nextInt());
        // Limpiar el buffer del scanner
        sc.nextLine();

        return p;
    }

    /**
     * Agrega un nuevo producto al archivo XML, serializándolo y almacenándolo.
     *
     * @param p El producto a escribir en el archivo XML.
     * @return true si la operación es exitosa, de lo contrario lanza una excepción.
     */
    public boolean escribeProducto(Producto p) {
        // Crear un nuevo producto solicitando datos al usuario
        p = creaProducto();
        ArrayList<Producto> productos = new ArrayList<>();
        // Especificar la ubicación del archivo XML
        File f = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");
        try {
            // Crear el contexto JAXB para la clase Productos
            JAXBContext jaxbContext = JAXBContext.newInstance(Productos.class);

            // Si el archivo XML ya existe, deserializar su contenido en una lista de productos
            if (f.exists()) {
                Unmarshaller um = jaxbContext.createUnmarshaller();
                Productos productosWrapper = (Productos) um.unmarshal(f);
                if (productosWrapper.getProductos() != null) {
                    productos.addAll(productosWrapper.getProductos());
                }
            }

            // Agregar el nuevo producto a la lista de productos
            productos.add(p);

            // Serializar la lista actualizada de productos y guardarla en el archivo XML
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(new Productos(productos), f);
            System.out.println("El producto se ha guardado correctamente");

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Busca un producto en el archivo XML según su nombre y lo muestra si es encontrado.
     *
     * @param nombre El nombre del producto a buscar.
     * @return El producto encontrado, o un objeto vacío si no existe.
     */
    public Producto buscarProducto(String nombre) {
        Producto p = new Producto();
        try {
            // Ruta del archivo XML
            File archivo = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");

            // Configurar el parser XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            // Obtener la lista de productos del archivo XML
            NodeList listaProductos = doc.getElementsByTagName("producto");

            boolean productoEncontrado = false;
            // Iterar por cada producto en la lista para buscar por nombre
            for (int i = 0; i < listaProductos.getLength(); i++) {
                Element producto = (Element) listaProductos.item(i);
                String nombreProducto = producto.getElementsByTagName("nombre").item(0).getTextContent();

                if (nombreProducto.equalsIgnoreCase(nombre)) {
                    // Si el producto es encontrado, extraer sus datos y asignarlos al objeto Producto
                    productoEncontrado = true;
                    p.setNombre(nombreProducto);
                    p.setCantidad(Integer.parseInt(producto.getElementsByTagName("cantidad").item(0).getTextContent()));
                    p.setPrecio(Double.parseDouble(producto.getElementsByTagName("precio").item(0).getTextContent()));
                    System.out.println("Producto encontrado:\nNombre: " + p.getNombre() + "\nCantidad: " + p.getCantidad() + "\nPrecio: $" + p.getPrecio());
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

    /**
     * Modifica los atributos de un producto especificado por su nombre en el archivo XML.
     *
     * @param nombre El nombre del producto a modificar.
     * @return El producto modificado con los nuevos valores.
     */
    public Producto modificarProducto(String nombre) {
        String nuevoNombre;
        int nuevaCantidad;
        double nuevoPrecio;
        Producto p = new Producto();
        try {
            // Ruta del archivo XML
            File archivo = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");

            // Configurar el parser XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            // Obtener la lista de productos del archivo XML
            NodeList listaProductos = doc.getElementsByTagName("producto");
            boolean productoEncontrado = false;

            // Iterar por cada producto en la lista para buscar por nombre
            for (int i = 0; i < listaProductos.getLength(); i++) {
                Element producto = (Element) listaProductos.item(i);
                String nombreProducto = producto.getElementsByTagName("nombre").item(0).getTextContent();

                if (nombreProducto.equalsIgnoreCase(nombre)) {
                    // Solicitar nuevos valores y actualizarlos en el XML
                    productoEncontrado = true;
                    System.out.println("Introduce el nuevo nombre del producto: ");
                    nuevoNombre = sc.nextLine();
                    producto.getElementsByTagName("nombre").item(0).setTextContent(nuevoNombre);
                    System.out.println("Introduce el nuevo precio del producto: ");
                    nuevoPrecio = sc.nextDouble();
                    producto.getElementsByTagName("precio").item(0).setTextContent(String.valueOf(nuevoPrecio));
                    System.out.println("Introduce la nueva cantidad del producto: ");
                    nuevaCantidad = sc.nextInt();
                    producto.getElementsByTagName("cantidad").item(0).setTextContent(String.valueOf(nuevaCantidad));

                    // Actualizar los valores del objeto Producto
                    p.setNombre(nuevoNombre);
                    p.setPrecio(nuevoPrecio);
                    p.setCantidad(nuevaCantidad);
                    break;
                }
            }

            // Guardar los cambios en el archivo XML si se encontró el producto
            if (productoEncontrado) {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(new DOMSource(doc), new StreamResult(archivo));
                System.out.println("Los cambios se han guardado en el archivo XML.");
            } else {
                System.out.println("El producto no existe.");
            }
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * Elimina un producto del archivo XML según su nombre.
     *
     * @param nombre El nombre del producto a eliminar.
     * @return true si el producto fue eliminado, false si no se encontró.
     */
    public boolean eliminarProducto(String nombre) {
        boolean productoEliminado = false;

        try {
            // Ruta del archivo XML
            File archivo = new File("C:\\Users\\Mario\\IdeaProjects\\SGProductos\\src\\main\\resources\\producto.xml");

            // Configurar el parser XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            // Obtener la lista de productos del archivo XML
            NodeList listaProductos = doc.getElementsByTagName("producto");

            // Iterar por cada producto en la lista para buscar por nombre
            for (int i = 0; i < listaProductos.getLength(); i++) {
                Element producto = (Element) listaProductos.item(i);
                String nombreProducto = producto.getElementsByTagName("nombre").item(0).getTextContent();

                if (nombreProducto.equalsIgnoreCase(nombre)) {
                    // Eliminar el producto encontrado
                    producto.getParentNode().removeChild(producto);
                    productoEliminado = true;
                    System.out.println("Producto eliminado correctamente.");
                    break;
                }
            }

            // Guardar los cambios en el archivo XML si se eliminó un producto
            if (productoEliminado) {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(new DOMSource(doc), new StreamResult(archivo));
            } else {
                System.out.println("El producto no existe.");
            }

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return productoEliminado;
    }

    /**
     * Lee productos desde un archivo XML y los convierte en una lista de objetos Producto.
     *
     * @param archivoXML La ruta del archivo XML.
     * @return La lista de productos leídos del archivo.
     */
    public ArrayList<Producto> leerProductosDesdeXML(String archivoXML) {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Productos.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Productos productosWrapper = (Productos) unmarshaller.unmarshal(new File(archivoXML));
            if (productosWrapper.getProductos() != null) {
                productos.addAll(productosWrapper.getProductos());
            }
        } catch (JAXBException e) {
            System.out.println("Error al leer productos desde XML: " + e.getMessage());
        }
        return productos;
    }

    /**
     * Exporta una lista de productos a un archivo CSV.
     *
     * @param productos La lista de productos a exportar.
     * @param archivoCSV La ruta del archivo CSV de salida.
     */
    public void exportarProductosACSV(ArrayList<Producto> productos, String archivoCSV) {
        try (FileWriter writer = new FileWriter(archivoCSV)) {
            writer.write("Nombre,Precio,Cantidad\n");
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + "," + producto.getPrecio() + "," + producto.getCantidad() + "\n");
            }
            System.out.println("Los productos se han exportado correctamente a " + archivoCSV);

        } catch (IOException e) {
            System.out.println("Error al exportar los productos a CSV: " + e.getMessage());
        }
    }

    /**
     * Lee productos de un archivo XML y los exporta a un archivo CSV.
     *
     * @param archivoXML La ruta al archivo XML de entrada.
     * @param archivoCSV La ruta del archivo CSV de salida.
     * @return true si la operación es exitosa.
     */
    public boolean exportarXMLaCSV(String archivoXML, String archivoCSV) {
        ArrayList<Producto> productos = leerProductosDesdeXML(archivoXML);
        exportarProductosACSV(productos, archivoCSV);
        return true;
    }
}