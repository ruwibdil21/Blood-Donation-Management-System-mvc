package lk.ijse.blood.dto;

import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class DonorDto {
    private String d_id;
    private String firstName;
    private String lastName;
    private String dob;
    private String type;
    private int tel;
    private String lastDate;
}



