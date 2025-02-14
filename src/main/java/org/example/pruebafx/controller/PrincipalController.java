package org.example.pruebafx.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.pruebafx.model.Especialidad;
import org.example.pruebafx.service.EspecialidadService;
import org.example.pruebafx.service.MedicoService;
import javafx.fxml.FXML;
import org.example.pruebafx.model.Medico;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido1;
    @FXML
    private ComboBox cboEspecialidad;

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
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private TextField txtPagina;
    @FXML
    private Label lblTotal;
    private static final int OFFSET=20;

    private HashMap<String, String> filtros = new HashMap<>();
    private long totalMedicos;


    @FXML
    protected void mostrarMedicos() throws IOException {

        MedicoService medicoService = new MedicoService();
        List<Medico> medicos = medicoService.getPaginated(1, 50, null);
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

    public void rellenaTabla(List<Medico> medicos){
        try {
            tabla.setItems(FXCollections.observableArrayList(medicos));

            tabla_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tabla_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tabla_apellido1.setCellValueFactory(new PropertyValueFactory<Medico, String>("apellido1"));

            // Opción más sencilla para mostrar el valor de un atributo de tipo objeto
            //tabla_especialidad.setCellValueFactory(data-> new SimpleStringProperty(data.getValue()));

            // Similar a la anterior pero más detallada
            /*tabla_especialidad.setCellValueFactory(cellData -> {
                // Obtener el objeto Medico
                Medico medico = (Medico) cellData.getValue();

                // Si la especialidad no es nula, devolver su nombre, si no, devolver una cadena vacía
                return new SimpleStringProperty(
                        medico != null && medico.getEspecialidad() != null ? medico.getEspecialidad().getNombre() : ""
                );
            });
             */

            // Opción por si no va el getValue() del Object
            tabla_especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medico, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Medico, String> cellData) {
                    Medico medico = cellData.getValue(); // Obtener el objeto Medico
                    return new SimpleStringProperty(
                            (medico != null && medico.getEspecialidad() != null) ? medico.getEspecialidad().getNombre() : ""
                    );
                }
            });

        } catch (Exception e) {
            // TODO Mettre une popup erreur base de données
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Rellenar el combo de Especialidades
        EspecialidadService especialidadService = new EspecialidadService();
        List<Especialidad> especialidades = especialidadService.getAll();
        for (Especialidad especialidad: especialidades){
            cboEspecialidad.getItems().add(especialidad.getNombre());
        }

        // Rellena la tabla con la primera página sin filtros
        MedicoService medicoService = new MedicoService();
        List<Medico> medicos = medicoService.getPaginated(1, OFFSET, null);
        rellenaTabla(medicos);
        totalMedicos = medicoService.cout(null);
        lblTotal.setText("Total registros: "+totalMedicos+" - Total páginas: "+Math.round(Math.ceil((float)totalMedicos/(float)OFFSET)));
    }

    @FXML
    public void findBuscador(){
        filtros.clear();

        // Construcción de un HashMap con los filtros
        if (!txtNombre.getText().isEmpty())
            filtros.put("nombre", txtNombre.getText());
        if (!txtApellido1.getText().isEmpty())
            filtros.put("apellidos", txtApellido1.getText());
        if (cboEspecialidad.getValue() != null)
            filtros.put("especialidad", (String) cboEspecialidad.getValue());


        MedicoService medicoService = new MedicoService();
        totalMedicos = medicoService.cout(filtros);
        List<Medico> medicos = medicoService.getPaginated(1, OFFSET, filtros);
        rellenaTabla(medicos);

        txtPagina.setText("1");
        btnSiguiente.setDisable(false);
        lblTotal.setText("Total registros: "+totalMedicos+" - Total páginas: "+Math.round(Math.ceil((float)totalMedicos/(float)OFFSET)));

    }

    @FXML
    public void siguientePagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page++;

        MedicoService medicoService = new MedicoService();
        List<Medico> medicos = medicoService.getPaginated(page, OFFSET, filtros);
        if (!medicos.isEmpty()) {
            rellenaTabla(medicos);

            txtPagina.setText(page + "");
            btnAnterior.setDisable(false);
        } else {
            btnSiguiente.setDisable(true);
        }
    }

    @FXML
    public void anteriorPagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page--;

        if (page>0) {
            MedicoService medicoService = new MedicoService();
            List<Medico> medicos = medicoService.getPaginated(page, OFFSET, filtros);
            if (!medicos.isEmpty()) {
                rellenaTabla(medicos);
                txtPagina.setText(page + "");
                btnSiguiente.setDisable(false);
            } else {
                btnAnterior.setDisable(true);
            }
        } else {
            btnAnterior.setDisable(true);
        }
    }
}
