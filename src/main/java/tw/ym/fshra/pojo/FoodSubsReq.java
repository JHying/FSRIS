package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
public class FoodSubsReq implements Serializable {

    @JsonProperty("Foodid")
//    @NotBlank(message = "Foodid 不得為空")
    private String foodid;

    @JsonProperty("BodyWeight")
    private Double bodyWeight;

}
