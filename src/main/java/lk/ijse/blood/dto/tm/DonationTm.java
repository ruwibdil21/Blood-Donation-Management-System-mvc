package lk.ijse.blood.dto.tm;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class DonationTm {
      private String Do_id;
      private String D_id;
      private LocalDate Date;
      private String Blood_type;
      private String Hemoglobin_level;

}
