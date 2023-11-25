package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.TravelType;

@Data
public class UpdateTravelReq {
    private String name;
    private String description;
    private String detail;
    private TravelType type;
    private Integer price;
    private String image;
}
