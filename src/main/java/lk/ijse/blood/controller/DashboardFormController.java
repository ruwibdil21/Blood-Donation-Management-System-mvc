package lk.ijse.blood.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class  DashboardFormController {
    @FXML
    private AnchorPane bodyPane;

    public AnchorPane dashboard;

    public void initialize() throws IOException {
        btnDashboardOnAction(new ActionEvent());
    }
    public void btnDonorOnAction(ActionEvent actionEvent) throws IOException {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/donor_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/user_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnDonationOnAction(ActionEvent event) throws IOException {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/donation_form.fxml")));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
       bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/employee_form.fxml")));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event){
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/inventory_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnNeederOnAction(ActionEvent event){
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/needer_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboardmain_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
