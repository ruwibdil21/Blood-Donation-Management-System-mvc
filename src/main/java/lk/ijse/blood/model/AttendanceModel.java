package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.AttendanceDto;
import lk.ijse.blood.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {

    public List<AttendanceDto> loadAllAttendans()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AttendanceDto> attendanceList= new ArrayList<>();

        while (resultSet.next()) {
            attendanceList.add(new AttendanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)



            ));
        }

        return attendanceList;
    }



    public boolean deleteAttendance(String attId)throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM attendance WHERE Att_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, attId);
        return statement.executeUpdate() > 0;
    }




    public AttendanceDto searchAttendance(String attId)throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance WHERE Att_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, attId);

        ResultSet resultSet = statement.executeQuery();
        AttendanceDto dto = null;

        if (resultSet.next()){
            String att_id = resultSet.getString(1);
            String employee_id = resultSet.getString(2);
            String date = resultSet.getString(3);
            String status = resultSet.getString(4);

            dto = new AttendanceDto(att_id,employee_id,date,status);
        }
        return dto;
    }


    public boolean saveAttendance(AttendanceDto dto) throws  SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into attendance values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getAtt_id());
        statement.setString(2, dto.getEmp_id());
        statement.setString(3, dto.getDate());
        statement.setString(4, dto.getStatus());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }


    public boolean updateAttendance(AttendanceDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE attendance SET Emp_id = ?, Date= ?, Status = ? WHERE Att_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getEmp_id());
        statement.setString(2, dto.getDate());
        statement.setString(3, dto.getStatus());
        statement.setString(4, dto.getAtt_id());

        return statement.executeUpdate() > 0;
    }
}

