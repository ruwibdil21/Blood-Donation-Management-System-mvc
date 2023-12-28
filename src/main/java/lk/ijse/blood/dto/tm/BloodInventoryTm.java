package lk.ijse.blood.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BloodInventoryTm {
    private String bloodBagId;
    private String donation_id;
    private  String donation_date;
    private String ex_date;
    private String blood_type;

}
