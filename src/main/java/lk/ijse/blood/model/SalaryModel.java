package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.EmployeeDto;
import lk.ijse.blood.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into Salary values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getSalary_id());
        statement.setString(2, dto.getEmployee_id());
        statement.setInt(3, dto.getAmount());
        statement.setString(4, dto.getMonth());
        statement.setString(5, dto.getYear());


        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }


    public boolean deleteSalary(String salary_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM salary WHERE Salary_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, salary_id);
        return statement.executeUpdate() > 0;
    }

    public List<SalaryDto> loadAllSalarys()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SalaryDto> salaryList= new ArrayList<>();

        while (resultSet.next()) {
            salaryList.add(new SalaryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5)


            ));
        }

        return salaryList;
    }

    public boolean updateSalary(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE salary SET Emp_id = ?, Amount = ?,Month = ?,Year= ?  WHERE Salary_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getEmployee_id());
        statement.setInt(2, dto.getAmount());
        statement.setString(3, dto.getMonth());
        statement.setString(4, dto.getYear());
        statement.setString(5, dto.getSalary_id());

        return statement.executeUpdate() > 0;
    }

    public SalaryDto searchSalary(String salaryId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary WHERE Salary_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, salaryId);

        ResultSet resultSet = statement.executeQuery();
        SalaryDto dto = null;

        if (resultSet.next()){
            String salary_id = resultSet.getString(1);
            String employee_id = resultSet.getString(2);
            Integer amount = Integer.valueOf(resultSet.getString(3));
            String month = resultSet.getString(4);
            String year = resultSet.getString(5);

            dto = new SalaryDto(salary_id,employee_id,amount,month,year);
        }
        return dto;
    }
}

