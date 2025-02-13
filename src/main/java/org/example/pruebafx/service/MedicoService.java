package org.example.pruebafx.service;

import org.example.pruebafx.dao.MedicoDAO;
import org.example.pruebafx.model.Medico;

import java.util.HashMap;
import java.util.List;

public class MedicoService {

    private final MedicoDAO medicoDAO = new MedicoDAO();

    public void save(Medico medico) {
        // Validación antes de guardar
        if (medico.getId() == null || medico.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        medicoDAO.save(medico);
    }

    public List<Medico> getAll() {
        return medicoDAO.getAll();
    }

    public List<Medico> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return medicoDAO.getPaginated(page, offset, filtros);
    }
}