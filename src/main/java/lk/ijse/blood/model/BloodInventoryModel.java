package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.BloodInventoryDto;
import lk.ijse.blood.dto.DonationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodInventoryModel {

    public BloodInventoryDto searchBloodInventory(String bloodBagId)throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM blood_inventory WHERE  BloodBag_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,bloodBagId);

        ResultSet resultSet = statement.executeQuery();
        BloodInventoryDto dto = null;

        if (resultSet.next()){
            String bloodBag_id= resultSet.getString(1);
            String donation_id = resultSet.getString(2);
            String donation_date = resultSet.getString(3);
            String ex_date = resultSet.getString(4);
            String blood_type = resultSet.getString(5);

            dto = new BloodInventoryDto(bloodBag_id,donation_id,donation_date,ex_date,blood_type);
        }
        return dto;
    }


    public boolean isDeleteBloodInventory(String BloodBag_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM blood_inventory WHERE  BloodBag_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,BloodBag_id);
        return statement.executeUpdate() > 0;
    }


    public static boolean saveBloodInventory(BloodInventoryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into blood_inventory values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getBloodBagId());
        statement.setString(2, dto.getDonation_id());
        statement.setString(3, dto.getDonation_date());
        statement.setString(4, dto.getEx_date());
        statement.setString(5, dto.getBlood_type());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateBloodInventory(BloodInventoryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE blood_inventory SET Donation_id = ?, Donation_date= ?,Ex_date = ?,Blood_type = ? ,WHERE  BloodBag_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getDonation_id());
        statement.setString(2, dto.getDonation_date());
        statement.setString(3, dto.getEx_date());
        statement.setString(4, dto.getBlood_type());
        statement.setString(5, dto.getBloodBagId());


        return statement.executeUpdate() > 0;
    }


    public List<BloodInventoryDto> loadAllBloodInventorys() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM blood_inventory ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<BloodInventoryDto> bloodInventoryList = new ArrayList<>();

        while (resultSet.next()) {
            bloodInventoryList.add(new BloodInventoryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }

        return bloodInventoryList;
    }
}

