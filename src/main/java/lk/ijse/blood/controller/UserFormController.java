package lk.ijse.blood.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.UserDto;
import lk.ijse.blood.model.AdminModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFormController {
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;
    @FXML
    private AnchorPane user;

    public void initialize() throws SQLException {
        autoGenarateId();
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        if (id.isEmpty() || name.isEmpty() || password.isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields").show();
        }

        var dto = new UserDto(id,name,password);
        var model = new AdminModel();

        try {
            boolean isSaved = model.saveUser(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Register Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtUserName.setText("");
        txtUserId.setText("");
        txtPassword.setText("");
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) user.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Register Form");
        stage.centerOnScreen();
    }

    private void autoGenarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        boolean isIdExits = resultSet.next();
        if (isIdExits) {
            String oldId = resultSet.getString(1);
            String substring = oldId.substring(1, 4);
            int id = Integer.parseInt(substring);
            id++;
            if (id < 10) {
                txtUserId.setText("U00" + id);
            } else if (id < 100) {
                txtUserId.setText("U0" + id);
            } else {
                txtUserId.setText("U" + id);
            }
        } else {
            txtUserId.setText("U001");
        }
    }
}

