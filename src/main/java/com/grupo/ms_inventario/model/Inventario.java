package com.grupo.ms_inventario.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @Column(name = "id_producto", length = 50)
    private String idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "stock", nullable = false)
    private int stock;

    public Inventario() {}

    public Inventario(String idProducto, String nombre, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
