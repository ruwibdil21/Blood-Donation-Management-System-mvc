package lk.ijse.blood.dto.tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceTm {
    private String Att_id;
    private String Emp_id;
    private String Date;
    private String Status;
}
