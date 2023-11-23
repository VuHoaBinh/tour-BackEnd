package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.TravelDomainType;
import sf.travel.enums.TravelType;

import javax.validation.constraints.NotNull;

@Data
public class CreateTravelReq {
    @NotNull
    private String name;
    private String description;
    private String detail;
    private Integer price;
    @NotNull
    private TravelType type;
    private TravelDomainType domain;
}
