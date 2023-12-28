package lk.ijse.blood.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeDto {
    private String Emp_id;
    private String user_id;
    private String Name;
    private String Address;
    private  String Role;
    private Date DOB;


}
