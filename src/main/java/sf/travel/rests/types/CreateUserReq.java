package sf.travel.rests.types;

import lombok.Data;

@Data
public class CreateUserReq {
    private String name;
    private String email;
    private String password;
}
