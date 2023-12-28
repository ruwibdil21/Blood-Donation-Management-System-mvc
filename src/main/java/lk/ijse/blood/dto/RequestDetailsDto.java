package lk.ijse.blood.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDetailsDto {
    private String neeReq_id;
    private String bloodBagId;
    private String description;

}
