package lk.ijse.blood.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class NeederDto {
    private String neederId;
    private String userId;
    private String name;
    private String address;
    private String contact;
    private String email;
}
