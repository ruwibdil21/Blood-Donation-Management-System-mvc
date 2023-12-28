package lk.ijse.blood.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NeederRequestDto {
    private String neeReq_id;
    private String neederId;
    private String date;
    private String amount;
}
