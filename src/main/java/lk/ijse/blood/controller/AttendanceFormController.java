package lk.ijse.blood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.blood.dto.AttendanceDto;
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.SalaryDto;
import lk.ijse.blood.dto.tm.AttendanceTm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.dto.tm.SalaryTm;
import lk.ijse.blood.model.AttendanceModel;
import lk.ijse.blood.model.DonationModel;
import lk.ijse.blood.model.SalaryModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class AttendanceFormController {
    public DatePicker DtpDate;
    @FXML
    private AnchorPane Attendance;

    @FXML
    private TableColumn<?, ?> colAttId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<AttendanceTm> tblAttendance;

    @FXML
    private TextField txtAttId;


    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtStatus;

    public void initialize() {
        loadAllAttendans();
        setCellValueFactory();
    }
    private void setCellValueFactory() {
        colAttId.setCellValueFactory(new PropertyValueFactory<>("Att_id"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

    }

    public void loadAllAttendans(){
        var model = new AttendanceModel();

        ObservableList<AttendanceTm> obList = FXCollections.observableArrayList();

        try{
            List<AttendanceDto> dtoList = model.loadAllAttendans();

            for(AttendanceDto dto : dtoList){
                obList.add(new AttendanceTm(
                        dto.getAtt_id(),
                        dto.getEmp_id(),
                        dto.getDate(),
                        dto.getStatus()


                ));
            }
            tblAttendance.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent actionEvent) {
        String att_id = txtAttId.getText();
        var model = new AttendanceModel();

        try {
            AttendanceDto dto = model.searchAttendance(att_id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Attendance Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String attId= txtAttId.getText();
        var model = new AttendanceModel();

        try{
            AttendanceDto dto = model.searchAttendance(attId);
            if(dto != null) {
                boolean isDeleted = model.deleteAttendance(attId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Attendance Delete Succesfull!!!").show();
                    clearFields();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Attendance Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(AttendanceDto dto) {
        txtAttId.setText (dto.getAtt_id());
        txtEmpId.setText(dto.getEmp_id());
        DtpDate.setValue(LocalDate.parse(dto.getDate()));
        txtStatus.setText(dto.getStatus());
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String att_Id = txtAttId.getText();
        String emp_id = txtEmpId.getText();
        String date = String.valueOf(DtpDate.getValue());
        String status =txtStatus.getText();


        boolean isAttendanceValidated = validateAttendance();
        if (!isAttendanceValidated){return;}

        var dto = new AttendanceDto(att_Id,emp_id,date,status);
        var model = new AttendanceModel();

        try {
            boolean isSaved = model.saveAttendance(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Added Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String att_Id = txtAttId.getText();
        String empId = txtEmpId.getText();
        String date = String.valueOf(DtpDate.getValue());
        String status = txtStatus.getText();


        boolean isAttendanceValidated = validateAttendance();
        if (!isAttendanceValidated){return;}

        var dto = new AttendanceDto(att_Id,empId,date,status);
        var model = new AttendanceModel();

        try {
            boolean isUpdated = model.updateAttendance(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Update Succesfull!!!").show();
                fillFields(dto);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtAttId.setText("");
        txtEmpId.setText("");
        DtpDate.setValue(LocalDate.parse(""));
        txtStatus.setText("");
    }
    private boolean validateAttendance() {
        String attId = txtAttId.getText();
        boolean isAttIdValidated = Pattern.compile("^(Att)[0-9]{1,3}$").matcher(attId).matches();
        if (!isAttIdValidated) {
            txtAttId.requestFocus();
        }

        String empId = txtEmpId.getText();
        boolean isEmpIdValidated = Pattern.compile("^(E)[0-9]{1,3}$").matcher(empId).matches();
        if (!isEmpIdValidated) {
            txtEmpId.requestFocus();
        }

        String status = txtStatus.getText();
        boolean isStatusValidated = Pattern.compile("^[A-z]]").matcher(status).matches();
        if (!isStatusValidated) {
            txtStatus.requestFocus();
        }
        return true;
    }

}


