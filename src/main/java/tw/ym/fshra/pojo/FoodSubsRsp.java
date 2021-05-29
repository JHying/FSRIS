package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodSubsRsp implements Serializable {

    @JsonProperty("FoodIntakeCommmend")
    private CommendRsp foodIntakeCommmend;

    @JsonProperty("SubsClasses")
    private Map<String, String> subsClasses;

    @JsonProperty("FoodSubsList")
    private List<FoodSubsList> foodSubsList;

}
