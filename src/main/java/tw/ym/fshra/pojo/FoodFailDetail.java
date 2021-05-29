package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodFailDetail implements Serializable {

    @JsonProperty("FoodNameCh")
    private String foodNameCh;

    @JsonProperty("SubsNameCh")
    private String subsNameCh;

    @JsonProperty("SubsName")
    private String subsName;

    @JsonProperty("FailRate")
    private Double failRate;

    @JsonProperty("FoodConcList")
    private List<FoodFailConc> foodConcList;

}
