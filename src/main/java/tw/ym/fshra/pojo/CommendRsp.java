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
public class CommendRsp implements Serializable {

    @JsonProperty("Subsid")
    private String subsid;

    @JsonProperty("SubsName")
    private String subsName;

    @JsonProperty("IntakeCommmend")
    private Double intakeCommmend;

}
