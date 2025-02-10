package org.example.pruebafx.controller;

import javafx.scene.control.TextField;
import org.example.pruebafx.model.Especialidad;
import org.example.pruebafx.service.EspecialidadService;
import org.example.pruebafx.service.MedicoService;
import javafx.fxml.FXML;
import org.example.pruebafx.model.Medico;

import java.io.IOException;
import java.util.List;

public class PrincipalController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    protected void mostrarMedicos() throws IOException {
        /*System.out.println("Inicio");
        try {

            Class.forName("org.mariadb.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/universidad?user=root&password=");

            if (connection != null) {
                System.out.println("Conexión a base de datos " + " OK");

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from alumno");
                while (rs.next()) {
                    System.out.println(rs.getString("dni") + " " + rs.getString(2) + " " + rs.getString(3));
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Fin");
        */

        System.out.println(txtNombre.getText());

        MedicoService medicoService = new MedicoService();
        List<Medico> medicos = medicoService.getPaginated();
        for(Medico medico : medicos)
        {
            //imprimimos el objeto pivote
            System.out.println(medico.toString());
        }
    }

    @FXML
    protected void mostrarEspecialidades() throws IOException {
        EspecialidadService especialidadService = new EspecialidadService();

        // Obtener una especialidad por su ID
        Especialidad especialidad = especialidadService.getEspecialidadById(1);

        if (especialidad != null) {
            System.out.println(especialidad.toString());

            // Cargar la lista de médicos
            for (Medico medico : especialidad.getMedicos()) {
                System.out.println(medico.toString());
            }
        }
    }
}
