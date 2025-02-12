package org.example.pruebafx.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.pruebafx.model.Especialidad;
import org.example.pruebafx.service.EspecialidadService;
import org.example.pruebafx.service.MedicoService;
import javafx.fxml.FXML;
import org.example.pruebafx.model.Medico;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn tabla_id;
    @FXML
    private TableColumn tabla_nombre;
    @FXML
    private TableColumn tabla_apellido1;
    @FXML
    private TableColumn tabla_especialidad;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MedicoService medicoService = new MedicoService();

        try {
            List<Medico> medicos = medicoService.getPaginated();
            tabla.setItems(FXCollections.observableArrayList(medicos));

            tabla_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tabla_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tabla_apellido1.setCellValueFactory(new PropertyValueFactory<Medico, String>("apellido1"));
            /*tabla_especialidad.setCellValueFactory(cellData -> {
                // Obtener el objeto Medico
                Medico medico = (Medico) cellData.getValue();

                // Si la especialidad no es nula, devolver su nombre, si no, devolver una cadena vacía
                return new SimpleStringProperty(
                        medico != null && medico.getEspecialidad() != null ? medico.getEspecialidad().getNombre() : ""
                );
            });

             */
            tabla_especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medico, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Medico, String> cellData) {
                    Medico medico = cellData.getValue(); // Obtener el objeto Medico
                    return new SimpleStringProperty(
                            (medico != null && medico.getEspecialidad() != null) ? medico.getEspecialidad().getNombre() : ""
                    );
                }
            });



            //tabla_especialidad.setCellValueFactory(data-> new SimpleStringProperty(data.getValue()));

        } catch (Exception e) {
            // TODO Mettre une popup erreur base de données
            e.printStackTrace();
        }
    }
}
