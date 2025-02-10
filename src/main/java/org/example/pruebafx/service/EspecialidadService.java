package org.example.pruebafx.service;

import org.example.pruebafx.dao.EspecialidadDAO;
import org.example.pruebafx.model.Especialidad;

import java.util.List;

public class EspecialidadService {

    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    public Especialidad getEspecialidadById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        return especialidadDAO.getEspecialidadById(id);
    }
    public void save(Especialidad especialidad) {
        // Validación antes de guardar
        if (especialidad.getId() == null || especialidad.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        especialidadDAO.save(especialidad);
    }

    public List<Especialidad> getAll() {
        return especialidadDAO.getAll();
    }

    public List<Especialidad> getPaginated() {
        return especialidadDAO.getPaginated(1, 50);
    }
}