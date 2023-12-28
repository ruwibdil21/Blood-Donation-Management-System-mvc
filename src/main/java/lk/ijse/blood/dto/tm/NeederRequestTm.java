package lk.ijse.blood.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NeederRequestTm {
    private String neeReq_id;
    private String neederId;
    private String date;
    private String amount;

}
