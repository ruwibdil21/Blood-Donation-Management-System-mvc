package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.RequestDetailsDto;
import lk.ijse.blood.dto.tm.DonationTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorModel {

    public boolean saveDonor(DonorDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into donor values (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getD_id());
        statement.setString(2, dto.getFirstName());
        statement.setString(3, dto.getLastName());
        statement.setString(4, dto.getDob());
        statement.setString(5, dto.getType());
        statement.setInt(6,dto.getTel());
        statement.setString(7, dto.getLastDate());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateDonor(DonorDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE donor SET F_name = ?, L_name = ?, DOB = ?, Tel = ?, Blood_type = ?, L_donate_date = ? WHERE D_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getFirstName());
        statement.setString(2, dto.getLastName());
        statement.setString(3, dto.getDob());
        statement.setString(5, dto.getType());
        statement.setInt(4,dto.getTel());
        statement.setString(6, dto.getLastDate());
        statement.setString(7, dto.getD_id());

        return statement.executeUpdate() > 0;
    }

    public DonorDto searchDonor(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM donor WHERE D_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);

        ResultSet resultSet = statement.executeQuery();
        DonorDto dto = null;

        if (resultSet.next()){
            String d_id = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String dob = resultSet.getString(4);
            String tel = resultSet.getString(5);
            int B_type = resultSet.getInt(6);
            String l_date = resultSet.getString(7);

            dto = new DonorDto(d_id,firstName,lastName,dob,tel,B_type,l_date);
        }
        return dto;
    }

    public boolean deleteDonor(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM donor WHERE D_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        return statement.executeUpdate() > 0;
    }

    public static List<DonorDto> loadAllDonors() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM donor";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<DonorDto> donorList = new ArrayList<>();

        while (resultSet.next()) {
            donorList.add(new DonorDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7)
            ));
        }

        return donorList;
    }
}

