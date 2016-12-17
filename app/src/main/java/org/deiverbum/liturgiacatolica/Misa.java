package org.deiverbum.liturgiacatolica;

/**
 * Created by cedano on 10/12/16.
 */

public class Misa {
    // Atributos
    private String titulo;
    private String descripcion;
    private String imagen;

    public Misa() {
    }

    public Misa(String titulo, String descripcion, String imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}