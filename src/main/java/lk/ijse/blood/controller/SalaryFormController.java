package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.SalaryDto;
import lk.ijse.blood.dto.tm.DonorTm;
import lk.ijse.blood.dto.tm.SalaryTm;
import lk.ijse.blood.model.DonorModel;
import lk.ijse.blood.model.SalaryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Integer.*;

public class SalaryFormController {
    @FXML
    private AnchorPane Salary;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private TableColumn<?, ?> colYear;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtSalaryId;

    @FXML
    private TextField txtYear;

    public void initialize() {
        setCellValueFactory();
        loadAllSalarys();

    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("Salary_id"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("Month"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("Year"));

    }

    public void loadAllSalarys() {
        var model = new SalaryModel();

        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<SalaryDto> dtoList = model.loadAllSalarys();

            for (SalaryDto dto : dtoList) {
                obList.add(new SalaryTm(
                        dto.getSalary_id(),
                        dto.getEmployee_id(),
                        dto.getAmount(),
                        dto.getMonth(),
                        dto.getYear()

                ));
            }
            tblSalary.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String salaryId = txtSalaryId.getText();
        var model = new SalaryModel();

        try {
            var salaryModel = new SalaryModel();
            SalaryDto dto = model.searchSalary(salaryId);
            if (dto != null) {
                boolean isDeleted = model.deleteSalary(salaryId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Salary Delete Succesfull!!!").show();
                    clearFields();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Salary Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(SalaryDto dto) {
        txtSalaryId.setText(dto.getSalary_id());
        txtEmpId.setText(dto.getEmployee_id());
        txtAmount.setText(String.valueOf(dto.getAmount()));
        txtMonth.setText(dto.getMonth());
        txtYear.setText(dto.getYear());

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String salaryId = txtSalaryId.getText();
        String emp_id = txtEmpId.getText();
        int amount = Integer.parseInt(txtAmount.getText());
        String month = txtMonth.getText();
        String year = txtYear.getText();


        boolean isSalaryValidated = validateSalary();
        if (!isSalaryValidated){return;}


        var dto = new SalaryDto(salaryId, emp_id, amount, month, year);
        var model = new SalaryModel();

        try {
            boolean isSaved = model.saveSalary(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary Added Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String salaryId = txtSalaryId.getText();
        String empId = txtEmpId.getText();
        Integer amount = valueOf(txtAmount.getText());
        String month = txtMonth.getText();
        String year = txtYear.getText();


        boolean isSalaryValidated = validateSalary();
        if (!isSalaryValidated){return;}

        var dto = new SalaryDto(salaryId, empId, amount, month, year);
        var model = new SalaryModel();

        try {
            boolean isUpdated = model.updateSalary(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtSalaryId.setText("");
        txtEmpId.setText("");
        txtAmount.setText("");
        txtMonth.setText("");
        txtYear.setText("");

    }

    private boolean validateSalary() {
        String salaryId = txtSalaryId.getText();
        boolean isSalaryIdValidated = Pattern.compile("^(S)[0-9]{1,3}$").matcher(salaryId).matches();
        if (!isSalaryIdValidated) {
            txtSalaryId.requestFocus();
        }


        String employeeId = txtEmpId.getText();
        boolean isEmployeeIdValidated = Pattern.compile("^(E)[0-9]{1,3}$").matcher(employeeId).matches();
        if (!isSalaryIdValidated) {
            txtEmpId.requestFocus();
        }

       /* String amount = txtAmount.getText();
        boolean isAmountValidated = Pattern.compile("^[1-9]\\d{0,2}(,\\d{3})*(\\.\\d{1,2})?|0(\\.\\d{1,2})?$\n").matcher(amount).matches();
        if (!isAmountValidated) {
            txtAmount.requestFocus();
        }

       /* String month = txtMonth.getText();
        boolean isMonthValidated = Pattern.compile("\\d{2}").matcher(month).matches();
        if (!isMonthValidated) {
            txtMonth.requestFocus();
        }

        String year = txtYear.getText();
        boolean isYearValidated = Pattern.compile("\\d{4}").matcher(year).matches();
        if (!isYearValidated) {
            txtYear.requestFocus();
        }*/

        return true;

    }
}
