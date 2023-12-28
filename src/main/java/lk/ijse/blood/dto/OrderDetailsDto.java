package lk.ijse.blood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data


public class OrderDetailsDto {
    private String Order_id;
    private String Med_id;
    private String Description;

}
