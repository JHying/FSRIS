package tw.ym.fshra.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.ym.fshra.config.JsonDateDeseriConfig;

import java.io.Serializable;
import java.util.Date;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntroRsp implements Serializable {



    @JsonProperty("SubName")
    private String subsName;

    @JsonProperty("SubsNameCh")
    private String subsNameCh;

    @JsonProperty("Casrn")
    private String casrn;

    @JsonProperty("Intro")
    private String intro;

    @JsonProperty("IntroRef")
    private String introRef;

    @JsonProperty("UpdateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = JsonDateDeseriConfig.class)
    private Date updateTime;

//    @NotBlank(message = "Tn2_Parking_Plate 不能為空")
//    @Pattern(regexp = "(([A-Z]{2}))")

}
