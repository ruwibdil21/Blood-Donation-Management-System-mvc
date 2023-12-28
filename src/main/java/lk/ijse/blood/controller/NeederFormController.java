package lk.ijse.blood.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.NeederDto;
import lk.ijse.blood.dto.UserDto;
import lk.ijse.blood.dto.tm.NeederTm;
import lk.ijse.blood.model.AdminModel;
import lk.ijse.blood.model.NeederModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class NeederFormController {

    public ComboBox cmbUserId;
    @FXML
    private AnchorPane neederPane;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNeederId;

    @FXML
    private TableColumn<?, ?> colUserId;


    @FXML
    private TableView<NeederTm> tblNeeder;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNeederId;


    public void initialize() throws SQLException {
        loadAllNeeders();
        setCellValueFactory();
        loadAllUsers();
        autoGenarateId();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) neederPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    private void setCellValueFactory() {
        colNeederId.setCellValueFactory(new PropertyValueFactory<>("Needer_id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("User_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    public void loadAllNeeders() throws SQLException {
        var model = new NeederModel();

        ObservableList<NeederTm> obList = FXCollections.observableArrayList();

        List<NeederDto> dtoList = model.loadAllNeeders();

        for (NeederDto dto : dtoList) {
            obList.add(new NeederTm(
                    dto.getNeederId(),
                    dto.getUserId(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getContact(),
                    dto.getEmail())
            );
        }
        tblNeeder.setItems(obList);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String neederId = txtNeederId.getText();
        var model = new NeederModel();

        try {
            boolean isDeleted = model.deleteNeeder(neederId);
            if (isDeleted) {
                System.out.println("Needer Deleted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnNeederRequestOnAction(ActionEvent actionEvent) {
        try {
            URL resource =NeederRequestController.class.getResource("/view/needer_request.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Needer Request Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRequestDetailOnAction(ActionEvent actionEvent) {
        try {
            URL resource =NeederRequestController.class.getResource("/view/request_details.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("RequestDetails Form");
            stage.setAlwaysOnTop(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String neederId = txtNeederId.getText();
        String userId = String.valueOf(cmbUserId.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        boolean isNeederValidated = validateNeeder();
        if (!isNeederValidated){return;}


        var dto = new NeederDto(neederId,userId,name, address, contact, email);
        var model = new NeederModel();

        try {
            boolean isSaved = model.saveNeeder(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Needer Saved Succesfull").show();
                initialize();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String neederId = txtNeederId.getText();
        String userId = String.valueOf(cmbUserId.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        boolean isNeederValidated = validateNeeder();
        if (!isNeederValidated){return;}

        var dto = new NeederDto(neederId,userId,name, address, contact, email);
        var model = new NeederModel();

        try {
            boolean isUpdated = model.updateNeeder(dto);
            if (isUpdated) {
                System.out.println("Needer Updated");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateNeeder() {
        String name = txtName.getText();
        boolean isNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(name).matches();
        if (!isNameValidated) {
            txtName.requestFocus();
        }

        String Address = txtAddress.getText();
        boolean isAddressValidated = Pattern.compile("^[A-z]{1,20}$").matcher(Address).matches();
        if (!isAddressValidated) {
            txtAddress.requestFocus();
        }

        String contact = txtContact.getText();
        boolean isContactValidated = Pattern.compile("^[0-9]{10}$").matcher(contact).matches();
        if (!isContactValidated) {
            txtContact.requestFocus();
        }

        String email = txtEmail.getText();
        boolean isEmailValidated = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$").matcher(email).matches();
        if (!isEmailValidated) {
            txtEmail.requestFocus();
        }
        return true;
    }

    private void loadAllUsers(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<UserDto> userList = AdminModel.loadAllUsers();

            for (UserDto userDto  : userList) {
                obList.add(userDto.getUser_id());
            }
            cmbUserId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void autoGenarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT needer_id FROM needer ORDER BY needer_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        boolean isIdExits = resultSet.next();
        if (isIdExits) {
            String oldId = resultSet.getString(1);
            String substring = oldId.substring(1, 4);
            int id = Integer.parseInt(substring);
            id++;
            if (id < 10) {
                txtNeederId.setText("N00" + id);
            } else if (id < 100) {
                txtNeederId.setText("N0" + id);
            } else {
                txtNeederId.setText("N" + id);
            }
        } else {
            txtNeederId.setText("N001");
        }
    }

    @FXML
    public void printNeederFormOnAction(ActionEvent event)throws JRException,SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Neederform.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        compileReport,
                        null,
                        DbConnection.getInstance().getConnection()
                );
        JasperViewer.viewReport(jasperPrint, false);
    }


}
