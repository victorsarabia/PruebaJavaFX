package org.example.pruebafx.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicos")
public class Medicos {

    @Id
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido1;


    public Medicos(Long id, String nombre, String apellido1) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
    }

    public Medicos() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    @Override
    public String toString() {
        return "Medico{" +
                " id='"+ id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido1 + '\'' +
                '}';
    }
}
