package org.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement
public class Productos {
    ArrayList<Producto> productos;

    public Productos() {
    }

    public Productos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @XmlElement
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

}
