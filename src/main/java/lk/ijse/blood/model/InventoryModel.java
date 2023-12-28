package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.AttendanceDto;
import lk.ijse.blood.dto.InventoryDto;
import lk.ijse.blood.dto.NeederDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {

    public static boolean addInventory(InventoryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO medical_inventory VALUES (?,?,?)";
        var statement = connection.prepareStatement(sql);

        statement.setString(1,dto.getMedical_id());
        statement.setString(2,dto.getBloodType());
        statement.setString(3, String.valueOf(dto.getDate()));


        return statement.executeUpdate() > 0;
    }

    public static boolean saveInventory(InventoryDto dto) throws  SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into medical_inventory values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getMedical_id());
        statement.setString(2, dto.getDate());
        statement.setString(3, dto.getBloodType());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateInventory(InventoryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE medical_inventory SET Blood_type = ?, Date = ? WHERE Med_id = ?";
        var statement = connection.prepareStatement(sql);

        statement.setString(3,dto.getMedical_id());
        statement.setString(1,dto.getBloodType());
        statement.setString(2, String.valueOf(dto.getDate()));


        return statement.executeUpdate() > 0;
    }

    public static List<InventoryDto> loadAllInventories()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medical_inventory ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<InventoryDto> inventoryList= new ArrayList<>();

        while (resultSet.next()) {
            inventoryList.add(new InventoryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }

        return inventoryList;
    }
}
