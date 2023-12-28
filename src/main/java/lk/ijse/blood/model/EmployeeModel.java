package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonationDto;
import lk.ijse.blood.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into employee values (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getEmp_id());
        statement.setString(2, dto.getUser_id());
        statement.setString(3, dto.getName());
        statement.setString(4, dto.getAddress());
        statement.setString(5, dto.getRole());
        statement.setDate(6, dto.getDOB());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }


    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE Emp_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        return statement.executeUpdate() > 0;
    }

    public List<EmployeeDto> loadAllEmployees()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> employeeList= new ArrayList<>();

        while (resultSet.next()) {
            employeeList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6)

            ));
        }

        return employeeList;
    }
}
