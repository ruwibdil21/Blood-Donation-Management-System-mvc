package lk.ijse.blood.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryDto {
    private String Salary_id;
    private String Employee_id;
    private Integer Amount;
    private String Month;
    private String Year;

}
