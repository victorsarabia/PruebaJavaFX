package org.example.pruebafx.service;

import org.example.pruebafx.dao.MedicoDAO;
import org.example.pruebafx.model.Medicos;

import java.util.List;

public class MedicoService {

    private final MedicoDAO medicoDAO = new MedicoDAO();

    public void save(Medicos medico) {
        // Validación antes de guardar
        if (medico.getId() == null || medico.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        medicoDAO.save(medico);
    }

    public List<Medicos> getAll() {
        return medicoDAO.getAll();
    }

    public List<Medicos> getPaginated() {
        return medicoDAO.getPaginated();
    }
}