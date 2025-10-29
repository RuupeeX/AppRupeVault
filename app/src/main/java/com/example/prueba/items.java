package com.example.prueba;

public class items {
    private int idImagen;
    private String textoTitulo;
    private String textoContenido;
    private boolean seleccion;
    private String descripcion;
    private String categoria;

    // Constructor original (mantener para compatibilidad)
    public items(int idImagen, String textoTitulo, String textoContenido, boolean seleccion) {
        this.idImagen = idImagen;
        this.textoTitulo = textoTitulo;
        this.textoContenido = textoContenido;
        this.seleccion = seleccion;
        this.descripcion = "Descripción por defecto del producto " + textoTitulo;
        this.categoria = "SNEAKERS"; // Valor por defecto
    }

    // Constructor con descripción
    public items(int idImagen, String textoTitulo, String textoContenido, boolean seleccion, String descripcion) {
        this.idImagen = idImagen;
        this.textoTitulo = textoTitulo;
        this.textoContenido = textoContenido;
        this.seleccion = seleccion;
        this.descripcion = descripcion;
        this.categoria = "SNEAKERS"; // Valor por defecto
    }

    // Nuevo constructor con categoría
    public items(int idImagen, String textoTitulo, String textoContenido, boolean seleccion, String categoria, String descripcion) {
        this.idImagen = idImagen;
        this.textoTitulo = textoTitulo;
        this.textoContenido = textoContenido;
        this.seleccion = seleccion;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int get_idImagen() { return idImagen; }
    public String get_textoTitulo() { return textoTitulo; }
    public String get_textoContenido() { return textoContenido; }
    public boolean get_seleccion() { return seleccion; }
    public void set_seleccion(boolean seleccion) { this.seleccion = seleccion; }
}