package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.EmployeeDto;
import lk.ijse.blood.dto.NeederDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NeederModel {
    public boolean saveNeeder(NeederDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into needer values (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getNeederId());
        statement.setString(2, dto.getUserId());
        statement.setString(3, dto.getName());
        statement.setString(4, dto.getAddress());
        statement.setString(5, dto.getContact());
        statement.setString(6, dto.getEmail());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateNeeder(NeederDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE needer SET User_id = ?, Name = ?, Address = ?, Contact = ?, Email = ? WHERE Needer_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getUserId());
        statement.setString(2, dto.getName());
        statement.setString(3, dto.getAddress());
        statement.setString(4, dto.getContact());
        statement.setString(5, dto.getEmail());
        statement.setString(6, dto.getNeederId());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteNeeder(String neederId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM needer WHERE Needer_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, neederId);
        return statement.executeUpdate() > 0;
    }

    public static List<NeederDto> loadAllNeeders()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM needer ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<NeederDto> neederList= new ArrayList<>();

        while (resultSet.next()) {
            neederList.add(new NeederDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            ));
        }

        return neederList;
    }
}

