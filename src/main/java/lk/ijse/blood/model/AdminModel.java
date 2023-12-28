package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    public static List<UserDto> loadAllUsers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<UserDto> userList = new ArrayList<>();

        while (resultSet.next()) {
            userList.add(new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return userList;
    }

    public boolean loginAdmin(String id, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE User_id =? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, id);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean saveUser(UserDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into user values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getUser_id());
        statement.setString(2, dto.getName());
        statement.setString(3, dto.getPassword());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }
}
