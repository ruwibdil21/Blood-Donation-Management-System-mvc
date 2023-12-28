package lk.ijse.blood.model;

import javafx.fxml.FXML;
import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.DonorDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DonationModel {

    @FXML
    public boolean saveDonation(DonationDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into donation values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getDo_id());
        statement.setString(2, dto.getD_id());
        statement.setDate(3, dto.getDate());
        statement.setString(4, dto.getBlood_type());
        statement.setString(5, dto.getHemoglobin_level());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean isDeleteDonation(String Do_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM donation WHERE Donation_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,Do_id);
        return statement.executeUpdate() > 0;
    }

    public DonationDto searchDonation(String DoId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM donation WHERE Donation_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,DoId);

        ResultSet resultSet = statement.executeQuery();
        DonationDto dto = null;

        if (resultSet.next()){
            String do_id = resultSet.getString(1);
            String d_id = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String Blood_type = resultSet.getString(4);
            String hemoglobinLevel = resultSet.getString(5);

            dto = new DonationDto(do_id,d_id,date,Blood_type,hemoglobinLevel);
        }
        return dto;
    }

    public static List<DonationDto> loadAllDonations() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM donation ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<DonationDto> donationList = new ArrayList<>();

        while (resultSet.next()) {
            donationList.add(new DonationDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }

        return donationList;
    }
}






