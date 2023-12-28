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
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.tm.DonationTm;
import lk.ijse.blood.dto.tm.DonorTm;
import lk.ijse.blood.model.DonationModel;
import lk.ijse.blood.model.DonorModel;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class DonationFormController {
    @FXML
    private TableView<DonationTm> tblDonation;

    @FXML
    private TableColumn<?, ?> colBloodType;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDonationId;

    @FXML
    private TableColumn<?, ?> colDonorId;

    @FXML
    private TableColumn<?, ?> colHemoglobinLevel;

    @FXML
    private TextField txtDoId;
    @FXML
    private TextField txtHemoglobinLevel;

    @FXML
    private TextField txtBloodType;

    @FXML
    private DatePicker txtDate;

    @FXML
    private AnchorPane donation;
    @FXML
    private ComboBox cmbDonorid;

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) donation.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("donation Form");
        stage.centerOnScreen();
    }

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllDonations();
        loadAllDonors();
        autoGenerateId();
        txtDate.setValue(LocalDate.now());
    }

    private void setCellValueFactory() {
        colDonationId.setCellValueFactory(new PropertyValueFactory<>("Do_id"));
        colDonorId.setCellValueFactory(new PropertyValueFactory<>("D_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("Blood_type"));
        colHemoglobinLevel.setCellValueFactory(new PropertyValueFactory<>("Hemoglobin_level"));
    }

    public void loadAllDonations() {
        var model = new DonationModel();

        ObservableList<DonationTm> obList = FXCollections.observableArrayList();

        try {
            List<DonationDto> dtoList = model.loadAllDonations();

            for (DonationDto dto : dtoList) {
                obList.add(new DonationTm(
                        dto.getDo_id(),
                        dto.getD_id(),
                        dto.getDate().toLocalDate(),
                        dto.getBlood_type(),
                        dto.getHemoglobin_level()

                        ));
            }
            tblDonation.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String do_id = txtDoId.getText();
        var model = new DonationModel();

        try {
            DonationDto dto = model.searchDonation(do_id);
            if (dto != null) {
                boolean isDeleted = model.isDeleteDonation(do_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Donation Delete Succesfull!!!").show();
                    clearFields();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Donation Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(DonationDto dto) {
        txtDoId.setText(dto.getDo_id());
        txtDate.setValue(dto.getDate().toLocalDate());
        txtBloodType.setText(dto.getBlood_type());
        txtHemoglobinLevel.setText(dto.getHemoglobin_level());
    }

    @FXML
    void btnSaveOnAction(ActionEvent actionEvent) {
        String do_id = txtDoId.getText();
        String d_id = String.valueOf(cmbDonorid.getValue());
        Date date = Date.valueOf(txtDate.getValue());
        String blood_type = txtBloodType.getText();
        String hemoglobin_level = txtHemoglobinLevel.getText();

        boolean isDonationValidated = validateDonation();
        if (!isDonationValidated){return;}

        var dto = new DonationDto(do_id, d_id, date, blood_type, hemoglobin_level);
        var model = new DonationModel();

        try {
            boolean isSaved = model.saveDonation(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Donation Added Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {

        String do_id = txtDoId.getText();
        var model = new DonationModel();

        try {
            DonationDto dto = model.searchDonation(do_id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Donation Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void clearFields() {
        txtDoId.setText("");
        txtBloodType.setText("");
        txtHemoglobinLevel.setText("");
    }


    private boolean validateDonation() {
       /* String hemoglobinLevel = txtHemoglobinLevel.getText();
        boolean isHemoglobinLevelValidated = Pattern.compile("\"^[0-9] HB[^(P|S)](|[^G])\"").matcher(hemoglobinLevel).matches();
        if (!isHemoglobinLevelValidated){
            txtHemoglobinLevel.requestFocus();
        }*/

        String bloodType = txtBloodType.getText();
        boolean isBloodTypeValidated = Pattern.compile("(A|B|AB|O)[+-]").matcher(bloodType).matches();
        if (!isBloodTypeValidated){
            txtBloodType.requestFocus();
        }


        return true;
    }

    private void loadAllDonors() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DonorDto> donList = DonorModel.loadAllDonors();

            for (DonorDto donorDto : donList) {
                obList.add(donorDto.getD_id());
            }
            cmbDonorid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void autoGenerateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT donation_id FROM donation ORDER BY donation_id DESC LIMIT 1");
        if (rst.next()) {
            String tempId = rst.getString(1);
            String[] arr = tempId.split("DT");
            int id = Integer.parseInt(arr[1]);
            id++;
            if (id < 10) {
                txtDoId.setText("DT00" + id);
            } else if (id < 100) {
                txtDoId.setText("DT0" + id);
            } else {
                txtDoId.setText("DT" + id);
            }
        } else {
            txtDoId.setText("DT001");
        }
    }
}

