package com.example.prueba;

public class Categoria {
    private String nombre;
    private String descripcion;
    private int icono;



    public Categoria(String nombre, String descripcion, int icono ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;

    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public int getIcono() { return icono;}
    public String getDescripcion() { return descripcion;}
}