package lk.ijse.blood.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryTm {
    private String Salary_id;
    private String Employee_id;
    private Integer  Amount;
    private String Month;
    private String Year;
}
