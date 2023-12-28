package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.BloodInventoryDto;
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.SalaryDto;
import lk.ijse.blood.dto.tm.BloodInventoryTm;
import lk.ijse.blood.dto.tm.DonationTm;
import lk.ijse.blood.model.BloodInventoryModel;
import lk.ijse.blood.model.DonationModel;
import lk.ijse.blood.model.SalaryModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class BloodInventoryFormController {

    public AnchorPane inventoryPane;
    public TextField txtBloodBag_id;
    public TextField txtDonation_id;
    public TextField txtBlood_Type;
    public DatePicker txtDonation_Date;
    public DatePicker txtEx_Date;
    public TableView tblBlood_Inventory;
    public TableColumn colBloodBag_id;
    public TableColumn colDonation_id;
    public TableColumn colEx_Date;
    public TableColumn colBlood_Type;
    @FXML
    private TableColumn<?, ?> colDonationDate;


    public void initialize() {
        loadAllBloodInvenorys();
        setCellValueFactory();
        try {
            autoGenarateId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colBloodBag_id.setCellValueFactory(new PropertyValueFactory<>("bloodBagId"));
        colDonation_id.setCellValueFactory(new PropertyValueFactory<>("donation_id"));
        colDonationDate.setCellValueFactory(new PropertyValueFactory<>("donation_date"));
        colEx_Date.setCellValueFactory(new PropertyValueFactory<>("ex_date"));
        colBlood_Type.setCellValueFactory(new PropertyValueFactory<>("blood_type"));
    }

    public void loadAllBloodInvenorys(){
        var model = new BloodInventoryModel();

        ObservableList<BloodInventoryTm> obList = FXCollections.observableArrayList();

        try{
            List<BloodInventoryDto> dtoList = model.loadAllBloodInventorys();

            for(BloodInventoryDto dto : dtoList){
                obList.add(new BloodInventoryTm(
                        dto.getBloodBagId(),
                        dto.getDonation_id(),
                        dto.getDonation_date(),
                        dto.getEx_date(),
                        dto.getBlood_type()

                ));
            }
            tblBlood_Inventory.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
   public void btnDeleteOnAction(ActionEvent event) {
        String bloodBag_id = txtBloodBag_id.getText();
        var model = new BloodInventoryModel();

        try{
            BloodInventoryDto dto = model.searchBloodInventory(bloodBag_id);
            if(dto != null) {
                boolean isDeleted = model.isDeleteBloodInventory(bloodBag_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Blood Inventory Delete Succesfull!!!").show();
                    clearFields();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Blood Inventory Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        String bloodBagId = txtBloodBag_id.getText();
        String donationId = txtDonation_id.getText();
        String donationDate = String.valueOf(txtDonation_Date.getValue());
        String exDate = String.valueOf(txtEx_Date.getValue());
        String bloodType = txtBlood_Type.getText();

        boolean isBloodInventoryValidated = validateBloodInventory();
        if (!isBloodInventoryValidated){return;}

        var dto = new BloodInventoryDto(bloodBagId,donationId,donationDate,exDate,bloodType);
        var model = new BloodInventoryModel();

        try {
            boolean isSaved = model.saveBloodInventory(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "BloodInventory Added Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        String bloodBagId = colBloodBag_id.getText();
        String donationId = txtDonation_id.getText();
        String donationDate = String.valueOf(txtDonation_Date.getValue());
        String exDate = String.valueOf(txtEx_Date.getValue());
        String bloodType = colBlood_Type.getText();


        boolean isBloodInventoryValidated = validateBloodInventory();
        if (!isBloodInventoryValidated){return;}

        var dto = new BloodInventoryDto(bloodBagId,donationId,donationDate,exDate,bloodType);
        var model = new BloodInventoryModel();

        try {
            boolean isUpdated = model.updateBloodInventory(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Blood Inventory Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtBloodBag_id.setText("");
        txtDonation_id.setText("");
        txtBlood_Type.setText("");

    }

    private boolean validateBloodInventory() {
        String bloodType = txtBlood_Type.getText();
        boolean isBloodTypeValidated = Pattern.compile("(A|B|AB|O)[+-]").matcher(bloodType).matches();
        if (!isBloodTypeValidated){
            txtBlood_Type.requestFocus();
        }

        return true;
        }

    private void autoGenarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT BloodBag_id FROM blood_inventory ORDER BY BloodBag_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        boolean isExists = resultSet.next();

        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("BB");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtBloodBag_id.setText("BB00" + id);
            } else if (id < 100) {
                txtBloodBag_id.setText("BB0" + id);
            } else {
                txtBloodBag_id.setText("BB" + id);
            }
        } else {
            txtBloodBag_id.setText("BB001");
        }
    }
}



