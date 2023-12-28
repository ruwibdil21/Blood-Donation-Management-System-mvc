package lk.ijse.blood.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.RequestDetailsDto;
import lk.ijse.blood.dto.tm.RequestDetailsTm;
import lk.ijse.blood.model.RequestDetailsModel;

import java.sql.SQLException;
import java.util.List;

public class RequestDetailsFormController {
    @FXML
    private AnchorPane RequestDetails;

    @FXML
    private TableColumn<?, ?> colBloodBagId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colNeederId;

    @FXML
    private TableView<RequestDetailsTm> tblRequestDetails;

    @FXML
    private TextField txtBloodBagId;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtNeederId;


    public void initialize() {
        loadAllRequestDetails();
        setCellValueFactory();
    }
    private void setCellValueFactory() {
        colNeederId.setCellValueFactory(new PropertyValueFactory<>("neeReq_id"));
        colBloodBagId.setCellValueFactory(new PropertyValueFactory<>("bloodBagId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));

    }

    public void loadAllRequestDetails(){
        var model = new RequestDetailsModel();

        ObservableList<RequestDetailsTm> obList = FXCollections.observableArrayList();

        try{
            List<RequestDetailsDto> dtoList = model.loadAllRequestDetails();

            for(RequestDetailsDto dto : dtoList){
                obList.add(new RequestDetailsTm(
                        dto.getNeeReq_id(),
                        dto.getBloodBagId(),
                        dto.getDescription()

                ));
            }
            tblRequestDetails.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event){
            String Needer_id = txtNeederId.getText();
            var model = new RequestDetailsModel();

            try{
                var requestDetailsModel = new RequestDetailsModel();

                DonorDto dto =model.searchRequestDetails(Needer_id);
                if(dto != null) {
                    boolean isDeleted = model.deleteRequestDetails(Needer_id);
                    if (isDeleted) {
                        return;
                    }
                    new Alert(Alert.AlertType.CONFIRMATION, "RequestDetails Request Delete Succesfull!!!").show();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.ERROR, "RequestDetails Request Not Found!!!").show();
                    clearFields();
                }
            } catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }



    @FXML
    public void btnSaveOnAction(ActionEvent event)  {
        String needer_id = txtNeederId.getText();
        String bloodbag_id = txtBloodBagId.getText();
        String description = txtDescription.getText();


        if (needer_id.isEmpty() || bloodbag_id.isEmpty() || description.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields").show();
        }

        var dto = new RequestDetailsDto(needer_id,bloodbag_id,description);
        var model = new RequestDetailsModel();

        try {
            boolean isSaved = model.saveRequestDetails(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "RequestDetails Added Succesfull").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtNeederId.setText("");
        txtBloodBagId.setText("");
        txtDescription.setText("");
    }
}


