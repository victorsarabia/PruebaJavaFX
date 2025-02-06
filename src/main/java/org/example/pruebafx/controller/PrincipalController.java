package org.example.pruebafx.controller;

import javafx.scene.control.TextField;
import org.example.pruebafx.service.MedicoService;
import javafx.fxml.FXML;
import org.example.pruebafx.model.Medicos;

import java.io.IOException;
import java.util.List;

public class PrincipalController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    protected void mostrarDatos() throws IOException {
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
        List<Medicos> medicos = medicoService.getAll();
        for(Medicos medico : medicos)
        {
            //imprimimos el objeto pivote
            System.out.println(medico.toString());
        }
    }
}
