package lk.ijse.blood.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.blood.model.AdminModel;
import java.io.IOException;
import java.sql.SQLException;


public class LoginFormController {
    public TextField txtPassword;
    public TextField txtId;
    public AnchorPane root;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        AdminModel adminModel = new AdminModel();

        boolean isValidAdmin = adminModel.loginAdmin(txtId.getText(),txtPassword.getText());

        if (isValidAdmin){
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login Form");
            stage.centerOnScreen();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid User id or Password Please try again!!!").show();
        }
    }

    @FXML
    void btnSignupOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/user_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Register Form");
        stage.centerOnScreen();
    }

}
