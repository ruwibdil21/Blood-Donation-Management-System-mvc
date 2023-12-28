package lk.ijse.blood.dto.tm;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeTm {

    private String Emp_id;
    private String user_id;
    private String Name;
    private String Address;
    private  String Role;
    private Date DOB;


}
