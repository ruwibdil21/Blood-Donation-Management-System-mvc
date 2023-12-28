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
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.tm.DonorTm;
import lk.ijse.blood.model.DonorModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class DonorFormController {

    @FXML
    private DatePicker txtL_date;

    @FXML
    private DatePicker txtDob;

    @FXML
    private TableView<DonorTm> tblDonor;
    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colDonorid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private AnchorPane donor;


    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtType;

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllDonors();
        autoGenarateId();

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) donor.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    private void setCellValueFactory() {
        colDonorid.setCellValueFactory(new PropertyValueFactory<>("D_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("F_name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colType.setCellValueFactory(new PropertyValueFactory<>("blood_type"));
    }

    private void loadAllDonors() {
        var model = new DonorModel();

        ObservableList<DonorTm> obList = FXCollections.observableArrayList();

        try {
            List<DonorDto> dtoList = model.loadAllDonors();

            for (DonorDto dto : dtoList) {
                obList.add(new DonorTm(
                        dto.getD_id(),
                        dto.getFirstName(),
                        dto.getDob(),
                        dto.getType()
                ));
            }
            tblDonor.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String d_id = txtId.getText();
        var model = new DonorModel();

        try {
            DonorDto dto = model.searchDonor(d_id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Donor Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(DonorDto dto) {
        txtId.setText(dto.getD_id());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtTel.setText(String.valueOf(dto.getTel()));
        txtType.setText(dto.getType());
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String d_id = txtId.getText();
        var model = new DonorModel();

        try {
            var donorModel = new DonorModel();
            DonorDto dto = model.searchDonor(d_id);
            if (dto != null) {
                boolean isDeleted = model.deleteDonor(d_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Donor Delete Succesfull!!!").show();
                    clearFields();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Donor Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String d_id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        int tel = Integer.parseInt(txtTel.getText());
        String dob = String.valueOf(txtDob.getValue());
        String type = txtType.getText();
        String l_date = String.valueOf(txtL_date.getValue());

        boolean isDonorValidated = validateDonor();
        if (!isDonorValidated){return;}

        var dto = new DonorDto(d_id, firstName, lastName, dob, type,tel, l_date);
        var model = new DonorModel();

        try {
            boolean isUpdated = model.updateDonor(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Donor Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String d_id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dob = String.valueOf(txtDob.getValue());
        int tel = Integer.parseInt(txtTel.getText());
        String type = txtType.getText();
        String l_date = String.valueOf(txtL_date.getValue());

        boolean isDonorValidated = validateDonor();
        if (!isDonorValidated){return;}

        var dto = new DonorDto(d_id, firstName, lastName, dob, type,tel, l_date);
        var model = new DonorModel();

        try {
            boolean isSaved = model.saveDonor(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Donor Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtDob.setValue(null);
        txtTel.setText("");
        txtType.setText("");
        txtL_date.setValue(null);
    }

    private boolean validateDonor() {
        String FirstName =txtFirstName.getText();
        boolean isFirstNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(FirstName).matches();
        if (!isFirstNameValidated){
            txtFirstName.requestFocus();
        }

        String LastName = txtLastName.getText();
        boolean isLastNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(LastName).matches();
        if (!isLastNameValidated){
            txtLastName.requestFocus();
        }


        String tel = txtTel.getText();
        boolean isTelValidated = Pattern.compile("^[0-9]{10}$").matcher(tel).matches();
        if (!isTelValidated) {
            txtTel.requestFocus();
        }

        String bloodType = txtType.getText();
        boolean isbloodTypeValidated = Pattern.compile("^[A-z]{1,20}$").matcher(bloodType).matches();
        if (!isbloodTypeValidated){
            txtType.requestFocus();
        }
        return true;
    }

    private void autoGenarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT D_id FROM donor ORDER BY D_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        boolean isExists = resultSet.next();
        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("D");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtId.setText("D00" + id);
            } else if (id < 100) {
                txtId.setText("D0" + id);
            } else {
                txtId.setText("D" + id);
            }
        } else {
            txtId.setText("D001");
        }
    }
}
