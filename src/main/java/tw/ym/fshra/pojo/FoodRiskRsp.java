package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
public class FoodRiskRsp implements Serializable {

    @JsonProperty("SubsATDIRfD")
    private Double subsATDIRfD;

    @JsonProperty("SubsATDIRfDRef")
    private String subsATDIRfDRef;

    @JsonProperty("ConsumeMean")
    private Double consumeMean;

    @JsonProperty("ConcMean")
    private Double concMean;

    @JsonProperty("Mrl")
    private Double mrl;

    @JsonProperty("IntakeCommmend")
    private Double intakeCommmend;

    @JsonProperty("PersonalRisk")
    private Double personalRisk;

    @JsonProperty("RegulationRisk")
    private Double regulationRisk;

}
