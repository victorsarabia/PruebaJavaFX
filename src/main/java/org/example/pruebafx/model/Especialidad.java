package org.example.pruebafx.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "especialidades")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column
    private String descripcion;
    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // Usar solo si no hay muchos.
    private List<Medico> medicos;


    public Especialidad(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Especialidad() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String apellido1) { this.descripcion = descripcion; }

    public List<Medico> getMedicos() { return medicos; }
    public void setMedicos(List<Medico> medicos) { this.medicos = medicos; }

    @Override
    public String toString() {
        return "Especialidad{" +
                " id='"+ id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripción='" + descripcion + '\'' +
                '}';
    }
}