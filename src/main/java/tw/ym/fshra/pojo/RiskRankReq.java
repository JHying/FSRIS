package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
public class RiskRankReq implements Serializable {

    @JsonProperty("SmainId")
    @NotBlank(message = "smainId 不得為空")
    private String smainId;

}
