package lk.ijse.blood.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDetailsTm {
    private String neeReq_id;
    private String bloodBagId;
    private String description;
}
