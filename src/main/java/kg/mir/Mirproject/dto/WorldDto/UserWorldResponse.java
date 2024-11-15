package kg.mir.Mirproject.dto.WorldDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWorldResponse {
    private Long id;
    private String photoUrl;
    private String userName;
    private String email;
    private String phoneNumber;
    private int totalSum;
}



