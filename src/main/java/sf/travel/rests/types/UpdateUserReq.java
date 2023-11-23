package sf.travel.rests.types;

import lombok.Data;

@Data
public class UpdateUserReq {
    private String name;
    private String email;
}
