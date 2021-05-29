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
public class FoodSubsDetail implements Serializable {

    @JsonProperty("SubsName")
    private String subsName;

    @JsonProperty("SubsConc")
    private String subsConc;

    @JsonProperty("ConcCount")
    private Integer concCount;

    @JsonProperty("ConcDate")
    private String concDate;

    @JsonProperty("SubsMrl")
    private String subsMrl;

    @JsonProperty("SubsMrlRef")
    private String subsMrlRef;

    @JsonProperty("MrlUpdateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = JsonDateDeseriConfig.class)
    private Date mrlUpdatedTime;

    @JsonProperty("Subsid")
    private String subsid;

    @JsonProperty("SubsIntro")
    private String subsIntro;

    @JsonProperty("SubsIntroRef")
    private String subsIntroRef;

}
