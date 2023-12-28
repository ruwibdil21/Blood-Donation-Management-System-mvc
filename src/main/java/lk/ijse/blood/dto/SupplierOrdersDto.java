package lk.ijse.blood.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupplierOrdersDto {
    private String supOrder_id;
    private String supplier_id;
    private String date;
    private String amount;
}
