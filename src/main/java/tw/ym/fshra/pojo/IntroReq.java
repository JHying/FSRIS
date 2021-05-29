package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
public class IntroReq implements Serializable {

    @JsonProperty("Subsid")
//    @NotBlank(message = "Subsid 不得為空")
    private String subsid;

}
