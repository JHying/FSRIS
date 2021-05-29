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
public class RiskRankRsp implements Serializable {

    @JsonProperty("ScoreDetailList")
    private List<RiskScoreDetail> riskScoreList;

    @JsonProperty("FoodPassList")
    private List<FoodFailDetail> foodFailList;

}
