package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.DonorDto;
import lk.ijse.blood.dto.RequestDetailsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDetailsModel {

    public boolean deleteRequestDetails(String neeReqId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM request_details WHERE NeeReqId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, neeReqId);
        return statement.executeUpdate() > 0;
    }

    public static boolean saveRequestDetails(RequestDetailsDto dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into request_details values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getNeeReq_id());
        statement.setString(2, dto.getBloodBagId());
        statement.setString(3, dto.getDescription());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }


    public DonorDto searchRequestDetails(String neeReqId)throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM neeReq_id WHERE NeeReqId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, neeReqId);

        ResultSet resultSet = statement.executeQuery();
        DonorDto dto = null;

        if (resultSet.next()){
            String neeReq_id = resultSet.getString(1);
            String bloodBagId = resultSet.getString(2);
            String description = resultSet.getString(3);

            dto = new DonorDto();
        }
        return dto;
    }

    public List<RequestDetailsDto> loadAllRequestDetails() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM requestDetails";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<DonorDto> donorList = new ArrayList<>();

        List<RequestDetailsDto> requestDetailsList = null;
        while (resultSet.next()) {
            requestDetailsList.add(new RequestDetailsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }

        return requestDetailsList;
    }
}

