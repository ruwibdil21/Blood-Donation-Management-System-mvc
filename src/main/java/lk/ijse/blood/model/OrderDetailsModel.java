package lk.ijse.blood.model;

import lk.ijse.blood.db.DbConnection;
import lk.ijse.blood.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsModel {
    public static boolean placeOrderDetails(SupplierOrdersDto supplierOrdersDto, InventoryDto inventoryDto, OrderDetailsDto orderDetailsDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            boolean isSupplierOrdersSaved = SupplierOrderModel.saveSupplierOrders(supplierOrdersDto);
            if (isSupplierOrdersSaved) {
                boolean isInventorySaved = InventoryModel.saveInventory(inventoryDto);
                if (isInventorySaved) {
                    boolean isOrderDetailsSaved = OrderDetailsModel.saveOrderDetails(orderDetailsDto);
                    if (isOrderDetailsSaved) {
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

    public static boolean saveOrderDetails(OrderDetailsDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into order_details values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getOrder_id());
        statement.setString(2, dto.getMed_id());
        statement.setString(3, dto.getDescription());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

   /* public static List<OrderDetailsDto> loadAllOrderDetails() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM orderDetails";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<OrderDetailsDto> orderDetailsList = new ArrayList<>();

        while (resultSet.next()) {
            orderDetailsList.add(new OrderDetailsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }

        return orderDetailsList;
    }*/

}

