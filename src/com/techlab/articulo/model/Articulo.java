package com.techlab.articulo.model;

public abstract class Articulo {

    //------ Atributos
    private int codigo;
    private String nombre;
    private double precio;
    private Categoria categoria;

    //------ Constructor
    public Articulo(int codigo, String nombre, double precio, Categoria categoria){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    //------ Metodos
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public abstract String getTipoArticulo();

    public abstract String getDetalleEspecifico();

    @Override
    public String toString() {
        return "----- ARTÍCULO -----\n" +
                "Código: " + codigo + "\n" +
                "Nombre: " + nombre + "\n" +
                "Precio: $" + precio + "\n" +
                "Categoría: " + categoria.getNombre() + "\n" +
                "Tipo: " + getTipoArticulo() + "\n" +
                "Detalle: " + getDetalleEspecifico() + "\n" +
                "--------------------";
    }

}
