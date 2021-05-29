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
public class FoodConcContent implements Serializable {

    @JsonProperty("Concid")
    private String concid;

    @JsonProperty("SubsName")
    private String subsName;

    @JsonProperty("SubsConc")
    private String subsConc;

    @JsonProperty("ConcRef")
    private String concRef;

    @JsonProperty("ConcDTtime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = JsonDateDeseriConfig.class)
    private Date concDTtime;

}
