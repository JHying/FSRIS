package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Data
@NoArgsConstructor
public class FoodSubsList implements Serializable {

    @JsonProperty("SubsClassName")
    private String subsClassName;

    @JsonProperty("FoodConcList")
    private List<FoodConcContent> foodConcList;

    @JsonProperty("FoodSubsDetailList")
    private List<FoodSubsDetail> foodSubsDetailList;

}
