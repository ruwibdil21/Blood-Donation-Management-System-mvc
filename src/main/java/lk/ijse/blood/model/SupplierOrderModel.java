package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.SupplierOrdersDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderModel {

    public SupplierOrdersDto searchSupplierOrders(String supplierId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier_orders WHERE Supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierId);

        ResultSet resultSet = statement.executeQuery();
        SupplierOrdersDto dto = null;

        if (resultSet.next()){
            String order_id= resultSet.getString(1);
            String supplier_id= resultSet.getString(2);
            String date= resultSet.getString(3);
            String amount = resultSet.getString(4);

            dto = new SupplierOrdersDto(order_id,supplier_id,date,amount);

        }
        return dto;
    }

    public boolean deleteSupplierOrder(String supplierId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier_orders WHERE Supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, supplierId);
        return statement.executeUpdate() > 0;
    }

    public static boolean saveSupplierOrders(SupplierOrdersDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into supplier_orders values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getSupOrder_id());
        statement.setString(2, dto.getSupplier_id());
        statement.setString(3, dto.getDate());
        statement.setString(4, dto.getAmount());


        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public List<SupplierOrdersDto> loadAllSupplierOrders()throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier_orders";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SupplierOrdersDto> SupplierOrdersList= new ArrayList<>();

        while (resultSet.next()) {
            SupplierOrdersList.add(new SupplierOrdersDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }

        return SupplierOrdersList;
    }

}
