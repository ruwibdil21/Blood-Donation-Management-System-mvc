package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.EmployeeDto;
import lk.ijse.blood.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public boolean addSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into supplier values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getSup_id());
        statement.setString(2, dto.getUser_id());
        statement.setString(3, dto.getName());
        statement.setString(4, dto.getAddress());
        statement.setString(5, dto.getTel());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET User_id = ?, Name = ?, Address = ?, Mobile = ? WHERE Sup_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getUser_id());
        statement.setString(2, dto.getName());
        statement.setString(3, dto.getAddress());
        statement.setString(4, dto.getTel());
        statement.setString(5, dto.getSup_id());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteSupplier(String supId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE Sup_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, supId);

        return statement.executeUpdate() > 0;

    }

    public static List<SupplierDto> loadAllSuppliers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SupplierDto> supplierList= new ArrayList<>();

        while (resultSet.next()) {
            supplierList.add(new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)



            ));
        }

        return supplierList;
    }
}

