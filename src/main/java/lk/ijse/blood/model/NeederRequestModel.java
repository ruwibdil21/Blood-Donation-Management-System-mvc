package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.BloodInventoryDto;
import lk.ijse.blood.dto.NeederRequestDto;
import lk.ijse.blood.dto.RequestDetailsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NeederRequestModel {

    public static boolean placeNeederRequest(NeederRequestDto neederRequestDto, BloodInventoryDto bagdto,RequestDetailsDto requestDetailsDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            boolean isNeederRequestSaved = NeederRequestModel.saveNeederRequest(neederRequestDto);
            if (isNeederRequestSaved) {
                boolean isBloodInventorySaved = BloodInventoryModel.saveBloodInventory(bagdto);
                if (isBloodInventorySaved) {
                    boolean isrequestDetailsSaved = RequestDetailsModel.saveRequestDetails(requestDetailsDto);
                    if (isrequestDetailsSaved) {
                        connection.commit();
                        return true;
                    }
                }
                connection.rollback();
                return false;
            } connection.rollback();
            return false;
        }finally{
            connection.setAutoCommit(true);
        }
    }

    public boolean updateNeederRequest(NeederRequestDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE needer_Request SET Needer_id = ?, Date = ?,Amount = ?, WHERE Request_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getNeeReq_id());
        statement.setString(2, dto.getNeederId());
        statement.setString(3, dto.getDate());
        statement.setString(4, dto.getAmount());


        return statement.executeUpdate() > 0;
    }


    public static boolean saveNeederRequest(NeederRequestDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into needer_request values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getNeeReq_id());
        statement.setString(2, dto.getNeederId());
        statement.setString(3, dto.getDate());
        statement.setString(4, dto.getAmount());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }


    public NeederRequestDto searchNeeReq(String txtNeeReq) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM needer_Request WHERE NeeReq = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, txtNeeReq);

        ResultSet resultSet = statement.executeQuery();
        NeederRequestDto dto = null;

        if (resultSet.next()) {
            String neeReq = resultSet.getString(1);
            String neederId = resultSet.getString(2);
            String date = resultSet.getString(3);
            String amount = resultSet.getString(4);


            dto = new NeederRequestDto(neeReq, neederId, date, amount);
        }
        return dto;
    }



    public boolean deleteNeederRequest(String neeReq) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM needer_Request WHERE Request_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, neeReq);
        return statement.executeUpdate() > 0;
    }

    public List<NeederRequestDto> loadAllNeederRequests() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM needer_Request";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<NeederRequestDto> neederREquestList = new ArrayList<>();

        while (resultSet.next()) {
            neederREquestList.add(new NeederRequestDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }

        return neederREquestList;
    }
}



