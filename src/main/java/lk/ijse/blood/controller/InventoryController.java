package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.dto.InventoryDto;
import lk.ijse.blood.dto.tm.InventoryTm;
import lk.ijse.blood.model.InventoryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class InventoryController {
    @FXML
    private TextField txtBloodType;

    @FXML
    private DatePicker txtDate;
    @FXML
    private TableColumn<?, ?> colBloodType;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colMedicalId;

    @FXML
    private AnchorPane invnetory;

    @FXML
    private TableView<InventoryTm> tblInventory;

    @FXML
    private TextField txtMedicalId;

    public void initialize() throws SQLException {
        loadAllInventories();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colMedicalId.setCellValueFactory(new PropertyValueFactory<>("medical_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
    }

    public void loadAllInventories()throws SQLException {
        var model = new InventoryModel();

        ObservableList<InventoryTm> obList = FXCollections.observableArrayList();

        try {
            List<InventoryDto> dtoList = model.loadAllInventories();

            for (InventoryDto dto : dtoList) {
                obList.add(new InventoryTm(
                        dto.getMedical_id(),
                        dto.getDate(),
                        dto.getBloodType()
                ));
            }
            tblInventory.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) invnetory.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String med_id = txtMedicalId.getText();
        String bloodType = txtBloodType.getText();
        String date = String.valueOf(txtDate.getValue());

        if (med_id.isEmpty() || bloodType.isEmpty() || date.isEmpty()) {
            System.out.println("Please fill all the fields");
        }

        var dto = new InventoryDto(med_id, bloodType,date);
        var model = new InventoryModel();

        try {
            boolean isSaved = model.addInventory(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Added Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtMedicalId.setText("");
        txtBloodType.setText("");
        txtDate.setValue(LocalDate.parse(""));
    }


    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        String medical_id = txtMedicalId.getText();
        String blood_type = txtBloodType.getText();
        String date = String.valueOf(txtDate.getValue());


        if (medical_id.isEmpty() || blood_type.isEmpty() || date.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields").show();
        }

        var dto = new InventoryDto(medical_id, blood_type,date);
        var model = new InventoryModel();

        try {
            boolean isUpdated = model.updateInventory(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


       /* private boolean validateMedicalInventory();
        String medicalId = txtMedicalId.getText();
        boolean isMedicalIdValidated = Pattern.compile("^(M)[0-9]{1,3}$").matcher(medicalId).matches();
        if (!isMedicalIdValidated) {
            txtMedicalId.requestFocus();
        }

        String Date = txtDate.getText();
        boolean isDateValidated = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n").matcher(Date).matches();
        if (!isDateValidated) {
            txtDate.requestFocus();
        }

        String bloodType = txtBloodType.getText();
        boolean isBloodTypeValidated = Pattern.compile("(A|B|AB|O)[+-]").matcher(bloodType).matches();
        if (!isBloodTypeValidated) {
            txtBloodType.requestFocus();
        }*/

    }

