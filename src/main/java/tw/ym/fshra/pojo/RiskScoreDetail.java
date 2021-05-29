package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskScoreDetail implements Serializable {

    @JsonProperty("SubsNameCh")
    private String subsNameCh;

    @JsonProperty("SubsName")
    private String subsName;

    @JsonProperty("ScoreA")
    private Integer scoreA;

    @JsonProperty("ScoreB")
    private Integer scoreB;

    @JsonProperty("ScoreC")
    private Integer scoreC;

    @JsonProperty("ScoreD")
    private Integer scoreD;

    @JsonProperty("Total")
    private Integer total;

}
