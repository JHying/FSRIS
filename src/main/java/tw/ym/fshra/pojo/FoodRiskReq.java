package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
public class FoodRiskReq implements Serializable {

    @JsonProperty("Subsid")
    @NotBlank(message = "Subsid 不可為空")
    private String subsid;

    @JsonProperty("Foodid")
    @NotBlank(message = "Foodid 不可為空")
    private String foodid;

    @JsonProperty("BodyWeight")
    @NotNull(message = "BodyWeight 不可為空")
    private Double bodyWeight;

    @JsonProperty("Age")
    private Integer age;

    @JsonProperty("Consume")
    private Double consume;

    @JsonProperty("Gender")
    @NotBlank(message = "Gender 不可為空")
    private String gender;

}
