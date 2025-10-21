package com.example.prueba;

public class items {
    private int imagen;
    private String titulo;
    private String texto;
    private boolean seleccion;

    public items(int idImagen, String textoTitulo, String textoContenido, boolean favorite) {
        this.imagen = idImagen;
        this.titulo = textoTitulo;
        this.texto = textoContenido;
        this.seleccion = favorite;
    }

    // Getters y Setters
    public String get_textoTitulo() {
        return titulo;
    }

    public String get_textoContenido() {
        return texto;
    }

    public int get_idImagen() {
        return imagen;
    }

    public boolean get_seleccion() {
        return seleccion;
    }

    public void set_seleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }


}