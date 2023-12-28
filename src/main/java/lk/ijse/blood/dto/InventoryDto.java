package lk.ijse.blood.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class InventoryDto {
    private String medical_id;
    private String date;
    private String bloodType;
}