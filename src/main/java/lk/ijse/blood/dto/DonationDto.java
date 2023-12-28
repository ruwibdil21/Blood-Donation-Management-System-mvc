package lk.ijse.blood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DonationDto {
    private String Do_id;
    private String D_id;
    private Date date;
    private String Blood_type;
    private String Hemoglobin_level;
}
